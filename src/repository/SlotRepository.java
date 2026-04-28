package src.repository;

import src.exception.BookingException;
import src.model.Slot;
import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class SlotRepository {
    private final Map<Integer, Slot> store = new HashMap<>();
    private int idCounter = 1;

    public Slot save(Slot slot) {
        store.put(slot.getSlotId(), slot);
        return slot;
    }

    public Slot findById(int slotId) {
        Slot slot = store.get(slotId);
        if (slot == null) throw new BookingException("Slot not found: " + slotId);
        return slot;
    }

    public List<Slot> findAvailableByDoctorAndDate(int doctorId, LocalDate date) {
        return store.values().stream()
                .filter(s -> s.getDoctorId() == doctorId
                        && s.getStartTime().toLocalDate().equals(date)
                        && !s.isBooked()
                        && !s.isEmergency())
                .collect(Collectors.toList());
    }

    public long countBookedByDoctorAndDate(int doctorId, LocalDate date) {
        return store.values().stream()
                .filter(s -> s.getDoctorId() == doctorId
                        && s.getStartTime().toLocalDate().equals(date)
                        && s.isBooked())
                .count();
    }

    public int nextId() { return idCounter++; }
}