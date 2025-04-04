package com.flightbooking.command;

import com.flightbooking.api.Command;
import com.flightbooking.model.Flight;
import com.flightbooking.service.FlightService;
import com.flightbooking.util.InputReader;
import com.flightbooking.util.InputValidator;
import com.flightbooking.util.UIHelper;
import java.time.LocalDate;
import java.util.List;

/**
 * Comando para realizar consultas de voos.
 * Implementa o padrão Command para encapsular as operações de busca.
 */
public class SearchCommand implements Command {
    private final FlightService flightService;
    private final InputReader input;

    public SearchCommand() {
        this.flightService = new FlightService();
        this.input = new InputReader();
    }

    @Override
    public void execute() {
        while (true) {
            UIHelper.clearScreen();
            UIHelper.displayMenu(
                "Consulta de Voos",
                "1. Buscar por Origem",
                "2. Buscar por Destino",
                "3. Buscar por Data",
                "4. Voltar"
            );

            int option = input.readInt("Escolha uma opção: ");
            
            switch (option) {
                case 1 -> searchByOrigin();
                case 2 -> searchByDestination();
                case 3 -> searchByDate();
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

    private void searchByOrigin() {
        UIHelper.clearScreen();
        String origin;
        do {
            origin = input.readString("Digite a origem: ");
            if (!InputValidator.isValidOrigin(origin)) {
                UIHelper.displayError(InputValidator.getOriginErrorMessage());
            }
        } while (!InputValidator.isValidOrigin(origin));
        
        List<Flight> flights = flightService.searchByOrigin(origin);
        displayResults(flights, "Voos com origem em " + origin);
    }

    private void searchByDestination() {
        UIHelper.clearScreen();
        String destination;
        do {
            destination = input.readString("Digite o destino: ");
            if (!InputValidator.isValidDestination(destination)) {
                UIHelper.displayError(InputValidator.getDestinationErrorMessage());
            }
        } while (!InputValidator.isValidDestination(destination));
        
        List<Flight> flights = flightService.searchByDestination(destination);
        displayResults(flights, "Voos com destino a " + destination);
    }

    private void searchByDate() {
        UIHelper.clearScreen();
        LocalDate date = null;
        boolean validDate = false;
        
        while (!validDate) {
            try {
                date = input.readDate("Digite a data (dd/MM/yyyy): ");
                if (date.isBefore(LocalDate.now())) {
                    UIHelper.displayError("A data não pode ser no passado.");
                } else {
                    validDate = true;
                }
            } catch (Exception e) {
                UIHelper.displayError("Formato de data inválido. Use o formato dd/MM/yyyy.");
            }
        }
        
        List<Flight> flights = flightService.searchByDate(date);
        displayResults(flights, "Voos para o dia " + date.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    private void displayResults(List<Flight> flights, String title) {
        UIHelper.clearScreen();
        if (flights.isEmpty()) {
            UIHelper.displayError("Nenhum voo encontrado.");
        } else {
            UIHelper.displayMenu(title);
            System.out.println("\nDetalhes dos Voos:");
            System.out.println("════════════════════════════════════════════════════════════════════════════════");
            for (Flight flight : flights) {
                System.out.println("\n" + flight);
                System.out.println("Assentos disponíveis: " + flight.getAvailableSeats());
                System.out.println("────────────────────────────────────────────────────────────────────────────");
            }
        }
        UIHelper.pause();
    }
} 