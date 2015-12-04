package control;

import Visitor.XSDBuilder;
import extraction.Database;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.jdesktop.swingx.JXTreeTable;
import persistence.Connection;
import persistence.ConnectionParameter;
import persistence.DataFormat;
import persistence.MySQLConnection;
import rdb2xml.ui.*;
import rdb2xml.ui.tree.node.SchemaNode;

public class Controller
{

    private Connection sqlConnection;
    private ConnectionDialog connectFrame;
    private Main mainFrame;
    private JXTreeTable treeTable;
    private XSDBuilder xsdBuilder;

    public void openConnectionDialog()
    {
        connectFrame = new ConnectionDialog( this );
        connectFrame.setLocationRelativeTo( null );
        connectFrame.setVisible( true );
        mainFrame.setEnabled( false );
    }

    public void setMainFrame( Main mainFrame )
    {
        this.mainFrame = mainFrame;
    }

    public void connect( HashMap< ConnectionParameter, String> connectionParams, DataFormat dataFormat )
    {
        sqlConnection = new MySQLConnection();
        sqlConnection.connect( connectionParams );
        importSchema( dataFormat );
        mainFrame.setEnabled( true );
    }

    public void save( File file )
    {
        xsdBuilder.print( file );
    }

    public void enableMainForm()
    {
        mainFrame.setEnabled( true );
        mainFrame.setVisible( true );
    }

    public void save( RSyntaxTextArea rSyntaxTextArea, File file )
    {
        try
        {
            BufferedWriter outFile;
            outFile = new BufferedWriter( new FileWriter( file ) );
            rSyntaxTextArea.write( outFile );
            try
            {
                outFile.close();
            }
            catch ( IOException e )
            {
            }
        }
        catch ( IOException ex )
        {
        }
    }

    private void importSchema( DataFormat dataFormat )
    {
        if ( sqlConnection.isConnected() )
        {
            Database database = new Database( sqlConnection );
            mainFrame.setSchema( treeTable = database.importSchema( dataFormat ) );
        }
    }

    public void exportSchema( RSyntaxTextArea syntaxTextArea )
    {
        xsdBuilder = new XSDBuilder();
        SchemaNode schemaNode = ( SchemaNode ) treeTable.getTreeTableModel().getRoot();
        schemaNode.acceptVisitor( xsdBuilder );

        xsdBuilder.print( syntaxTextArea );
    }
}
