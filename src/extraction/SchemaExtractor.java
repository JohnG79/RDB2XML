package extraction;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXTreeTable;
import persistence.Connection;
import persistence.ConnectionParameter;
import treetable.table.Table;

public class SchemaExtractor
{

    JComboBox relationNames_comboBox;

    private Table treeTable;

    private String schemaName;

    private final Connection connection;

    public SchemaExtractor( Connection connection )
    {
        this.connection = connection;
    }

    public JXTreeTable getSchema()
    {
        relationNames_comboBox = new JComboBox( new String[]
        {
        } );

        treeTable = new Table( new String[]
        {
            "Data Objects", "New Terms", "Property Range"
        } );

        treeTable.setSchema( schemaName = connection.getConnectionParameter( ConnectionParameter.SCHEMA ) );

        ArrayList< String> relationNames = importRelations( schemaName );

        relationNames.stream().forEach( this::importPrimaryKeys );
        relationNames.stream().forEach( this::ImportForeignKeys );
        relationNames.stream().forEach( this::importNonKeys );

        JComboBox datatype_comboBox = new JComboBox();
        datatype_comboBox.addItem( "xsd:string" );
        datatype_comboBox.addItem( "xsd:integer" );
        datatype_comboBox.addItem( "xsd:decimal" );
        datatype_comboBox.addItem( "xsd:anyURI" );

        return treeTable.getTreeTable( new JTextField(), relationNames_comboBox, datatype_comboBox );
    }

    private void importPrimaryKeys( String relationName )
    {
        String query = "SELECT column_name FROM information_schema.columns WHERE table_name = ? AND column_key = 'PRI' AND table_schema = ?";

        HashMap< Integer, String> parameters = new HashMap<>();
        parameters.put( 1, relationName );
        parameters.put( 2, schemaName );

        ArrayList< String > primaryKeyNames = connection.getResultList( connection.executeQuery( query, parameters ) );

        primaryKeyNames.stream().forEach( ( String primaryKeyName )
                -> 
                {
                    treeTable.addPrimaryKey( relationName, primaryKeyName );
        } );
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
                    treeTable.addNonKey( relationName, nonKeyName );
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
            query = "SELECT referenced_table_name FROM information_schema.key_column_usage WHERE table_name = ? AND constraint_name != 'PRIMARY' AND constraint_name != column_name AND column_name = ? AND table_schema = ?";

            parameters = new HashMap<>();
            parameters.put( 1, relationName );
            parameters.put( 2, foreignKeyName );
            parameters.put( 3, schemaName );

            String referencedRelationName = connection.getFirstResult( connection.executeQuery( query, parameters ) );
            treeTable.addForeignKey( relationName, foreignKeyName, "FK" + relationName + "_" + foreignKeyName, "PK" + referencedRelationName );
        }
    }

    private ArrayList< String> importRelations( String schemaName )
    {
        String query = "SELECT DISTINCT table_name FROM information_schema.key_column_usage WHERE table_schema = ?";

        HashMap< Integer, String> parameters = new HashMap<>();
        parameters.put( 1, schemaName );

        ArrayList< String> relationNames = connection.getResultList( connection.executeQuery( query, parameters ) );
        relationNames.stream().forEach( ( String relationName )
                -> 
                {
                    treeTable.addRelation( relationName );
                    relationNames_comboBox.addItem( relationName );
        } );
        return relationNames;
    }

}
