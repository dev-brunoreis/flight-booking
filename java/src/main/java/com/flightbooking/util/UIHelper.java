package com.flightbooking.util;

import java.util.Scanner;

public class UIHelper {
    
    /**
     * Limpa a tela do terminal para uma melhor experiência do usuário.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    /**
     * Exibe um menu com bordas para melhor visualização.
     * @param title Título do menu
     * @param options Opções do menu
     */
    public static void displayMenu(String title, String... options) {
        int maxLength = title.length();
        for (String option : options) {
            if (option.length() > maxLength) {
                maxLength = option.length();
            }
        }
        
        // Adiciona espaço para o número da opção e alguns caracteres de margem
        maxLength += 10;
        
        // Desenha a borda superior
        System.out.print("╔");
        for (int i = 0; i < maxLength; i++) {
            System.out.print("═");
        }
        System.out.println("╗");
        
        // Desenha o título
        System.out.print("║ ");
        System.out.print(title);
        for (int i = 0; i < maxLength - title.length() - 2; i++) {
            System.out.print(" ");
        }
        System.out.println("║");
        
        // Desenha a linha separadora
        System.out.print("╠");
        for (int i = 0; i < maxLength; i++) {
            System.out.print("═");
        }
        System.out.println("╣");
        
        // Desenha as opções
        for (String option : options) {
            System.out.print("║ ");
            System.out.print(option);
            for (int i = 0; i < maxLength - option.length() - 2; i++) {
                System.out.print(" ");
            }
            System.out.println("║");
        }
        
        // Desenha a borda inferior
        System.out.print("╚");
        for (int i = 0; i < maxLength; i++) {
            System.out.print("═");
        }
        System.out.println("╝");
    }
    
    /**
     * Pausa a execução para o usuário ler as mensagens.
     */
    public static void pause() {
        System.out.println("\nPressione ENTER para continuar...");
        new java.util.Scanner(System.in).nextLine();
    }
    
    /**
     * Exibe uma mensagem de sucesso formatada.
     * @param message Mensagem a ser exibida
     */
    public static void displaySuccess(String message) {
        System.out.println("\n✓ " + message);
    }
    
    /**
     * Exibe uma mensagem de erro formatada.
     * @param message Mensagem a ser exibida
     */
    public static void displayError(String message) {
        System.out.println("\n✗ " + message);
    }
} 