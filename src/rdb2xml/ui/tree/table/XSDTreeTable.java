package rdb2xml.ui.tree.table;

import java.awt.Color;
import static java.awt.Color.LIGHT_GRAY;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.FontHighlighter;
import static org.jdesktop.swingx.decorator.HighlightPredicate.IS_SELECTED;
import static org.jdesktop.swingx.decorator.HighlightPredicate.ODD;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import rdb2xml.ui.tree.node.Attribute;
import rdb2xml.ui.tree.node.Foreign;
import rdb2xml.ui.tree.node.PrimaryKeyNode;
import rdb2xml.ui.tree.node.RelationNode;

public class XSDTreeTable extends TreeTable
{

    private final Font font;
    private final Highlighter hl;

    public XSDTreeTable( String[] treeTableHeadings )
    {
        super( treeTableHeadings, 2 );
        font = new Font( "Courier New", Font.BOLD, 16 );
        hl = new FontHighlighter( font );
    }

    public XSDTreeTable( String[] treeTableHeadings, String rootNodeName )
    {
        super( treeTableHeadings, rootNodeName, 2 );
        font = new Font( "Courier New", Font.BOLD, 16 );
        hl = new FontHighlighter( font );
    }

    private XSDTreeTable()
    {
        super( null, 2 );
        font = new Font( "Courier New", Font.BOLD, 16 );
        hl = new FontHighlighter( font );
    }

    @Override
    public JXTreeTable getTreeTable( JTextField textField, JComboBox relationsComboBox, JComboBox datatypesComboBox )
    {
        TreeTable.setFont( relationsComboBox );
        TreeTable.setFont( datatypesComboBox );
        TreeTable.setFont( textField );

        TreeTable.setFocusHighlight( relationsComboBox );
        TreeTable.setFocusHighlight( datatypesComboBox );
        TreeTable.setFocusHighlight( textField );

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
        resetTreeTable();
        this.treeTable.setRowHeight( 35 );
        this.treeTable.setTreeCellRenderer( new TreeIconRenderer() );
        this.treeTable.getTableHeader().setBackground( LIGHT_GRAY );

        this.treeTable.addMouseListener( new MouseListener()
        {
            private Object node;
            private int row;
            private int col;

            private JPopupMenu rightClickMenu;

            @Override
            public void mouseClicked( MouseEvent evnt )
            {
                if ( ( row = treeTable.rowAtPoint( evnt.getPoint() ) ) != 0 && evnt.getButton() == 3 )
                {
                    col = treeTable.columnAtPoint( evnt.getPoint() );
                    rightClickMenu = new JPopupMenu();

                    JMenuItem menuItem1;
                    rightClickMenu.add( menuItem1 = new JMenuItem( "Exclude" ) );
                    menuItem1.setHorizontalTextPosition( JMenuItem.RIGHT );
                    menuItem1.addMouseListener( new MouseListener()
                    {
                        @Override
                        public void mouseClicked( MouseEvent e )
                        {
                            if ( ( ( node = treeTable.getPathForRow( row ).getLastPathComponent() ) instanceof Attribute ) )
                            {
                                ( ( Attribute ) node ).getParent().remove( ( MutableTreeTableNode ) node );
                                resetTreeTable();
                            }
                            else if ( node instanceof RelationNode )
                            {
                                ( ( MutableTreeTableNode ) ( ( TreeNode ) node ).getParent() ).remove( ( MutableTreeTableNode ) node );
                                resetTreeTable();
                            }
                            rightClickMenu.setVisible( false );
                        }

                        @Override
                        public void mousePressed( MouseEvent e )
                        {

                        }

                        @Override
                        public void mouseReleased( MouseEvent e )
                        {

                        }

                        @Override
                        public void mouseEntered( MouseEvent e )
                        {
                            menuItem1.setBackground( Color.LIGHT_GRAY );
                        }

                        @Override
                        public void mouseExited( MouseEvent e )
                        {
                            menuItem1.setBackground( null );
                        }

                    } );

                    JMenuItem menuItem2;
                    rightClickMenu.add( menuItem2 = new JMenuItem( "Insert" ) );
                    menuItem2.setHorizontalTextPosition( JMenuItem.RIGHT );
                    menuItem2.addMouseListener( new MouseListener()
                    {
                        @Override
                        public void mouseClicked( MouseEvent e )
                        {
                            rightClickMenu.setVisible( false );
                        }

                        @Override
                        public void mousePressed( MouseEvent e )
                        {

                        }

                        @Override
                        public void mouseReleased( MouseEvent e )
                        {

                        }

                        @Override
                        public void mouseEntered( MouseEvent e )
                        {
                            menuItem2.setBackground( Color.LIGHT_GRAY );
                        }

                        @Override
                        public void mouseExited( MouseEvent e )
                        {
                            menuItem2.setBackground( null );
                        }

                    } );
                    //menuItem.addActionListener( menuListener );

                    rightClickMenu.setBorder( new BevelBorder( BevelBorder.RAISED ) );

                    rightClickMenu.show( null, evnt.getXOnScreen() - 20, evnt.getYOnScreen() - 20 );

                    rightClickMenu.addMouseListener( new MouseListener()
                    {
                        @Override
                        public void mouseClicked( MouseEvent e )
                        {

                        }

                        @Override
                        public void mousePressed( MouseEvent e )
                        {

                        }

                        @Override
                        public void mouseReleased( MouseEvent e )
                        {

                        }

                        @Override
                        public void mouseEntered( MouseEvent e )
                        {

                        }

                        @Override
                        public void mouseExited( MouseEvent e )
                        {
                            rightClickMenu.setVisible( false );
                        }
                    } );

                }
            }

            @Override
            public void mousePressed( MouseEvent e )
            {

            }

            @Override
            public void mouseReleased( MouseEvent e )
            {

            }

            @Override
            public void mouseEntered( MouseEvent e )
            {

            }

            @Override
            public void mouseExited( MouseEvent e )
            {

            }
        } );

        return this.treeTable;
    }

    private void resetTreeTable()
    {
        this.treeTable.setTreeTableModel( new DefaultTreeTableModel( schemaNode, asList( columnHeadings ) ) );
        this.treeTable.expandAll();
        this.treeTable.getColumnExt( 0 ).setHighlighters( hl );
        this.treeTable.getColumnExt( 1 ).setHighlighters( hl );
    }
}
