package com.charvey.example.cpuBurn;

class NotFoundException extends RuntimeException {

    NotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}