package com.flightbooking;

import com.flightbooking.api.Command;
import com.flightbooking.command.BookingCommand;
import com.flightbooking.command.FlightCommand;
import com.flightbooking.command.SearchCommand;
import com.flightbooking.util.InputReader;
import com.flightbooking.util.UIHelper;

/**
 * Classe principal do sistema de reservas de voos.
 * Implementa o padrão Command para gerenciar as operações do sistema.
 */
public class Main {
    /**
     * Método principal que inicia a aplicação.
     * Implementa o padrão Command para gerenciar as diferentes operações do sistema.
     * @param args Argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        InputReader input = new InputReader();
        
        while (true) {
            UIHelper.clearScreen();
            UIHelper.displayMenu(
                "Sistema de Reservas de Voos",
                "1. Gerenciar Voos",
                "2. Gerenciar Reservas",
                "3. Consultar Voos",
                "4. Sair"
            );
            
            int option = input.readInt("Escolha uma opção: ");
            
            Command command = switch (option) {
                case 1 -> new FlightCommand();
                case 2 -> new BookingCommand();
                case 3 -> new SearchCommand();
                case 4 -> {
                    System.exit(0);
                    yield null;
                }
                default -> {
                    UIHelper.displayError("Opção inválida!");
                    UIHelper.pause();
                    yield null;
                }
            };
            
            if (command != null) {
                command.execute();
            }
        }
    }
}