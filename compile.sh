#!/bin/bash

rm -rf build

mkdir -p build

find src/main/java/com/flightbooking -name "*.java" -type f | xargs javac -verbose -d build

if [ -f "build/com/flightbooking/Main.class" ]; then
    echo "Main.class foi gerado com sucesso!"
else
    echo "ERRO: Main.class não foi gerado!"
    exit 1
fi

java -cp build com.flightbooking.Main 