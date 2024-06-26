package com.aluracursos.springscreenmatch.service;

import com.aluracursos.springscreenmatch.model.DatosSerie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ConversorDatos implements IConversorDatos{

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) throws JsonProcessingException {
        return objectMapper.readValue(json, clase);
    }

    public String obtenerTipo(String json) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path("Type").asText(); // Verifica el campo "Type" del JSON
    }
}
