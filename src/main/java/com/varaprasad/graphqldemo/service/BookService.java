package com.varaprasad.graphqldemo.service;

import com.varaprasad.graphqldemo.entity.Book;
import com.varaprasad.graphqldemo.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookService {
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

//    public Flux<Author> getAuthorsForBook(List<String> bookIds) {
//
//    }
}
