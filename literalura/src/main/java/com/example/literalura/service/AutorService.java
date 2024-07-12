package com.example.literalura.service;

import com.example.literalura.model.Autor;
import com.example.literalura.model.DatosLibro;
import com.example.literalura.model.Libro;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {
    public Libro addLibro(DatosLibro datosLibro, List<Autor> autores){
        Libro libroNuevo = new Libro(datosLibro);
        Optional<Autor> busquedaAutor = autores.stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(libroNuevo.getAutor().getNombre()))
                .findFirst();
        if(busquedaAutor.isPresent()){
            libroNuevo.setAutor(busquedaAutor.get());
            return libroNuevo;
        } else{
            return libroNuevo;
        }
    }

    public AutorService() {
    }
}
