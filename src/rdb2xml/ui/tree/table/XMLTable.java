package rdb2xml.ui.tree.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import static java.util.Arrays.asList;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
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

public class XMLTable extends Table
{

    public XMLTable()
    {
        super( new String[]
        {
            "Schema", "Datatype"
        } );
    }

    @Override
    public JXTreeTable getTreeTable( JTextField textField, JComboBox relationsComboBox, JComboBox datatypesComboBox )
    {
        relationsComboBox.setFont( new Font( "Courier new", Font.PLAIN, 16 ) );
        datatypesComboBox.setFont( new Font( "Courier new", Font.PLAIN, 16 ) );

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
                JTextField textField = new JTextField();
                textField.setEditable( false );
                previousCellEditor = newCellEditor = new DefaultCellEditor( textField );

                TreePath treePath = treeTable.getPathForRow( row );
                Object node = treePath.getLastPathComponent();
                Object selectedNode;

                if ( column == 1 )
                {
                    String textBefore = getValueAt( row, column ).toString();
                    if ( treePath != null )
                    {
                        if ( ( selectedNode = treePath.getLastPathComponent() ) instanceof CombinedKeyNode )
                        {
                            newCellEditor = new DefaultCellEditor( datatypesComboBox );
                            newCellEditor.addCellEditorListener( cellEditorListener = new CellEditorListener()
                            {
                                @Override
                                public void editingStopped( ChangeEvent e )
                                {
                                    String textAfter = ( String ) getValueAt( row, column );
                                    if ( !textBefore.equals( textAfter ) )
                                    {
                                        ( ( CombinedKeyNode ) selectedNode ).setValueAt( textAfter, 3 );
                                    }
                                }

                                @Override
                                public void editingCanceled( ChangeEvent e )
                                {
                                }
                            } );
                        }
                        else if ( selectedNode instanceof ForeignKeyNode )
                        {
                            newCellEditor = new DefaultCellEditor( datatypesComboBox );
                            newCellEditor.addCellEditorListener( cellEditorListener = new CellEditorListener()
                            {
                                @Override
                                public void editingStopped( ChangeEvent e )
                                {
                                    String textAfter = ( String ) getValueAt( row, column );
                                    if ( !textBefore.equals( textAfter ) )
                                    {
                                        ( ( ForeignKeyNode ) selectedNode ).setValueAt( textAfter, 3 );
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
                            newCellEditor = new DefaultCellEditor( datatypesComboBox );
                            newCellEditor.addCellEditorListener( cellEditorListener = new CellEditorListener()
                            {

                                @Override
                                public void editingStopped( ChangeEvent e )
                                {
                                    String textAfter = ( String ) getValueAt( row, column );
                                    if ( !textBefore.equals( textAfter ) )
                                    {
                                        ( ( PrimaryKeyNode ) selectedNode ).setValueAt( textAfter, 3 );
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
                            newCellEditor.addCellEditorListener( cellEditorListener = new CellEditorListener()
                            {
                                @Override
                                public void editingStopped( ChangeEvent e )
                                {
                                    String textAfter = ( String ) getValueAt( row, column );
                                    if ( !textBefore.equals( textAfter ) )
                                    {
                                        ( ( NonKeyNode ) selectedNode ).setValueAt( textAfter, 3 );

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

                newCellEditor.setClickCountToStart( 2 );
                return newCellEditor;
            }

        };

        for ( int i = 0; i < treeTable.getRowCount(); i++ )
        {
            TreePath treePath = treeTable.getPathForRow( i );
            Object node = treePath.getLastPathComponent();
            if ( node instanceof RelationNode )
            {
                ( ( RelationNode ) node ).setEditable( 1 );
                treeTable.setValueAt( "", i, 1 );
                ( ( RelationNode ) node ).setUneditable( 1 );
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

        this.treeTable.setHighlighters( alternatingRowHighlighter, selectedRowHighlighter );
        this.treeTable.setShowGrid( true, true );
        this.treeTable.setColumnControlVisible( true );
        this.treeTable.setRootVisible( true );
        this.treeTable.expandAll();
        this.treeTable.setRowHeight( 35 );
        this.treeTable.setTreeCellRenderer( new TreeIconRenderer() );

        int rowCount = treeTable.getRowCount();
        for ( int i = 1; i < rowCount; i++ )
        {
            TreePath treePath = treeTable.getPathForRow( i );
            Object node = treePath.getLastPathComponent();
            if ( node instanceof PrimaryKeyNode || node instanceof ForeignKeyNode || node instanceof CombinedKeyNode || node instanceof NonKeyNode )
            {

                treeTable.setValueAt( ( ( AbstractLeafNode ) node ).getValueAt( 3 ), i, 1 );

            }
        }

        for ( RelationNode relationNode : schemaNode.getRelations() )
        {
            relationNode.setEditable( 1 );
        }

        return this.treeTable;
    }
}
