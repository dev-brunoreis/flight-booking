<?php

namespace App\Model\Validator\Flight;

use App\API\ValidatorInterface;

class Destination implements ValidatorInterface
{
    /**
     * @inheritdoc
     * @param mixed $destination
     * @return bool
     */
    public static function validate(mixed $destination): bool
    {
        return empty(trim($destination));
    }
}
