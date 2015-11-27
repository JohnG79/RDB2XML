/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author johng
 */
public interface Connection
{
    public boolean connect( HashMap< ConnectionParameter, String> connectionParameters );
    
    public ResultSet executeQuery( String query, HashMap< Integer, String > parameters );
    
    public String getFirstResult( ResultSet resultSet );
    
    public ArrayList< String > getResultList( ResultSet resultSet );
    
    public String getConnectionParameter( ConnectionParameter parameter );
}
