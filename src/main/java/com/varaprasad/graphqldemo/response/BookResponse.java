package com.varaprasad.graphqldemo.response;

import com.varaprasad.graphqldemo.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {
    private String id;
    private String name;
    private int revision;
    private List<AuthorResponse> author;
    private List<String> genre;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.revision = book.getRevision();
        this.genre = book.getGenre();
    }
}
