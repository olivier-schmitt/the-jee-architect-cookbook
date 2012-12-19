package jee.architect.cookbook.netbeans.iso6391;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.completion.CompletionProvider;
import org.netbeans.spi.editor.completion.CompletionResultSet;
import org.netbeans.spi.editor.completion.CompletionTask;
import org.netbeans.spi.editor.completion.support.AsyncCompletionQuery;
import org.netbeans.spi.editor.completion.support.AsyncCompletionTask;
import org.openide.text.NbDocument;
import org.openide.util.Exceptions;

/**
 * <p>
 * Fourni la complétion pour l'attribut lang.
 * </p>
 * 
 * <p>
 * Les codes sont issus de la norme ISO639-1 et sont stockés dans un fichier CSV.
 * </p>
 * 
 * @author oschmitt
 */
@MimeRegistration(mimeType = "text/xhtml", service = CompletionProvider.class)
public class ISO6391CompletionProvider implements CompletionProvider {

    private static Set<String> codes = new TreeSet<String>();

    /*
     * Chargement des codes au chargement de la classe
     */
    static {
        try {
            loadISO3691Codes();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private static void loadISO3691Codes() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("jee/architect/cookbook/netbeans/iso6391/ISO6391.csv");
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                codes.add(line);
            }
        }
    }

    @Override
    public CompletionTask createTask(int queryType, JTextComponent jTextComponent) {

        int position = jTextComponent.getCaretPosition();
        String text = jTextComponent.getText();
        StyledDocument styledDocument = (StyledDocument) jTextComponent.getDocument();
        int lineNumber = NbDocument.findLineNumber(styledDocument, position);
        Element lineElement = styledDocument.getDefaultRootElement().getElement(lineNumber);
        int startOffset = lineElement.getStartOffset();
        int endOffset = lineElement.getEndOffset();
        String lineOfText = text.substring(startOffset, endOffset);
        int column = NbDocument.findLineColumn(styledDocument, position);

        if (LangMatcher.containsRef(lineOfText)) {

            final LangAttribute langAttribute = LangMatcher.getValue(lineOfText, column);
            if (langAttribute == null) {
                return null;
            } else {
                langAttribute.setLineOffset(startOffset);
                return new AsyncCompletionTask(new AsyncCompletionQuery() {
                    @Override
                    protected void query(CompletionResultSet completionResultSet,
                            Document document, int caretOffset) {

                        for (String code : ISO6391CompletionProvider.codes) {
                            if (code.startsWith(langAttribute.getValue())) {
                                completionResultSet.addItem(new ISO6391CompletionItem(langAttribute, code));
                            }
                        }
                        completionResultSet.finish();
                    }
                });
            }

        } else {
            return null;
        }

    }

    @Override
    public int getAutoQueryTypes(JTextComponent jTextComponent, String string) {
        return 0;
    }
}