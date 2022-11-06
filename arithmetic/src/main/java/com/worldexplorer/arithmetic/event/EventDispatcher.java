package com.worldexplorer.arithmetic.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * This is the dispatcher for sending events to the event bus
 * 
 * @author tanku
 *
 */
@Component
public class EventDispatcher {
	
	private RabbitTemplate rabbitTemplate;
	// The exchange used to send anything related to our Arithmetic topic
	private String arithmeticExchange;
	// The routing key to use to send this particular event
	private String arithmeticSolvedRoutingKey;

	@Autowired
	public EventDispatcher(final RabbitTemplate rabbitTemplate,
			@Value("${arithmetic.exchange}") final String arithmeticExchange,
			@Value("${arithmetic.solved.key}") final String arithmeticSolvedRoutingKey) {
		this.rabbitTemplate = rabbitTemplate;
		this.arithmeticExchange = arithmeticExchange;
		this.arithmeticSolvedRoutingKey = arithmeticSolvedRoutingKey;
	}

	public void send(final ArithmeticSolvedEvent arithmeticSolvedEvent) {
		rabbitTemplate.convertAndSend(arithmeticExchange, arithmeticSolvedRoutingKey,
				arithmeticSolvedEvent);
	}
}
