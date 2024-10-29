package com.ApiRest.demo.controller;

import com.ApiRest.demo.entity.Cliente;
import com.ApiRest.demo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private ClienteService clienteService;
    @Autowired
    ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @PostMapping(value = "/crear", headers = "Accept=application/json")
    public ResponseEntity<String> crearCliente(@RequestBody Cliente cliente) {
        clienteService.createdClient(cliente);
        return ResponseEntity.ok().build();
    }

}
