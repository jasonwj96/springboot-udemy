package com.formacionbdi.springboot.app.item.models.service;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import com.formacionbdi.springboot.app.item.models.entity.Item;
import java.util.List;

public interface ItemService {

  public List<Item> findAll();

  public Item findById(Long id, Integer cantidad);

  public Producto save(Producto producto);

  public Producto editProduct(Producto producto, Long id);

  public void deleteProduct(Long id);
}