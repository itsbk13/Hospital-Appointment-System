package src.service;

import src.exception.ResourceNotFoundException;
import src.model.Appointment;
import src.model.Consultation;
import src.repository.AppointmentRepository;
import java.util.*;
import java.time.LocalDate;


public class ConsultationService {
    private final AppointmentRepository appointmentRepository;
    private final Map<Integer, Consultation> store = new HashMap<>();
    private int idCounter = 1;

    public ConsultationService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Consultation record(int appointmentId, String notes, String prescription, LocalDate followUpDate) {
        Appointment appointment = appointmentRepository.findById(appointmentId);
        appointment.complete();
        Consultation consultation = new Consultation(idCounter++, appointmentId, notes, prescription, followUpDate);
        store.put(consultation.getConsultationId(), consultation);
        return consultation;
    }

    public Consultation findByAppointmentId(int appointmentId) {
        return store.values().stream()
                .filter(c -> c.getAppointmentId() == appointmentId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No consultation for appointment: " + appointmentId));
    }
}