<?php

namespace App\Lib\Data;

use InvalidArgumentException;

abstract class DataCollection
{
    protected static array $items = [];

    public function addItem(object $item)
    {
        if (!isset($item->id)) {
            throw new InvalidArgumentException("Item must have an id");
        }

        self::$items[] = $item;
    }

    public function removeById(int $id)
    {
        self::$items = array_filter(self::$items, fn($item) => $item->id != $id);
    }

    public function getItems()
    {
        return self::$items;
    }

    public function getById(int $id)
    {
        $items = array_filter(self::getItems(), fn($item) => $item->id == $id);
        return reset($items) ?: null;
    }

    public function filterBy(string $key, mixed $value)
    {
        $items = array_filter(self::getItems(), fn($item) => $item->$key == $value);
        return array_values($items);
    }
}
