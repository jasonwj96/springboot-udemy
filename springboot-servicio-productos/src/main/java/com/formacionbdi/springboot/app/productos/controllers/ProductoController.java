package com.formacionbdi.springboot.app.productos.controllers;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.service.IProductoService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductoController {

  @Autowired private Environment env;

  @Value("${server.port}")
  private Integer port;

  @Autowired private IProductoService productoService;

  @GetMapping("/list")
  public List<Producto> list() {
    return productoService.findAll().stream()
        .map(
            producto -> {
              // producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
              producto.setPort(port);
              return producto;
            })
        .collect(Collectors.toList());
  }

  @GetMapping("/findProduct/{id}")
  public Producto getProduct(@PathVariable Long id) {
    Producto producto = productoService.findById(id);
    // producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
    producto.setPort(port);
    //    boolean ok = false;
    //    if (!ok) throw new Exception("No se pudo cargar el producto");
    //    Thread.sleep(2000L);
    return producto;
  }

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Producto createProduct(@RequestBody Producto producto) {
    return productoService.save(producto);
  }

  @PutMapping("/edit/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public Producto editProduct(@RequestBody Producto producto, @PathVariable Long id) {
    Producto prod = productoService.findById(id);
    producto.setId(prod.getId());
    producto.setPrecio(prod.getPrecio());
    return productoService.save(producto);
  }

  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProduct(@PathVariable Long id) {
    productoService.deleteById(id);
  }
}