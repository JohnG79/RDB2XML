package control;

import extraction.SchemaExtractor;
import java.util.HashMap;
import org.jdesktop.swingx.JXTreeTable;
import persistence.Connection;
import persistence.ConnectionParameter;
import static persistence.ConnectionParameter.HOST;
import static persistence.ConnectionParameter.PASSWORD;
import static persistence.ConnectionParameter.PORT;
import static persistence.ConnectionParameter.SCHEMA;
import static persistence.ConnectionParameter.USERNAME;
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
        parameters.put(HOST, host );
        parameters.put(PORT, port );
        parameters.put(SCHEMA, database );
        parameters.put(USERNAME, username );
        parameters.put(PASSWORD, password );

        Connection connection = new MySQLConnection();
        if ( connection.connect( parameters ) )
        {
            SchemaExtractor schemaExtractor = new SchemaExtractor( connection );
            JXTreeTable treeTable = schemaExtractor.getSchema();
            mainFrame.setTreeTable( treeTable );
        }
    }
}
