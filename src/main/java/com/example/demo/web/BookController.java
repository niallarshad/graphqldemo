package com.example.demo.web;

import com.example.demo.model.Book;
import com.example.demo.model.BookInput;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class BookController {

    @Resource
    private BookRepository bookRepository;

    @Resource
    private BookService bookService;

    @QueryMapping
    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    @SchemaMapping(typeName = "Query", value = "book")
    public Book findOne(@Argument String id) {
        return bookRepository.findById(id).get();
    }

    @MutationMapping
    public Book addBook(@Argument BookInput book){
        return bookService.addBook(book);
    }

    @MutationMapping
    public Boolean deleteBook(@Argument String isn){
        return bookService.deleteBook(isn);
    }

    @MutationMapping
    public Book updateBook(@Argument BookInput book){
        return bookService.updateBook(book);
    }
}
