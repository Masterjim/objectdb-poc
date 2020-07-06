package be.jeremy.poc.objectdb.domain.book;

import be.jeremy.poc.objectdb.domain.author.Author;
import be.jeremy.poc.objectdb.domain.author.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.EnumSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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

        Book bookToPersist = Book.builder()
                .author(author)
                .title("The art of kung fu")
                .build();

        bookToPersist.addStyle(Style.NOVEL);
        bookToPersist.addStyle(Style.COMICS);

        Book saved = bookRepository.save(bookToPersist);

        assertThat(saved.getStyles()).containsExactly(Style.NOVEL, Style.COMICS);
        assertThat(saved.getStylesRep()).isEqualTo(33);
    }

    @Test
    void shouldUpdateBook() {
        // Given
        Author author = authorRepository.save(Author.builder()
                .firstName("Chuck")
                .middleName("Paul")
                .lastName("Norris")
                .build());

        EnumSet<Style> styles = EnumSet.of(Style.NOVEL, Style.COMICS);

        Book toPersist = Book.builder()
                .author(author)
                .title("The art of kung fu")
                .build();

        toPersist.addStyle(Style.NOVEL);
        toPersist.addStyle(Style.COMICS);

        Book saved = bookRepository.save(toPersist);
        // When
        saved.setTitle("The art of kung fu 2");
        saved.addStyle(Style.HEROIC_FANTASY);

        bookRepository.saveAndFlush(saved);

        Optional<Book> actual = bookRepository.findById(saved.getId());

        assertThat(actual).isNotEmpty().hasValueSatisfying(actualBook -> {
            assertThat(actualBook.getTitle()).isEqualTo("The art of kung fu 2");
            assertThat(actualBook.getStyles()).containsOnly(Style.NOVEL, Style.COMICS, Style.HEROIC_FANTASY);
            assertThat(actualBook.getStylesRep()).isEqualTo(49);
        });
    }
}