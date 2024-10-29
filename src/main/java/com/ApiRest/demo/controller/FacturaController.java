package com.ApiRest.demo.controller;

import com.ApiRest.demo.entity.Factura;
import com.ApiRest.demo.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/factura")
public class FacturaController {

   private FacturaService facturaService;
   @Autowired
   public FacturaController(FacturaService facturaService){
       this.facturaService = facturaService;
   }

    @PostMapping(value = "/crear", headers = "Accept=application/json")
    public ResponseEntity<String> crearFactura(@RequestBody Factura factura) {
       facturaService.createdFactura(factura);
       return ResponseEntity.ok().build();
    }
}
