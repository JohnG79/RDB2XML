package rdb2xml.ui;

import java.io.IOException;
import java.io.OutputStream;
import static java.lang.String.valueOf;
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
        textArea.append( valueOf( ( char ) b ) );

    }
}
