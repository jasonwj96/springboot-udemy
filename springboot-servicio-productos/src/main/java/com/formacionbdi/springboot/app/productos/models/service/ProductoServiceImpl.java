package com.formacionbdi.springboot.app.productos.models.service;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.dao.ProductoDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServiceImpl implements IProductoService {

  @Autowired private ProductoDAO productoDAO;

  @Override
  @Transactional(readOnly = true)
  public List<Producto> findAll() {
    return (List<Producto>) productoDAO.findAll();
  }

  @Override
  public Producto findById(Long id) {
    return productoDAO.findById(id).orElse(null);
  }

  @Override
  @Transactional
  public Producto save(Producto producto) {
    return productoDAO.save(producto);
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
      productoDAO.deleteById(id);
  }
}
