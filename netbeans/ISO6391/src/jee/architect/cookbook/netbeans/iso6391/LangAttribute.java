package jee.architect.cookbook.netbeans.iso6391;

/**
 * <p>
 * Représente un attribut lang dans le document XHTML courant.
 * </p>
 * 
 * <p>
 * lineOffset représente l'offset de la ligne dans la String qui contient l'ensemble du document.
 * </p>
 * 
 * <p>
 * start et end sont les offsets à l'intérieur de ligne de texte qui contient l'attribut
 * </p>
 * 
 * <p>value est la valeur courante saisie par l'utilisateur pour l'attribut lang courant</p>
 * 
 * @author olivierschmitt
 */
public class LangAttribute {

    int lineOffset;
    private int start;
    private int end;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setLineOffset(int lineOffset) {
        this.lineOffset = lineOffset;
    }

    public int getLineOffset() {
        return lineOffset;
    }
}
