package com.ApiRest.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "laboratorio_fabrica")
    private String laboratorioFabrica;

    @Column(name = "fecha_fabricacion")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFabricacion;

    @Column(name = "fecha_vencimiento")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaVencimiento;

    @Column(name = "cantidad_stock")
    private Long cantidadStock;

    @Column(name = "valor_unitario")
    private Double valorUnitario;

    @Column(name = "codigo_producto")
    private Integer codigoProducto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLaboratorioFabrica() {
        return laboratorioFabrica;
    }

    public void setLaboratorioFabrica(String laboratorioFabrica) {
        this.laboratorioFabrica = laboratorioFabrica;
    }

    public LocalDate getFechaFabricacion() {
        return fechaFabricacion;
    }

    public void setFechaFabricacion(LocalDate fechaFabricacion) {
        this.fechaFabricacion = fechaFabricacion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Long getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(Long cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Integer getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Integer codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", laboratorioFabrica='" + laboratorioFabrica + '\'' +
                ", fechaFabricacion=" + fechaFabricacion +
                ", fechaVencimiento=" + fechaVencimiento +
                ", cantidadStock=" + cantidadStock +
                ", valorUnitario=" + valorUnitario +
                '}';
    }
}
