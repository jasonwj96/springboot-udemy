package com.formacionbdi.springboot.app.item.models.service;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import com.formacionbdi.springboot.app.item.models.entity.Item;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("restTemplate")
public class ItemServiceImpl implements ItemService {

  @Autowired private RestTemplate clienteRest;

  @Override
  public List<Item> findAll() {
    List<Producto> productos =
        Arrays.asList(clienteRest.getForObject("http://servicio-productos/list", Producto[].class));
    return productos.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
  }

  @Override
  public Item findById(Long id, Integer cantidad) {
    Map<String, String> pathVariables = new HashMap<>();
    pathVariables.put("id", id.toString());
    Producto producto =
        clienteRest.getForObject(
            "http://servicio-productos/findProduct/{id}", Producto.class, pathVariables);
    return new Item(producto, cantidad);
  }

  @Override
  public Producto save(Producto producto) {
    HttpEntity<Producto> body = new HttpEntity<>(producto);
    ResponseEntity<Producto> responseEntity =
        clienteRest.exchange(
            "http://servicio-productos/create", HttpMethod.POST, body, Producto.class);

    return responseEntity.getBody();
  }

  @Override
  public Producto editProduct(Producto producto, Long id) {
    HttpEntity<Producto> body = new HttpEntity<>(producto);
    Map<String, String> pathVariables = new HashMap<>();

    pathVariables.put("id", id.toString());

    ResponseEntity<Producto> responseEntity =
        clienteRest.exchange(
            "http://servicio-productos/edit/{id}",
            HttpMethod.PUT,
            body,
            Producto.class,
            pathVariables);

    return responseEntity.getBody();
  }

  @Override
  public void deleteProduct(Long id) {
    Map<String, String> pathVariables = new HashMap<>();
    pathVariables.put("id", id.toString());
    clienteRest.delete("http://servicio-productos/delete/{id}", pathVariables);
  }
}
