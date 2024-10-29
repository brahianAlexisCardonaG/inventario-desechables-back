package com.ApiRest.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "factura")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cod_vendedor")
    private String codVendedor;
    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "factura")
    private String factura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_cedula", nullable = true)
    private Cliente cliente;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(String codVendedor) {
        this.codVendedor = codVendedor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public Cliente getCliente() {
            return cliente;
        }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    }
