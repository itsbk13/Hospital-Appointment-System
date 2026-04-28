package src.repository;

import src.exception.ResourceNotFoundException;
import src.model.Appointment;
import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class AppointmentRepository {
    private final Map<Integer, Appointment> store = new HashMap<>();
    private int idCounter = 1;

    public Appointment save(Appointment appointment) {
        store.put(appointment.getAppointmentId(), appointment);
        return appointment;
    }

    public Appointment findById(int appointmentId) {
        Appointment appt = store.get(appointmentId);
        if (appt == null) throw new ResourceNotFoundException("Appointment not found: " + appointmentId);
        return appt;
    }

    public boolean existsByPatientDoctorDate(int patientId, int doctorId, LocalDate date) {
        return store.values().stream()
                .anyMatch(a -> a.getPatientId() == patientId
                        && a.getDoctorId() == doctorId
                        && a.getDate().equals(date)
                        && a.getStatus() == Appointment.Status.CONFIRMED);
    }

    public List<Appointment> findByDoctorAndDate(int doctorId, LocalDate date) {
        return store.values().stream()
                .filter(a -> a.getDoctorId() == doctorId && a.getDate().equals(date))
                .collect(Collectors.toList());
    }

    public int nextId() { return idCounter++; }
}