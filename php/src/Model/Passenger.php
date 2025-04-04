<?php

namespace App\Model;

use App\Validator\Passenger\{Name, Document, Phone};

class Passenger
{
    private int $id;
    private string $name;
    private string $document;
    private string $phone;

    public function __construct(
        int $id,
        string $name,
        string $document,
        string $phone
    ) {
        $this->id = $id;
        $this->name = $name;
        $this->document = $document;
        $this->phone = $phone;
    }

    public function getId(): int
    {
        return $this->id;
    }

    public function getName(): string
    {
        return $this->name;
    }

    public function getDocument(): string
    {
        return $this->document;
    }

    public function getPhone(): string
    {
        return $this->phone;
    }

    public function toArray(): array
    {
        return [
            'id' => $this->id,
            'name' => $this->name,
            'document' => $this->document,
            'phone' => $this->phone
        ];
    }
} 