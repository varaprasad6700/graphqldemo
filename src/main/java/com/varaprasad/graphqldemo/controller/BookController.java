package com.varaprasad.graphqldemo.controller;

import com.varaprasad.graphqldemo.entity.Author;
import com.varaprasad.graphqldemo.entity.Book;
import com.varaprasad.graphqldemo.requests.AuthorRequest;
import com.varaprasad.graphqldemo.requests.BookRequest;
import com.varaprasad.graphqldemo.response.AuthorResponse;
import com.varaprasad.graphqldemo.response.BookResponse;
import com.varaprasad.graphqldemo.service.AuthorService;
import com.varaprasad.graphqldemo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @QueryMapping
    public Mono<String> ping() {
        return Mono.just("pong!");
    }

    @QueryMapping
    public Mono<BookResponse> book(@Argument String id) {
        return bookService.getBookById(id).map(BookResponse::new);
    }

    @QueryMapping
    public Flux<BookResponse> books() {
        return bookService.getAllBooks().map(BookResponse::new);
    }

    @MutationMapping
    public Mono<AuthorResponse> author(@Argument AuthorRequest author) {
        return authorService.saveAuthor(new Author(author)).map(AuthorResponse::new);
    }

    @MutationMapping
    public Mono<BookResponse> book(@Argument BookRequest book) {
        return bookService.saveBook(new Book(book)).map(BookResponse::new);
    }

//    @SchemaMapping(typeName = "Book")
//    public Flux<AuthorResponse> author(BookResponse book) {
//        return bookService.getAuthorsForBook(List.of(book.getId()))
//                .log()
//                .map(o -> o.get(book.getId()))
//                .flatMapIterable(Function.identity())
//                .map(AuthorResponse::new);
//    }

    @BatchMapping(typeName = "Book")
    public Mono<Map<BookResponse, List<AuthorResponse>>> author(List<BookResponse> book) {
        List<String> bookIds = book.stream()
                .map(BookResponse::getId)
                .toList();
        return bookService.getAuthorsForBook(bookIds)
                .map(Map::entrySet)
                .flatMapIterable(Function.identity())
                .collectMap(
                        o -> book.stream()
                                .filter(bookResponse -> bookResponse.getId().equalsIgnoreCase(o.getKey()))
                                .findFirst()
                                .orElse(BookResponse.builder().id(o.getKey()).build()),
                        o -> o.getValue().stream().map(AuthorResponse::new).toList()
                );
    }

    @SubscriptionMapping
    public Flux<BookResponse> bookSubscribe() {
        return bookService.subscriptionMapping();
    }
}