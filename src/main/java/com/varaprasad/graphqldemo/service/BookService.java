package com.varaprasad.graphqldemo.service;

import com.varaprasad.graphqldemo.entity.Author;
import com.varaprasad.graphqldemo.entity.Book;
import com.varaprasad.graphqldemo.repo.BookRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@RequiredArgsConstructor
public class BookService {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final BookRepository bookRepository;

    public Mono<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Mono<Book> saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Mono<Map<String, List<Author>>> getAuthorsForBook(List<String> bookIds) {
        MatchOperation matchOperation = match(Criteria.where("_id").in(bookIds));
        LookupOperation lookupOperation = lookup("author", "author", "_id", "authors");
        return reactiveMongoTemplate
                .aggregate(newAggregation(matchOperation, lookupOperation), Book.class, AuthorAggregation.class)
                .collectMap(AuthorAggregation::getId, AuthorAggregation::getAuthors);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class AuthorAggregation {
    @Id
    private String id;
    private List<Author> authors;
}
