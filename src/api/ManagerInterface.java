package com.flightbooking.api;

/**
 * Interface que define o contrato para gerenciadores de operações do sistema.
 * Esta interface estende Command para manter a consistência com o padrão Command.
 * 
 * Gerenciadores são responsáveis por coordenar operações mais complexas,
 * que podem envolver múltiplos comandos ou validações adicionais.
 */
public interface ManagerInterface extends Command {
    /**
     * Execute the operation of the manager.
     * 
     * @return void
     */
    void execute();
}
