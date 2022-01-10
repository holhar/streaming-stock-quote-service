package de.holhar.spring.reactive.streamingstockquoteservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Profile("rabbitmq")
@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqQuoteRunner implements CommandLineRunner {

    private final QuoteGeneratorService quoteGeneratorService;
    private final QuoteMessageSender quoteMessageSender;

    @Override
    public void run(String... args) {
        quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100L))
                .take(25)
                .log("Got quote")
                .flatMap(quoteMessageSender::sendQuoteMessage)
                .subscribe(
                        result -> log.debug("Send message to RabbitMQ"),
                        throwable -> log.error("Got error", throwable),
                        () -> log.debug("All done!!!")
                );
    }
}
