package src.service;

import src.constants.AppConstants;
import src.exception.BookingException;
import src.model.Appointment;
import src.model.Slot;
import src.repository.AppointmentRepository;
import src.repository.SlotRepository;
import java.time.LocalDate;

public class BookingService {
    private final AppointmentRepository appointmentRepository;
    private final SlotRepository slotRepository;
    private final PatientService patientService;

    public BookingService(AppointmentRepository appointmentRepository,
                          SlotRepository slotRepository,
                          PatientService patientService) {
        this.appointmentRepository = appointmentRepository;
        this.slotRepository = slotRepository;
        this.patientService = patientService;
    }

    public Appointment book(int patientId, int doctorId, int slotId, LocalDate date) {
        patientService.findById(patientId);

        if (appointmentRepository.existsByPatientDoctorDate(patientId, doctorId, date))
            throw new BookingException("Patient already has an appointment with this doctor on " + date);

        if (slotRepository.countBookedByDoctorAndDate(doctorId, date) >= AppConstants.ONLINE_BOOKING_LIMIT)
            throw new BookingException("Doctor has reached max appointments for " + date);

        Slot slot = slotRepository.findById(slotId);
        if (slot.isBooked()) throw new BookingException("Slot " + slotId + " is already booked.");
        if (slot.isEmergency()) throw new BookingException("Emergency slots cannot be booked online.");

        slot.book();
        Appointment appointment = new Appointment(
                appointmentRepository.nextId(), patientId, doctorId, slotId, date);
        return appointmentRepository.save(appointment);
    }

    public void cancel(int appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId);

        if (appointment.getStatus() == Appointment.Status.CANCELLED)
            throw new BookingException("Appointment is already cancelled.");

        Slot slot = slotRepository.findById(appointment.getSlotId());
        slot.release();
        appointment.cancel();
        System.out.println("Appointment cancelled. Note: Late cancellation fee may apply if within 1 hour.");
    }

    public Appointment reschedule(int appointmentId, int newSlotId, LocalDate newDate) {
        Appointment appointment = appointmentRepository.findById(appointmentId);

        if (appointment.getRescheduleCount() >= AppConstants.MAX_RESCHEDULES)
            throw new BookingException("Max reschedule limit of " + AppConstants.MAX_RESCHEDULES + " reached.");

        Slot oldSlot = slotRepository.findById(appointment.getSlotId());
        oldSlot.release();

        Slot newSlot = slotRepository.findById(newSlotId);
        if (newSlot.isBooked()) throw new BookingException("New slot " + newSlotId + " is already booked.");
        newSlot.book();

        appointment.reschedule(newSlotId, newDate);
        return appointment;
    }
}