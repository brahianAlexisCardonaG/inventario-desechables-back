package com.ApiRest.demo.service;

import com.ApiRest.demo.entity.Cliente;
import com.ApiRest.demo.entity.Factura;
import com.ApiRest.demo.repository.IClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    IClienteRepository clienteRepository;
    @Autowired
    ClienteService(IClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public void createdClient(Cliente cliente){
        for (Factura factura: cliente.getFacturas()){
            factura.setCliente(cliente);
        }
        clienteRepository.save(cliente);
    }
}
