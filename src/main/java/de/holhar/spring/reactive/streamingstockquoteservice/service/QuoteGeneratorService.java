package de.holhar.spring.reactive.streamingstockquoteservice.service;

import de.holhar.spring.reactive.streamingstockquoteservice.model.Quote;
import reactor.core.publisher.Flux;

import java.time.Duration;

public interface QuoteGeneratorService {
    Flux<Quote> fetchQuoteStream(Duration period);
}
