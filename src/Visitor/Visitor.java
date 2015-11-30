package Visitor;

import rdb2xml.ui.tree.node.CombinedKeyNode;
import rdb2xml.ui.tree.node.ForeignKeyNode;
import rdb2xml.ui.tree.node.Key;
import rdb2xml.ui.tree.node.NonKeyNode;
import rdb2xml.ui.tree.node.RelationNode;
import rdb2xml.ui.tree.node.SchemaNode;

/**
 *
 * @author John
 */
public interface Visitor
{

    /**
     *
     * @param data_schema
     */
    void visit( SchemaNode data_schema );

    /**
     *
     * @param relation_schema
     */
    void visit( RelationNode relation_schema );

    /**
     *
     * @param non_key
     */
    void visit( NonKeyNode non_key );

    /**
     *
     * @param key
     */
    void visit( Key key );

    /**
     *
     * @param foreign_key
     */
    void visit( ForeignKeyNode foreign_key );

    /**
     *
     * @param combined_key
     */
    void visit( CombinedKeyNode combined_key );

}
