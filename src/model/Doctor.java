package src.model;

import java.util.*;

public class Doctor {
    private final int doctorId;
    private String name;
    private String specialty;
    private String department;
    private List<Slot> slots;

    public Doctor(int doctorId, String name, String specialty, String department) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialty = specialty;
        this.department = department;
        this.slots = new ArrayList<>();
    }

    public int getDoctorId() { return doctorId; }
    public String getName() { return name; }
    public String getSpecialty() { return specialty; }
    public String getDepartment() { return department; }
    public List<Slot> getSlots() { return slots; }
    public void addSlot(Slot slot) { slots.add(slot); }

    @Override
    public String toString() {
        return String.format("Doctor[%d] Dr.%s | %s | %s", doctorId, name, specialty, department);
    }
}