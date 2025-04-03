<?php

namespace App\Model;

use App\Model\Validator\Flight\{Date, Destination, Hour, Number, Origin};
use InvalidArgumentException;

abstract class AbstractFlight
{
    private array $validators = [
        'id' => Number::class,
        'date' => Date::class,
        'hour' => Hour::class,
        'origin' => Origin::class,
        'destination' => Destination::class,
        'seats' => Number::class,
    ];

    public function __construct(
        int $id,
        string $date,
        string $hour,
        string $origin,
        string $destination,
        string $seats
    ) {
        $this->id = $id;
        $this->date = $date;
        $this->hour = $hour;
        $this->origin = $origin;
        $this->destination = $destination;
        $this->seats = $seats;
    }

    public function __set(string $name, mixed $value): void
    {
        if (isset($this->validators[$name])) {
            $validator = $this->validators[$name];
            $validator = new $validator();

            // if (!$validator::validate($value)) {
            //     throw new InvalidArgumentException("Invalid value '$value' for property '$name'.");
            // }

            $this->$name = $value;
        }
    }

    public function __get(string $name)
    {
        if (property_exists($this, $name)) {
            return $this->$name;
        }
        throw new InvalidArgumentException("Property '$name' does not exist");
    }

    public function toArray(): array
    {
        return get_object_vars($this);
    }
}
