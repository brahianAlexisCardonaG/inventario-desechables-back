package com.ApiRest.demo.repository;

import com.ApiRest.demo.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository <Cliente, Long> {

}
