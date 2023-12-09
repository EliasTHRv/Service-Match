package com.ServiceMatch.SM.controllers;

import com.ServiceMatch.SM.exceptions.MyException;
import com.ServiceMatch.SM.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

}

