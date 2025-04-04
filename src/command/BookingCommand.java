package com.flightbooking.command;

import com.flightbooking.api.Command;
import com.flightbooking.model.Flight;
import com.flightbooking.model.Passenger;
import com.flightbooking.service.FlightService;
import com.flightbooking.util.InputReader;
import com.flightbooking.util.InputValidator;
import com.flightbooking.util.UIHelper;
import java.util.List;
import java.util.Map;

/**
 * Comando para gerenciar as reservas de voos.
 * Implementa o padrão Command para encapsular as operações de reserva.
 */
public class BookingCommand implements Command {
    private final FlightService flightService;
    private final InputReader input;

    public BookingCommand() {
        this.flightService = new FlightService();
        this.input = new InputReader();
    }

    @Override
    public void execute() {
        while (true) {
            UIHelper.clearScreen();
            UIHelper.displayMenu(
                "Gerenciamento de Reservas",
                "1. Fazer Reserva",
                "2. Listar Minhas Reservas",
                "3. Cancelar Reserva",
                "4. Voltar"
            );

            int option = input.readInt("Escolha uma opção: ");
            
            switch (option) {
                case 1 -> makeReservation();
                case 2 -> listReservations();
                case 3 -> cancelReservation();
                case 4 -> {
                    return;
                }
                default -> {
                    UIHelper.displayError("Opção inválida!");
                    UIHelper.pause();
                }
            }
        }
    }

    private void makeReservation() {
        UIHelper.clearScreen();
        List<Flight> flights = flightService.getAllFlights();
        if (flights.isEmpty()) {
            UIHelper.displayError("Não há voos disponíveis.");
            UIHelper.pause();
            return;
        }

        UIHelper.displayMenu("Voos Disponíveis");
        System.out.println("\nDetalhes dos Voos:");
        System.out.println("════════════════════════════════════════════════════════════════════════════════");
        for (Flight flight : flights) {
            System.out.println("\n" + flight);
            System.out.println("Assentos disponíveis: " + flight.getAvailableSeats());
            System.out.println("────────────────────────────────────────────────────────────────────────────");
        }

        int flightId = input.readInt("\nNúmero do voo desejado: ");
        if (!InputValidator.isValidFlightId(flightId)) {
            UIHelper.displayError(InputValidator.getFlightIdErrorMessage());
            UIHelper.pause();
            return;
        }
        
        var flight = flightService.getFlightById(flightId);
        if (flight.isEmpty()) {
            UIHelper.displayError("Voo não encontrado.");
            UIHelper.pause();
            return;
        }

        List<Integer> availableSeats = flight.get().getAvailableSeats();
        if (availableSeats.isEmpty()) {
            UIHelper.displayError("Não há assentos disponíveis neste voo.");
            UIHelper.pause();
            return;
        }

        System.out.println("\nAssentos disponíveis: " + availableSeats);
        int seatNumber;
        boolean validSeat = false;
        do {
            seatNumber = input.readInt("Número do assento desejado: ");
            if (!availableSeats.contains(seatNumber)) {
                UIHelper.displayError("Assento inválido ou não disponível. Escolha um dos assentos listados acima.");
            } else {
                validSeat = true;
            }
        } while (!validSeat);

        UIHelper.displayMenu("Dados do Passageiro");
        
        // Validação do ID do passageiro
        int passengerId;
        do {
            passengerId = input.readInt("ID do passageiro: ");
            if (!InputValidator.isValidPassengerId(passengerId)) {
                UIHelper.displayError(InputValidator.getPassengerIdErrorMessage());
            }
        } while (!InputValidator.isValidPassengerId(passengerId));
        
        // Validação do nome do passageiro
        String name;
        do {
            name = input.readString("Nome: ");
            if (!InputValidator.isValidPassengerName(name)) {
                UIHelper.displayError(InputValidator.getPassengerNameErrorMessage());
            }
        } while (!InputValidator.isValidPassengerName(name));
        
        // Validação do documento do passageiro
        String document;
        do {
            document = input.readString("Documento: ");
            if (!InputValidator.isValidPassengerDocument(document)) {
                UIHelper.displayError(InputValidator.getPassengerDocumentErrorMessage());
            }
        } while (!InputValidator.isValidPassengerDocument(document));
        
        // Validação do telefone do passageiro
        String phone;
        do {
            phone = input.readString("Telefone: ");
            if (!InputValidator.isValidPassengerPhone(phone)) {
                UIHelper.displayError(InputValidator.getPassengerPhoneErrorMessage());
            }
        } while (!InputValidator.isValidPassengerPhone(phone));

        try {
            Passenger passenger = new Passenger(passengerId, name, document, phone);
            
            if (flightService.makeReservation(flightId, seatNumber, passenger)) {
                UIHelper.displaySuccess("Reserva realizada com sucesso!");
            } else {
                UIHelper.displayError("Não foi possível realizar a reserva.");
            }
        } catch (IllegalArgumentException e) {
            UIHelper.displayError("Erro ao criar passageiro: " + e.getMessage());
        }
        UIHelper.pause();
    }

