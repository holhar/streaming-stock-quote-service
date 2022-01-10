package de.holhar.spring.reactive.streamingstockquoteservice.service;

import de.holhar.spring.reactive.streamingstockquoteservice.domain.QuoteHistory;
import de.holhar.spring.reactive.streamingstockquoteservice.model.Quote;
import reactor.core.publisher.Mono;

public interface QuoteHistoryService {
    Mono<QuoteHistory> saveQuoteToMongo(Quote quote);
}
