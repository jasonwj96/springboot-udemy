package com.formacionbdi.springboot.app.commons.models.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "productos") /*Por defecto se toma el nombre de la clase para mapear a la tabla*/
public class Producto implements Serializable {

  private static final long serialVersionUID = -3918029655544251994L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;
  private Double precio;

  @Column(name = "create_at")
  @Temporal(TemporalType.DATE)
  private Date createdAt;

  @Transient
  private Integer port;
}
