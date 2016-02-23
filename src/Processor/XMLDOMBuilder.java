package Processor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import static java.util.logging.Level.SEVERE;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import rdb2xml.ui.SyntaxAreaOutputStream;
import rdb2xml.ui.tree.node.Attribute;
import rdb2xml.ui.tree.node.CombinedKeyNode;
import rdb2xml.ui.tree.node.ForeignKeyNode;
import rdb2xml.ui.tree.node.Key;
import rdb2xml.ui.tree.node.NonKeyNode;
import rdb2xml.ui.tree.node.PrimaryKeyNode;
import rdb2xml.ui.tree.node.RelationNode;
import rdb2xml.ui.tree.node.SchemaNode;
import rdb2xml.ui.tree.node.Tuple;
import static java.util.logging.Logger.getLogger;
import static javax.xml.parsers.DocumentBuilderFactory.newInstance;

/**
 *
 * @author John
 */
public class XMLDOMBuilder implements Processor
{

    private DOMImplementation implementation = null;
    private Element database_root;
    private Element current_relation_root;
    private Document document;

    private final String targetNamespace_prefix_declation;
    private final String targetNamespace_prefix;
    private final String targetNamespace;

    public XMLDOMBuilder()
    {
        targetNamespace_prefix = "un";
        targetNamespace = "http://www.example.com/";
        targetNamespace_prefix_declation = "xmlns:un";

        DocumentBuilderFactory factory = newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            implementation = builder.getDOMImplementation();
        }
        catch ( ParserConfigurationException ex )
        {
            getLogger( XMLDOMBuilder.class.getName() ).log( SEVERE, null, ex );
        }
    }

    @Override
    public void visit( SchemaNode schemaNode )
    {
        database_root = append_database_to_document( schemaNode.getName() );
    }

    @Override
    public void visit( RelationNode relationNode )
    {
        current_relation_root = append_relation_to_database( database_root, relationNode.getName() );
    }

    @Override
    public void visit( NonKeyNode nonKeyNode )
    {

    }

    @Override
    public void visit( Key key )
    {

    }

    @Override
    public void visit( ForeignKeyNode foreignKeyNode )
    {

    }

    @Override
    public void visit( CombinedKeyNode combinedKeyNode )
    {

    }

    private Element append_database_to_document( String database_name )
    {
        String full_targetNamespace = targetNamespace + database_name;
        String prefixed_document_tag_name = targetNamespace_prefix + ":" + database_name;
        document = implementation.createDocument( full_targetNamespace, prefixed_document_tag_name, null );
        Element root_element = document.getDocumentElement();
        String xmlns_namespace = "http://www.w3.org/2000/xmlns/";
        String xsd_instance_namespace = "http://www.w3.org/2001/XMLSchema-instance";
        String xsd_instance_namespace_prefix_declation = "xmlns:xsi";
        root_element.setAttributeNS( xmlns_namespace, xsd_instance_namespace_prefix_declation, xsd_instance_namespace );
        root_element.setAttribute( "xsi:schemaLocation", full_targetNamespace + " " + database_name + ".xsd" );
        root_element.setAttribute( targetNamespace_prefix_declation, full_targetNamespace );
        return root_element;
    }

    private Element append_relation_to_database( Element parent_element, String relation_name )
    {
        Element relation_element = document.createElement( relation_name + "_relation" );
        parent_element.appendChild( relation_element );
        return relation_element;
    }

    private Element add_variable_and_datum_as_element( Element parent_element, String variable_name, String datum )
    {
        Element variable_element = document.createElement( variable_name );
        parent_element.appendChild( variable_element );
        Text text = document.createTextNode( datum );
        variable_element.appendChild( text );
        return variable_element;
    }

    public void serialise( RSyntaxTextArea syntaxTextArea )
    {
        RSyntaxTextArea r = new RSyntaxTextArea();

        try
        {
            OutputFormat format = new OutputFormat( document );
            format.setIndenting( true );

            XMLSerializer serializer = new XMLSerializer(
                    ( new SyntaxAreaOutputStream( syntaxTextArea ) ), format );

            serializer.serialize( document );
        }
        catch ( IOException ioe )
        {
        }
    }

    @Override
    public void visit( Tuple tuple )
    {
        Element tuple_element = document.createElement( tuple.getName() );
        current_relation_root.appendChild( tuple_element );

        HashMap<Attribute, String> attributes_and_data = tuple.get_data();
        Set<Attribute> attributes = attributes_and_data.keySet();
        for ( Attribute attribute : attributes )
        {
            if ( attribute instanceof PrimaryKeyNode || attribute instanceof CombinedKeyNode )
            {
                tuple_element.setAttribute( attribute.getName(), attributes_and_data.get( attribute ) );
            }
            else
            {
                add_variable_and_datum_as_element( tuple_element, attribute.getName(), attributes_and_data.get( attribute ) );
            }
        }
    }
}
