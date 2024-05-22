package com.aluracursos.springscreenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosPelicula(
        @JsonAlias("Title") String titulo,
        @JsonAlias("imdbRating") String evaluacion,
        @JsonAlias("Year") String anio,
        @JsonAlias("Genre") String genero,
        @JsonAlias("Director") String director,
        @JsonAlias("Actors") String actores
) {
}
