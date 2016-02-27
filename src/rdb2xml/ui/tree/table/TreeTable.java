package rdb2xml.ui.tree.table;

import extraction.AttributeItem;
import static extraction.AttributeItem.ATTRIBUTE_NAME;
import static extraction.AttributeItem.PARENT_RELATION_NAME;
import static extraction.AttributeItem.REFERENCED_ATTRIBUTE_NAME;
import static extraction.AttributeItem.REFERENCED_RELATION_NAME;
import extraction.XSDDatatype;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.TreePath;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;
import rdb2xml.ui.tree.node.AbstractLeafNode;
import rdb2xml.ui.tree.node.AbstractNode;
import rdb2xml.ui.tree.node.Attribute;
import rdb2xml.ui.tree.node.CombinedKeyNode;
import rdb2xml.ui.tree.node.ForeignKeyNode;
import rdb2xml.ui.tree.node.Key;
import rdb2xml.ui.tree.node.NonKeyNode;
import rdb2xml.ui.tree.node.Primary;
import rdb2xml.ui.tree.node.PrimaryKeyNode;
import rdb2xml.ui.tree.node.RelationNode;
import rdb2xml.ui.tree.node.SchemaNode;

public abstract class TreeTable {

    public static void setFont(JComponent jComponent) {
        jComponent.setFont(new Font("Courier New", Font.BOLD, 16));
    }

    protected static void setFocusHighlight(JComponent component) {
        component.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                component.setBackground(new Color(255, 255, 225));
            }

            @Override
            public void focusLost(FocusEvent e) {
                component.setBackground(new Color(255, 255, 225));
            }
        });
    }

    protected final String[] columnHeadings;
    protected SchemaNode schemaNode;
    protected DefaultTreeTableModel tableModel;
    protected JXTreeTable treeTable;

    public TreeTable(String[] columnHeadings, int columnCount) {
        this.columnHeadings = columnHeadings;
        this.schemaNode = new SchemaNode(new String());
        this.schemaNode.setColumnCount(columnCount);
    }

    public TreeTable(String[] columnHeadings, String rootNodeName, int columnCount) {
        this.columnHeadings = columnHeadings;
        this.schemaNode = new SchemaNode(rootNodeName);

        this.schemaNode.addDataNamespace("wtc", "http://www.weltec.ac.nz/lod/data#");
        this.schemaNode.addOntologyNamespace("ont", "http://www.weltec.ac.nz/lod/ontology#");
        this.schemaNode.setColumnCount(columnCount);
    }

    private TreeTable() {
        this.columnHeadings = null;
    }

    public SchemaNode getSchemaNode() {
        return schemaNode;
    }

    public RelationNode addRelation(String relationName) {
        RelationNode newRelationSchemaNode = new RelationNode(relationName, relationName.substring(0, 1).toUpperCase() + relationName.substring(1));

        this.schemaNode.add(newRelationSchemaNode);
        return newRelationSchemaNode;
    }

    public Enumeration<? extends MutableTreeTableNode> getRelationNodes() {
        return this.schemaNode.children();
    }

    public NonKeyNode addNonKey(HashMap<AttributeItem, String> attributeItems, XSDDatatype xsdDatatype) {
        final String nonKeyName = attributeItems.get(ATTRIBUTE_NAME);
        final String relationName = attributeItems.get(PARENT_RELATION_NAME);

        NonKeyNode newNonKeyNode = new NonKeyNode(nonKeyName, xsdDatatype);
        Enumeration< RelationNode> relationNodes = (Enumeration<RelationNode>) getRelationNodes();
        while (relationNodes.hasMoreElements()) {
            RelationNode relationNode = relationNodes.nextElement();
            if (relationNode.getValueAt(0).toString().equals(relationName)) {
                relationNode.add(newNonKeyNode);
            }
        }
        return newNonKeyNode;
    }

    public PrimaryKeyNode addPrimaryKey(HashMap<AttributeItem, String> attributeItems, XSDDatatype xsdDatatype) {
        final String relationName = attributeItems.get(PARENT_RELATION_NAME);

        PrimaryKeyNode newPrimaryKeyNode = new PrimaryKeyNode(attributeItems, xsdDatatype);

        Enumeration< RelationNode> relationNodes = (Enumeration<RelationNode>) getRelationNodes();
        while (relationNodes.hasMoreElements()) {
            RelationNode relationNode = relationNodes.nextElement();
            if (relationNode.getValueAt(0).toString().equals(relationName)) {
                relationNode.add(newPrimaryKeyNode);
            }
        }
        return newPrimaryKeyNode;
    }

    public Key addForeignKey(HashMap<AttributeItem, String> attributeItems, XSDDatatype xsdDatatype) {
        final String foreignKeyName = attributeItems.get(ATTRIBUTE_NAME);
        final String relationName = attributeItems.get(PARENT_RELATION_NAME);
        final String refRelationName = attributeItems.get(REFERENCED_RELATION_NAME);
        final String refKeyName = attributeItems.get(REFERENCED_ATTRIBUTE_NAME);

        rdb2xml.ui.tree.node.AbstractMutableTreeTableNode key = null;
        Enumeration< RelationNode> relationNodes = (Enumeration<RelationNode>) getRelationNodes();
        while (relationNodes.hasMoreElements()) {
            RelationNode relationNode = relationNodes.nextElement();
            if (relationNode.getValueAt(0).toString().equals(relationName)) {
                Enumeration< AbstractLeafNode> attributes = (Enumeration< AbstractLeafNode>) relationNode.children();
                while (attributes.hasMoreElements()) {
                    AbstractLeafNode attribute = attributes.nextElement();
                    String attributeName = attribute.getValueAt(0).toString();

                    if (attributeName.equals(foreignKeyName)) {
                        int index = relationNode.getIndex(attribute);
                        relationNode.remove(index);

                        relationNode.insert(
                                key = new CombinedKeyNode(
                                        attributeItems,
                                        referencedKey(refRelationName, refKeyName),
                                        xsdDatatype),
                                index);

                        return (Key) key;
                    }
                }
                relationNode.add(key = new ForeignKeyNode(
                        attributeItems,
                        referencedKey(refRelationName, refKeyName),
                        xsdDatatype
                ));
            }
        }
        return (Key) key;
    }

    public abstract JXTreeTable getTreeTable(JTextField jTextField, JComboBox relationNames_comboBox, JComboBox datatype_comboBox);

    private Primary referencedKey(String relationName, String keyName) {
        ArrayList< RelationNode> relations = (ArrayList< RelationNode>) this.schemaNode.getRelations();
        for (RelationNode relationNode : relations) {
            if (relationNode.getName().equals(relationName)) {
                for (Attribute attribute : relationNode.getAttributes()) {
                    if (attribute.getName().equals(keyName)) {
                        return (Primary) attribute;
                    }
                }
            }
        }
        throw new IllegalStateException("Referenced primary key doesn't exist!");
    }

    protected void clearCell(Class clas, int column) {
        TreePath treePath;
        Object node;
        Class c;
        boolean isEditable;
        for (int row = 0; row < treeTable.getRowCount(); row++) {
            treePath = treeTable.getPathForRow(row);
            node = treePath.getLastPathComponent();
            c = node.getClass();
            if (c.equals(clas)) {
                isEditable = ((TreeTableNode) node).isEditable(column);
                if (!isEditable) {
                    ((AbstractNode) node).setEditable(column, true);
                }
                treeTable.setValueAt("", row, column);
                ((AbstractNode) node).setEditable(column, isEditable);
            }
        }
    }
}
