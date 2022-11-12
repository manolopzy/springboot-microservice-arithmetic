package com.worldexplorer.arithmeticgamification.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.worldexplorer.arithmeticgamification.client.dto.ArithmeticResultAttempt;

import lombok.extern.slf4j.Slf4j;

/**
 * esta implementacion de la interfaz {@link ArithmeticResultAttemptClient} 
 * utiliza Request/response patron para comunicar con el micro servicio de 
 * "arithmetic microservice", RestTemplate ya ha implementado el patron
 */
@Slf4j
@Component
class ArithmeticResultAttemptClientImpl implements ArithmeticResultAttemptClient {

	/**
	 * para mandar pediciones de rest a otros servidores
	 */
    private final RestTemplate restTemplate;
    /**
     * el url del servidor donde contiene los recursos
     */
    private final String arithmeticHost;

    @Autowired
    public ArithmeticResultAttemptClientImpl(final RestTemplate restTemplate,
                                                 @Value("${arithmeticHost}") final String arithmeticHost) {
        this.restTemplate = restTemplate;
        this.arithmeticHost = arithmeticHost;
    }

    @Override
    public ArithmeticResultAttempt retrieveArithmeticResultAttemptbyId(final String arithmeticResultAttemptId) {
    	log.info("Retrieve arithmetic attempt result information with attempt result id = " + arithmeticResultAttemptId);
    	ArithmeticResultAttempt result = restTemplate.getForObject(
                arithmeticHost + "/arithmetic/" + arithmeticResultAttemptId,
                ArithmeticResultAttempt.class);
    	if(result != null) {
    		log.info("Retrieve arithmetic attempt result information from arithmetic microservice = " + result.toString());
    		return result;
    	}
    	else {
    		log.error("Retrieved no information from arithmetic microservice = " + result);
    		return null;
    	}
        
    }
}
