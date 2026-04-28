package src.model;

import java.util.*;
import java.util.stream.Collectors;

public class Hospital {
    private final int hospitalId;
    private String name;
    private List<Doctor> doctors;
    private List<Appointment> appointments;

    public Hospital(int hospitalId, String name) {
        this.hospitalId = hospitalId;
        this.name = name;
        this.doctors = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }

    public int getHospitalId() { return hospitalId; }
    public String getName() { return name; }
    public List<Doctor> getDoctors() { return doctors; }
    public List<Appointment> getAppointments() { return appointments; }

    public void addDoctor(Doctor doctor) {
        if (!doctors.contains(doctor)) doctors.add(doctor);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        return doctors.stream()
                .filter(d -> d.getSpecialty().equalsIgnoreCase(specialty))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("Hospital[%d] %s | Doctors: %d",
                hospitalId, name, doctors.size());
    }
}