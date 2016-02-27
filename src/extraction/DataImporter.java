package extraction;

import java.util.List;
import persistence.Connection;
import rdb2xml.ui.tree.node.Attribute;
import rdb2xml.ui.tree.node.RelationNode;

public class DataImporter {

    private final Connection connection;

    public DataImporter(Connection connection) {
        this.connection = connection;
    }

    public void importData(RelationNode relationNode) {
        String query = "SELECT ";
        List<Attribute> attributes = relationNode.getAttributes();
        for (Attribute attribute : attributes) {
            query += (attribute.getName() + ", ");
        }
        query = query.substring(0, query.length() - 2);
        query += " FROM " + relationNode.getName();

        connection.importResults(relationNode, connection.executeQuery(query));
    }
}
