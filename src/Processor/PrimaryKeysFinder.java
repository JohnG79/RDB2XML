/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processor;

import java.util.HashMap;
import java.util.Map;
import rdb2xml.ui.tree.node.CombinedKeyNode;
import rdb2xml.ui.tree.node.ForeignKeyNode;
import rdb2xml.ui.tree.node.Key;
import rdb2xml.ui.tree.node.NonKeyNode;
import rdb2xml.ui.tree.node.PrimaryKeyNode;
import rdb2xml.ui.tree.node.RelationNode;
import rdb2xml.ui.tree.node.SchemaNode;
import rdb2xml.ui.tree.node.Tuple;

/**
 *
 * @author johng
 */
public class PrimaryKeysFinder implements Processor
{
    public Map<RelationNode, PrimaryKeyNode> primaryKeys;
    
    public PrimaryKeysFinder()
    {
        primaryKeys = new HashMap<>();
    }
    
    @Override
    public void visit( Tuple tuple )
    {

    }

    @Override
    public void visit( SchemaNode data_schema )
    {

    }
    private RelationNode currentRelation = null;
    
    @Override
    public void visit( RelationNode relation_schema )
    {
        currentRelation = relation_schema;
    }

    @Override
    public void visit( NonKeyNode non_key )
    {

    }

    @Override
    public void visit( Key key )
    {
        if( currentRelation != null )
            primaryKeys.put( currentRelation, (PrimaryKeyNode)key );
    }

    @Override
    public void visit( ForeignKeyNode foreign_key )
    {

    }

    @Override
    public void visit( CombinedKeyNode combined_key )
    {

    }    
}
