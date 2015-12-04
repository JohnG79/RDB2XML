package rdb2xml.ui.tree.table;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;
import javax.swing.*;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
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

public abstract class Table
{

    protected int treeItemNumber;

    protected final String[] columnHeadings;

    protected SchemaNode schemaNode;

    protected DefaultTreeTableModel tableModel;
    protected JXTreeTable treeTable;

    public Table()
    {
        treeItemNumber = 1;
        this.columnHeadings = null;
    }

    protected Table( String[] columnHeadings )
    {
        treeItemNumber = 1;
        this.columnHeadings = columnHeadings;
        this.schemaNode = new SchemaNode( 0, new String[]
        {
            "SCHEMA_NAME_NOT_SET"
        } );
    }

    public void setSchemaName( String schemaName )
    {
        this.schemaNode.setUserObject( new String[]
        {
            schemaName
        } );
    }

    public AbstractNode getDataSchemaNode()
    {
        return schemaNode;
    }

    public RelationNode addRelation( String relationName )
    {
        RelationNode newRelationSchemaNode = new RelationNode( treeItemNumber, new Object[]
        {
            relationName, relationName.substring( 0, 1 ).toUpperCase() + relationName.substring( 1 )
        } );

        this.schemaNode.add( newRelationSchemaNode );
        treeItemNumber++;
        return newRelationSchemaNode;
    }

    public Enumeration<? extends MutableTreeTableNode> getRelationNodes()
    {
        return this.schemaNode.children();
    }

    public NonKeyNode addNonKey( String relationName, String nonKeyName )
    {
        NonKeyNode newNonKeyNode = new NonKeyNode( treeItemNumber, new String[]
        {
            nonKeyName, nonKeyName, "", ""
        }, "xsd:string" );
        Enumeration< RelationNode> relationNodes = ( Enumeration<RelationNode> ) getRelationNodes();
        while ( relationNodes.hasMoreElements() )
        {
            RelationNode relationNode = relationNodes.nextElement();
            if ( relationNode.getValueAt( 0 ).toString().equals( relationName ) )
            {
                relationNode.add( newNonKeyNode );
            }
        }

        treeItemNumber++;
        return newNonKeyNode;
    }

    public PrimaryKeyNode addPrimaryKey( String relationName, String primaryKeyName, String constraintName )
    {
        PrimaryKeyNode newPrimaryKeyNode = new PrimaryKeyNode( treeItemNumber, new Object[]
        {
            primaryKeyName, primaryKeyName, "", ""
        }, constraintName, "xsd:string" );

        Enumeration< RelationNode> relationNodes = ( Enumeration<RelationNode> ) getRelationNodes();
        while ( relationNodes.hasMoreElements() )
        {
            RelationNode relationNode = relationNodes.nextElement();
            if ( relationNode.getValueAt( 0 ).toString().equals( relationName ) )
            {
                relationNode.add( newPrimaryKeyNode );
            }
        }
        treeItemNumber++;
        return newPrimaryKeyNode;
    }

    public Key addForeignKey( String relationName, String foreignKeyName, String referencedTableName, String referencedColumnName, String constraintName )
    {
        rdb2xml.ui.tree.node.AbstractMutableTreeTableNode key = null;
        Enumeration< RelationNode> relationNodes = ( Enumeration<RelationNode> ) getRelationNodes();
        while ( relationNodes.hasMoreElements() )
        {
            RelationNode relationNode = relationNodes.nextElement();
            if ( relationNode.getValueAt( 0 ).toString().equals( relationName ) )
            {
                Enumeration< AbstractLeafNode> attributes = ( Enumeration< AbstractLeafNode> ) relationNode.children();
                while ( attributes.hasMoreElements() )
                {
                    AbstractLeafNode attribute = attributes.nextElement();
                    String attributeName = attribute.getValueAt( 0 ).toString();

                    if ( attributeName.equals( foreignKeyName ) )
                    {
                        int index = relationNode.getIndex( attribute );
                        relationNode.remove( index );

                        relationNode.insert( key = new CombinedKeyNode( attribute.getTreeItemNumber(), new Object[]
                        {
                            foreignKeyName, foreignKeyName, "", ""
                        }, referencedTableName, referencedColumnName, constraintName, getReferencedPrimary( referencedTableName, referencedColumnName ), "xsd:string" ), index );

                        return ( Key ) key;
                    }
                }
                relationNode.add( key = new ForeignKeyNode( treeItemNumber, new Object[]
                {
                    foreignKeyName, foreignKeyName, "", ""
                }, referencedTableName, referencedColumnName, constraintName, getReferencedPrimary( referencedTableName, referencedColumnName ), "xsd:string" ) );

                treeItemNumber++;
            }
        }
        return ( Key ) key;
    }

    public abstract JXTreeTable getTreeTable( JTextField jTextField, JComboBox relationNames_comboBox, JComboBox datatype_comboBox );

    private Primary getReferencedPrimary( String relationName, String keyName )
    {
        ArrayList< RelationNode> relations = ( ArrayList< RelationNode> ) this.schemaNode.getRelations();
        for ( RelationNode relationNode : relations )
        {
            if ( relationNode.getName().equals( relationName ) )
            {
                for ( Attribute attribute : relationNode.getAttributes() )
                {
                    if ( ( ( PrimaryKeyNode ) attribute ).getName().equals( keyName ) )
                    {
                        return ( Primary ) attribute;
                    }
                }
            }
        }
        throw new IllegalStateException( "Referenced primary key doesn't exist!" );
    }

    protected void setFocusHighlight( JComponent component )
    {
        component.addFocusListener( new FocusListener()
        {
            @Override
            public void focusGained( FocusEvent e )
            {
                component.setBackground( new Color( 255, 255, 225 ) );
            }

            @Override
            public void focusLost( FocusEvent e )
            {
                component.setBackground( new Color( 255, 255, 225 ) );
            }
        } );
    }

}
