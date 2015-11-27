package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Set;
import static java.sql.DriverManager.getConnection;

public class MySQLConnection implements Connection
{
    private java.sql.Connection connection;
    private HashMap< ConnectionParameter, String > parameters;
    public MySQLConnection()
    {
        try
        {
            Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
        }
        catch ( ClassNotFoundException | InstantiationException | IllegalAccessException ex )
        {
            System.err.println( ex.getMessage() );
        }
        parameters = new HashMap<>();
    }

    public boolean connect( HashMap< ConnectionParameter, String> connectionParameters )
    {
        parameters = connectionParameters;
        String host = connectionParameters.get( ConnectionParameter.HOST );
        String port = connectionParameters.get( ConnectionParameter.PORT );
        String user_name = connectionParameters.get( ConnectionParameter.USERNAME );
        String password = connectionParameters.get( ConnectionParameter.PASSWORD );
        String databaseName = connectionParameters.get(ConnectionParameter.SCHEMA );
        
        try
        {
            connection = getConnection( "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?user=" + user_name + "&password=" + password );
            return true;
        }
        catch ( SQLException ex )
        {
            System.err.println( ex.getMessage() );
        }
        return false;
    }

    public String getConnectionParameter( ConnectionParameter parameter )
    {
        return parameters.get( parameter );
    }
    
    @Override
    public ResultSet executeQuery( String query, HashMap< Integer, String> parameters )
    {
        try
        {
            PreparedStatement prepared_statement = connection.prepareStatement( query );

            Set< Integer > keys = parameters.keySet();

            for ( Integer key : keys )
            {
                prepared_statement.setString( key, parameters.get( key ) );
            }

            return prepared_statement.executeQuery();
        }
        catch ( SQLException ex )
        {
            System.err.println( ex.getMessage() );
        }
        return null;
    }

    @Override
    public String getFirstResult( ResultSet resultSet )
    {
        try
        {
            if ( resultSet.next() )
            {
                return resultSet.getString( 1 );
            }
        }
        catch ( SQLException ex )
        {
            System.err.println( ex.getMessage() );
        }
        return "";
    }

    @Override
    public ArrayList< String > getResultList( ResultSet resultSet )
    {
        try
        {
            ArrayList< String > results = new ArrayList<>();
            while ( resultSet.next() )
            {
                results.add( resultSet.getString( 1 ) );
            }
            return results;
        }
        catch ( SQLException ex )
        {
            System.err.println( ex.getMessage() );
        }
        return new ArrayList<>();
    }
}