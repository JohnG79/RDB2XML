package extraction;

import persistence.Connection;

public abstract class Importer {

    protected final Connection connection;

    public Importer(Connection connection) {
        this.connection = connection;
    }
}
