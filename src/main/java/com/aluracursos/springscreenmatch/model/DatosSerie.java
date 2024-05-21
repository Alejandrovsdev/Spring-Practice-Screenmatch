package com.aluracursos.springscreenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignora el resto de los campos, si no se coloca saldra error dado a que leera todos los datos del json y puede no encontrar los datos que no esten deserializados
public record DatosSerie(
        @JsonAlias("Title") String titulo,
        @JsonAlias("totalSeasons") Integer totalTemporadas,
        @JsonAlias("imdbRating") String evaluacion) {

    // JsonAlias: solo lectura, JsonProperty: Lectura y escritura (Get Post)
}
