<?php

require_once 'vendor/autoload.php';

use App\Model\Flight;
use App\Model\Passenger;
use App\Model\FlightCollection;

function clearScreen() {
    echo chr(27).chr(91).'H'.chr(27).chr(91).'J';
}

function readInput(string $prompt): string {
    echo $prompt;
    return trim(fgets(STDIN));
}

function showMainMenu() {
    clearScreen();
    echo "=== Sistema de Reservas de Passagens ===\n";
    echo "1. Gerenciar Voos\n";
    echo "2. Gerenciar Reservas\n";
    echo "3. Consultar Voos\n";
    echo "4. Sair\n";
    return readInput("Escolha uma opção: ");
}

function showFlightMenu() {
    clearScreen();
    echo "=== Gerenciar Voos ===\n";
    echo "1. Cadastrar Novo Voo\n";
    echo "2. Listar Todos os Voos\n";
    echo "3. Excluir Voo\n";
    echo "4. Voltar\n";
    return readInput("Escolha uma opção: ");
}

function showBookingMenu() {
    clearScreen();
    echo "=== Gerenciar Reservas ===\n";
    echo "1. Nova Reserva\n";
    echo "2. Listar Minhas Reservas\n";
    echo "3. Cancelar Reserva\n";
    echo "4. Voltar\n";
    return readInput("Escolha uma opção: ");
}

function showSearchMenu() {
    clearScreen();
    echo "=== Consultar Voos ===\n";
    echo "1. Buscar por Origem\n";
    echo "2. Buscar por Destino\n";
    echo "3. Buscar por Data\n";
    echo "4. Voltar\n";
    return readInput("Escolha uma opção: ");
}

function isValidDate(string $date): bool {
    $d = \DateTime::createFromFormat('Y-m-d', $date);
    return $d && $d->format('Y-m-d') === $date && $d >= new \DateTime('today');
}

function createFlight() {
    clearScreen();
    echo "=== Cadastrar Novo Voo ===\n";
    
    $id = (int)readInput("Número do voo: ");
    
    do {
        $date = readInput("Data (YYYY-MM-DD): ");
        if (!isValidDate($date)) {
            echo "Data inválida ou anterior a hoje. Tente novamente.\n";
        }
    } while (!isValidDate($date));
    
    $hour = readInput("Hora (HH:MM): ");
    $origin = readInput("Origem: ");
    $destination = readInput("Destino: ");
    $totalSeats = (int)readInput("Total de assentos: ");

    try {
        $flight = new Flight($id, $date, $hour, $origin, $destination, $totalSeats);
        FlightCollection::getInstance()->addItem($flight);
        echo "\nVoo cadastrado com sucesso!\n";
    } catch (\Exception $e) {
        echo "\nErro ao cadastrar voo: " . $e->getMessage() . "\n";
    }

    readInput("\nPressione ENTER para continuar...");
}

function deleteFlight() {
    clearScreen();
    echo "=== Excluir Voo ===\n\n";

    $flights = FlightCollection::getInstance()->getItems();
    if (empty($flights)) {
        echo "Nenhum voo cadastrado.\n";
        readInput("\nPressione ENTER para continuar...");
        return;
    }

    foreach ($flights as $flight) {
        echo "Voo " . $flight->getId() . ": " . $flight->getOrigin() . " -> " . $flight->getDestination() . 
             " (" . $flight->getDate() . " " . $flight->getHour() . ")\n";
    }

    $id = (int)readInput("\nNúmero do voo que deseja excluir (0 para cancelar): ");
    if ($id === 0) {
        return;
    }

    $flight = FlightCollection::getInstance()->getById($id);
    if (!$flight) {
        echo "Voo não encontrado.\n";
    } else {
        if (count($flight->getReservedSeats()) > 0) {
            echo "Não é possível excluir voo com reservas ativas.\n";
        } else {
            FlightCollection::getInstance()->removeById($id);
            echo "Voo excluído com sucesso!\n";
        }
    }

    readInput("\nPressione ENTER para continuar...");
}

function listFlights() {
    clearScreen();
    echo "=== Lista de Voos ===\n\n";

    $flights = FlightCollection::getInstance()->getItems();
    
    if (empty($flights)) {
        echo "Nenhum voo cadastrado.\n";
    } else {
        foreach ($flights as $flight) {
            echo "Voo: " . $flight->getId() . "\n";
            echo "Data: " . $flight->getDate() . "\n";
            echo "Hora: " . $flight->getHour() . "\n";
            echo "Origem: " . $flight->getOrigin() . "\n";
            echo "Destino: " . $flight->getDestination() . "\n";
            echo "Assentos disponíveis: " . count($flight->getAvailableSeats()) . "\n";
            echo "Assentos reservados: " . count($flight->getReservedSeats()) . "\n";
            echo "----------------------------------------\n";
        }
    }

    readInput("\nPressione ENTER para continuar...");
}

