package com.example.literalura.model;

public enum Idioma {
    INGLES ("en", "ingles"),
    SPANISH("es", "español"),
    FRANCES("fr", "frances"),
    PORTUGUES("pt", "portugues");

    private String idiomaAPI;
    private String idiomaBusqueda;

    Idioma(String idiomaAPI, String iidomaBusqueda) {
        this.idiomaAPI = idiomaAPI;
        this.idiomaBusqueda = iidomaBusqueda;
    }

    public static Idioma fromAPI (String apiRequest){
        for(Idioma idioma : Idioma.values()){
            if(idioma.idiomaAPI.equalsIgnoreCase(apiRequest)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningun libro en el idioma: " + apiRequest);
    }

    public static Idioma fromBusquedaAPP(String appRequest){
        for(Idioma idioma : Idioma.values()){
            if(idioma.idiomaBusqueda.equalsIgnoreCase(appRequest)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningun libro en el idioma: " + appRequest);
    }
}
