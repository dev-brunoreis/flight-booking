<?php

namespace App\Model;

use App\Validator\Flight\{Date, Destination, Hour, Number, Origin};

class Flight
{
    private int $id;
    private string $date;
    private string $hour;
    private string $origin;
    private string $destination;
    private int $totalSeats;
    private array $reservedSeats = [];

    public function __construct(
        int $id,
        string $date,
        string $hour,
        string $origin,
        string $destination,
        int $totalSeats
    ) {
        $this->id = $id;
        $this->date = $date;
        $this->hour = $hour;
        $this->origin = $origin;
        $this->destination = $destination;
        $this->totalSeats = $totalSeats;
    }

    public function getId(): int
    {
        return $this->id;
    }

    public function getDate(): string
    {
        return $this->date;
    }

    public function getHour(): string
    {
        return $this->hour;
    }

    public function getOrigin(): string
    {
        return $this->origin;
    }

    public function getDestination(): string
    {
        return $this->destination;
    }

    public function getTotalSeats(): int
    {
        return $this->totalSeats;
    }

    public function getAvailableSeats(): array
    {
        $allSeats = range(1, $this->totalSeats);
        return array_diff($allSeats, array_keys($this->reservedSeats));
    }

    public function reserveSeat(int $seatNumber, Passenger $passenger): bool
    {
        if ($seatNumber < 1 || $seatNumber > $this->totalSeats) {
            throw new \InvalidArgumentException("Invalid seat number");
        }

        if (isset($this->reservedSeats[$seatNumber])) {
            return false;
        }

        $this->reservedSeats[$seatNumber] = $passenger;
        return true;
    }

    public function getReservedSeats(): array
    {
        return $this->reservedSeats;
    }

    public function cancelReservation(int $seatNumber): bool
    {
        if (!isset($this->reservedSeats[$seatNumber])) {
            return false;
        }

        unset($this->reservedSeats[$seatNumber]);
        return true;
    }

    public function toArray(): array
    {
        return [
            'id' => $this->id,
            'date' => $this->date,
            'hour' => $this->hour,
            'origin' => $this->origin,
            'destination' => $this->destination,
            'totalSeats' => $this->totalSeats,
            'availableSeats' => $this->getAvailableSeats(),
            'reservedSeats' => array_map(fn($passenger) => $passenger->toArray(), $this->reservedSeats)
        ];
    }
}
