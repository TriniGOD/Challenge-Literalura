package com.example.literalura.model;

import jakarta.persistence.*;

import java.util.Optional;
@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private Integer numeroDescargas;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    @ManyToOne (cascade = CascadeType.ALL)
    private Autor autor;

    public Libro() {
    }

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idioma = Idioma.fromAPI((datosLibro.idioma().get(0)));
        this.numeroDescargas = datosLibro.numeroDescargas();
        Optional<DatosAutor> autor = datosLibro.autor().stream()
                .findFirst();
        if(autor.isPresent()){
            this.autor = new Autor(autor.get());
        }else{
            System.out.println("El libro no tiene autor registrado");
            this.autor = null;
        }
    }

    @Override
    public String toString() {
        return "Titulo: " + titulo + '\n'
                + "Idioma: " + idioma + '\n' +
                "Autor: " + autor.getNombre() +'\n'
                + "NumeroDescargas: " + numeroDescargas
                + '\n';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }
}
