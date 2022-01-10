package de.holhar.spring.reactive.streamingstockquoteservice.repositories;

import de.holhar.spring.reactive.streamingstockquoteservice.domain.QuoteHistory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface QuoteHistoryRepository extends ReactiveMongoRepository<QuoteHistory, String> {
}
