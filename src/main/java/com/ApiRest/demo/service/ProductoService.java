package com.ApiRest.demo.service;

import com.ApiRest.demo.entity.Producto;
import com.ApiRest.demo.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private IProductoRepository productoRepository;

    private JavaMailSender javaMailSender;

    @Autowired
    public ProductoService(IProductoRepository productoRepository, JavaMailSender javaMailSender) {
        this.productoRepository = productoRepository;
        this.javaMailSender = javaMailSender;
    }

    public void createdProducto(Producto producto){
        productoRepository.save(producto);
    }

    public void updateProducto(Producto producto){
        productoRepository.save(producto);
    }

    public void deleteProducto(Long id){
        productoRepository.deleteById(id);
    }

    public List<Producto> getAllProducto(){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return productoRepository.findAll(sort);
    }

    public Optional<Producto> getProductoById(Long id){
        return productoRepository.findById(id);
    }

    public List<Producto> getProductoByNombre(String nombre){
        return productoRepository.findByNombreContaining(nombre);
    }

    public List<Producto> getProductoByLaboratorio(String laboratorioFabrica){
        return productoRepository.findByLaboratorioFabrica(laboratorioFabrica);
    }

    public List<Producto> getProductoByCodigoProducto(Integer codigoProducto){
        return productoRepository.findByCodigoProducto(codigoProducto);
    }

    @Transactional
    public void restarStock(Long id, Long discountStock) {
        Optional<Producto> idInventario = productoRepository.findById(id);
        if(idInventario.isPresent()) {
            Producto producto = idInventario.get();
            Long newStock = producto.getCantidadStock() - discountStock;

            if(newStock <= 0){
                throw new RuntimeException("Por favor verifique la cantidad ya que no debe superar el stock actual.");
            }

            producto.setCantidadStock(newStock);
            productoRepository.save(producto);
        }
    }

    @Scheduled(fixedRate = 300000) // Verifica cada minuto (en milisegundos)
    public void verificarCantidad() {
        // Obtener registros con cantidad menor o igual a dos
        List<Producto> registros = productoRepository.findByCantidadStockLessThan(3L);

        if (!registros.isEmpty()) {
            // Construir el mensaje de correo electrónico
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("Registros con cantidad igual o menor a dos:\n");
            for (Producto registro : registros) {
                mensaje.append(registro.toString()).append("\n");
            }

            // Enviar el correo electrónico
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo("dimigra35@gmail.com");
            mail.setSubject("Registros con cantidad igual o menor a dos");
            mail.setText(mensaje.toString());
            javaMailSender.send(mail);
        }
    }

}
