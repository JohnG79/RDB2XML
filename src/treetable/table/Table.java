package treetable.table;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.*;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import treetable.tree.node.*;

/**
 *
 * This is the Table part of the Tree-Table
 */
public class Table
{

    private final String[] columnHeadings;

    private SchemaNode schemaNode;
    private RelationNode currentRelationNode;

    private DefaultTreeTableModel tableModel;
    private JXTreeTable treeTable;
    private int treeTableRowCount = 0;

    private Table()
    {
        this.columnHeadings = null;
    }

    public Table( String[] columnHeadings )
    {
        this.columnHeadings = columnHeadings;
        this.schemaNode = new SchemaNode( new String[]
        {
            "SCHEMA_NAME_NOT_SET"
        } );
        // table row 0 = data schema node (root node)
        treeTableRowCount = 1;
    }

    public Table( String[] columnHeadings, String dataSchemaName )
    {
        this.columnHeadings = columnHeadings;
        this.schemaNode = new SchemaNode( new String[]
        {
            dataSchemaName
        } );
        // table row 0 = data schema node (root node)
        treeTableRowCount = 1;
    }

    public void setSchema( String schemaName )
    {
        this.schemaNode.setUserObject( new String[]
        {
            schemaName
        } );
    }

    public Node getDataSchemaNode()
    {
        return schemaNode;
    }

    public RelationNode addRelation( String relationName )
    {
        RelationNode newRelationSchemaNode = new RelationNode( new String[]
        {
            relationName, relationName.substring( 0, 1 ).toUpperCase() + relationName.substring( 1 )
        } );
        this.schemaNode.add( newRelationSchemaNode );
        currentRelationNode = newRelationSchemaNode;
        relationSchema_rowNumbers.add( treeTableRowCount );
        treeTableRowCount++;
        return newRelationSchemaNode;
    }

    public Enumeration<? extends MutableTreeTableNode> getRelationNodes()
    {
        return this.schemaNode.children();
    }

    public void addPrimaryKey( String relationName, String primaryKeyName )
    {
        PrimaryKeyNode newPrimaryKeyNode = new PrimaryKeyNode( new String[]
        {
            primaryKeyName, primaryKeyName, ""
        } );

        Enumeration< RelationNode> relationNodes = ( Enumeration<RelationNode> ) getRelationNodes();
        while ( relationNodes.hasMoreElements() )
        {
            RelationNode relationNode = relationNodes.nextElement();
            if ( relationNode.getValueAt( 0 ).toString().equals( relationName ) )
            {
                relationNode.add( newPrimaryKeyNode );
                treeTableRowCount++;
            }
        }
    }

    public void addNonKey( String relationName, String nonKeyName )
    {
        NonKeyNode newNonKeyNode = new NonKeyNode( new String[]
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
                nonKey_rowNumbers.add( treeTableRowCount );
                treeTableRowCount++;
            }
        }
    }

    public CombinedKeyNode addCombinedKey( String combinedKeyName, String referencedTableName, String referencedColumnName )
    {
        CombinedKeyNode newCombinedKeyNode = new CombinedKeyNode( new String[]
        {
            combinedKeyName, combinedKeyName, ""
        }, referencedTableName, referencedColumnName );
        currentRelationNode.add( newCombinedKeyNode );
        // record table row number of each foreign key
        foreignKey_rowNumbers.add( treeTableRowCount );
        treeTableRowCount++;
        return newCombinedKeyNode;
    }

    public void addForeignKey( String relationName, String foreignKeyName, String referencedSchemaName, String referencedKeyName )
    {
        ForeignKeyNode newForiegnKeyNode = new ForeignKeyNode( new Object[]
        {
            foreignKeyName, foreignKeyName, ""
        }, referencedSchemaName, referencedKeyName );

        Enumeration< RelationNode> relationNodes = ( Enumeration<RelationNode> ) getRelationNodes();
        while ( relationNodes.hasMoreElements() )
        {
            RelationNode relationNode = relationNodes.nextElement();
            if ( relationNode.getValueAt( 0 ).toString().equals( relationName ) )
            {
                relationNode.add( newForiegnKeyNode );
                foreignKey_rowNumbers.add( treeTableRowCount );
                treeTableRowCount++;
            }
        }
    }

    public JXTreeTable getTreeTable( JTextField termsColumn_textField, JComboBox relationSchemaComboBox, JComboBox datatypesComboBox )
    {

        this.tableModel = new DefaultTreeTableModel( schemaNode, Arrays.asList( columnHeadings ) );

        this.treeTable = new JXTreeTable( tableModel )
        {

            @Override
            public TableCellEditor getCellEditor( int tree_table_row, int tree_table_column )
            {
                DefaultCellEditor newCellEditor;
                // if column = 0 OR column = 1...
                if ( tree_table_column == 1 )
                {
                    String preEditText = getValueAt( tree_table_row, tree_table_column ).toString();
                    newCellEditor = new DefaultCellEditor( termsColumn_textField );

                    // relation and column 1
                    if ( relationSchema_rowNumbers.contains( tree_table_row ) )
                    {
                        newCellEditor.addCellEditorListener( new CellEditorListener()
                        {
                            @Override
                            public void editingStopped( ChangeEvent e )
                            {
                                String postEditText = newCellEditor.getCellEditorValue().toString();

                                // if user changed text
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
                                    // if value at 
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

                    // foreign key row and column 1
                    else
                    {
                        if ( foreignKey_rowNumbers.contains( tree_table_row ) )
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

                // foreign key row and column 2
                else
                {
                    if ( foreignKey_rowNumbers.contains( tree_table_row ) )
                    {
                        newCellEditor = new DefaultCellEditor( relationSchemaComboBox );
                    }

                    // not foreignKey and column 2
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
        AlternatingRowHighlighter alternatingRowHighlighter = new AlternatingRowHighlighter( HighlightPredicate.EVEN );
        SelectedRowHighlighter selectedRowHighlighter = new SelectedRowHighlighter( HighlightPredicate.IS_SELECTED );

        class TreeIconRenderer extends DefaultTreeCellRenderer
        {

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
                return super.getTreeCellRendererComponent( jtree, ( ( Node ) value ).getValueAt( 0 ).toString(), sel, expanded, leaf, row, hasFocus );
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
        this.treeTable.setRowHeight( 25 );
        treeTable.setTreeCellRenderer( new TreeIconRenderer() );
        return this.treeTable;
    }
    private final ArrayList<Integer> relationSchema_rowNumbers = new ArrayList<>();
    private final ArrayList<Integer> nonKey_rowNumbers = new ArrayList<>();
    private final ArrayList<Integer> foreignKey_rowNumbers = new ArrayList<>();
}
