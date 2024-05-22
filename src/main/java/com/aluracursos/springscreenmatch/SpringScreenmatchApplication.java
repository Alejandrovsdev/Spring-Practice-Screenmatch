package com.aluracursos.springscreenmatch;

import com.aluracursos.springscreenmatch.model.DatosPelicula;
import com.aluracursos.springscreenmatch.model.DatosSerie;
import com.aluracursos.springscreenmatch.service.ConsumoApi;
import com.aluracursos.springscreenmatch.service.ConversorDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SpringScreenmatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scan = new Scanner(System.in);
        var consumoApi = new ConsumoApi();

        // Pide el título de una pelicula o serie al usuario y le da formato para poder ser introducida a la Url
        System.out.println("Ingrese el titulo de una pelicula o serie");
        String titulo = scan.nextLine();
        titulo = titulo.replace(" ", "+");
        String enlace = "http://www.omdbapi.com/?t="+titulo+"&apikey=c924f56b";

        // Obtiene el json con los datos de la serie y los deserializa a una clase sea DatosSerie o DatosPelicula
        var json = consumoApi.obtenerDatos(enlace);
        ConversorDatos conversor = new ConversorDatos();

        // Verificar el tipo antes de deserializar
        String tipo = conversor.obtenerTipo(json);

        // Verifica el tipo de contenido y obtiene los datos de la serie o la película
        if ("series".equalsIgnoreCase(tipo)) {
            var datoSerie = conversor.obtenerDatos(json, DatosSerie.class);
            System.out.println(datoSerie.titulo() + " " + datoSerie.totalTemporadas());
        } else if ("movie".equalsIgnoreCase(tipo)) {
            var datosPelicula = conversor.obtenerDatos(json, DatosPelicula.class);
            System.out.println(datosPelicula.titulo() + " " + datosPelicula.evaluacion());
        } else {
            System.out.println("El tipo de contenido no es ni serie ni película.");
        }
    }
}
