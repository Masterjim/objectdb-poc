package be.jeremy.poc.objectdb.domain.book;

import be.jeremy.poc.objectdb.ObjectDbApplication;
import be.jeremy.poc.objectdb.domain.author.Author;
import be.jeremy.poc.objectdb.domain.author.AuthorRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.net.URI;
import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ObjectDbApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookServiceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @SneakyThrows
    void shouldReturnOk() {
        // Given
        Author author = authorRepository.save(Author.builder()
                .firstName("Chuck")
                .middleName("Paul")
                .lastName("Norris")
                .build());

        Book toPersist = Book.builder()
                .author(author)
                .title("The art of kung fu")
                .build();

        toPersist.addStyle(Style.NOVEL);
        toPersist.addStyle(Style.COMICS);

        Book book = bookRepository.save(toPersist);

        // When
        URI url = new URI(createURLWithPort("/books/" + book.getId()));

        ResponseEntity<Book> bookResponse = restTemplate.getForEntity(url, Book.class);

        // Then
        assertThat(bookResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Book actualBook = bookResponse.getBody();

        assertThat(actualBook.getAuthor()).isNotNull();
        assertThat(actualBook.getTitle()).isNotNull();
        assertThat(actualBook.getStyles()).containsExactly(Style.NOVEL, Style.COMICS);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}