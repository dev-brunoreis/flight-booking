<?php

namespace App\Validator\Passenger;

class Document
{
    public static function validate(string $document): bool
    {
        return strlen($document) >= 3;
    }
} 