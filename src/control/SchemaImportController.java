package control;

import extraction.SchemaImporter;
import org.jdesktop.swingx.JXTreeTable;
import persistence.DataFormat;
import rdb2xml.ui.ConnectDialog;
import rdb2xml.ui.MainInterface;

public class SchemaImportController extends ImportController
{

    private final ConnectDialog connectDialog;
    private final MainInterface mainInterface;

    public SchemaImportController( ConnectDialog connectDialog, MainInterface mainInterface )
    {
        super();
        this.connectDialog = connectDialog;
        this.mainInterface = mainInterface;
    }

    @Override
    public void run()
    {
        connectDialog.setLocationRelativeTo( mainInterface );
        connectDialog.setVisible( true );
        DataFormat dataFormat = this.connectDialog.getDataFormat();
        if ( sqlConnection.connect( this.connectDialog.getConnectionParams() ) )
        {
            importSchema( dataFormat );
            mainInterface.setSchema( importSchema( dataFormat ) );
            mainInterface.showSchemaTab( true );
        }
        connectDialog.setVisible( false );
        super.sqlConnection = sqlConnection;
        this.stop();
    }

    private JXTreeTable importSchema( DataFormat newFormat )
    {
        if ( sqlConnection.isConnected() )
        {
            SchemaImporter database = new SchemaImporter( sqlConnection );
            jXTreeTable = database.importSchema( newFormat );
            return jXTreeTable;
        }
        return null;
    }
    private static JXTreeTable jXTreeTable = null;

    public static JXTreeTable getSchemaTreeTable()
    {
        return jXTreeTable;
    }
}
