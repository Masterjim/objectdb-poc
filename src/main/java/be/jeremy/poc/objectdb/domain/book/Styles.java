package be.jeremy.poc.objectdb.domain.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.EnumSet;

@Getter
@Setter
@AllArgsConstructor
public class Styles {

    private EnumSet<Style> styles;

    public Styles() {
        styles = EnumSet.noneOf(Style.class);
    }

    public static int toInt(Styles s) {
        EnumSet<Style> styles = s.getStyles();
        int result = 0;

        for (Style t : styles) {
            result |= 1 << t.ordinal();
        }

        return result;
    }

    public static Styles toStyles(int stylesInt) {
        Styles styles = new Styles();
        EnumSet<Style> result = styles.getStyles();
        Style[] values = Style.values();

        for (byte i = 0; stylesInt != 0; i++, stylesInt >>= 1) {
            if ((stylesInt & 1) != 0) {
                Style style = values[i];
                result.add(style);
            }
        }

        return styles;
    }
}
