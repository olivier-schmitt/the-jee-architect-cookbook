
package jee.architect.cookbook.netbeans.iso6391;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.completion.CompletionProvider;
import org.netbeans.spi.editor.completion.CompletionResultSet;
import org.netbeans.spi.editor.completion.CompletionTask;
import org.netbeans.spi.editor.completion.support.AsyncCompletionQuery;
import org.netbeans.spi.editor.completion.support.AsyncCompletionTask;
import org.openide.util.Exceptions;

/**
 *
 * @author oschmitt
 */
@MimeRegistration(mimeType = "text/xhtml", service = CompletionProvider.class)
public class ISO6391CompletionProvider implements CompletionProvider {

    private static Set<String> codes = new TreeSet<String>();
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
        if(inputStream!=null){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line=bufferedReader.readLine())!=null){
                codes.add(line);
            }
        }
    }
    
    
    @Override
    public CompletionTask createTask(int queryType, JTextComponent jTextComponent) {
        
        return new AsyncCompletionTask(new AsyncCompletionQuery() {
            @Override
            protected void query(CompletionResultSet completionResultSet, 
                        Document document, int caretOffset) {
                for(String code:ISO6391CompletionProvider.codes){
                    completionResultSet.addItem(new ISO6391CompletionItem(code));
                }
                completionResultSet.finish();
            }
        });
    }

    @Override
    public int getAutoQueryTypes(JTextComponent jTextComponent, String string) {
        return 0;
    }
}