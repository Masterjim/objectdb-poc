package be.jeremy.poc.objectdb.domain.author;

import be.jeremy.poc.objectdb.ObjectDbApplication;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ObjectDbApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorServiceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @SneakyThrows
    void shouldReturnOk() {
        // Given
        Author author = repository.save(Author.builder()
                .firstName("Chuck")
                .middleName("Paul")
                .lastName("Norris")
                .build());

        // When
        URI url = new URI(createURLWithPort("/authors/" + author.getId()));

        ResponseEntity<Author> authorResponse = restTemplate.getForEntity(url, Author.class);

        // Then
        assertThat(authorResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @SneakyThrows
    void shouldReturnCreated() {
        URI url = new URI(createURLWithPort("/authors/"));

        Author author = Author.builder().firstName("Jeremy").lastName("Vanpé").build();
        HttpEntity<Author> entity = new HttpEntity<>(author, new HttpHeaders());

        ResponseEntity<Author> customerResponse = restTemplate.postForEntity(url, entity, Author.class);

        assertThat(customerResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(customerResponse.getBody()).isNotNull();
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}