package com.worldexplorer.arithmeticgamification.event;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.worldexplorer.arithmeticgamification.service.GameService;

import lombok.extern.slf4j.Slf4j;

/**
 * Esta clase trata de los eventos recibidos
 */
@Slf4j
@Component
public class EventHandler {

    private GameService gameService;

    public EventHandler(final GameService gameService) {
        this.gameService = gameService;
    }

    @RabbitListener(queues = "${arithmetic.queue}")
    void handleMultiplicationSolved(final ArithmeticSolvedEvent event) {
        log.info("Arithmetic Solved Event received with userid: {}, arithmeticAttemptResultId: {}", event.getUserId(), event.getArithmeticResultAttemptId());
        try {
        	gameService.newArithmeticAttempt(event.getUserId(),
                    event.getArithmeticResultAttemptId(),
                    event.isCorrect());
        } catch (final Exception e) {
            log.error("Error when trying to process " + event.getClass().getName(), e);
            // Avoids the event to be re-queued and reprocessed.
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
