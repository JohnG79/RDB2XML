package rdb2xml.ui.tree.table;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import rdb2xml.ui.tree.node.AbstractNode;
import rdb2xml.ui.tree.node.CombinedKeyNode;
import rdb2xml.ui.tree.node.ForeignKeyNode;
import rdb2xml.ui.tree.node.NonKeyNode;
import rdb2xml.ui.tree.node.PrimaryKeyNode;
import rdb2xml.ui.tree.node.RelationNode;

class TreeIconRenderer extends DefaultTreeCellRenderer
{

    @Override
    public Component getTreeCellRendererComponent( JTree jtree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus )
    {
        if ( value instanceof ForeignKeyNode )
        {
            setLeafIcon( new ImageIcon( ".\\Resources\\foreign_24x24.png" ) );
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
            setLeafIcon( new ImageIcon( ".\\Resources\\non_24x24.png" ) );
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
