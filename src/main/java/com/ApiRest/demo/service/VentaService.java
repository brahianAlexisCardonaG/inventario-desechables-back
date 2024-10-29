package com.ApiRest.demo.service;

import com.ApiRest.demo.entity.Producto;
import com.ApiRest.demo.entity.Venta;
import com.ApiRest.demo.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class VentaService {

    private IVentaRepository ventaRepository;
    private DateTimeFormatter formatter;
    private ZonedDateTime currentDateTime;

    @Autowired
    public VentaService(IVentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    public void createdVenta(Venta venta){
        ZoneId colombiaZoneId = ZoneId.of("America/Bogota");
        this.currentDateTime = ZonedDateTime.now(colombiaZoneId);

        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaFormat = currentDateTime.format(formatter);
        venta.setFecha(fechaFormat);
        ventaRepository.save(venta);
    }

    public List<Venta> listarVenta(){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return ventaRepository.findAll(sort);
    }

    public List<Venta> listarVentaByFecha(String fecha){
        return ventaRepository.findByFechaContaining(fecha);
    }

    public List<Venta> listarVentaByNombreProducto(String nombreProducto){
        return ventaRepository.findByNombreProductoContaining(nombreProducto);
    }

    public List<Venta> listarVentaByValorTotal(Double valorTotal){
        return ventaRepository.findByValorTotal(valorTotal);
    }

    public List<Venta> getProductoByCodigoProducto(Integer codigoProducto){
        return ventaRepository.findByCodigoProducto(codigoProducto);
    }

}
