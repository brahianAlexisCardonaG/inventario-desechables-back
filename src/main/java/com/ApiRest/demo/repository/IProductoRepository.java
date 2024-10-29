package com.ApiRest.demo.repository;

import com.ApiRest.demo.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductoRepository extends JpaRepository <Producto, Long> {

    List<Producto> findByNombreContaining(String nombre);
    List<Producto> findByLaboratorioFabrica(String laboratorioFabrica);
    List<Producto> findByCantidadStockLessThan(Long cantidadStock);
    List<Producto> findByCodigoProducto(Integer codigoProducto);

}
