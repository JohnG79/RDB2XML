/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processor;

import java.util.ArrayList;
import rdb2xml.ui.tree.node.Attribute;
import rdb2xml.ui.tree.node.CombinedKeyNode;
import rdb2xml.ui.tree.node.Foreign;
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
public class ReferencingKeyFinder implements Processor {

    private final PrimaryKeyNode primaryKeyNode;
    public final ArrayList<Foreign> referencingKeys;

    private ReferencingKeyFinder() {
        this.primaryKeyNode = null;
        this.referencingKeys = null;
    }

    public ReferencingKeyFinder(PrimaryKeyNode primaryKeyNode) {
        this.primaryKeyNode = primaryKeyNode;
        referencingKeys = new ArrayList<Foreign>();
    }

    @Override
    public void visit(Tuple tuple) {

    }

    @Override
    public void visit(SchemaNode data_schema) {
        for (RelationNode relationNode : data_schema.getRelations()) {
            relationNode.acceptProcessor(this);
        }
    }

    @Override
    public void visit(RelationNode relation_schema) {
        for (Attribute attribute : relation_schema.getAttributes()) {
            attribute.acceptProcessor(this);
        }
    }

    @Override
    public void visit(NonKeyNode non_key) {

    }

    @Override
    public void visit(Key key) {

    }

    @Override
    public void visit(ForeignKeyNode foreign_key) {
        if (foreign_key.getReferencedKey() == primaryKeyNode) {
            referencingKeys.add(foreign_key);
        }
    }

    @Override
    public void visit(CombinedKeyNode combined_key) {
        if (combined_key.getReferencedKey() == primaryKeyNode) {
            referencingKeys.add(combined_key);
        }
    }
}
