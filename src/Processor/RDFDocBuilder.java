package Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import rdb2xml.ui.tree.node.Attribute;
import rdb2xml.ui.tree.node.CombinedKeyNode;
import rdb2xml.ui.tree.node.ForeignKeyNode;
import rdb2xml.ui.tree.node.Key;
import rdb2xml.ui.tree.node.NonKeyNode;
import rdb2xml.ui.tree.node.RelationNode;
import rdb2xml.ui.tree.node.SchemaNode;
import rdb2xml.ui.tree.node.Tuple;
import org.apache.jena.rdf.model.Model ;
import org.apache.jena.rdf.model.ModelFactory ;
import rdb2xml.ui.tree.node.PrimaryKeyNode;



public class RDFDocBuilder implements Processor
{
    private Map<RelationNode, PrimaryKeyNode> primaryKeys;
    private Model model;    
    private String NS = "http://www.example-university.com/some-resource";
    public RDFDocBuilder()
    {
        primaryKeys = new HashMap<>();
        
        model = ModelFactory.createDefaultModel();
        

    }
    
    @Override
    public void visit( Tuple tuple )
    {        
        PrimaryKeyNode primaryKeyNode = primaryKeys.get( tuple.getParent() );
        tuple.addToRDFModel( NS, model, primaryKeyNode );

    }

    @Override
    public void visit( SchemaNode data_schema )
    {
        PrimaryKeysFinder primaryKeysFinder = new PrimaryKeysFinder();
        for( RelationNode relationNode : data_schema.getRelations() )
        {
            relationNode.acceptProcessor( primaryKeysFinder );
            for( Attribute attribute : relationNode.getAttributes() )
            {
                attribute.acceptProcessor( primaryKeysFinder );
            }
        }
        primaryKeys = primaryKeysFinder.primaryKeys;
    }
    
    @Override
    public void visit( RelationNode relation_schema )
    {
    }

    @Override
    public void visit( NonKeyNode non_key )
    {
    }

    @Override
    public void visit( Key key )
    {
    }

    @Override
    public void visit( ForeignKeyNode foreign_key )
    {
    }

    @Override
    public void visit( CombinedKeyNode combined_key )
    {
    }

    public void serialise( RSyntaxTextArea syntaxTextArea )
    {
    }
}
