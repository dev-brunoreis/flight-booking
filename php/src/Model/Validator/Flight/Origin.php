<?php

namespace App\Model\Validator\Flight;

use App\API\ValidatorInterface;

class Origin implements ValidatorInterface
{
    /**
     * @inheritdoc
     * @param mixed $origin
     * @return bool
     */
    public static function validate(mixed $origin): bool
    {
        return empty(trim($origin));
    }
}
