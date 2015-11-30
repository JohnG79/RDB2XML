package rdb2xml.ui.tree.table;

import java.awt.*;
import java.util.*;
import static java.util.Arrays.asList;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.*;
import static org.jdesktop.swingx.decorator.HighlightPredicate.EVEN;
import static org.jdesktop.swingx.decorator.HighlightPredicate.IS_SELECTED;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import rdb2xml.ui.tree.node.AbstractLeafNode;
import rdb2xml.ui.tree.node.AbstractNode;
import rdb2xml.ui.tree.node.Attribute;
import rdb2xml.ui.tree.node.CombinedKeyNode;
import rdb2xml.ui.tree.node.ForeignKeyNode;
import rdb2xml.ui.tree.node.NonKeyNode;
import rdb2xml.ui.tree.node.Primary;
import rdb2xml.ui.tree.node.PrimaryKeyNode;
import rdb2xml.ui.tree.node.RelationNode;
import rdb2xml.ui.tree.node.SchemaNode;

public class Table
{

    private int treeItemNumber;

    private final String[] columnHeadings;

    private SchemaNode schemaNode;
    private RelationNode currentRelationNode;

    private DefaultTreeTableModel tableModel;
    private JXTreeTable treeTable;

    private Table()
    {
        treeItemNumber = 1;
        this.columnHeadings = null;
    }

