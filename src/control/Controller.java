package control;

import Visitor.XSDDOMBuilder;
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
    private MainInterface mainDialog;
    private XSDDOMBuilder xsdDOMBuilder;

    public Controller()
    {
        connectDialog = new ConnectDialog( this );
    }

    public void importSchema()
    {
        new SchemaImportController( connectDialog, mainDialog ).start();
    }

    public void setMainFrame( MainInterface mainFrame )
    {
        this.mainDialog = mainFrame;
    }

    public void save( File file )
    {
        xsdDOMBuilder.print( file );
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

    public void serialiseData( RSyntaxTextArea syntaxTextArea )
    {
        DataImportController dataImporter = new DataImportController( ( SchemaNode ) ( SchemaImportController.getSchemaTreeTable() ).getTreeTableModel().getRoot(), syntaxTextArea );
        dataImporter.start();
    }

    public void serialiseSchema( RSyntaxTextArea syntaxTextArea )
    {
        xsdDOMBuilder = new XSDDOMBuilder();
        SchemaNode schemaNode = ( SchemaNode ) ( SchemaImportController.getSchemaTreeTable() ).getTreeTableModel().getRoot();
        schemaNode.acceptVisitor( xsdDOMBuilder );
        for ( RelationNode relation : schemaNode.getRelations() )
        {
            relation.acceptVisitor( xsdDOMBuilder );
            for ( Attribute attribute : relation.getAttributes() )
            {
                attribute.acceptVisitor( xsdDOMBuilder );
            }
        }
        xsdDOMBuilder.print( syntaxTextArea );
    }
}
