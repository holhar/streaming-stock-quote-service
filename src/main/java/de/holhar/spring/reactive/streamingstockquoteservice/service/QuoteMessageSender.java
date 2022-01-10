package de.holhar.spring.reactive.streamingstockquoteservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.holhar.spring.reactive.streamingstockquoteservice.config.RabbitConfig;
import de.holhar.spring.reactive.streamingstockquoteservice.model.Quote;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@Component
@RequiredArgsConstructor
public class QuoteMessageSender {

    private final ObjectMapper objectMapper;
    private final Sender sender;

    @SneakyThrows
    public Mono<Void> sendQuoteMessage(Quote quote) {
        byte[] jsonBytes = objectMapper.writeValueAsBytes(quote);
        return sender.send(Mono.just(new OutboundMessage("", RabbitConfig.QUEUE, jsonBytes)));
    }
}
