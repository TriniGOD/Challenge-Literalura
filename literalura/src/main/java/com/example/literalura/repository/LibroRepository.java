package com.example.literalura.repository;

import com.example.literalura.model.Idioma;
import com.example.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository <Libro, Long> {
    Libro findByTituloContainsIgnoreCase(String titulo);
    List<Libro> findByIdioma(Idioma idioma);
}
