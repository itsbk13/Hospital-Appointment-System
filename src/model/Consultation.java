package src.model;

import java.time.LocalDate;

public class Consultation {
    private final int consultationId;
    private final int appointmentId;
    private String doctorNotes;
    private String prescription;
    private LocalDate followUpDate;

    public Consultation(int consultationId, int appointmentId, String doctorNotes, String prescription, LocalDate followUpDate) {
        this.consultationId = consultationId;
        this.appointmentId = appointmentId;
        this.doctorNotes = doctorNotes;
        this.prescription = prescription;
        this.followUpDate = followUpDate;
    }

    public int getConsultationId() { return consultationId; }
    public int getAppointmentId() { return appointmentId; }
    public String getDoctorNotes() { return doctorNotes; }
    public String getPrescription() { return prescription; }
    public LocalDate getFollowUpDate() { return followUpDate; }

    @Override
    public String toString() {
        return String.format("Consultation[%d] Appointment:%d | Notes: %s | Prescription: %s | FollowUp: %s",
                consultationId, appointmentId, doctorNotes, prescription, followUpDate);
    }
}