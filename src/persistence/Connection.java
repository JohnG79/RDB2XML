package persistence;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import rdb2xml.ui.tree.node.RelationNode;

public interface Connection {

    public boolean connect(HashMap< ConnectionParameter, String> connectionParameters);

    public boolean isConnected();

    public ResultSet executePreparedStatement(String query, HashMap< Integer, String> parameters);

    public String getFirstResult(ResultSet resultSet);

    public ArrayList< String> getResultList(ResultSet resultSet);

    public String getConnectionParameter(ConnectionParameter parameter);

    public ResultSet executeQuery(String query);

    public void importResults(RelationNode relationNode, ResultSet resultSet);

}
