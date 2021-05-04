package com.formacionbdi.springboot.app.usuarios.models.dao;

import com.formacionbdi.springboot.app.usuarios.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "usuarios", collectionResourceRel = "usuarios")
public interface UsuarioDAO extends PagingAndSortingRepository<Usuario, Long> {
  Usuario findByUsername(String username);

  @Query(value = "select u from Usuario u where u.username=?1")
  Usuario obtenerPorUsername(String username);
}
