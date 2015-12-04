package extraction;

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
import rdb2xml.ui.tree.node.Attribute;
import rdb2xml.ui.tree.node.Key;
import rdb2xml.ui.tree.node.NonKeyNode;
import rdb2xml.ui.tree.table.RDFTable;
import rdb2xml.ui.tree.table.Table;
import rdb2xml.ui.tree.table.XMLTable;

public class Database
{

    private Table treeTable;
    private String schemaName;
    private final Connection connection;
    JComboBox relationNames_comboBox;

    public Database( Connection connection )
    {
        this.connection = connection;
    }

    public JXTreeTable importSchema( DataFormat dataFormat )
    {
        relationNames_comboBox = new JComboBox( new String[]
        {
        } );

        treeTable = ( dataFormat == DataFormat.XML ? new XMLTable() : new RDFTable() );

        treeTable.setSchemaName( schemaName = connection.getConnectionParameter( SCHEMA ) );

        ArrayList< String> relationNames = importRelations( schemaName );

        for ( String relationName : relationNames )
        {
            treeTable.addRelation( relationName );
            relationNames_comboBox.addItem( relationName.substring( 0, 1 ).toUpperCase() + relationName.substring( 1 ) );
            importPrimaryKeys( relationName );
            importNonKeys( relationName );
        }
        for ( String relationName : relationNames )
        {
            ImportForeignKeys( relationName );
        }

        JComboBox datatype_comboBox = new JComboBox();
        datatype_comboBox.addItem( "xsd:string" );
        datatype_comboBox.addItem( "xsd:integer" );
        datatype_comboBox.addItem( "xsd:decimal" );
        datatype_comboBox.addItem( "xsd:anyURI" );
        return treeTable.getTreeTable( new JTextField(), relationNames_comboBox, datatype_comboBox );
    }

    private void importNonKeys( String relationName )
    {
        String query = "SELECT column_name FROM information_schema.columns WHERE table_name = ? AND column_key != 'PRI' AND column_key != 'MUL' AND table_schema = ?";

        HashMap< Integer, String> parameters = new HashMap<>();
        parameters.put( 1, relationName );
        parameters.put( 2, schemaName );

        ArrayList< String> non_key_names = connection.getResultList( connection.executeQuery( query, parameters ) );
        non_key_names.stream().forEach( ( nonKeyName )
                -> 
                {
                    treeTable.addNonKey( relationName, nonKeyName ).setValueAt( getXSDDatatype( getDatatype( relationName, nonKeyName ) ), 2 );
        } );
    }

    private void importPrimaryKeys( String relationName )
    {
        String query = "SELECT column_name FROM information_schema.columns WHERE table_name = ? AND column_key = 'PRI' AND table_schema = ?";

        HashMap< Integer, String> parameters = new HashMap<>();
        parameters.put( 1, relationName );
        parameters.put( 2, schemaName );

        ArrayList< String> primaryKeyNames = connection.getResultList( connection.executeQuery( query, parameters ) );

        primaryKeyNames.stream().forEach( ( String primaryKeyName )
                -> 
                {
                    Attribute a = ( treeTable.addPrimaryKey( relationName, primaryKeyName, "PK" + relationName ) );
                    a.setDatatype( getXSDDatatype( getDatatype( relationName, primaryKeyName ) ) );
        } );
    }

    private void ImportForeignKeys( String relationName )
    {
        String query = "SELECT column_name FROM information_schema.key_column_usage WHERE table_name = ? AND constraint_name != 'PRIMARY' AND constraint_name != column_name AND table_schema = ?";

        HashMap< Integer, String> parameters = new HashMap<>();
        parameters.put( 1, relationName );
        parameters.put( 2, schemaName );

        ArrayList< String> foreign_key_names = connection.getResultList( connection.executeQuery( query, parameters ) );

        for ( String foreignKeyName : foreign_key_names )
        {

            query = "SELECT * FROM information_schema.key_column_usage WHERE table_name = ? AND constraint_name != 'PRIMARY' AND constraint_name != column_name AND column_name = ? AND table_schema = ?";

            parameters = new HashMap<>();
            parameters.put( 1, relationName );
            parameters.put( 2, foreignKeyName );
            parameters.put( 3, schemaName );

            try
            {
                ResultSet r = ( connection.executeQuery( query, parameters ) );
                if ( r.next() )
                {
                    getXSDDatatype( getDatatype( relationName, foreignKeyName ) );

                    Attribute a = ( treeTable.addForeignKey( relationName, foreignKeyName, r.getString( "referenced_table_name" ), r.getString( "REFERENCED_COLUMN_NAME" ), "FK" + relationName + "_" + foreignKeyName ) );
                    a.setDatatype( getXSDDatatype( getDatatype( relationName, foreignKeyName ) ) );

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

        return connection.getFirstResult( connection.executeQuery( query, parameters ) );
    }

    private String getXSDDatatype( String dataType )
    {
        if ( dataType.toLowerCase().contains( "int" ) )
        {
            return "xsd:integer";
        }
        else if ( dataType.toLowerCase().contains( "dec" ) )
        {
            return "xsd:decimal";
        }
        else if ( dataType.toLowerCase().contains( "date" ) )
        {
            return "xsd:date";
        }
        else
        {
            return "xsd:string";
        }
    }

    private ArrayList< String> importRelations( String schemaName )
    {
        String query = "SELECT DISTINCT table_name FROM information_schema.key_column_usage WHERE table_schema = ?";
        HashMap< Integer, String> parameters = new HashMap<>();
        parameters.put( 1, schemaName );
        return connection.getResultList( connection.executeQuery( query, parameters ) );
    }

}
