package com.flightbooking.service;

import java.util.Scanner;

public class Menu {
    public static void showMainMenu() {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Gerenciar Voos");
        System.out.println("2. Gerenciar Reservas");
        System.out.println("3. Consultar Voos");
        System.out.println("4. Sair");
    }

    public static int readOption() {
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        scanner.close();
        return option;
    }

    public static void showFlightMenu() {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Cadastrar Voo");
        System.out.println("2. Listar Voos");
        System.out.println("3. Voltar");
    }

    public static void showBookingMenu() {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Cadastrar Reserva");
        System.out.println("2. Listar Reservas");
        System.out.println("3. Voltar");
    }

}
