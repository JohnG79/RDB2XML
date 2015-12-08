package extraction;

import static extraction.AttributeItem.ATTRIBUTE_NAME;
import static extraction.AttributeItem.PARENT_RELATION_NAME;
import static extraction.AttributeItem.REFERENCED_ATTRIBUTE_NAME;
import static extraction.AttributeItem.REFERENCED_RELATION_NAME;
import static extraction.XSDDatatype.get;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXTreeTable;
import persistence.Connection;
import static persistence.ConnectionParameter.SCHEMA;
import persistence.DataFormat;
import static persistence.DataFormat.XSD;
import rdb2xml.ui.tree.table.OWLTreeTable;
import rdb2xml.ui.tree.table.TreeTable;
import rdb2xml.ui.tree.table.XSDTreeTable;

public class SchemaImporter extends Importer
{

    private TreeTable treeTable;
    private String schemaName;

    public SchemaImporter( Connection connection )
    {
        super( connection );
    }

    public JXTreeTable importSchema( DataFormat newFormat )
    {
        JComboBox relations_comboBox = new JComboBox();
        treeTable = ( newFormat == XSD
                ? new XSDTreeTable( newFormat.treeTableHeadings(), schemaName = connection.getConnectionParameter( SCHEMA ) )
                : new OWLTreeTable( newFormat.treeTableHeadings(), schemaName = connection.getConnectionParameter( SCHEMA ) ) );

        ArrayList< String> relationNames = importRelations( schemaName );

        for ( String relationName : relationNames )
        {
            treeTable.addRelation( relationName );
            relations_comboBox.addItem( relationName.substring( 0, 1 ).toUpperCase() + relationName.substring( 1 ) );
            importPrimaryKeys( relationName );
            importNonKeys( relationName );
        }
        for ( String relationName : relationNames )
        {
            ImportForeignKeys( relationName );
        }

        JComboBox datatypes_comboBox = new JComboBox();
        datatypes_comboBox.addItem( "xsd:string" );
        datatypes_comboBox.addItem( "xsd:integer" );
        datatypes_comboBox.addItem( "xsd:decimal" );
        datatypes_comboBox.addItem( "xsd:anyURI" );
        datatypes_comboBox.addItem( "xsd:time" );
        datatypes_comboBox.addItem( "xsd:date" );
        datatypes_comboBox.addItem( "xsd:dateTime" );
        return treeTable.getTreeTable( new JTextField(), relations_comboBox, datatypes_comboBox );
    }

    private void importNonKeys( String relationName )
    {
        String query = "SELECT column_name FROM information_schema.columns WHERE table_name = ? AND column_key != 'PRI' AND column_key != 'MUL' AND table_schema = ?";

        HashMap< Integer, String> parameters = new HashMap<>();
        parameters.put( 1, relationName );
        parameters.put( 2, schemaName );

        ArrayList< String> nonKeyNames = connection.getResultList( connection.executePreparedStatement( query, parameters ) );
        nonKeyNames.stream().forEach( ( nonKeyName )
                -> 
                {
                    HashMap<AttributeItem, String> attributeItems = new HashMap<>();
                    attributeItems.put( ATTRIBUTE_NAME, nonKeyName );
                    attributeItems.put( PARENT_RELATION_NAME, relationName );
                    treeTable.addNonKey( attributeItems, get( getDatatype( relationName, nonKeyName ) ) );
        } );
    }

    private void importPrimaryKeys( String relationName )
    {
        String query = "SELECT column_name FROM information_schema.columns WHERE table_name = ? AND column_key = 'PRI' AND table_schema = ?";

        HashMap< Integer, String> parameters = new HashMap<>();
        parameters.put( 1, relationName );
        parameters.put( 2, schemaName );

        ArrayList< String> primaryKeyNames = connection.getResultList( connection.executePreparedStatement( query, parameters ) );

        primaryKeyNames.stream().forEach( ( String primaryKeyName )
                -> 
                {
                    HashMap<AttributeItem, String> attributeItems = new HashMap<>();
                    attributeItems.put( ATTRIBUTE_NAME, primaryKeyName );
                    attributeItems.put( PARENT_RELATION_NAME, relationName );
                    treeTable.addPrimaryKey( attributeItems, get( getDatatype( relationName, primaryKeyName ) ) );
        } );
    }

    private void ImportForeignKeys( String relationName )
    {
        String query = "SELECT column_name FROM information_schema.key_column_usage WHERE table_name = ? AND constraint_name != 'PRIMARY' AND constraint_name != column_name AND table_schema = ?";

        HashMap< Integer, String> parameters = new HashMap<>();
        parameters.put( 1, relationName );
        parameters.put( 2, schemaName );

        ArrayList< String> foreign_key_names = connection.getResultList( connection.executePreparedStatement( query, parameters ) );

        for ( String foreignKeyName : foreign_key_names )
        {

            query = "SELECT * FROM information_schema.key_column_usage WHERE table_name = ? AND constraint_name != 'PRIMARY' AND constraint_name != column_name AND column_name = ? AND table_schema = ?";

            parameters = new HashMap<>();
            parameters.put( 1, relationName );
            parameters.put( 2, foreignKeyName );
            parameters.put( 3, schemaName );

            try
            {
                ResultSet resultSet = ( connection.executePreparedStatement( query, parameters ) );
                if ( resultSet.next() )
                {
                    HashMap<AttributeItem, String> attributeItems = new HashMap<>();
                    attributeItems.put( ATTRIBUTE_NAME, foreignKeyName );
                    attributeItems.put( PARENT_RELATION_NAME, relationName );
                    attributeItems.put( REFERENCED_RELATION_NAME, resultSet.getString( "referenced_table_name" ) );
                    attributeItems.put( REFERENCED_ATTRIBUTE_NAME, resultSet.getString( "referenced_column_name" ) );
                    treeTable.addForeignKey( attributeItems, get( getDatatype( relationName, foreignKeyName ) ) );
                }
            }
            catch ( SQLException ex )
            {
            }
        }
    }

    private String getDatatype( String relationName, String nonKeyName )
    {
        String query = "SELECT data_type FROM information_schema.columns WHERE table_schema = ? AND table_name = ? AND column_name = ?";
        HashMap< Integer, String> parameters = new HashMap<>();
        parameters.put( 1, schemaName );
        parameters.put( 2, relationName );
        parameters.put( 3, nonKeyName );

        return connection.getFirstResult( connection.executePreparedStatement( query, parameters ) );
    }

    private ArrayList< String> importRelations( String schemaName )
    {
        String query = "SELECT DISTINCT table_name FROM information_schema.key_column_usage WHERE table_schema = ?";
        HashMap< Integer, String> parameters = new HashMap<>();
        parameters.put( 1, schemaName );
        return connection.getResultList( connection.executePreparedStatement( query, parameters ) );
    }

}
