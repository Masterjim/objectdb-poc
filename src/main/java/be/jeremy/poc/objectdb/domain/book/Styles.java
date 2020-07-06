package be.jeremy.poc.objectdb.domain.book;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.EnumSet;

@Getter
@Setter
public class Styles {

    @Transient
    private EnumSet<Style> styles;

    @Basic
    private int stylesRep;

    public Styles() {
        styles = EnumSet.noneOf(Style.class);
    }

    public Styles(EnumSet<Style> styles) {
        this.styles = styles;
    }

}
