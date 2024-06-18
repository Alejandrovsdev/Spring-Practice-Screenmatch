package com.aluracursos.springscreenmatch;

import com.aluracursos.springscreenmatch.model.Episodio;
import com.aluracursos.springscreenmatch.service.ConversorDatos;
import com.aluracursos.springscreenmatch.model.DatosEpisodio;
import com.aluracursos.springscreenmatch.model.DatosSerie;
import com.aluracursos.springscreenmatch.model.DatosTemporada;
import com.aluracursos.springscreenmatch.service.ConsumoApi;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private final String URL = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=c924f56b";
    private ConsumoApi consumoAPI = new ConsumoApi();
    private ConversorDatos conversor = new ConversorDatos();
    public void muestraElMenu() throws JsonProcessingException {
        System.out.println("Escribe el nombre de la série que deseas buscar");
        var nombreSerie = teclado.nextLine();

        var json = consumoAPI.obtenerDatos(URL + nombreSerie.replace(" ", "+") + APIKEY);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);

        List<DatosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= datos.totalTemporadas(); i++) {
            json = consumoAPI.obtenerDatos(URL + nombreSerie.replace(" ", "+") + "&Season=" + i + APIKEY);
            DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(datosTemporada);
        }
        //temporadas.forEach(System.out::println);

       /* for (int i = 0; i < datos.totalTemporadas(); i++) {
            List<DatosEpisodio> episodiosTemporadas = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporadas.size(); j++) {
                System.out.println(episodiosTemporadas.get(j).titulo()); //imprime todos los episodios de todas las temporadas de la serie
            }
        }
        */

        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo()))); // usa lambda para imprimir todos los episodios de todas las temporadas de la serie

        //temporadas.get(1).episodios().forEach(e -> System.out.println(e.titulo())); //imprime todos los episodios de la primera temporada (1)

        /*List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList()); //datos de cada episodio con stream y lambdas

        datosEpisodios.stream()
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed()) //compara los episodios por evaluacion y los ordena de mayor a menor
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A")) //filtra para evitar los episodios que en evaluacion muestran N/A
                .limit(5) // limita a 5 episodios, es decir top 5.
                .forEach(e -> System.out.println(e.titulo() + " = " + e.evaluacion())); //imprime tituloEpisodio = evaluacion
        */
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(),d)))
                .collect(Collectors.toList());

        episodios.stream()
                .sorted(Comparator.comparing(Episodio::getEvaluacion).reversed())
                .filter(e -> !Double.isNaN(e.getEvaluacion())) // Filtra las evaluaciones inválidas
                .limit(5)   // limita a 5;
                .forEach(e -> System.out.println("Episodio: " +  e.getTitulo() + " |" + " Temporada: " + e.getTemporada() + " | Evaluacion = " + e.getEvaluacion()));

    }

}
