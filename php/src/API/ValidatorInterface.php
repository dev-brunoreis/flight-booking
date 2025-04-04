<?php

namespace App\API;

interface ValidatorInterface
{
    /**
     * Validate a value
     * @param mixed $value
     * @return bool
     */
    public static function validate(mixed $value): bool;
}
