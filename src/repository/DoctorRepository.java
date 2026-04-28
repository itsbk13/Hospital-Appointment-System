package src.repository;

import src.exception.ResourceNotFoundException;
import src.model.Doctor;
import java.util.*;
import java.util.stream.Collectors;

public class DoctorRepository {
    private final Map<Integer, Doctor> store = new HashMap<>();
    private int idCounter = 1;

    public Doctor save(Doctor doctor) {
        store.put(doctor.getDoctorId(), doctor);
        return doctor;
    }

    public Doctor findById(int doctorId) {
        Doctor doctor = store.get(doctorId);
        if (doctor == null) throw new ResourceNotFoundException("Doctor not found: " + doctorId);
        return doctor;
    }

    public List<Doctor> findBySpecialty(String specialty) {
        List<Doctor> result = store.values().stream()
                .filter(d -> d.getSpecialty().equalsIgnoreCase(specialty))
                .collect(Collectors.toList());
        if (result.isEmpty()) throw new ResourceNotFoundException("No doctor found for specialty: " + specialty);
        return result;
    }

    public List<Doctor> findByName(String name) {
        return store.values().stream()
                .filter(d -> d.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public int nextId() { return idCounter++; }
}