package de.holhar.spring.reactive.streamingstockquoteservice.service;

import de.holhar.spring.reactive.streamingstockquoteservice.domain.QuoteHistory;
import de.holhar.spring.reactive.streamingstockquoteservice.model.Quote;
import de.holhar.spring.reactive.streamingstockquoteservice.repositories.QuoteHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class QuoteHistoryServiceImpl implements QuoteHistoryService {

    private final QuoteHistoryRepository repository;

    @Override
    public Mono<QuoteHistory> saveQuoteToMono(Quote quote) {
        return repository.save(QuoteHistory.builder()
                .ticker(quote.getTicker())
                .price(quote.getPrice())
                .instant(quote.getInstant())
                .build());
    }
}
