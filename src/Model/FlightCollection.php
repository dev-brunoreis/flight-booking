<?php

namespace App\Model;

class FlightCollection
{
    private static ?FlightCollection $instance = null;
    private array $flights = [];

    private function __construct()
    {
    }

    public static function getInstance(): self
    {
        if (self::$instance === null) {
            self::$instance = new self();
        }

        return self::$instance;
    }

    public function addItem(Flight $flight): void
    {
        $this->flights[$flight->getId()] = $flight;
    }

    public function removeById(int $id): void
    {
        unset($this->flights[$id]);
    }

    public function getItems(): array
    {
        return $this->flights;
    }

    public function getById(int $id): ?Flight
    {
        return $this->flights[$id] ?? null;
    }

    public function searchFlights(string $origin, string $destination, string $date): array
    {
        return array_filter($this->flights, function(Flight $flight) use ($origin, $destination, $date) {
            return $flight->getOrigin() === $origin &&
                   $flight->getDestination() === $destination &&
                   $flight->getDate() === $date;
        });
    }
}
