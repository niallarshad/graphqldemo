package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.model.BookInput;
import com.example.demo.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @Resource
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddBook() {
        BookInput bookInput = new BookInput("123", "The legendary mechanic", "Kindle",
                Collections.singletonList("happy fox"), "jan 22");
        Book book = new Book("123", "The legendary mechanic", "Kindle",
                Collections.singletonList("happy fox"), "jan 22");

        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book returnedBook = bookService.addBook(bookInput);

        Mockito.verify(bookRepository, Mockito.times(1)).save(any(Book.class));
        assertThat(returnedBook.getIsn().toLowerCase()).isEqualTo(bookInput.getIsn().toLowerCase());
        assertThat(returnedBook.getPublisher().toLowerCase()).isEqualTo(bookInput.getPublisher().toLowerCase());
        assertThat(returnedBook.getTitle().toLowerCase()).isEqualTo(bookInput.getTitle().toLowerCase());
        assertThat(returnedBook.getPublishedDate().toLowerCase()).isEqualTo(bookInput.getPublishedDate().toLowerCase());
        assertThat(returnedBook.getAuthors().get(0).toLowerCase()).isEqualTo(bookInput.getAuthors().get(0).toLowerCase());
    }

    @Test
    public void testDeleteBook() {
        String isn = "123";

        Boolean returnedBool = bookService.deleteBook(isn);

        Mockito.verify(bookRepository, Mockito.times(1)).deleteById("123");
        assertThat(returnedBool).isEqualTo(true);
    }

    @Test
    public void testUpdateBook() {
        BookInput bookInput = new BookInput("123", "The legendary mechanic", "Kindle",
                Collections.singletonList("happy fox"), "jan 22");
        Book book = new Book("123", "The legendary mechanic", "Kindle",
                Collections.singletonList("happy fox"), "jan 22");

        Mockito.when(bookRepository.findById("123")).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book returnedBook = bookService.updateBook(bookInput);

        Mockito.verify(bookRepository, Mockito.times(1)).save(any(Book.class));
        Mockito.verify(bookRepository, Mockito.times(1)).findById("123");
        assertThat(returnedBook.getIsn().toLowerCase()).isEqualTo(bookInput.getIsn().toLowerCase());
        assertThat(returnedBook.getPublisher().toLowerCase()).isEqualTo(bookInput.getPublisher().toLowerCase());
        assertThat(returnedBook.getTitle().toLowerCase()).isEqualTo(bookInput.getTitle().toLowerCase());
        assertThat(returnedBook.getPublishedDate().toLowerCase()).isEqualTo(bookInput.getPublishedDate().toLowerCase());
        assertThat(returnedBook.getAuthors().get(0).toLowerCase()).isEqualTo(bookInput.getAuthors().get(0).toLowerCase());
    }
}