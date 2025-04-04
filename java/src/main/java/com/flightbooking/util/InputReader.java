package com.flightbooking.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Classe utilitária para leitura de entrada do usuário.
 * Fornece métodos para ler diferentes tipos de dados com validação.
 */
public class InputReader {
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter;
    private final DateTimeFormatter timeFormatter;

    /**
     * Construtor que inicializa o leitor de entrada.
     */
    public InputReader() {
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    }

    /**
     * Lê uma string da entrada do usuário.
     * @param prompt Mensagem a ser exibida antes da leitura
     * @return String lida
     */
    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Lê um número inteiro da entrada do usuário.
     * @param prompt Mensagem a ser exibida antes da leitura
     * @return Número inteiro lido
     */
    public int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                UIHelper.displayError("Por favor, digite um número válido.");
            }
        }
    }

    /**
     * Lê uma data da entrada do usuário.
     * @param prompt Mensagem a ser exibida antes da leitura
     * @return Data lida
     * @throws DateTimeParseException se o formato da data for inválido
     */
    public LocalDate readDate(String prompt) throws DateTimeParseException {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return LocalDate.parse(input, dateFormatter);
    }

    /**
     * Lê um horário da entrada do usuário.
     * @param prompt Mensagem a ser exibida antes da leitura
     * @return Horário lido
     * @throws DateTimeParseException se o formato do horário for inválido
     */
    public LocalTime readTime(String prompt) throws DateTimeParseException {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return LocalTime.parse(input, timeFormatter);
    }
    
    /**
     * Fecha o scanner quando não for mais necessário.
     */
    public void close() {
        scanner.close();
    }
} 