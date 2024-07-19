package com.example.service;

public interface NewStatusAutomationService {
    default void doTheThing() {
        System.out.println("Beep boop, automation!");
    }
}
