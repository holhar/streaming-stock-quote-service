package de.holhar.spring.reactive.streamingstockquoteservice.service;

import de.holhar.spring.reactive.streamingstockquoteservice.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.rabbitmq.Receiver;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Profile("rabbitmq")
@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqQuoteRunner implements CommandLineRunner {

    private final QuoteGeneratorService quoteGeneratorService;
    private final QuoteMessageSender quoteMessageSender;
    private final Receiver receiver;

    @Override
    public void run(String... args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(25);
        quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100L))
                .take(25)
                .log("Got quote")
                .flatMap(quoteMessageSender::sendQuoteMessage)
                .subscribe(
                        result -> {
                            log.debug("Send message to RabbitMQ");
                            countDownLatch.countDown();
                        },
                        throwable -> log.error("Got error", throwable),
                        () -> log.debug("All done!!!")
                );

        countDownLatch.await(1, TimeUnit.SECONDS);

        AtomicInteger receivedCount = new AtomicInteger();
        receiver.consumeAutoAck(RabbitConfig.QUEUE)
                .log("Msg reveiver")
                .subscribe(
                        msg -> log.debug("Received message # {} - {}", receivedCount.incrementAndGet(), new String(msg.getBody())),
                        throwable -> log.debug("Error receiving", throwable),
                        () -> log.debug("Complete")
                );
    }
}
