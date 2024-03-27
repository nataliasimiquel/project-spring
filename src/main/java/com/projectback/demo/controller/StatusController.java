package com.projectback.demo.controller;

import com.projectback.demo.client.ClientComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


    @RestController
    public class StatusController {
	@Autowired
	private ClientComponent clientComponent;
	@GetMapping("/status")
	public String getStatus() {
	    return clientComponent.getValues();
	}
    }


