package com.formacionbdi.springboot.app.item.clients;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

// El parametro name es la variable spring.application.name en el application.properties
@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {

  @GetMapping("/list")
  public List<Producto> list();

  @GetMapping("/findProduct/{id}")
  public Producto getProduct(@PathVariable Long id);

  @PostMapping("/create")
  public Producto create(@RequestBody Producto producto);

  @PutMapping("/edit/{id}")
  public Producto editProduct(@RequestBody Producto producto, @PathVariable Long id);

  @DeleteMapping("/delete/{id}")
  public void deleteProduct(@PathVariable Long id);
}