function listMyReservations() {
    clearScreen();
    echo "=== Minhas Reservas ===\n\n";

    $document = readInput("Digite seu documento para buscar suas reservas: ");
    $found = false;

    foreach (FlightCollection::getInstance()->getItems() as $flight) {
        foreach ($flight->getReservedSeats() as $seat => $passenger) {
            if ($passenger->getDocument() === $document) {
                echo "Voo: " . $flight->getId() . "\n";
                echo "Data: " . $flight->getDate() . "\n";
                echo "Hora: " . $flight->getHour() . "\n";
                echo "Origem: " . $flight->getOrigin() . "\n";
                echo "Destino: " . $flight->getDestination() . "\n";
                echo "Assento: " . $seat . "\n";
                echo "----------------------------------------\n";
                $found = true;
            }
        }
    }

    if (!$found) {
        echo "Nenhuma reserva encontrada para este documento.\n";
    }

    readInput("\nPressione ENTER para continuar...");
}

function cancelReservation() {
    clearScreen();
    echo "=== Cancelar Reserva ===\n\n";

    $document = readInput("Digite seu documento: ");
    $found = false;
    $reservations = [];

    foreach (FlightCollection::getInstance()->getItems() as $flight) {
        foreach ($flight->getReservedSeats() as $seat => $passenger) {
            if ($passenger->getDocument() === $document) {
                $reservations[] = [
                    'flight' => $flight,
                    'seat' => $seat
                ];
                echo count($reservations) . ". Voo " . $flight->getId() . 
                     ": " . $flight->getOrigin() . " -> " . $flight->getDestination() . 
                     " (Assento: " . $seat . ")\n";
                $found = true;
            }
        }
    }

    if (!$found) {
        echo "Nenhuma reserva encontrada para este documento.\n";
        readInput("\nPressione ENTER para continuar...");
        return;
    }

    $option = (int)readInput("\nDigite o número da reserva que deseja cancelar (0 para voltar): ");
    if ($option === 0 || !isset($reservations[$option - 1])) {
        return;
    }

    $reservation = $reservations[$option - 1];
    $reservation['flight']->cancelReservation($reservation['seat']);
    echo "\nReserva cancelada com sucesso!\n";

    readInput("\nPressione ENTER para continuar...");
}

function searchFlightsByOrigin() {
    clearScreen();
    echo "=== Buscar Voos por Origem ===\n\n";
    
    $origin = readInput("Digite a origem: ");
    $flights = array_filter(
        FlightCollection::getInstance()->getItems(),
        fn($flight) => stripos($flight->getOrigin(), $origin) !== false && 
                       isValidDate($flight->getDate()) &&
                       count($flight->getAvailableSeats()) > 0
    );

    displaySearchResults($flights);
}

function searchFlightsByDestination() {
    clearScreen();
    echo "=== Buscar Voos por Destino ===\n\n";
    
    $destination = readInput("Digite o destino: ");
    $flights = array_filter(
        FlightCollection::getInstance()->getItems(),
        fn($flight) => stripos($flight->getDestination(), $destination) !== false && 
                       isValidDate($flight->getDate()) &&
                       count($flight->getAvailableSeats()) > 0
    );

    displaySearchResults($flights);
}

function searchFlightsByDate() {
    clearScreen();
    echo "=== Buscar Voos por Data ===\n\n";
    
    do {
        $date = readInput("Data (YYYY-MM-DD): ");
        if (!isValidDate($date)) {
            echo "Data inválida ou anterior a hoje. Tente novamente.\n";
        }
    } while (!isValidDate($date));

    $flights = array_filter(
        FlightCollection::getInstance()->getItems(),
        fn($flight) => $flight->getDate() === $date && count($flight->getAvailableSeats()) > 0
    );

    displaySearchResults($flights);
}

function displaySearchResults(array $flights) {
    if (empty($flights)) {
        echo "\nNenhum voo encontrado com os critérios informados.\n";
    } else {
        echo "\nVoos encontrados:\n";
        foreach ($flights as $flight) {
            echo "\nVoo: " . $flight->getId() . "\n";
            echo "Data: " . $flight->getDate() . "\n";
            echo "Hora: " . $flight->getHour() . "\n";
            echo "Origem: " . $flight->getOrigin() . "\n";
            echo "Destino: " . $flight->getDestination() . "\n";
            echo "Assentos disponíveis: " . count($flight->getAvailableSeats()) . "\n";
            echo "----------------------------------------\n";
        }
    }

    readInput("\nPressione ENTER para continuar...");
}

