package com.ApiRest.demo.repository;

import com.ApiRest.demo.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVentaRepository extends JpaRepository <Venta, Long> {
    List<Venta> findByFechaContaining(String fecha);

    List<Venta> findByNombreProductoContaining(String nombreProducto);

    List<Venta> findByValorTotal(Double valorTotal);

    List<Venta> findByCodigoProducto(Integer codigoProducto);
}
