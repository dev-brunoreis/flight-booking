package com.flightbooking.command;

import com.flightbooking.api.Command;
import com.flightbooking.service.FlightService;
import com.flightbooking.util.InputReader;
import com.flightbooking.util.InputValidator;
import com.flightbooking.util.UIHelper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Comando para gerenciar os voos do sistema.
 * Implementa o padrão Command para encapsular as operações de voo.
 */
public class FlightCommand implements Command {
    private final FlightService flightService;
    private final InputReader input;

    public FlightCommand() {
        this.flightService = new FlightService();
        this.input = new InputReader();
    }

    @Override
    public void execute() {
        while (true) {
            UIHelper.clearScreen();
            UIHelper.displayMenu(
                "Gerenciamento de Voos",
                "1. Cadastrar Voo",
                "2. Listar Voos",
                "3. Remover Voo",
                "4. Voltar"
            );

            int option = input.readInt("Escolha uma opção: ");
            
            switch (option) {
                case 1 -> createFlight();
                case 2 -> listFlights();
                case 3 -> removeFlight();
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

    private void createFlight() {
        UIHelper.clearScreen();
        try {
            UIHelper.displayMenu("Cadastro de Voo");
            
            // Validação do ID do voo
            int id;
            do {
                id = input.readInt("Número do voo: ");
                if (!InputValidator.isValidFlightId(id)) {
                    UIHelper.displayError(InputValidator.getFlightIdErrorMessage());
                }
            } while (!InputValidator.isValidFlightId(id));
            
            // Leitura e validação da data
            LocalDate date = null;
            boolean validDate = false;
            while (!validDate) {
                try {
                    date = input.readDate("Data do voo (dd/MM/yyyy): ");
                    if (date.isBefore(LocalDate.now())) {
                        UIHelper.displayError("A data não pode ser no passado.");
                    } else {
                        validDate = true;
                    }
                } catch (DateTimeParseException e) {
                    UIHelper.displayError("Formato de data inválido. Use o formato dd/MM/yyyy.");
                }
            }
            
            // Leitura e validação da hora
            LocalTime time = null;
            boolean validTime = false;
            while (!validTime) {
                try {
                    time = input.readTime("Hora do voo (HH:mm): ");
                    validTime = true;
                } catch (DateTimeParseException e) {
                    UIHelper.displayError("Formato de hora inválido. Use o formato HH:mm.");
                }
            }
            
            // Validação da origem
            String origin;
            do {
                origin = input.readString("Origem: ");
                if (!InputValidator.isValidOrigin(origin)) {
                    UIHelper.displayError(InputValidator.getOriginErrorMessage());
                }
            } while (!InputValidator.isValidOrigin(origin));
            
            // Validação do destino
            String destination;
            do {
                destination = input.readString("Destino: ");
                if (!InputValidator.isValidDestination(destination)) {
                    UIHelper.displayError(InputValidator.getDestinationErrorMessage());
                }
            } while (!InputValidator.isValidDestination(destination));
            
            // Validação do número de assentos
            int seats;
            do {
                seats = input.readInt("Número de assentos: ");
                if (!InputValidator.isValidSeats(seats)) {
                    UIHelper.displayError(InputValidator.getSeatsErrorMessage());
                }
            } while (!InputValidator.isValidSeats(seats));

            flightService.createFlight(id, date, time, origin, destination, seats);
            UIHelper.displaySuccess("Voo cadastrado com sucesso!");
        } catch (Exception e) {
            UIHelper.displayError("Erro ao cadastrar voo: " + e.getMessage());
        }
        UIHelper.pause();
    }

    private void listFlights() {
        UIHelper.clearScreen();
        var flights = flightService.getAllFlights();
        if (flights.isEmpty()) {
            UIHelper.displayError("Nenhum voo cadastrado.");
        } else {
            UIHelper.displayMenu("Lista de Voos");
            System.out.println("\nDetalhes dos Voos:");
            System.out.println("════════════════════════════════════════════════════════════════════════════════");
            for (var flight : flights) {
                System.out.println("\n" + flight);
                System.out.println("Assentos disponíveis: " + flight.getAvailableSeats());
                System.out.println("────────────────────────────────────────────────────────────────────────────");
            }
        }
        UIHelper.pause();
    }

    private void removeFlight() {
        UIHelper.clearScreen();
        UIHelper.displayMenu("Remover Voo");
        
        // Validação do ID do voo
        int id;
        do {
            id = input.readInt("Número do voo a ser removido: ");
            if (!InputValidator.isValidFlightId(id)) {
                UIHelper.displayError(InputValidator.getFlightIdErrorMessage());
            }
        } while (!InputValidator.isValidFlightId(id));
        
        if (flightService.removeFlight(id)) {
            UIHelper.displaySuccess("Voo removido com sucesso!");
        } else {
            UIHelper.displayError("Voo não encontrado.");
        }
        UIHelper.pause();
    }
}
