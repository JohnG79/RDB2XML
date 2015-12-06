package control;

import Visitor.XSDBuilder;
import extraction.SchemaImporter;
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
    private ConnectDialog connectDialog;
    private MainDialog mainDialog;
    private JXTreeTable treeTable;
    private XSDBuilder xsdBuilder;

    public void openConnectionDialog()
    {
        connectDialog = new ConnectDialog( this );
        connectDialog.setLocationRelativeTo( null );
        connectDialog.setVisible( true );
        mainDialog.setEnabled( false );

    }

    public void setMainFrame( MainDialog mainFrame )
    {
        this.mainDialog = mainFrame;
    }

    public void connect( HashMap< ConnectionParameter, String> connectionParams, DataFormat dataFormat )
    {
        sqlConnection = new MySQLConnection();
        sqlConnection.connect( connectionParams );
        importSchema( dataFormat );
        mainDialog.setEnabled( true );
        mainDialog.showSchemaTab( true );
    }

    public void save( File file )
    {
        xsdBuilder.print( file );
    }

    public void enableMainForm()
    {
        mainDialog.setEnabled( true );
        mainDialog.setVisible( true );
    }

    public void save( RSyntaxTextArea rSyntaxTextArea, File file )
    {
        try
        {
            BufferedWriter outFile;
            outFile = new BufferedWriter( new FileWriter( file ) );
            rSyntaxTextArea.write( outFile );
            outFile.close();
        }
        catch ( IOException ex )
        {
        }
    }

    private void importSchema( DataFormat newFormat )
    {
        if ( sqlConnection.isConnected() )
        {
            SchemaImporter database = new SchemaImporter( sqlConnection );
            mainDialog.setSchema( treeTable = database.importSchema( newFormat ) );
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
