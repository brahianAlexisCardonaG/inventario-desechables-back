package com.ApiRest.demo.controller;

import com.ApiRest.demo.entity.Producto;
import com.ApiRest.demo.entity.Venta;
import com.ApiRest.demo.service.ProductoService;
import com.ApiRest.demo.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/api/venta")
public class VentaController {

    private VentaService ventaService;
    private ProductoService productoService;
    private final ResourceLoader resourceLoader;

    @Autowired
    public VentaController(VentaService ventaService, ProductoService productoService, ResourceLoader resourceLoader) {
        this.ventaService = ventaService;
        this.productoService = productoService;
        this.resourceLoader = resourceLoader;
    }

    @PostMapping(value = "crear", headers = "Accept=application/json")
    public ResponseEntity<?> crearVentas(@RequestBody List<Venta> ventas) {
        try {
            // Tu código existente para crear ventas y restar stock
            for (Venta venta : ventas) {
                ventaService.createdVenta(venta);
                productoService.restarStock(venta.getIdProducto(), venta.getCantidad());
            }

            // Cargar el contenido del archivo HTML
            Resource resource = resourceLoader.getResource("classpath:templates/factura-venta.html");
            String htmlContent = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            // Reemplazar marcadores de posición en el HTML con datos reales
            Map<String, String> replacements = new HashMap<>();
            replacements.put("{{fecha}}", "01/01/2018");
            replacements.put("{{estado}}", "Pendiente");
            replacements.put("{{cliente.nombre}}", "Bob Mart");
            replacements.put("{{cliente.contacto}}", "Daniel Marek");
            replacements.put("{{cliente.direccion}}", "43-190 Mikolow, Poland");
            replacements.put("{{cliente.email}}", "marek@daniel.com");
            replacements.put("{{cliente.telefono}}", "+48 123 456 789");

            StringBuilder ventasHtml = new StringBuilder();
            double subtotal = 0;
            List<Map<String, Object>> ventasList = new ArrayList<>();
            for (Venta venta : ventas) {
                Producto producto = productoService.getProductoById(venta.getIdProducto())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + venta.getIdProducto()));
                double total = producto.getValorUnitario() * venta.getCantidad();
                subtotal += total;

                replacements.put("{{id}}", venta.getId().toString());
                replacements.put("{{producto}}", venta.getNombreProducto());
                replacements.put("{{descripcion}}", venta.getNombreProducto());
                replacements.put("{{precioUnidad}}", producto.getValorUnitario().toString());
                replacements.put("{{cantidad}}", venta.getCantidad().toString());
                String convertTotal = String.valueOf(total);
                replacements.put("{{total}}", convertTotal);
            }
            replacements.put("{{ventas}}", ventasHtml.toString());

            double descuento = subtotal * 0.20;
            double iva = (subtotal - descuento) * 0.10;
            double totalFinal = subtotal - descuento + iva;

            replacements.put("{{subtotal}}", String.format("%.2f€", subtotal));
            replacements.put("{{descuentoPorcentaje}}", "20");
            replacements.put("{{descuento}}", String.format("%.2f€", descuento));
            replacements.put("{{ivaPorcentaje}}", "10");
            replacements.put("{{iva}}", String.format("%.2f€", iva));
            replacements.put("{{totalFinal}}", String.format("%.2f€", totalFinal));

            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                htmlContent = htmlContent.replace(entry.getKey(), entry.getValue());
            }

            // Generar el PDF desde la plantilla HTML
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);

            // Convertir el PDF a base64
            String base64PDF = java.util.Base64.getEncoder().encodeToString(outputStream.toByteArray());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(base64PDF, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al leer el archivo HTML: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al generar el PDF: " + e.getMessage());
        }
    }


    @GetMapping(value = "listar", headers = "Accept=application/json")
    public List<Venta> ListarVentas(
            @RequestParam(required = false) Double valorTotal,
            @RequestParam(required = false) String nombreProducto,
            @RequestParam(required = false) String fecha,
            @RequestParam(required = false) Integer codigoProducto) {

        if (valorTotal == null && nombreProducto == null && fecha == null && codigoProducto == null) {
            return ventaService.listarVenta();
        } else if (valorTotal != null) {
            return ventaService.listarVentaByValorTotal(valorTotal);
        } else if (nombreProducto != null) {
            return ventaService.listarVentaByNombreProducto(nombreProducto);
        } else if (fecha != null) {
            return ventaService.listarVentaByFecha(fecha);
        } else if (codigoProducto != null) {
            return ventaService.getProductoByCodigoProducto(codigoProducto);
        } else {
            return null;
        }
    }
}
