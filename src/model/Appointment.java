package src.model;

import java.time.LocalDate;

public class Appointment {

    public enum Status { CONFIRMED, CANCELLED, COMPLETED, RESCHEDULED }

    private final int appointmentId;
    private final int patientId;
    private final int doctorId;
    private int slotId;
    private LocalDate date;
    private Status status;
    private int rescheduleCount;

    public Appointment(int appointmentId, int patientId, int doctorId, int slotId, LocalDate date) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.slotId = slotId;
        this.date = date;
        this.status = Status.CONFIRMED;
        this.rescheduleCount = 0;
    }

    public int getAppointmentId() { return appointmentId; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public int getSlotId() { return slotId; }
    public LocalDate getDate() { return date; }
    public Status getStatus() { return status; }
    public int getRescheduleCount() { return rescheduleCount; }

    public void cancel() { this.status = Status.CANCELLED; }
    public void complete() { this.status = Status.COMPLETED; }
    public void reschedule(int newSlotId, LocalDate newDate) {
        this.slotId = newSlotId;
        this.date = newDate;
        this.status = Status.RESCHEDULED;
        this.rescheduleCount++;
    }

    @Override
    public String toString() {
        return String.format("Appointment[%d] Patient:%d Doctor:%d Date:%s Status:%s Reschedules:%d",
                appointmentId, patientId, doctorId, date, status, rescheduleCount);
    }
}