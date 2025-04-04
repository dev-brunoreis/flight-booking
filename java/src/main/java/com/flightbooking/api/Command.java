package com.flightbooking.api;

/**
 * Interface que define o contrato para o padrão Command.
 * Todas as operações do sistema devem implementar esta interface.
 * 
 * O padrão Command encapsula uma solicitação como um objeto,
 * permitindo parametrizar clientes com diferentes solicitações,
 * enfileirar ou registrar solicitações e suportar operações que podem ser desfeitas.
 */
public interface Command {
    /**
     * Executa o comando.
     * Cada implementação deve definir sua própria lógica de execução.
     */
    void execute();
} 