package com.worldexplorer.arithmeticgamification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

/**
 * Configurar RabbitMQ para usar eventos en nuestra cliente app
 */
@Configuration
public class RabbitMQConfig implements RabbitListenerConfigurer {

	/**
	 * the topic to exchange among microservices
	 * @param exchangeName
	 * @return
	 */
    @Bean
    public TopicExchange arithmeticExchange(@Value("${arithmetic.exchange}") final String exchangeName) {
        return new TopicExchange(exchangeName);
    }

    /**
     * 
	 * Construct a new queue, specify its name and durability flag.
	 * 
	 * @param name the name of the queue.
	 * @param durable true if we are declaring a durable queue (the queue will survive a server restart)
	 */
    @Bean
    public Queue gamificationarithmeticQueue(@Value("${arithmetic.queue}") final String queueName) {
        return new Queue(queueName, true);
    }

    /**
     * binding the queue, topic, and with a routing key to specify 
     * what kind of events to receive, for example, if the routing key 
     * is arithmetic.*, then our queue will contain all events start 
     * with "arithmetic", like "arithmetic.solved"...
     * @param queue
     * @param exchange
     * @param routingKey
     * @return
     */
    @Bean
    Binding binding(final Queue queue, final TopicExchange exchange,
                    @Value("${arithmetic.anything.routing-key}") final String routingKey) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    //-------------configurar el convertidor de json para los mensajes-----
    //como consumidor, convierte los mesajes de json a java objects
    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}
