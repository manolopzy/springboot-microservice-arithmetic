package com.worldexplorer.arithmetic;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Here we use RabbitMQ for event-driven communication among 
 * micro services, this arithmetic micro service will send 
 * messages to RabbitMQ which distributes the messages to 
 * their corresponding subscribers
 * @author tanku
 *
 */
@Configuration
public class RabbitMQConfig {

	@Bean
	public TopicExchange arithmeticExchange(@Value("${arithmetic.exchange}") final String exchangeName) {
		return new TopicExchange(exchangeName);
	}

	/**
	 * RabbitTemplate is used for sending messages to RabbitMQ for 
	 * subscribers to consume
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}

	/**
	 * Arithmetic service as a producer, sends messages mapped to json 
	 * to RabbitMQ
	 * instead of default java serialization mechanism converters, we use the following 
	 * converter for mapping java objects to json
	 * Java serialization of messages include information of the full 
	 * name of the class for later deserialization. In that case 
	 * all subscribers consume the message have to use the 
	 * same class name, in the same package. This leads to 
	 * tight coupling between services.
	 * @return
	 */
	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
