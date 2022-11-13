package com.varaprasad.graphqldemo.service;

import com.varaprasad.graphqldemo.entity.Author;
import com.varaprasad.graphqldemo.repo.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Mono<Author> getAuthorById(String id) {
        return authorRepository.findById(id);
    }

    public Flux<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Mono<Author> saveAuthor(Author author) {
        return authorRepository.save(author);
    }
}
