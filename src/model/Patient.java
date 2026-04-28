package src.model;

import java.util.*;

public class Patient {
    private final int patientId;
    private String name;
    private int age;
    private String contact;
    private List<String> medicalHistory;

    public Patient(int patientId, String name, int age, String contact) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.medicalHistory = new ArrayList<>();
    }

    public int getPatientId() { return patientId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getContact() { return contact; }
    public List<String> getMedicalHistory() { return medicalHistory; }
    public void addMedicalHistory(String record) { medicalHistory.add(record); }

    @Override
    public String toString() {
        return String.format("Patient[%d] %s | Age: %d | Contact: %s", patientId, name, age, contact);
    }
}