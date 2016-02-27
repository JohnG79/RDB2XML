package control;

import persistence.MySQLConnection;

public abstract class ImportController extends Thread {

    protected MySQLConnection sqlConnection;

    public ImportController() {
        this.sqlConnection = new MySQLConnection();
    }
}
