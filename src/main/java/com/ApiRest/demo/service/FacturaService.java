package com.ApiRest.demo.service;

import com.ApiRest.demo.entity.Factura;
import com.ApiRest.demo.repository.IFacturaRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacturaService {
    IFacturaRespository facturaRespository;
    @Autowired
    FacturaService(IFacturaRespository facturaRespository){
        this.facturaRespository = facturaRespository;
    }

    @Transactional
    public void createdFactura(Factura factura){
        facturaRespository.save(factura);
    }

}
