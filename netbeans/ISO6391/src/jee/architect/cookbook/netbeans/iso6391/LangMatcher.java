/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jee.architect.cookbook.netbeans.iso6391;

import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 *
 * @author oschmitt
 */
public final class LangMatcher {
    
    public static final String LANG_PATTERN = "(lang)=\"([a-z]*)\"";

    public static boolean containsRef(String line) {
        if (line == null) {
            return false;
        }
        Scanner scanner = new Scanner(line);

        return scanner.findInLine(LANG_PATTERN) != null;
    }
    
    
    public static String getValue(String line, int caret) {

        Scanner scanner = new Scanner(line);
        while (true) {
            scanner.findInLine(LANG_PATTERN);
            try {
                MatchResult matchResult = scanner.match();
                if (matchResult != null) {
                    String attr = matchResult.group(1);
                    String value = matchResult.group(2);
                    int start = matchResult.start(2);
                    int end = matchResult.end(2);
                    if ((caret >= start)
                            && (caret <= end)) {
                        return value;
                    }
                }
            } catch (IllegalStateException ise) {
                return null;
            }
        }
    }
}
