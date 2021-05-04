package com.formacionbdi.springboot.app.item.models.service;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import com.formacionbdi.springboot.app.item.clients.ProductoClienteRest;
import com.formacionbdi.springboot.app.item.models.entity.Item;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("serviceFeign")
public class ItemServiceFeign implements ItemService {

  @Autowired ProductoClienteRest productoClienteRest;

  @Override
  public List<Item> findAll() {
    return productoClienteRest.list().stream()
        .map(p -> new Item(p, 1))
        .collect(Collectors.toList());
  }

  @Override
  public Item findById(Long id, Integer cantidad) {
    return new Item(productoClienteRest.getProduct(id), cantidad);
  }

  @Override
  public Producto save(Producto producto) {
    return productoClienteRest.create(producto);
  }

  @Override
  public Producto editProduct(Producto producto, Long id) {
    return productoClienteRest.editProduct(producto, id);
  }

  @Override
  public void deleteProduct(Long id) {
    productoClienteRest.deleteProduct(id);
  }
}
