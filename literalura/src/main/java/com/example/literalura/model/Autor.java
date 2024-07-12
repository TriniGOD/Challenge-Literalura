package com.example.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name ="autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (unique = true)
    private String nombre;
    private Integer nacimiento;
    private Integer fallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + '\n' +
                "Nacimiento: " + nacimiento + '\n'+
                "Fallecimiento: " + fallecimiento + '\n'+
                "Libros: " + libros.stream().map(Libro::getTitulo).toList()
                +'\n';
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        if(datosAutor.yearNacimiento() == null){
            this.nacimiento = 0;
        } else{
            this.nacimiento = datosAutor.yearNacimiento();
        }
        if(datosAutor.yearFallecimiento()==null){
            this.fallecimiento = 0;
        }else{
            this.fallecimiento = datosAutor.yearFallecimiento();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getFallecimiento() {
        return fallecimiento;
    }

    public void setFallecimiento(Integer fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(libro -> libro.setAutor(this));
    }
}
