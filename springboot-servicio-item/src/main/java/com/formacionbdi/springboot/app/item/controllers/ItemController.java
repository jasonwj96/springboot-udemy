package com.formacionbdi.springboot.app.item.controllers;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import com.formacionbdi.springboot.app.item.models.entity.Item;
import com.formacionbdi.springboot.app.item.models.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@Slf4j
public class ItemController {

  @Autowired
  @Qualifier("serviceFeign")
  private ItemService itemService;

  @Value("${configuracion.texto}")
  private String text;

  @Autowired private Environment env;

  @GetMapping("/list")
  public List<Item> list() {
    return itemService.findAll();
  }

  @GetMapping("/obtener-config")
  public ResponseEntity<?> obtainConfig(@Value("${server.port}") String port) {
    log.info(text);

    Map<String, String> json = new HashMap<>();
    json.put("text", text);
    json.put("port", port);

    if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
      json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
      json.put("autor.email", env.getProperty("configuracion.autor.email"));
    }

    return new ResponseEntity<>(json, HttpStatus.OK);
  }

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Producto create(@RequestBody Producto producto) {
    return itemService.save(producto);
  }

  @PutMapping("/edit/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public Producto editProduct(@RequestBody Producto producto, @PathVariable Long id) {
    return itemService.editProduct(producto, id);
  }

  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProduct(@PathVariable Long id) {
    itemService.deleteProduct(id);
  }

  @HystrixCommand(fallbackMethod = "alternateMethod")
  @GetMapping("/findItem/{id}/cantidad/{cantidad}")
  public Item findItem(@PathVariable Long id, @PathVariable Integer cantidad) {
    return itemService.findById(id, cantidad);
  }

  public Item alternateMethod(Long id, Integer cantidad) {
    Item item = new Item();
    Producto producto = new Producto();

    producto.setId(id);
    producto.setNombre("Camara Sony");
    producto.setPrecio(500.00);

    item.setCantidad(cantidad);
    item.setProducto(producto);
    return item;
  }
}
