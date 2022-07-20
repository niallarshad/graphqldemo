package com.example.demo.web;

import com.example.demo.model.Book;
import com.example.demo.model.BookInput;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    @Resource
    @InjectMocks
    private BookController bookController;

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookService bookService;

    private ObjectMapper om;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        om = new ObjectMapper();
    }

    @Test
    public void findAllBooks() throws IOException {
        Book book = new Book("123", "The legendary mechanic", "Kindle",
                Collections.singletonList("happy fox"), "jan 22");
        List<Book> bookList = Collections.singletonList(book);

        Mockito.when(bookRepository.findAll()).thenReturn(bookList);

        GraphQLResponse response = graphQLTestTemplate.postForResource("schema/allBooks.graphql");
        assertThat(response.isOk()).isTrue();

        String responseStr = response.getRawResponse().getBody();
        Map<String, Object> responseDataMap = om.readValue(responseStr, new TypeReference<>() {});
        Map<String, List<Book>> responseBookMap = om.convertValue(responseDataMap.get("data"), new TypeReference<>() {}) ;
        List<Book> responseBookList =  responseBookMap.get("allBooks");

        assertThat(responseBookList.get(0).getIsn().toLowerCase()).isEqualTo(book.getIsn().toLowerCase());
        assertThat(responseBookList.get(0).getPublisher().toLowerCase()).isEqualTo(book.getPublisher().toLowerCase());
        assertThat(responseBookList.get(0).getTitle().toLowerCase()).isEqualTo(book.getTitle().toLowerCase());
        assertThat(responseBookList.get(0).getPublishedDate().toLowerCase()).isEqualTo(book.getPublishedDate().toLowerCase());
        assertThat(responseBookList.get(0).getAuthors().get(0).toLowerCase()).isEqualTo(book.getAuthors().get(0).toLowerCase());
    }

    @Test
    public void findOneBook() throws IOException {
        Book book = new Book("123", "The legendary mechanic", "Kindle",
                Collections.singletonList("happy fox"), "jan 22");

        Mockito.when(bookRepository.findById("123")).thenReturn(Optional.of(book));

        GraphQLResponse response = graphQLTestTemplate.postForResource("schema/findOne.graphql");
        assertThat(response.isOk()).isTrue();

        String responseStr = response.getRawResponse().getBody();
        Map<String, Object> responseDataMap = om.readValue(responseStr, new TypeReference<>() {});
        Map<String, Book> responseBookMap = om.convertValue(responseDataMap.get("data"), new TypeReference<>() {}) ;
        Book responseBook =  responseBookMap.get("book");

        assertThat(responseBook.getIsn().toLowerCase()).isEqualTo(book.getIsn().toLowerCase());
        assertThat(responseBook.getPublisher().toLowerCase()).isEqualTo(book.getPublisher().toLowerCase());
        assertThat(responseBook.getTitle().toLowerCase()).isEqualTo(book.getTitle().toLowerCase());
        assertThat(responseBook.getPublishedDate().toLowerCase()).isEqualTo(book.getPublishedDate().toLowerCase());
        assertThat(responseBook.getAuthors().get(0).toLowerCase()).isEqualTo(book.getAuthors().get(0).toLowerCase());
    }

    @Test
    public void updateBook() throws IOException {
        Book book = new Book("125", "Java 10 programming", "Orielly",
                Collections.singletonList("Tony Stark"), "mar 22");

        Mockito.when(bookService.updateBook(Mockito.any(BookInput.class))).thenReturn(book);

        GraphQLResponse response = graphQLTestTemplate.postForResource("schema/updateBook.graphql");
        assertThat(response.isOk()).isTrue();

        String responseStr = response.getRawResponse().getBody();
        Map<String, Object> responseDataMap = om.readValue(responseStr, new TypeReference<>() {});
        Map<String, Book> responseBookMap = om.convertValue(responseDataMap.get("data"), new TypeReference<>() {}) ;
        Book responseBook =  responseBookMap.get("updateBook");

        assertThat(responseBook.getIsn().toLowerCase()).isEqualTo(book.getIsn().toLowerCase());
        assertThat(responseBook.getPublisher().toLowerCase()).isEqualTo(book.getPublisher().toLowerCase());
        assertThat(responseBook.getTitle().toLowerCase()).isEqualTo(book.getTitle().toLowerCase());
        assertThat(responseBook.getPublishedDate().toLowerCase()).isEqualTo(book.getPublishedDate().toLowerCase());
        assertThat(responseBook.getAuthors().get(0).toLowerCase()).isEqualTo(book.getAuthors().get(0).toLowerCase());
    }

    @Test
    public void addBook() throws IOException {
        Book book = new Book("125", "Java 10 programming", "Orielly",
                Collections.singletonList("Tony Stark"), "mar 22");

        Mockito.when(bookService.addBook(Mockito.any(BookInput.class))).thenReturn(book);

        GraphQLResponse response = graphQLTestTemplate.postForResource("schema/addBook.graphql");
        assertThat(response.isOk()).isTrue();

        String responseStr = response.getRawResponse().getBody();
        Map<String, Object> responseDataMap = om.readValue(responseStr, new TypeReference<>() {});
        Map<String, Book> responseBookMap = om.convertValue(responseDataMap.get("data"), new TypeReference<>() {}) ;
        Book responseBook =  responseBookMap.get("addBook");

        assertThat(responseBook.getIsn().toLowerCase()).isEqualTo(book.getIsn().toLowerCase());
        assertThat(responseBook.getPublisher().toLowerCase()).isEqualTo(book.getPublisher().toLowerCase());
        assertThat(responseBook.getTitle().toLowerCase()).isEqualTo(book.getTitle().toLowerCase());
        assertThat(responseBook.getPublishedDate().toLowerCase()).isEqualTo(book.getPublishedDate().toLowerCase());
        assertThat(responseBook.getAuthors().get(0).toLowerCase()).isEqualTo(book.getAuthors().get(0).toLowerCase());
    }

    @Test
    public void deleteBook() throws IOException {
        String isn = "123";

        Mockito.when(bookService.deleteBook(isn)).thenReturn(true);

        GraphQLResponse response = graphQLTestTemplate.postForResource("schema/deleteBook.graphql");
        assertThat(response.isOk()).isTrue();

        String responseStr = response.getRawResponse().getBody();
        Map<String, Object> responseDataMap = om.readValue(responseStr, new TypeReference<>() {});
        Map<String, Boolean> responseBookMap = om.convertValue(responseDataMap.get("data"), new TypeReference<>() {}) ;
        Boolean responseBook =  responseBookMap.get("deleteBook");
        assertThat(responseBook).isEqualTo(true);
    }

}