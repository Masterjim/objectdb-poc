package be.jeremy.poc.objectdb.domain.book;

import be.jeremy.poc.objectdb.domain.author.Author;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import static be.jeremy.poc.objectdb.domain.book.Styles.toInt;
import static be.jeremy.poc.objectdb.domain.book.Styles.toStyles;

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

    @Column(name = "styles")
    @JsonIgnore
    private int stylesRep;

    @Transient
    private Styles styles;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @PostLoad
    void fillStyles() {
        styles = toStyles(stylesRep);
    }

    @PrePersist
    void fillStylesRep() {
        stylesRep = toInt(styles);
    }
}
