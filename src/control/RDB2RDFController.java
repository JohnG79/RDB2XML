package control;

import Processor.RDFDocBuilder;
import Processor.XMLDOMBuilder;
import extraction.DataImporter;
import java.util.List;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import rdb2xml.ui.tree.node.RelationNode;
import rdb2xml.ui.tree.node.SchemaNode;
import rdb2xml.ui.tree.node.Tuple;

public class RDB2RDFController extends ImportController
{

    private final SchemaNode schemaNode;
    private final RSyntaxTextArea syntaxTextArea;

    public RDB2RDFController( SchemaNode schemaNode, RSyntaxTextArea syntaxTextArea )
    {
        super();
        this.schemaNode = schemaNode;
        this.syntaxTextArea = syntaxTextArea;
    }

    @Override
    public void run()
    {
        RDFDocBuilder rdfDocBuilder = new RDFDocBuilder();
        DataImporter dataImporter = new DataImporter( sqlConnection );
        SchemaNode schemaNode = ( SchemaNode ) ( SchemaImportThread.getSchemaTreeTable() ).getTreeTableModel().getRoot();
        
        schemaNode.acceptProcessor( rdfDocBuilder );
        for ( RelationNode relationNode : schemaNode.getRelations() )
        {
            dataImporter.importData( relationNode );
            for ( Tuple tuple : relationNode.getTuples() )
            {
                tuple.acceptProcessor( rdfDocBuilder );
            }
            relationNode.removeTuples();
        }
        rdfDocBuilder.serialise( syntaxTextArea );
        this.stop();
        
        
        

    }
    
    

}
