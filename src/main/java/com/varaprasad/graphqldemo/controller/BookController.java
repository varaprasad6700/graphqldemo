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
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

//    @BatchMapping
//    public Mono<Map<BookResponse, AuthorResponse>> author(List<BookResponse> book) {
//        book.stream().map(BookResponse::getId)
//    }
}