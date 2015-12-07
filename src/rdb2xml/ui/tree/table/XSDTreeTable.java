package rdb2xml.ui.tree.table;

import java.awt.Color;
import static java.awt.Color.LIGHT_GRAY;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreePath;
import org.jdesktop.swingx.JXTreeTable;
import static org.jdesktop.swingx.decorator.HighlightPredicate.EVEN;
import static org.jdesktop.swingx.decorator.HighlightPredicate.IS_SELECTED;
import static org.jdesktop.swingx.decorator.HighlightPredicate.ODD;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import rdb2xml.ui.tree.node.Attribute;
import rdb2xml.ui.tree.node.Foreign;
import rdb2xml.ui.tree.node.PrimaryKeyNode;
import rdb2xml.ui.tree.node.SchemaObject;

public class XSDTreeTable extends TreeTable
{

    public XSDTreeTable( String[] treeTableHeadings )
    {
        super( treeTableHeadings, 2 );
    }

    public XSDTreeTable( String[] treeTableHeadings, String rootNodeName )
    {
        super( treeTableHeadings, rootNodeName, 2 );
    }

    private XSDTreeTable()
    {
        super( null, 2 );
    }

    @Override
    public JXTreeTable getTreeTable( JTextField textField, JComboBox relationsComboBox, JComboBox datatypesComboBox )
    {

        super.setFont( relationsComboBox );
        super.setFont( datatypesComboBox );
        super.setFont( textField );
        super.setFocusHighlight( relationsComboBox );
        super.setFocusHighlight( datatypesComboBox );
        super.setFocusHighlight( textField );

        this.tableModel = new DefaultTreeTableModel( schemaNode, asList( columnHeadings ) );

        this.treeTable = new JXTreeTable( tableModel )
        {

            private DefaultCellEditor previousCellEditor;
            private CellEditorListener cellEditorListener;

            @Override
            public boolean isCellEditable( int row, int column )
            {
                return ( column == 1 );
            }

            @Override
            public Object getValueAt( int row, int column )
            {
                Object node;
                if ( ( node = treeTable.getPathForRow( row ).getLastPathComponent() ) instanceof Attribute )
                {
                    return ( ( Attribute ) node ).getDatatype().toString();
                }
                else
                {
                    return "";
                }
            }

            @Override
            public TableCellEditor getCellEditor( int row, int column )
            {
                DefaultCellEditor newCellEditor = null;

                if ( previousCellEditor != null )
                {
                    previousCellEditor.removeCellEditorListener( cellEditorListener );
                }

                if ( column == 1 )
                {
                    String textBefore = getValueAt( row, column ).toString();
                    TreePath treePath = treeTable.getPathForRow( row );
                    Object node;
                    if ( ( node = treePath.getLastPathComponent() ) instanceof Attribute )
                    {
                        previousCellEditor = newCellEditor = new DefaultCellEditor( datatypesComboBox );

                        newCellEditor.addCellEditorListener( cellEditorListener = new CellEditorListener()
                        {
                            @Override
                            public void editingStopped( ChangeEvent e )
                            {
                                String textAfter = treeTable.getCellEditor( row, column ).getCellEditorValue().toString();
                                if ( !textBefore.equals( textAfter ) )
                                {
                                    ( ( Attribute ) node ).setDatatype( textAfter );
                                    if ( node instanceof PrimaryKeyNode )
                                    {
                                        ArrayList<Foreign> referencingKeys = ( ( PrimaryKeyNode ) node ).getReferencingKeys();
                                        for ( Foreign foreign : referencingKeys )
                                        {
                                            foreign.setDatatype( textAfter );
                                            setValueAt( textAfter, foreign.getOrderNumber(), 1 );
                                        }
                                    }
                                }
                            }

                            @Override
                            public void editingCanceled( ChangeEvent e )
                            {
                            }
                        } );
                        newCellEditor.setClickCountToStart( 2 );
                    }
                }
                return newCellEditor;
            }
        };

        AlternatingRowHighlighter alternatingRowHighlighter1 = new AlternatingRowHighlighter( ODD, new Color( 245, 245, 245 ) );
        SelectedRowHighlighter selectedRowHighlighter = new SelectedRowHighlighter( IS_SELECTED );

        this.treeTable.setHighlighters( alternatingRowHighlighter1, selectedRowHighlighter );
        this.treeTable.setShowGrid( true, true );
        this.treeTable.setColumnControlVisible( true );
        this.treeTable.setRootVisible( true );
        this.treeTable.expandAll();
        this.treeTable.setRowHeight( 35 );
        this.treeTable.setTreeCellRenderer( new TreeIconRenderer() );
        this.treeTable.getTableHeader().setBackground( LIGHT_GRAY );
        return this.treeTable;
    }
}
