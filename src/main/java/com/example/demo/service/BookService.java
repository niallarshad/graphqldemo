package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.model.BookInput;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.stream.Stream;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(BookInput bookInput){
        Book b = new Book(bookInput.getIsn(),
                bookInput.getTitle(), bookInput.getPublisher(),
                bookInput.getAuthors(), bookInput.getPublishedDate());
        return bookRepository.save(b);
    }

    public Boolean deleteBook(String isn) {
        bookRepository.deleteById(isn);
        return true;
    }

    public Book updateBook(BookInput book) {
        Book b = bookRepository.findById(book.getIsn()).orElseThrow(() -> new IllegalArgumentException("item not found"));
        b.setAuthors(book.getAuthors());
        b.setPublisher(book.getPublisher());
        b.setTitle(book.getTitle());
        b.setPublishedDate(book.getPublishedDate());
        return bookRepository.save(b);
    }

    @PostConstruct
    private void loadSchema() {
        Stream.of(new Book("123", "The legendary mechanic", "Kindle",
                Collections.singletonList("happy fox"), "jan 22"),
                new Book("124", "Book of clouds", "Kindle edition",
                        Collections.singletonList("Peter Parker"), "feb 22"),
                new Book("125", "Java 9 programming", "Orielly",
                        Collections.singletonList("Tony Stark"), "mar 22")
        ).forEach(book -> bookRepository.save(book));
    }

}
