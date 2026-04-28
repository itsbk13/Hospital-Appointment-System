package src;

import src.model.*;
import src.repository.*;
import src.service.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final PatientRepository patientRepo = new PatientRepository();
    private static final DoctorRepository doctorRepo = new DoctorRepository();
    private static final SlotRepository slotRepo = new SlotRepository();
    private static final AppointmentRepository appointmentRepo = new AppointmentRepository();

    private static final PatientService patientService = new PatientService(patientRepo);
    private static final DoctorService doctorService = new DoctorService(doctorRepo, slotRepo);
    private static final BookingService bookingService = new BookingService(appointmentRepo, slotRepo, patientService);
    private static final ConsultationService consultationService = new ConsultationService(appointmentRepo);

    private static final Hospital hospital = new Hospital(1, "City Clinic");
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedData();
        while (true) {
            printMenu();
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> registerPatient();
                case 2 -> searchDoctors();
                case 3 -> viewAvailableSlots();
                case 4 -> bookAppointment();
                case 5 -> cancelAppointment();
                case 6 -> rescheduleAppointment();
                case 7 -> recordConsultation();
                case 8 -> viewDoctorSchedule();
                case 0 -> { System.out.println("Exiting..."); return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== City Clinic =====");
        System.out.println("1. Register Patient");
        System.out.println("2. Search Doctor by Specialty");
        System.out.println("3. View Available Slots");
        System.out.println("4. Book Appointment");
        System.out.println("5. Cancel Appointment");
        System.out.println("6. Reschedule Appointment");
        System.out.println("7. Record Consultation");
        System.out.println("8. View Doctor Schedule");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    private static void registerPatient() {
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Age: "); int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Contact: "); String contact = scanner.nextLine();
        Patient p = patientService.register(name, age, contact);
        hospital.addAppointment(null);
        System.out.println("Registered: " + p);
    }

    private static void searchDoctors() {
        System.out.print("Specialty: ");
        String specialty = scanner.nextLine();
        try {
            doctorService.searchBySpecialty(specialty).forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewAvailableSlots() {
        System.out.print("Doctor ID: "); int doctorId = Integer.parseInt(scanner.nextLine());
        System.out.print("Date (YYYY-MM-DD): "); LocalDate date = LocalDate.parse(scanner.nextLine());
        List<Slot> slots = doctorService.getAvailableSlots(doctorId, date);
        if (slots.isEmpty()) System.out.println("No available slots.");
        else slots.forEach(System.out::println);
    }

    private static void bookAppointment() {
        System.out.print("Patient ID: "); int patientId = Integer.parseInt(scanner.nextLine());
        System.out.print("Doctor ID: "); int doctorId = Integer.parseInt(scanner.nextLine());
        System.out.print("Slot ID: "); int slotId = Integer.parseInt(scanner.nextLine());
        System.out.print("Date (YYYY-MM-DD): "); LocalDate date = LocalDate.parse(scanner.nextLine());
        try {
            Appointment a = bookingService.book(patientId, doctorId, slotId, date);
            hospital.addAppointment(a);
            System.out.println("Booked: " + a);
            System.out.println("Reminder will be sent 24 hours before: " + date);
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    private static void cancelAppointment() {
        System.out.print("Appointment ID: "); int id = Integer.parseInt(scanner.nextLine());
        try {
            bookingService.cancel(id);
            System.out.println("Appointment cancelled successfully.");
        } catch (Exception e) {
            System.out.println("Cancellation failed: " + e.getMessage());
        }
    }

    private static void rescheduleAppointment() {
        System.out.print("Appointment ID: "); int id = Integer.parseInt(scanner.nextLine());
        System.out.print("New Slot ID: "); int newSlotId = Integer.parseInt(scanner.nextLine());
        System.out.print("New Date (YYYY-MM-DD): "); LocalDate newDate = LocalDate.parse(scanner.nextLine());
        try {
            Appointment a = bookingService.reschedule(id, newSlotId, newDate);
            System.out.println("Rescheduled: " + a);
        } catch (Exception e) {
            System.out.println("Reschedule failed: " + e.getMessage());
        }
    }

    private static void recordConsultation() {
        System.out.print("Appointment ID: "); int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Doctor Notes: "); String notes = scanner.nextLine();
        System.out.print("Prescription: "); String prescription = scanner.nextLine();
        System.out.print("Follow-up Date (YYYY-MM-DD): "); LocalDate followUp = LocalDate.parse(scanner.nextLine());
        try {
            Consultation c = consultationService.record(id, notes, prescription, followUp);
            System.out.println("Recorded: " + c);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewDoctorSchedule() {
        System.out.print("Doctor ID: "); int doctorId = Integer.parseInt(scanner.nextLine());
        System.out.print("Date (YYYY-MM-DD): "); LocalDate date = LocalDate.parse(scanner.nextLine());
        appointmentRepo.findByDoctorAndDate(doctorId, date).forEach(System.out::println);
    }

    private static void seedData() {
        Doctor d1 = doctorService.register("Kumar", "Cardiology", "Cardiac");
        Doctor d2 = doctorService.register("Priya", "Neurology", "Neuro");

        hospital.addDoctor(d1);
        hospital.addDoctor(d2);

        LocalDateTime base = LocalDateTime.now().plusDays(1).withHour(9).withMinute(0);
        for (int i = 0; i < 5; i++) {
            Slot slot = new Slot(slotRepo.nextId(), d1.getDoctorId(),
                    base.plusHours(i), base.plusHours(i).plusMinutes(30), false);
            slotRepo.save(slot);
            d1.addSlot(slot);
        }
        System.out.println("Hospital ready: " + hospital);
    }
}