package com.ApiRest.demo.repository;

import com.ApiRest.demo.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFacturaRespository extends JpaRepository <Factura, Long>{
}