    private void listReservations() {
        UIHelper.clearScreen();
        final String document = getValidDocument();
        boolean found = false;
        for (Flight flight : flightService.getAllFlights()) {
            Map<Integer, Passenger> reservations = flight.getReservedSeats();
            for (Map.Entry<Integer, Passenger> entry : reservations.entrySet()) {
                if (entry.getValue().getDocument().equals(document)) {
                    if (!found) {
                        UIHelper.displayMenu("Suas Reservas");
                        System.out.println("\nDetalhes das Reservas:");
                        System.out.println("════════════════════════════════════════════════════════════════════════════════");
                        found = true;
                    }
                    System.out.println("\nVoo: " + flight);
                    System.out.println("Assento: " + entry.getKey());
                    System.out.println("Passageiro: " + entry.getValue());
                    System.out.println("────────────────────────────────────────────────────────────────────────────");
                }
            }
        }

        if (!found) {
            UIHelper.displayError("Nenhuma reserva encontrada para este documento.");
        }
        UIHelper.pause();
    }

    private void cancelReservation() {
        UIHelper.clearScreen();
        final String document = getValidDocument();
        
        int flightId;
        do {
            flightId = input.readInt("Digite o número do voo: ");
            if (!InputValidator.isValidFlightId(flightId)) {
                UIHelper.displayError(InputValidator.getFlightIdErrorMessage());
            }
        } while (!InputValidator.isValidFlightId(flightId));
        
        var flight = flightService.getFlightById(flightId);
        if (flight.isEmpty()) {
            UIHelper.displayError("Voo não encontrado.");
            UIHelper.pause();
            return;
        }

        Map<Integer, Passenger> reservations = flight.get().getReservedSeats();
        List<Integer> reservedSeats = reservations.entrySet().stream()
            .filter(entry -> entry.getValue().getDocument().equals(document))
            .map(Map.Entry::getKey)
            .toList();

        if (reservedSeats.isEmpty()) {
            UIHelper.displayError("Nenhuma reserva encontrada para este documento neste voo.");
            UIHelper.pause();
            return;
        }

        System.out.println("\nSeus assentos reservados neste voo: " + reservedSeats);
        int seatNumber;
        boolean validSeat = false;
        do {
            seatNumber = input.readInt("Digite o número do assento a ser cancelado: ");
            if (!reservedSeats.contains(seatNumber)) {
                UIHelper.displayError("Assento inválido ou não reservado por você. Escolha um dos assentos listados acima.");
            } else {
                validSeat = true;
            }
        } while (!validSeat);

        if (flightService.cancelReservation(flightId, seatNumber, document)) {
            UIHelper.displaySuccess("Reserva cancelada com sucesso!");
        } else {
            UIHelper.displayError("Não foi possível cancelar a reserva.");
        }
        UIHelper.pause();
    }

    private String getValidDocument() {
        String doc;
        do {
            doc = input.readString("Digite seu documento: ");
            if (!InputValidator.isValidPassengerDocument(doc)) {
                UIHelper.displayError(InputValidator.getPassengerDocumentErrorMessage());
            }
        } while (!InputValidator.isValidPassengerDocument(doc));
        return doc;
    }
}
