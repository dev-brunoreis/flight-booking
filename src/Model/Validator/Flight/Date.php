<?php

namespace App\Model\Validator\Flight;

use App\API\ValidatorInterface;

class Date implements ValidatorInterface
{
    /**
     * @inheritdoc
     * @param mixed $date
     * @return bool
     */
    public static function validate(mixed $date): bool
    {
        return !preg_match("/^\d{2}-\d{2}-\d{4}$/", $date);
    }
}
