package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookInput {
    private String isn;
    private String title;
    private String publisher;
    private List<String> authors;
    private String publishedDate;
}
