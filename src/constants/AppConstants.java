package src.constants;

public final class AppConstants {
    public static final int MAX_APPOINTMENTS_PER_DAY = 20;
    public static final int MAX_RESCHEDULES = 2;
    public static final int EMERGENCY_SLOTS = 3;
    public static final int ONLINE_BOOKING_LIMIT = MAX_APPOINTMENTS_PER_DAY - EMERGENCY_SLOTS;
    public static final int LATE_CANCEL_MINUTES = 60;

    private AppConstants() {}
}