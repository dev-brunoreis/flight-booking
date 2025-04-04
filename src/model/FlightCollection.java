package com.flightbooking.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightCollection {
    private static FlightCollection instance;
    private final List<Flight> flights;

    private FlightCollection() {
        this.flights = new ArrayList<>();
    }

    public static FlightCollection getInstance() {
        if (instance == null) {
            instance = new FlightCollection();
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

    public boolean removeFlight(int id) {
        return flights.removeIf(flight -> flight.getId() == id);
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
} 