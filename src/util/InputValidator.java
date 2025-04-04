package com.flightbooking.util;

/**
 * Classe utilitária para validação de entrada de dados.
 * Fornece métodos para validar diferentes tipos de entrada antes de criar objetos.
 */
public class InputValidator {
    
    /**
     * Valida o ID do passageiro.
     * @param id ID a ser validado
     * @return true se o ID for válido, false caso contrário
     */
    public static boolean isValidPassengerId(int id) {
        return id > 0;
    }
    
    /**
     * Valida o nome do passageiro.
     * @param name Nome a ser validado
     * @return true se o nome for válido, false caso contrário
     */
    public static boolean isValidPassengerName(String name) {
        return name != null && name.trim().length() >= 3;
    }
    
    /**
     * Valida o documento do passageiro.
     * @param document Documento a ser validado
     * @return true se o documento for válido, false caso contrário
     */
    public static boolean isValidPassengerDocument(String document) {
        if (document == null || document.trim().isEmpty()) {
            return false;
        }
        // Remove caracteres não numéricos
        String numbersOnly = document.replaceAll("\\D", "");
        return numbersOnly.length() >= 11;
    }
    
    /**
     * Valida o telefone do passageiro.
     * @param phone Telefone a ser validado
     * @return true se o telefone for válido, false caso contrário
     */
    public static boolean isValidPassengerPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        // Remove caracteres não numéricos
        String numbersOnly = phone.replaceAll("\\D", "");
        return numbersOnly.length() >= 10 && numbersOnly.length() <= 11;
    }
    
    /**
     * Valida o ID do voo.
     * @param id ID a ser validado
     * @return true se o ID for válido, false caso contrário
     */
    public static boolean isValidFlightId(int id) {
        return id > 0;
    }
    
    /**
     * Valida o número de assentos.
     * @param seats Número de assentos a ser validado
     * @return true se o número de assentos for válido, false caso contrário
     */
    public static boolean isValidSeats(int seats) {
        return seats > 0;
    }
    
    /**
     * Valida a origem do voo.
     * @param origin Origem a ser validada
     * @return true se a origem for válida, false caso contrário
     */
    public static boolean isValidOrigin(String origin) {
        return origin != null && !origin.trim().isEmpty();
    }
    
    /**
     * Valida o destino do voo.
     * @param destination Destino a ser validado
     * @return true se o destino for válido, false caso contrário
     */
    public static boolean isValidDestination(String destination) {
        return destination != null && !destination.trim().isEmpty();
    }
    
    /**
     * Obtém a mensagem de erro para um ID de passageiro inválido.
     * @return Mensagem de erro
     */
    public static String getPassengerIdErrorMessage() {
        return "ID do passageiro deve ser positivo";
    }
    
    /**
     * Obtém a mensagem de erro para um nome de passageiro inválido.
     * @return Mensagem de erro
     */
    public static String getPassengerNameErrorMessage() {
        return "Nome do passageiro deve ter pelo menos 3 caracteres";
    }
    
    /**
     * Obtém a mensagem de erro para um documento de passageiro inválido.
     * @return Mensagem de erro
     */
    public static String getPassengerDocumentErrorMessage() {
        return "Documento do passageiro deve ter pelo menos 11 dígitos";
    }
    
    /**
     * Obtém a mensagem de erro para um telefone de passageiro inválido.
     * @return Mensagem de erro
     */
    public static String getPassengerPhoneErrorMessage() {
        return "Telefone do passageiro deve ter entre 10 e 11 dígitos";
    }
    
    /**
     * Obtém a mensagem de erro para um ID de voo inválido.
     * @return Mensagem de erro
     */
    public static String getFlightIdErrorMessage() {
        return "ID do voo deve ser positivo";
    }
    
    /**
     * Obtém a mensagem de erro para um número de assentos inválido.
     * @return Mensagem de erro
     */
    public static String getSeatsErrorMessage() {
        return "Número de assentos deve ser positivo";
    }
    
    /**
     * Obtém a mensagem de erro para uma origem inválida.
     * @return Mensagem de erro
     */
    public static String getOriginErrorMessage() {
        return "Origem não pode ser vazia";
    }
    
    /**
     * Obtém a mensagem de erro para um destino inválido.
     * @return Mensagem de erro
     */
    public static String getDestinationErrorMessage() {
        return "Destino não pode ser vazio";
    }
} 