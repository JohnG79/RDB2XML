package rdb2xml.ui;

import java.io.IOException;
import java.io.OutputStream;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

public class SyntaxAreaOutputStream extends OutputStream
{

    /**
     * This class extends from OutputStream to redirect output to a JTextArrea
     *
     * @author www.codejava.net
     *
     */
    private RSyntaxTextArea textArea;

    public SyntaxAreaOutputStream( RSyntaxTextArea syntaxTextArea )
    {
        this.textArea = syntaxTextArea;
    }

    @Override
    public void write( int b ) throws IOException
    {
        // redirects data to the text area
        textArea.append( String.valueOf( ( char ) b ) );
        // scrolls the text area to the end of data
        textArea.setCaretPosition( textArea.getDocument().getLength() );
    }
}
