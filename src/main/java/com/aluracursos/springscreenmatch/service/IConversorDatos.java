package com.aluracursos.springscreenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IConversorDatos {
    <T> T obtenerDatos(String json, Class<T> clase) throws JsonProcessingException;
}
