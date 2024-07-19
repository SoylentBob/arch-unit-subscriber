package com.example.automation;

import com.example.service.NewStatusAutomationService;
import com.example.util.Subscriber;

public class ProperAutomationSubscriber implements Subscriber {

    private NewStatusAutomationService service;

    @Override
    public int orderValue() {
        return 1;
    }
}
