package control;

import extraction.SchemaImporter;
import org.jdesktop.swingx.JXTreeTable;
import persistence.DataFormat;
import rdb2xml.ui.ConnectDialog;
import rdb2xml.ui.MainInterface;

public class SchemaImportThread extends ImportController
{

    private final ConnectDialog connectDialog;
    private final MainInterface mainInterface;

    public SchemaImportThread( ConnectDialog connectDialog, MainInterface mainInterface )
    {
        super();
        this.connectDialog = connectDialog;
        this.mainInterface = mainInterface;
    }

    @Override
    public void run()
    {
        //show connection-dialog box
        this.connectDialog.setLocationRelativeTo( mainInterface );
        this.connectDialog.setVisible( true );

        DataFormat dataFormat = this.connectDialog.getDataFormat();

        /**
         * getConnectionParams() is synchronized; this thread wait()s for user
         * to finish with the connectionDialog.
         */
        if ( sqlConnection.connect( this.connectDialog.getConnectionParams() ) )
        {
            importSchema( dataFormat );
            mainInterface.setTreeTable( importSchema( dataFormat ) );
            mainInterface.showTreeTableTab( true );
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
        if ( jXTreeTable == null )
        {
            throw new IllegalStateException( "Schema must be imported before the data is imported." );
        }
        return jXTreeTable;
    }
}
