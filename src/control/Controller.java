package control;

import Visitor.XSDBuilder;
import extraction.Schema;
import java.util.HashMap;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
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
import rdb2xml.ui.tree.node.SchemaNode;

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
    JXTreeTable treeTable;

    public void connect( String host, String port, String database, String username, String password )
    {

        HashMap< ConnectionParameter, String> parameters = new HashMap<>();
        parameters.put( HOST, host );
        parameters.put( PORT, port );
        parameters.put( SCHEMA, database );
        parameters.put( USERNAME, username );
        parameters.put( PASSWORD, password );

        Connection connection = new MySQLConnection();
        if ( connection.connect( parameters ) )
        {
            Schema schema = new Schema( connection );
            mainFrame.setSchema( treeTable = schema.importSchema() );
        }
    }

    public void translateXSD( RSyntaxTextArea syntaxTextArea )
    {
        XSDBuilder xsdBuilder = new XSDBuilder();
        SchemaNode schemaNode = ( SchemaNode ) treeTable.getTreeTableModel().getRoot();
        schemaNode.acceptVisitor( xsdBuilder );

        xsdBuilder.print( syntaxTextArea );

    }
}
