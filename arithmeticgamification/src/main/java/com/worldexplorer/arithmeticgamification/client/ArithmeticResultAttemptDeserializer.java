package com.worldexplorer.arithmeticgamification.client;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.worldexplorer.arithmeticgamification.client.dto.ArithmeticResultAttempt;

import lombok.extern.slf4j.Slf4j;

/**
 * deserializacion de datos del microservicio de arithmetic a 
 * su correspondiente representacion usada en este mismo 
 * microservicio
 * Es una clase extendida de {@link JsonDeserializer<T>}, la forma de los 
 * datos de comunicacion es de json, asi, los microservicios 
 * descoplan mas en vez de usar java serializacion la cual contiene 
 * informacion de clases, paquetes, ...
 */
@Slf4j
public class ArithmeticResultAttemptDeserializer
        extends JsonDeserializer<ArithmeticResultAttempt> {

    @Override
    public ArithmeticResultAttempt deserialize(JsonParser jsonParser,
                                                   DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        log.info("arithmetic attempt result data received from arithmetic app : " + node.toString());
        return new ArithmeticResultAttempt(node.get("user").get("alias").asText(),
                node.get("arithmetic").get("factorA").asInt(),
                node.get("arithmetic").get("factorB").asInt(),
                node.get("resultAttempt").asInt(),
                node.get("correct").asBoolean());
    }
}
