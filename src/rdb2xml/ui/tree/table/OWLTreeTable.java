package rdb2xml.ui.tree.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import static java.awt.Font.PLAIN;
import static java.util.Arrays.asList;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import static org.jdesktop.swingx.decorator.HighlightPredicate.EVEN;
import static org.jdesktop.swingx.decorator.HighlightPredicate.IS_SELECTED;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import rdb2xml.ui.tree.node.AbstractLeafNode;
import rdb2xml.ui.tree.node.AbstractNode;
import rdb2xml.ui.tree.node.CombinedKeyNode;
import rdb2xml.ui.tree.node.ForeignKeyNode;
import rdb2xml.ui.tree.node.NonKeyNode;
import rdb2xml.ui.tree.node.PrimaryKeyNode;
import rdb2xml.ui.tree.node.RelationNode;

public class OWLTreeTable extends TreeTable
{

    private OWLTreeTable()
    {
        super( null, 3 );
    }

    public OWLTreeTable( String[] treeTableHeadings )
    {
        super( treeTableHeadings, 3 );
    }

    public OWLTreeTable( String[] treeTableHeadings, String rootNodeName )
    {
        super( treeTableHeadings, rootNodeName, 3 );
    }

    @Override
    public JXTreeTable getTreeTable( JTextField textField, JComboBox relationsComboBox, JComboBox datatypesComboBox )
    {
        relationsComboBox.setFont( new Font( "Courier new", PLAIN, 16 ) );
        datatypesComboBox.setFont( new Font( "Courier new", PLAIN, 16 ) );
        textField.setFont( new Font( "Courier new", PLAIN, 16 ) );

        for ( RelationNode relationNode : schemaNode.getRelations() )
        {
            relationNode.setEditable( 1, true );
        }
        setFocusHighlight( relationsComboBox );
        setFocusHighlight( datatypesComboBox );
        setFocusHighlight( textField );

        this.tableModel = new DefaultTreeTableModel( schemaNode, asList( columnHeadings ) );
        this.treeTable = new JXTreeTable( tableModel )
        {
            private DefaultCellEditor previousCellEditor;
            private CellEditorListener cellEditorListener;

            @Override
            public TableCellEditor getCellEditor( int row, int column )
            {
                DefaultCellEditor newCellEditor;

                treeTable.expandAll();

                if ( previousCellEditor != null )
                {
                    previousCellEditor.removeCellEditorListener( cellEditorListener );
                }

                previousCellEditor = newCellEditor = new DefaultCellEditor( textField );

                TreeSelectionModel tsm = treeTable.getTreeSelectionModel();
                Object selectedNode;

                if ( column == 1 )
                {
                    String textBefore = getValueAt( row, column ).toString();
                    if ( tsm.getSelectionPath() != null )
                    {
                        if ( ( ( selectedNode = tsm.getSelectionPath().getLastPathComponent() ) instanceof RelationNode ) )
                        {
                            newCellEditor.addCellEditorListener( cellEditorListener = new CellEditorListener()
                            {
                                @Override
                                public void editingStopped( ChangeEvent e )
                                {
                                    String textAfter;
                                    if ( !( textAfter = textField.getText() ).equals( textBefore ) )
                                    {
                                        DefaultComboBoxModel relationNames = ( DefaultComboBoxModel ) relationsComboBox.getModel();
                                        int index = relationNames.getIndexOf( textBefore );
                                        if ( index != -1 )
                                        {
                                            relationNames.removeElementAt( index );
                                            relationNames.insertElementAt( textAfter, index );
                                        }
                                        ( ( RelationNode ) selectedNode ).setValueAt( textAfter, 1 );
                                        for ( int i = 1; i < treeTable.getRowCount(); i++ )
                                        {
                                            TreePath treePath = treeTable.getPathForRow( i );
                                            Object node = treePath.getLastPathComponent();
                                            if ( node instanceof ForeignKeyNode )
                                            {
                                                if ( treeTable.getValueAt( i, 2 ).toString().equals( textBefore ) )
                                                {
                                                    treeTable.setValueAt( textAfter, i, 2 );
                                                }
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
                        else if ( selectedNode instanceof ForeignKeyNode || selectedNode instanceof CombinedKeyNode )
                        {
                            newCellEditor.addCellEditorListener( cellEditorListener = new CellEditorListener()
                            {
                                @Override
                                public void editingStopped( ChangeEvent e )
                                {
                                    String textAfter;
                                    if ( !( textAfter = textField.getText() ).equals( textBefore ) )
                                    {
                                        ( ( AbstractLeafNode ) selectedNode ).setValueAt( textAfter, 1 );
                                    }
                                    for ( int i = 1; i < treeTable.getRowCount(); i++ )
                                    {
                                        TreePath treePath = treeTable.getPathForRow( i );
                                        Object node = treePath.getLastPathComponent();
                                        if ( ( node instanceof ForeignKeyNode || node instanceof CombinedKeyNode )
                                                && treeTable.getValueAt( i, 1 ).toString().equals( textBefore )
                                                && treeTable.getValueAt( i, 2 ).toString().equals( treeTable.getValueAt( row, 2 ).toString() ) )
                                        {
                                            treeTable.setValueAt( textAfter, i, 1 );
                                        }
                                    }
                                }

                                @Override
                                public void editingCanceled( ChangeEvent e )
                                {
                                }
                            } );
                        }
                        else if ( selectedNode instanceof PrimaryKeyNode )
                        {
                            newCellEditor.addCellEditorListener( cellEditorListener = new CellEditorListener()
                            {
                                @Override
                                public void editingStopped( ChangeEvent e )
                                {
                                    String textAfter;
                                    if ( !( textAfter = textField.getText() ).equals( textBefore ) )
                                    {
                                        ( ( PrimaryKeyNode ) selectedNode ).setValueAt( textAfter, 1 );
                                    }

                                }

                                @Override
                                public void editingCanceled( ChangeEvent e )
                                {
                                }

                            } );
                        }
                        else if ( selectedNode instanceof NonKeyNode )
                        {
                            newCellEditor.addCellEditorListener( cellEditorListener = new CellEditorListener()
                            {
                                @Override
                                public void editingStopped( ChangeEvent e )
                                {
                                    String textAfter;
                                    if ( !( textAfter = textField.getText() ).equals( textBefore ) )
                                    {
                                        ( ( NonKeyNode ) selectedNode ).setValueAt( textAfter, 1 );
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
                else if ( column == 2 )
                {
                    String textBefore = getValueAt( row, column ).toString();
                    if ( tsm.getSelectionPath() != null )
                    {
                        if ( ( ( selectedNode = tsm.getSelectionPath().getLastPathComponent() ) instanceof ForeignKeyNode )
                                || ( selectedNode instanceof CombinedKeyNode ) )
                        {
                            newCellEditor = new DefaultCellEditor( relationsComboBox );
                            newCellEditor.addCellEditorListener( cellEditorListener = new CellEditorListener()
                            {
                                @Override
                                public void editingStopped( ChangeEvent e )
                                {
                                    for ( int i = 1; i < treeTable.getRowCount(); i++ )
                                    {
                                        TreePath treePath = treeTable.getPathForRow( i );
                                        Object node = treePath.getLastPathComponent();
                                        if ( ( node instanceof ForeignKeyNode || node instanceof CombinedKeyNode )
                                                && treeTable.getValueAt( i, 2 ).toString().equals( textBefore )
                                                && treeTable.getValueAt( i, 1 ).toString().equals( treeTable.getValueAt( row, 1 ).toString() ) )
                                        {
                                            treeTable.setValueAt( getValueAt( row, 2 ).toString(), i, 2 );
                                        }
                                    }
                                }

                                @Override
                                public void editingCanceled( ChangeEvent e )
                                {
                                }

                            } );
                        }
                        else if ( selectedNode instanceof NonKeyNode )
                        {
                            newCellEditor = new DefaultCellEditor( datatypesComboBox );
                        }
                    }
                }
                newCellEditor.setClickCountToStart( 2 );
                return newCellEditor;
            }

        };

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

            @Override
            public Component getTreeCellRendererComponent( JTree jtree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus )
            {
                if ( value instanceof ForeignKeyNode )
                {
                    setLeafIcon( new ImageIcon( ".\\Resources\\foreign_16x16.png" ) );
                }
                else if ( value instanceof RelationNode )
                {
                    setOpenIcon( new ImageIcon( ".\\Resources\\table_24x24.png" ) );
                    setClosedIcon( new ImageIcon( ".\\Resources\\table_24x24.png" ) );
                }
                else if ( row == 0 )
                {
                    setOpenIcon( new ImageIcon( ".\\Resources\\database_24x24.png" ) );
                    setClosedIcon( new ImageIcon( ".\\Resources\\database_24x24.png" ) );
                }
                else if ( value instanceof CombinedKeyNode )
                {
                    setLeafIcon( new ImageIcon( ".\\Resources\\identifying_24x24.png" ) );
                }
                else if ( value instanceof NonKeyNode )
                {
                    setLeafIcon( new ImageIcon( ".\\Resources\\nonkey_16x16.png" ) );
                }
                else if ( value instanceof PrimaryKeyNode )
                {
                    setLeafIcon( new ImageIcon( ".\\Resources\\primary_24x24.png" ) );
                }
                repaint();
                revalidate();
                return super.getTreeCellRendererComponent( jtree, ( ( AbstractNode ) value ).getValueAt( 0 ).toString(), sel, expanded, leaf, row, hasFocus );
            }
        }
        this.treeTable.setFont( new Font( "Courier new", PLAIN, 16 ) );
        this.treeTable.setHighlighters( alternatingRowHighlighter, selectedRowHighlighter );
        this.treeTable.setShowGrid( true, true );
        this.treeTable.setColumnControlVisible( true );
        this.treeTable.setRootVisible( true );
        this.treeTable.expandAll();
        this.treeTable.setRowHeight( 35 );
        treeTable.setTreeCellRenderer( new TreeIconRenderer() );
        return this.treeTable;
    }
}
