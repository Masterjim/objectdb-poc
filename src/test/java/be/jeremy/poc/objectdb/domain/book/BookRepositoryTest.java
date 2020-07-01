package be.jeremy.poc.objectdb.domain.book;

import be.jeremy.poc.objectdb.domain.author.Author;
import be.jeremy.poc.objectdb.domain.author.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.EnumSet;

import static be.jeremy.poc.objectdb.domain.book.Styles.toInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.atomicIntegerFieldUpdater;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldPersistStylesAsInt() {
        Author author = authorRepository.save(Author.builder()
                .firstName("Chuck")
                .middleName("Paul")
                .lastName("Norris")
                .build());

        Styles styles = new Styles(EnumSet.of(Style.NOVEL, Style.COMICS));

        Book book = bookRepository.save(Book.builder()
                .author(author)
                .title("The art of kung fu")
                .styles(styles)
                .build());

        assertThat(book.getStylesRep()).isEqualTo(toInt(styles));
    }
}