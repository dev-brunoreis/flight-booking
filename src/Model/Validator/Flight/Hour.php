<?php

namespace App\Model\Validator\Flight;

use App\API\ValidatorInterface;

class Hour implements ValidatorInterface
{
    /**
     * @inheritdoc
     * @param mixed $hour
     * @return bool
     */
    public static function validate(mixed $hour): bool
    {
        return !preg_match("/^([01]?[0-9]|2[0-3]):[0-5][0-9]$/", $hour);
    }
}
