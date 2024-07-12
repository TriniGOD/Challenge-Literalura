package com.example.literalura.principal;

import com.example.literalura.model.*;
import com.example.literalura.repository.AutorRepository;
import com.example.literalura.repository.LibroRepository;
import com.example.literalura.service.AutorService;
import com.example.literalura.service.ConsumoAPI;
import com.example.literalura.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE ="https://gutendex.com/books/?search=%20";
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private AutorService service = new AutorService();

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu(){
        var opcion = -1;
        while (opcion!=0){
            var menu = """
                    1 - Buscar libros por titulo 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en determinado año
                    5 - Listar libros por idioma
           
                    0 - Salir
                    """;
            System.out.println(menu);
            System.out.println("Escribe la opcion que desees: ");
            try{
                opcion = teclado.nextInt();
            } catch (InputMismatchException e){
                System.out.println("No escribiste un valor correcto");
                teclado.nextLine();
            }
            switch (opcion){
                case 1:
                    buscarLibrosPorTitulo();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    busquedaYear();
                    break;
                case 5:
                    buscarIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarIdioma() {
        System.out.println("Categorias: ");
        System.out.println("español");
        System.out.println("ingles");
        System.out.println("portugues");
        System.out.println("frances");
        teclado.nextLine();
        System.out.println("Escribe la categoria");
        try{
            var idiomaBuscado = teclado.nextLine();
            var idioma = Idioma.fromBusquedaAPP(idiomaBuscado.toLowerCase());
            List<Libro> librosEncontrados = libroRepository.findByIdioma(idioma);
            if(librosEncontrados.isEmpty()){
                System.out.println("No se encontró nada");
            }else{
                librosEncontrados.forEach(System.out::println);
            }
        }catch (IllegalArgumentException e){
            System.out.println("No se selecciono la categoria correctamente, o bien no se encuentran libros en ese idioma");
        }

    }

    private void busquedaYear() {
        Integer fecha = 0;

        System.out.println("Escriba la fecha que deseas buscar");
        try{
            fecha = teclado.nextInt();
        } catch (InputMismatchException e){
            System.out.println("No escribiste un valor correcto");
            teclado.nextLine();
            return;
        }
        List<Autor> autoresVivos = autorRepository.autoresVivosPorFecha(fecha);
            if(!autoresVivos.isEmpty()){
            autoresVivos.forEach(System.out::println);
            System.out.println();
        }else{
            System.out.println("No se encontro nada");
        }
    }

    private void listarAutores() {
        List <Autor> autors = autorRepository.findAll();
        System.out.println("---------Lista de autores--------- ");
        autors.forEach(System.out::println);
        System.out.println();
    }

    private void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        if(libros.isEmpty()){
            System.out.println("No hay libros");
        }else{
            System.out.println("---------Listado de libros--------");
            System.out.println();
            libros.forEach(System.out::println);
        }
    }

    private void buscarLibrosPorTitulo() {
        Libro libroNuevo = null;
        System.out.println("Escribe el nombre del libro");
        teclado.nextLine();
        var tituloLibro = teclado.nextLine();
        //Buscar en la api
        var json = consumoAPI.obtenerDatos(URL_BASE+ tituloLibro.replace(" ", "+"));
        var results = conversor.obtenerDatos(json, Datos.class);
        //tomar elemento cualquiera
        Optional<DatosLibro> datosLibro = results.libros().stream()
                .filter(d -> d.titulo().toLowerCase().contains(tituloLibro.toLowerCase()))
                .findAny();
        if(datosLibro.isPresent()){
            //guardar datos en la clase
            System.out.println("Libro: " + datosLibro.get());
            Libro verificarLibro = libroRepository.findByTituloContainsIgnoreCase(datosLibro.get().titulo());
            if (verificarLibro == null) {
                if(datosLibro.get().autor() == null){
                    System.out.println("Existe el libro, pero no tiene autor");
                    System.out.println("Regresando...");
                }else{
                    List<Autor> autores = autorRepository.findAll();
                    try{
                         libroNuevo = service.addLibro(datosLibro.get(), autores);
                    }catch (Exception e){
                        libroNuevo = new Libro(datosLibro.get());
                        libroNuevo.setAutor(null);
                    }
                    libroRepository.save(libroNuevo);
                    System.out.println("Libro guardado");
                }
            }else{
                System.out.println("El libro ya esta en la base de datos");
                System.out.println("Regresando...");
            }
        }else{
            System.out.println("No se encontró el libro");
        }
    }
}
