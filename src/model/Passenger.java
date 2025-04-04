package com.flightbooking.model;

/**
 * Classe que representa um passageiro no sistema.
 * Implementa o padrão Value Object, sendo imutável após a criação.
 */
public class Passenger {
    private final int id;
    private final String name;
    private final String document;
    private final String phone;

    /**
     * Construtor que inicializa um novo passageiro.
     * @param id Identificador único do passageiro
     * @param name Nome completo do passageiro
     * @param document Documento de identificação (CPF ou RG)
     * @param phone Número de telefone
     * @throws IllegalArgumentException se algum dos parâmetros for inválido
     */
    public Passenger(int id, String name, String document, String phone) {
        validateId(id);
        validateName(name);
        validateDocument(document);
        validatePhone(phone);

        this.id = id;
        this.name = name;
        this.document = document;
        this.phone = phone;
    }

    /**
     * Valida o ID do passageiro.
     * @param id ID a ser validado
     * @throws IllegalArgumentException se o ID for inválido
     */
    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do passageiro deve ser positivo");
        }
    }

    /**
     * Valida o nome do passageiro.
     * @param name Nome a ser validado
     * @throws IllegalArgumentException se o nome for inválido
     */
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do passageiro não pode ser vazio");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("Nome do passageiro deve ter pelo menos 3 caracteres");
        }
    }

    /**
     * Valida o documento do passageiro.
     * @param document Documento a ser validado
     * @throws IllegalArgumentException se o documento for inválido
     */
    private void validateDocument(String document) {
        if (document == null || document.trim().isEmpty()) {
            throw new IllegalArgumentException("Documento do passageiro não pode ser vazio");
        }
        // Remove caracteres não numéricos
        String numbersOnly = document.replaceAll("\\D", "");
        if (numbersOnly.length() < 11) {
            throw new IllegalArgumentException("Documento do passageiro deve ter pelo menos 11 dígitos");
        }
    }

    /**
     * Valida o telefone do passageiro.
     * @param phone Telefone a ser validado
     * @throws IllegalArgumentException se o telefone for inválido
     */
    private void validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone do passageiro não pode ser vazio");
        }
        // Remove caracteres não numéricos
        String numbersOnly = phone.replaceAll("\\D", "");
        if (numbersOnly.length() < 10 || numbersOnly.length() > 11) {
            throw new IllegalArgumentException("Telefone do passageiro deve ter entre 10 e 11 dígitos");
        }
    }

    // Getters básicos
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDocument() { return document; }
    public String getPhone() { return phone; }

    @Override
    public String toString() {
        return String.format("Passageiro %d: %s (Doc: %s)", id, name, document);
    }
} 