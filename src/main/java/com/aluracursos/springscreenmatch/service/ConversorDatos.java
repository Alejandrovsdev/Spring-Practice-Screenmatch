package com.aluracursos.springscreenmatch.service;

import com.aluracursos.springscreenmatch.model.DatosSerie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversorDatos implements IConversorDatos{
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) throws JsonProcessingException {
        return objectMapper.readValue(json, clase);
    }
}
