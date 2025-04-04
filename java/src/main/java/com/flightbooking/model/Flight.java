package com.flightbooking.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.time.format.DateTimeFormatter;
/**
 * Classe que representa um voo no sistema.
 * Implementa o padrão Value Object, sendo imutável após a criação.
 */
public class Flight {
    private final int id;
    private final LocalDate date;
    private final LocalTime time;   
    private final String origin;
    private final String destination;
    private final int totalSeats;
    private final Map<Integer, Passenger> reservedSeats;
    private List<Integer> availableSeatsCache;
    private boolean cacheInvalid;

    /**
     * Construtor que inicializa um novo voo.
     * @param id Identificador único do voo
     * @param date Data do voo
     * @param time Horário do voo
     * @param origin Cidade de origem
     * @param destination Cidade de destino
     * @param totalSeats Número total de assentos
     */
    public Flight(int id, LocalDate date, LocalTime time, String origin, String destination, int totalSeats) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.origin = origin;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.reservedSeats = new HashMap<>();
        this.availableSeatsCache = new ArrayList<>();
        this.cacheInvalid = true;
    }

    // Getters básicos
    public int getId() { return id; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }

    /**
     * Retorna uma lista de assentos disponíveis.
     * Utiliza cache para melhor performance.
     * @return Lista de números dos assentos disponíveis
     */
    public List<Integer> getAvailableSeats() {
        if (cacheInvalid) {
            availableSeatsCache = new ArrayList<>(totalSeats);
            for (int i = 1; i <= totalSeats; i++) {
                if (!reservedSeats.containsKey(i)) {
                    availableSeatsCache.add(i);
                }
            }
            availableSeatsCache = Collections.unmodifiableList(availableSeatsCache);
            cacheInvalid = false;
        }
        return availableSeatsCache;
    }

    /**
     * Reserva um assento para um passageiro.
     * @param seatNumber Número do assento
     * @param passenger Passageiro
     * @return true se a reserva foi bem-sucedida, false caso contrário
     * @throws IllegalArgumentException se o número do assento for inválido
     */
    public boolean reserveSeat(int seatNumber, Passenger passenger) {
        if (seatNumber < 1 || seatNumber > totalSeats) {
            throw new IllegalArgumentException("Número de assento inválido");
        }
        if (reservedSeats.containsKey(seatNumber)) {
            return false;
        }
        reservedSeats.put(seatNumber, passenger);
        cacheInvalid = true;
        return true;
    }

    /**
     * Cancela uma reserva de assento.
     * @param seatNumber Número do assento
     * @return true se o cancelamento foi bem-sucedido, false caso contrário
     */
    public boolean cancelReservation(int seatNumber) {
        boolean removed = reservedSeats.remove(seatNumber) != null;
        if (removed) {
            cacheInvalid = true;
        }
        return removed;
    }

    /**
     * Retorna uma cópia imutável das reservas de assentos.
     * @return Map com os assentos reservados e seus passageiros
     */
    public Map<Integer, Passenger> getReservedSeats() {
        return Collections.unmodifiableMap(new HashMap<>(reservedSeats));
    }

    @Override
    public String toString() {
        return String.format("[%d]: %s -> %s (%s %s) - %d assentos disponíveis",
                id, origin, destination, date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), time.format(DateTimeFormatter.ofPattern("HH:mm")), getAvailableSeats().size());
    }
} 