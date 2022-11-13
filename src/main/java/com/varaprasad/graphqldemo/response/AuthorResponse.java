package com.varaprasad.graphqldemo.response;

import com.varaprasad.graphqldemo.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse {
    private String id;
    private String name;

    public AuthorResponse(Author author) {
        this.id = author.getId();
        this.name = author.getName();
    }
}
