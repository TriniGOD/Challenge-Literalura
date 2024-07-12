package com.example.literalura.repository;

import com.example.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository <Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE a.nacimiento <= :endYear AND (a.fallecimiento IS NULL OR a.fallecimiento >= :endYear)")
    List<Autor> autoresVivosPorFecha(@Param("endYear") Integer endYear);
}

