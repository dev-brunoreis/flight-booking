package com.flightbooking.service;

import com.flightbooking.model.Flight;
import com.flightbooking.model.Passenger;
import com.flightbooking.repository.FlightRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável por gerenciar as operações relacionadas aos voos.
 * Implementa a camada de serviço do padrão Repository.
 */
public class FlightService {
    private final FlightRepository repository;

    public FlightService() {
        this.repository = FlightRepository.getInstance();
    }

    /**
     * Cria um novo voo no sistema.
     * @param id Identificador único do voo
     * @param date Data do voo
     * @param time Horário do voo
     * @param origin Origem do voo
     * @param destination Destino do voo
     * @param totalSeats Número total de assentos
     */
    public void createFlight(int id, LocalDate date, LocalTime time, String origin, String destination, int totalSeats) {
        Flight flight = new Flight(id, date, time, origin, destination, totalSeats);
        repository.addFlight(flight);
    }

    /**
     * Retorna todos os voos cadastrados no sistema.
     * @return Lista de voos
     */
    public List<Flight> getAllFlights() {
        return repository.getAllFlights();
    }

    /**
     * Busca um voo pelo seu identificador.
     * @param id Identificador do voo
     * @return Optional contendo o voo, se encontrado
     */
    public Optional<Flight> getFlightById(int id) {
        return repository.getFlightById(id);
    }

    /**
     * Realiza uma reserva em um voo.
     * @param flightId Identificador do voo
     * @param seatNumber Número do assento
     * @param passenger Dados do passageiro
     * @return true se a reserva foi realizada com sucesso
     */
    public boolean makeReservation(int flightId, int seatNumber, Passenger passenger) {
        Optional<Flight> flight = repository.getFlightById(flightId);
        return flight.map(f -> f.reserveSeat(seatNumber, passenger)).orElse(false);
    }

    /**
     * Cancela uma reserva em um voo.
     * @param flightId Identificador do voo
     * @param seatNumber Número do assento
     * @param document Documento do passageiro
     * @return true se a reserva foi cancelada com sucesso
     */
    public boolean cancelReservation(int flightId, int seatNumber, String document) {
        Optional<Flight> flight = repository.getFlightById(flightId);
        return flight.map(f -> {
            var passenger = f.getReservedSeats().get(seatNumber);
            if (passenger != null && passenger.getDocument().equals(document)) {
                return f.cancelReservation(seatNumber);
            }
            return false;
        }).orElse(false);
    }

    /**
     * Busca voos por origem.
     * @param origin Cidade de origem
     * @return Lista de voos encontrados
     */
    public List<Flight> searchByOrigin(String origin) {
        return repository.searchByOrigin(origin);
    }

    /**
     * Busca voos por destino.
     * @param destination Cidade de destino
     * @return Lista de voos encontrados
     */
    public List<Flight> searchByDestination(String destination) {
        return repository.searchByDestination(destination);
    }

    /**
     * Busca voos por data.
     * @param date Data do voo
     * @return Lista de voos encontrados
     */
    public List<Flight> searchByDate(LocalDate date) {
        return repository.searchByDate(date);
    }

    /**
     * Remove um voo do sistema.
     * @param id Identificador do voo
     * @return true se o voo foi removido com sucesso
     */
    public boolean removeFlight(int id) {
        return repository.removeFlight(id);
    }
} 