package src.repository;

import src.exception.ResourceNotFoundException;
import src.model.Patient;
import java.util.*;

public class PatientRepository {
    private final Map<Integer, Patient> store = new HashMap<>();
    private int idCounter = 1;

    public Patient save(Patient patient) {
        store.put(patient.getPatientId(), patient);
        return patient;
    }

    public Patient findById(int patientId) {
        Patient patient = store.get(patientId);
        if (patient == null) throw new ResourceNotFoundException("Patient not found: " + patientId);
        return patient;
    }

    public int nextId() { return idCounter++; }
}