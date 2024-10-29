package com.ApiRest.demo.controller;

import com.ApiRest.demo.entity.Producto;
import com.ApiRest.demo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventario")
public class ProductoController {

    private ProductoService productoService;


    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping(value = "/crear", headers = "Accept=application/json")
    public ResponseEntity<String> crearProducto(@RequestBody Producto producto) {
        List<Producto> codProductoExistente = productoService.getProductoByCodigoProducto(producto.getCodigoProducto());
        if (codProductoExistente.isEmpty()) {
            productoService.createdProducto(producto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El código del producto ya existe");
        }
    }

    @GetMapping(value = "listar", headers = "Accept=application/json")
    public List<Producto> ListarProducto(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String laboratorioFabrica,
            @RequestParam(required = false) Integer codigoProducto
    ) {

        if (id == null && nombre == null && laboratorioFabrica == null && codigoProducto == null) {
            return productoService.getAllProducto();
        } else if (id != null) {
            // Si se proporciona el ID, devolver el medicamento por ID
            Optional<Producto> producto = productoService.getProductoById(id);
            return producto.map(Collections::singletonList).orElse(Collections.emptyList());
        } else if (nombre != null) {
            // Si se proporciona el nombre, devolver medicamentos por nombre
            return productoService.getProductoByNombre(nombre);
        } else if (laboratorioFabrica != null) {
            // Si se proporciona el laboratorio, devolver medicamentos por laboratorio
            return productoService.getProductoByLaboratorio(laboratorioFabrica);
        } else if (codigoProducto != null) {
            // Si se proporciona el laboratorio, devolver medicamentos por laboratorio
            return productoService.getProductoByCodigoProducto(codigoProducto);
        } else {
            // Si no se proporcionan filtros, devolver todos los medicamentos
            return null;
        }
    }

    @PutMapping(value = "actualizar", headers = "Accept=application/json")
    public void actualizarInventario(@RequestBody Producto producto){
        productoService.updateProducto(producto);
    }

    @DeleteMapping(value = "eliminar/{id}", headers = "Accept=application/json")
    public void eliminarProducto(@PathVariable Long id){
        productoService.deleteProducto(id);
    }

}
