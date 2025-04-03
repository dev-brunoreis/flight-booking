<?php

namespace App\Model\Validator\Flight;

use App\API\ValidatorInterface;

class Number implements ValidatorInterface
{
    /**
     * @inheritdoc
     * @param int $value
     * @return bool
     */
    public static function validate(mixed $value): bool
    {
        return $value <= 0;
    }
}
