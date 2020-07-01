package be.jeremy.poc.objectdb.domain.book;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static be.jeremy.poc.objectdb.domain.book.Styles.toInt;
import static be.jeremy.poc.objectdb.domain.book.Styles.toStyles;
import static org.assertj.core.api.Assertions.assertThat;

class StylesTest {

    @Test
    void shouldReturnInteger() {
        Styles styles = new Styles(EnumSet.of(Style.NOVEL, Style.COMICS));

        assertThat(toInt(styles)).isEqualTo(33);
    }

    @Test
    void shouldReturnStyles() {
        int stylesRep = 33;

        assertThat(toStyles(stylesRep))
                .extracting(Styles::getStyles)
                .isEqualTo(EnumSet.of(Style.NOVEL, Style.COMICS));
    }

}