    public Table( String[] columnHeadings )
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
        currentRelationNode = newRelationSchemaNode;
        relationSchema_rowNumbers.add( treeItemNumber );
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
            nonKeyName, nonKeyName, ""
        } );
        Enumeration< RelationNode> relationNodes = ( Enumeration<RelationNode> ) getRelationNodes();
        while ( relationNodes.hasMoreElements() )
        {
            RelationNode relationNode = relationNodes.nextElement();
            if ( relationNode.getValueAt( 0 ).toString().equals( relationName ) )
            {
                relationNode.add( newNonKeyNode );
                nonKey_rowNumbers.add( treeItemNumber );
            }
        }

        treeItemNumber++;
        return newNonKeyNode;
    }

    public void addPrimaryKey( String relationName, String primaryKeyName, String constraintName )
    {
        PrimaryKeyNode newPrimaryKeyNode = new PrimaryKeyNode( treeItemNumber, new Object[]
        {
            primaryKeyName, primaryKeyName, ""
        }, constraintName );

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
    }

    public CombinedKeyNode addCombinedKey( String combinedKeyName, String referencedTableName, String referencedColumnName, String constraintName )
    {
        CombinedKeyNode newCombinedKeyNode = new CombinedKeyNode( treeItemNumber, new String[]
        {
            combinedKeyName, combinedKeyName, ""
        }, referencedTableName, referencedColumnName, constraintName, getReferencedPrimary( referencedTableName, referencedColumnName ) );
        currentRelationNode.add( newCombinedKeyNode );
        // record table row number of each foreign key
        foreignKey_rowNumbers.add( treeItemNumber );
        treeItemNumber++;
        return newCombinedKeyNode;

    }

    public boolean addForeignKey( String relationName, String foreignKeyName, String referencedTableName, String referencedColumnName, String constraintName )
    {
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

                    // if key exists as foreign key...
                    if ( attributeName.equals( foreignKeyName ) )
                    {
                        int index = relationNode.getIndex( attribute );
                        relationNode.remove( index );
                        relationNode.insert( new CombinedKeyNode( attribute.getTreeItemNumber(), new Object[]
                        {
                            foreignKeyName, foreignKeyName, ""
                        }, referencedTableName, referencedColumnName, constraintName, getReferencedPrimary( referencedTableName, referencedColumnName ) ), index );

                        foreignKey_rowNumbers.add( attribute.getTreeItemNumber() );
                        return true;
                    }
                }
                relationNode.add( new ForeignKeyNode( treeItemNumber, new Object[]
                {
                    foreignKeyName, foreignKeyName, ""
                }, referencedTableName, referencedColumnName, constraintName, getReferencedPrimary( referencedTableName, referencedColumnName ) ) );

                foreignKey_rowNumbers.add( treeItemNumber );
                treeItemNumber++;
            }
        }
        return false;
    }

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

    public JXTreeTable getTreeTable( JTextField termsColumn_textField, JComboBox relationSchemaComboBox, JComboBox datatypesComboBox )
    {

        this.tableModel = new DefaultTreeTableModel( schemaNode, asList( columnHeadings ) );

        this.treeTable = new JXTreeTable( tableModel )
        {

            @Override
            public TableCellEditor getCellEditor( int treeTableRow, int tree_table_column )
            {
                DefaultCellEditor newCellEditor;
                // if column = 0 OR column = 1...
                if ( tree_table_column == 1 )
                {
                    String preEditText = getValueAt( treeTableRow, tree_table_column ).toString();
                    newCellEditor = new DefaultCellEditor( termsColumn_textField );

                    if ( relationSchema_rowNumbers.contains( treeTableRow ) )
                    {
                        newCellEditor.addCellEditorListener( new CellEditorListener()
                        {
                            @Override
                            public void editingStopped( ChangeEvent e )
                            {
                                String postEditText = newCellEditor.getCellEditorValue().toString();

                                if ( !preEditText.equals( postEditText ) )
                                {
                                    DefaultComboBoxModel relationSchema_names = ( DefaultComboBoxModel ) relationSchemaComboBox.getModel();
                                    int index = relationSchema_names.getIndexOf( preEditText );
                                    if ( index != -1 )
                                    {
                                        relationSchema_names.removeElementAt( index );
                                        relationSchema_names.insertElementAt( postEditText, index );
                                    }
                                }
                                treeTable.expandAll();
                                for ( int foreignKey_rowNumber : foreignKey_rowNumbers )
                                {
                                    if ( treeTable.getValueAt( foreignKey_rowNumber, 2 ).toString().equals( preEditText ) )
                                    {
                                        treeTable.setValueAt( postEditText, foreignKey_rowNumber, 2 );
                                    }
                                }
                            }

                            @Override
                            public void editingCanceled( ChangeEvent e )
                            {
                            }
                        } );
                    }
                    else
                    {
                        if ( foreignKey_rowNumbers.contains( treeTableRow ) )
                        {
                            newCellEditor.addCellEditorListener( new CellEditorListener()
                            {

                                @Override
                                public void editingStopped( ChangeEvent e )
                                {
                                    String postEditText = newCellEditor.getCellEditorValue().toString();
                                    if ( !postEditText.equals( preEditText ) )
                                    {
                                        treeTable.expandAll();
                                        for ( Integer foreignKey_rowNumber : foreignKey_rowNumbers )
                                        {
                                            if ( treeTable.getValueAt( foreignKey_rowNumber, 1 ).toString().equals( preEditText ) )
                                            {
                                                treeTable.setValueAt( postEditText, foreignKey_rowNumber, 1 );
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void editingCanceled( ChangeEvent e )
                                {
                                }
                            } );
                        }
                    }
                }
                else
                {
                    if ( foreignKey_rowNumbers.contains( treeTableRow ) )
                    {
                        newCellEditor = new DefaultCellEditor( relationSchemaComboBox );
                    }
                    else
                    {
                        newCellEditor = new DefaultCellEditor( datatypesComboBox );
                    }
                }

                newCellEditor.setClickCountToStart( 1 );
                return newCellEditor;
            }
        };
        HighlightPredicate literalHighlightPredicate = ( Component renderer, ComponentAdapter adapter ) -> ( nonKey_rowNumbers.contains( adapter.row ) );
        class LiteralHighlighter extends ColorHighlighter
        {

            private LiteralHighlighter( HighlightPredicate highlightPredicate )
            {
                super( highlightPredicate );
            }

            @Override
            protected Component doHighlight( Component component, ComponentAdapter componentAdapter )
            {
                return component;
            }
        }
        HighlightPredicate classHighlightPredicate = ( Component renderer, ComponentAdapter adapter ) -> ( relationSchema_rowNumbers.contains( adapter.row ) );
        class ClassHighlighter extends ColorHighlighter
        {

            private ClassHighlighter( HighlightPredicate highlightPredicate )
            {
                super( highlightPredicate );
            }

            @Override
            protected Component doHighlight( Component component, ComponentAdapter componentAdapter )
            {
                return component;
            }
        }
        HighlightPredicate foreignKeyHighlightPredicate = ( Component renderer, ComponentAdapter adapter ) -> ( foreignKey_rowNumbers.contains( adapter.row ) );
        class ObjectPropertyHighlighter extends ColorHighlighter
        {

            private ObjectPropertyHighlighter( HighlightPredicate highlightPredicate )
            {
                super( highlightPredicate );
            }

            @Override
            protected Component doHighlight( Component component, ComponentAdapter componentAdapter )
            {
                return component;
            }
        }
        class AlternatingRowHighlighter extends ColorHighlighter
        {

            private AlternatingRowHighlighter( HighlightPredicate highlightPredicate )
            {
                super( highlightPredicate );
            }

            @Override
            protected Component doHighlight( Component component, ComponentAdapter componentAdapter )
            {
                component.setBackground( new Color( 245, 245, 245 ) );
                return component;
            }
        }
        class SelectedRowHighlighter extends ColorHighlighter
        {

            private SelectedRowHighlighter( HighlightPredicate highlightPredicate )
            {
                super( highlightPredicate );
            }

            @Override
            protected Component doHighlight( Component component, ComponentAdapter componentAdapter )
            {
                component.setBackground( new Color( 255, 255, 225 ) );
                return component;
            }
        }
        AlternatingRowHighlighter alternatingRowHighlighter = new AlternatingRowHighlighter( EVEN );
        SelectedRowHighlighter selectedRowHighlighter = new SelectedRowHighlighter( IS_SELECTED );

        class TreeIconRenderer extends DefaultTreeCellRenderer
        {

            //@Override
            @Override
            public Component getTreeCellRendererComponent( JTree jtree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus )
            {
                if ( value instanceof ForeignKeyNode )
                {
                    setLeafIcon( new ImageIcon( "C:\\Users\\John\\Documents\\NetBeans\\Projects\\RDB2RDF\\icons\\green_key_16x16.png" ) );
                }
                else
                {
                    if ( value instanceof RelationNode )
                    {
                        setOpenIcon( new ImageIcon( "C:\\Users\\John\\Documents\\NetBeans\\Projects\\RDB2RDF\\icons\\table6.png" ) );
                        setClosedIcon( new ImageIcon( "C:\\Users\\John\\Documents\\NetBeans\\Projects\\RDB2RDF\\icons\\table6.png" ) );
                    }
                    else
                    {
                        if ( row == 0 )
                        {
                            setOpenIcon( new ImageIcon( "C:\\Users\\John\\Documents\\NetBeans\\Projects\\RDB2RDF\\icons\\db.png" ) );
                            setClosedIcon( new ImageIcon( "C:\\Users\\John\\Documents\\NetBeans\\Projects\\RDB2RDF\\icons\\db.png" ) );
                        }
                        else
                        {
                            if ( value instanceof CombinedKeyNode )
                            {
                                setLeafIcon( new ImageIcon( "C:\\Users\\John\\Documents\\NetBeans\\Projects\\RDB2RDF\\icons\\blue_key_16x16.png" ) );
                            }
                            else
                            {
                                if ( !( value instanceof NonKeyNode ) )
                                {
                                    setLeafIcon( new ImageIcon( "C:\\Users\\John\\Documents\\NetBeans\\Projects\\RDB2RDF\\icons\\orange_key_16x16.png" ) );
                                }
                                else
                                {
                                    setLeafIcon( new ImageIcon( "C:\\Users\\John\\Documents\\NetBeans\\Projects\\RDB2RDF\\icons\\square_16x16.png" ) );
                                }
                            }
                        }
                    }
                }
                return super.getTreeCellRendererComponent( jtree, ( ( AbstractNode ) value ).getValueAt( 0 ).toString(), sel, expanded, leaf, row, hasFocus );
            }
        }
        this.treeTable.setHighlighters( alternatingRowHighlighter, selectedRowHighlighter );
        this.treeTable.setShowGrid(
                true, true );

        this.treeTable.setColumnControlVisible(
                true );

        this.treeTable.setRootVisible(
                true );

        this.treeTable.expandAll();
        this.treeTable.setRowHeight( 35 );
        treeTable.setTreeCellRenderer( new TreeIconRenderer() );
        return this.treeTable;
    }
    private final ArrayList<Integer> relationSchema_rowNumbers = new ArrayList<>();
    private final ArrayList<Integer> nonKey_rowNumbers = new ArrayList<>();
    private final ArrayList<Integer> foreignKey_rowNumbers = new ArrayList<>();
}
