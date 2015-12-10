package control;

import Processor.XSDDOMBuilder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import rdb2xml.ui.*;
import rdb2xml.ui.tree.node.Attribute;
import rdb2xml.ui.tree.node.RelationNode;
import rdb2xml.ui.tree.node.SchemaNode;

public class Controller extends Thread
{

    private final ConnectDialog connectDialog;
    private final MainInterface mainInterface;
    private XSDDOMBuilder xsdDOMBuilder;

    public Controller()
    {
        connectDialog = new ConnectDialog( this );
        mainInterface = new MainInterface( this );
        mainInterface.setLocationRelativeTo( null );
        mainInterface.setVisible( true );
    }

    public void importSchema()
    {
        SchemaImportThread schemaImportThread = new SchemaImportThread( connectDialog, mainInterface );
        /**
         * This thread waits for user to finish with the connection-dialog box.
         */
        schemaImportThread.start();
    }

    public void save( File file )
    {
        xsdDOMBuilder.print( file );
    }

    public void enableMainForm()
    {
        mainInterface.setEnabled( true );
        mainInterface.setVisible( true );
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

    public void importAndSerialiseData( RSyntaxTextArea syntaxTextArea )
    {
        DataController dataController = new DataController( ( SchemaNode ) ( SchemaImportThread.getSchemaTreeTable() ).getTreeTableModel().getRoot(), syntaxTextArea );
        dataController.start();
    }

    public void serialiseSchema( RSyntaxTextArea syntaxTextArea )
    {
        xsdDOMBuilder = new XSDDOMBuilder();
        SchemaNode schemaNode = ( SchemaNode ) ( SchemaImportThread.getSchemaTreeTable() ).getTreeTableModel().getRoot();
        schemaNode.acceptProcessor( xsdDOMBuilder );
        for ( RelationNode relation : schemaNode.getRelations() )
        {
            relation.acceptProcessor( xsdDOMBuilder );
            for ( Attribute attribute : relation.getAttributes() )
            {
                attribute.acceptProcessor( xsdDOMBuilder );
            }
        }
        xsdDOMBuilder.serialise( syntaxTextArea );
    }
}
