package com.varaprasad.graphqldemo.entity;

import com.varaprasad.graphqldemo.requests.BookRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Book {
    @Id
    private String id;
    private String name;
    private int revision;
    private List<ObjectId> author;
    private List<String> genre;

    public Book(BookRequest bookRequest) {
        this.name = bookRequest.getName();
        this.revision = bookRequest.getRevision();
        this.author = Objects.requireNonNullElse(bookRequest.getAuthor(), Collections.emptyList());
        this.genre = Objects.requireNonNullElse(bookRequest.getGenre(), Collections.emptyList());
    }
}
