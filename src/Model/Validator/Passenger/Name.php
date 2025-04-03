<?php

namespace App\Validator\Passenger;

class Name
{
    public static function validate(string $name): bool
    {
        return strlen($name) >= 3;
    }
} 