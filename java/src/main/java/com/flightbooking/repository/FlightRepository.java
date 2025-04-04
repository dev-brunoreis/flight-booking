package com.flightbooking.repository;

import com.flightbooking.model.Flight;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightRepository {
    private static FlightRepository instance;
    private final List<Flight> flights;

    private FlightRepository() {
        this.flights = new ArrayList<>();
    }

    public static FlightRepository getInstance() {
        if (instance == null) {
            instance = new FlightRepository();
        }
        return instance;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public List<Flight> getAllFlights() {
        return new ArrayList<>(flights);
    }

    public Optional<Flight> getFlightById(int id) {
        return flights.stream()
                .filter(flight -> flight.getId() == id)
                .findFirst();
    }

    public List<Flight> searchByOrigin(String origin) {
        return flights.stream()
                .filter(flight -> flight.getOrigin().toLowerCase().contains(origin.toLowerCase()))
                .toList();
    }

    public List<Flight> searchByDestination(String destination) {
        return flights.stream()
                .filter(flight -> flight.getDestination().toLowerCase().contains(destination.toLowerCase()))
                .toList();
    }

    public List<Flight> searchByDate(LocalDate date) {
        return flights.stream()
                .filter(flight -> flight.getDate().equals(date))
                .toList();
    }

    public boolean removeFlight(int id) {
        return flights.removeIf(flight -> flight.getId() == id);
    }
} 