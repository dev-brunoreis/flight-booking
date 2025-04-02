<?php

require_once 'vendor/autoload.php';

if ($argc <= 1) {
    echo file_get_contents('helper.txt');
    echo "\n";
    exit;
}

$availableCommands = [
    'create_flight' => new \App\Flight\Create($argv[1])
];