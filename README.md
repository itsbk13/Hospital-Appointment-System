# Hospital Appointment System

A console-based Java application to handle patient registration, doctor scheduling, appointment booking, cancellations, and consultation records.

---

## Problem Statement

Build a system where patients can register, search for doctors by specialty, book available slots, reschedule or cancel appointments, and doctors can record consultation notes and followed business rules like booking limits, reschedule limits, and cancellation fee warnings.

---

## Approach

Followed a layered architecture — Model → Repository → Service

- Models hold data classes
- Repositories handle in-memory storage using HashMap
- Services contain all business logic and validations
- Main is responsible for the console menu by connecting everything together

Business rules applied:
- Max 17 online bookable slots per doctor per day (3 reserved for emergency)
- Emergency slots are blocked from online booking
- Patient cannot book the same doctor twice on the same day
- Max 2 reschedules per appointment
- Late cancellation warning if cancelled close to appointment time

---

## Steps to Run

1. Open the project folder
2. Run `Main.java`
3. Two doctors and 5 slots for next day are auto-loaded on start
4. Use menu options 1–8 to test

---

## Sample Flow
1. Register a patient
2. Search doctor by specialty (Eg: Cardiology)
3. View available slots for Doctor ID 1
4. Book appointment
5. Cancel appointment
6. Reschedule appointment
7. Record consultation after visit (By Doctor)
8. View doctor schedule for the day

---

## Future Enhancements

1. Using a persistent database to avoid losing data and prevent hardcoded data aswell
2. Enhanced exception handling with exact error message in a readable format
3. Building GUI for easier user navigation and access
