package src.service;

import src.model.Doctor;
import src.model.Slot;
import src.repository.DoctorRepository;
import src.repository.SlotRepository;
import java.time.LocalDate;
import java.util.*;

public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final SlotRepository slotRepository;

    public DoctorService(DoctorRepository doctorRepository, SlotRepository slotRepository) {
        this.doctorRepository = doctorRepository;
        this.slotRepository = slotRepository;
    }

    public Doctor register(String name, String specialty, String department) {
        Doctor doctor = new Doctor(doctorRepository.nextId(), name, specialty, department);
        return doctorRepository.save(doctor);
    }

    public Doctor findById(int doctorId) {
        return doctorRepository.findById(doctorId);
    }

    public List<Doctor> searchBySpecialty(String specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }

    public List<Doctor> searchByName(String name) {
        return doctorRepository.findByName(name);
    }

    public List<Slot> getAvailableSlots(int doctorId, LocalDate date) {
        return slotRepository.findAvailableByDoctorAndDate(doctorId, date);
    }
}