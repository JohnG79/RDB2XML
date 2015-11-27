package control;

import extraction.SchemaExtractor;
import java.util.HashMap;
import org.jdesktop.swingx.JXTreeTable;
import persistence.Connection;
import persistence.ConnectionParameter;
import persistence.MySQLConnection;
import rdb2xml.ui.*;

public class Controller
{
    private ConnectFrame connectFrame;
    
    public void initiateConnect()
    {
        connectFrame = new ConnectFrame( this );
        connectFrame.setLocationRelativeTo( null );
        connectFrame.setVisible( true );
    }
    
    private MainFrame mainFrame;
    public void setMainFrame( MainFrame mainFrame )
    {
        this.mainFrame = mainFrame;
    }
    
    public void connect( String host, String port, String database, String username, String password )
    {

        HashMap< ConnectionParameter, String> parameters = new HashMap<>();
        parameters.put( ConnectionParameter.HOST, host );
        parameters.put( ConnectionParameter.PORT, port );
        parameters.put(ConnectionParameter.SCHEMA, database );
        parameters.put( ConnectionParameter.USERNAME, username );
        parameters.put( ConnectionParameter.PASSWORD, password );

        Connection connection = new MySQLConnection();
        if ( connection.connect( parameters ) )
        {
            SchemaExtractor schemaExtractor = new SchemaExtractor( connection );
            JXTreeTable treeTable = schemaExtractor.getSchema();
            System.out.println(treeTable);
            mainFrame.setTreeTable( treeTable );
        }
    }
}

