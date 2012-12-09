/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jee.architect.cookbook.netbeans.iso6391;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.text.JTextComponent;
import org.netbeans.spi.editor.completion.CompletionItem;
import org.netbeans.spi.editor.completion.CompletionTask;
import org.netbeans.spi.editor.completion.support.CompletionUtilities;
import org.openide.util.ImageUtilities;

/**
 *
 * @author oschmitt
 */
public class ISO6391CompletionItem implements CompletionItem {

      private static ImageIcon ICON =
            new ImageIcon(ImageUtilities.loadImage("jee/architect/cookbook/netbeans/iso6391/bubble.png"));
  
    private String text;

    public ISO6391CompletionItem(String language) {
        this.text = language;
    }

    @Override
    public void defaultAction(JTextComponent jtc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void processKeyEvent(KeyEvent ke) {
    }

    @Override
    public int getPreferredWidth(Graphics graphics, Font font) {
        return CompletionUtilities.getPreferredWidth(getText(), null, graphics, font);
    }

    @Override
    public void render(Graphics graphics, Font defaultFont, Color defaultColor,
            Color backgroundColor, int width, int height, boolean selected) {
        CompletionUtilities.renderHtml(ICON, getText(), null, graphics, defaultFont,
                (selected ? Color.white : getColor()), width, height, selected);
    }

    @Override
    public CompletionTask createDocumentationTask() {
        return null;
    }

    protected Color getColor() {
        return Color.decode("0x0000B2");
    }

    @Override
    public CompletionTask createToolTipTask() {
        return null;
    }

    @Override
    public boolean instantSubstitution(JTextComponent jtc) {
        return false;
    }

    @Override
    public int getSortPriority() {
        return 0;
    }

    @Override
    public CharSequence getSortText() {
        return getText();
    }

    @Override
    public CharSequence getInsertPrefix() {
        return getText();
    }

    private String getText() {
        return this.text;
    }

}
