package src.model;

import java.time.LocalDateTime;

public class Slot {
    private final int slotId;
    private final int doctorId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private boolean isBooked;
    private final boolean isEmergency;

    public Slot(int slotId, int doctorId, LocalDateTime startTime, LocalDateTime endTime, boolean isEmergency) {
        this.slotId = slotId;
        this.doctorId = doctorId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isBooked = false;
        this.isEmergency = isEmergency;
    }

    public int getSlotId() { return slotId; }
    public int getDoctorId() { return doctorId; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public boolean isBooked() { return isBooked; }
    public boolean isEmergency() { return isEmergency; }
    public void book() { this.isBooked = true; }
    public void release() { this.isBooked = false; }

    @Override
    public String toString() {
        return String.format("Slot[%d] %s to %s | Booked: %s | Emergency: %s",
                slotId, startTime, endTime, isBooked, isEmergency);
    }
}