function createBooking() {
    clearScreen();
    echo "=== Nova Reserva ===\n\n";

    $flights = array_filter(
        FlightCollection::getInstance()->getItems(),
        fn($flight) => isValidDate($flight->getDate()) && count($flight->getAvailableSeats()) > 0
    );

    if (empty($flights)) {
        echo "Nenhum voo disponível para reserva.\n";
        readInput("\nPressione ENTER para continuar...");
        return;
    }

    echo "Voos disponíveis:\n";
    foreach ($flights as $flight) {
        echo "Voo " . $flight->getId() . ": " . 
             $flight->getOrigin() . " -> " . $flight->getDestination() . 
             " (" . $flight->getDate() . " " . $flight->getHour() . ") - " .
             count($flight->getAvailableSeats()) . " assentos disponíveis\n";
    }

    $flightId = (int)readInput("\nNúmero do voo desejado (0 para cancelar): ");
    if ($flightId === 0) {
        return;
    }

    $flight = FlightCollection::getInstance()->getById($flightId);
    if (!$flight) {
        echo "Voo não encontrado.\n";
        readInput("\nPressione ENTER para continuar...");
        return;
    }

    if (!isValidDate($flight->getDate())) {
        echo "Este voo não está mais disponível para reservas (data anterior a hoje).\n";
        readInput("\nPressione ENTER para continuar...");
        return;
    }

    $availableSeats = $flight->getAvailableSeats();
    if (empty($availableSeats)) {
        echo "Não há assentos disponíveis neste voo.\n";
        readInput("\nPressione ENTER para continuar...");
        return;
    }

    echo "\nAssentos disponíveis: " . implode(", ", $availableSeats) . "\n";
    $seatNumber = (int)readInput("Número do assento desejado (0 para cancelar): ");
    if ($seatNumber === 0) {
        return;
    }

    if (!in_array($seatNumber, $availableSeats)) {
        echo "Assento inválido ou não disponível.\n";
        readInput("\nPressione ENTER para continuar...");
        return;
    }

    echo "\nDados do passageiro:\n";
    $passengerId = (int)readInput("ID do passageiro: ");
    $name = readInput("Nome: ");
    $document = readInput("Documento: ");
    $phone = readInput("Telefone: ");

    try {
        $passenger = new Passenger($passengerId, $name, $document, $phone);
        if ($flight->reserveSeat($seatNumber, $passenger)) {
            echo "\nReserva realizada com sucesso!\n";
            echo "\nResumo da reserva:\n";
            echo "Voo: " . $flight->getId() . "\n";
            echo "Data: " . $flight->getDate() . "\n";
            echo "Hora: " . $flight->getHour() . "\n";
            echo "Origem: " . $flight->getOrigin() . "\n";
            echo "Destino: " . $flight->getDestination() . "\n";
            echo "Assento: " . $seatNumber . "\n";
            echo "Passageiro: " . $name . "\n";
        } else {
            echo "\nNão foi possível realizar a reserva. Assento pode estar ocupado.\n";
        }
    } catch (\Exception $e) {
        echo "\nErro ao fazer reserva: " . $e->getMessage() . "\n";
    }

    readInput("\nPressione ENTER para continuar...");
}

// Main loop
while (true) {
    $option = showMainMenu();

    switch ($option) {
        case '1':
            while (true) {
                $subOption = showFlightMenu();
                switch ($subOption) {
                    case '1':
                        createFlight();
                        break;
                    case '2':
                        listFlights();
                        break;
                    case '3':
                        deleteFlight();
                        break;
                    case '4':
                        break 2;
                }
            }
            break;

        case '2':
            while (true) {
                $subOption = showBookingMenu();
                switch ($subOption) {
                    case '1':
                        createBooking();
                        break;
                    case '2':
                        listMyReservations();
                        break;
                    case '3':
                        cancelReservation();
                        break;
                    case '4':
                        break 2;
                }
            }
            break;

        case '3':
            while (true) {
                $subOption = showSearchMenu();
                switch ($subOption) {
                    case '1':
                        searchFlightsByOrigin();
                        break;
                    case '2':
                        searchFlightsByDestination();
                        break;
                    case '3':
                        searchFlightsByDate();
                        break;
                    case '4':
                        break 2;
                }
            }
            break;

        case '4':
            echo "\nObrigado por usar o sistema!\n";
            exit(0);

        default:
            echo "\nOpção inválida!\n";
            sleep(1);
    }
} 