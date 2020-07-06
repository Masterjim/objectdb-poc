package be.jeremy.poc.objectdb.domain.book;

import be.jeremy.poc.objectdb.domain.author.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import java.util.EnumSet;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Transient
    private final EnumSet<Style> styles = EnumSet.noneOf(Style.class);

    @Basic
    private int stylesRep = 0;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public void addStyle(Style style) {
        styles.add(style);
        stylesRep = toInt(styles);
    }

    public void removeStyle(Style style) {
        styles.remove(style);
        stylesRep = toInt(styles);
    }

    private static int toInt(EnumSet<Style> styles) {
        int result = 0;

        for (Style t : styles) {
            result |= 1 << t.ordinal();
        }

        return result;
    }

    @PostLoad
    public void deserializeStyles() {
        Style[] values = Style.values();

        int stylesInt = this.stylesRep;

        for (byte i = 0; stylesInt != 0; i++, stylesInt >>= 1) {
            if ((stylesInt & 1) != 0) {
                Style style = values[i];
                this.styles.add(style);
            }
        }
    }
}
