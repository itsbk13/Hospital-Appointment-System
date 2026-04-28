package src.service;

import src.model.Patient;
import src.repository.PatientRepository;

public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient register(String name, int age, String contact) {
        Patient patient = new Patient(patientRepository.nextId(), name, age, contact);
        return patientRepository.save(patient);
    }

    public Patient findById(int patientId) {
        return patientRepository.findById(patientId);
    }
}