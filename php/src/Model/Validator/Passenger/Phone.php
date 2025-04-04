<?php

namespace App\Validator\Passenger;

class Phone
{
    public static function validate(string $phone): bool
    {
        return preg_match('/^\+?[1-9][0-9]{7,14}$/', $phone);
    }
} 