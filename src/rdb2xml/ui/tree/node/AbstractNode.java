package rdb2xml.ui.tree.node;

import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

public abstract class AbstractNode extends AbstractMutableTreeTableNode
{

    private AbstractNode()
    {
    }

    private AbstractNode( Object[] objects )
    {
    }

    protected int treeItemNumber = -1;

    public AbstractNode( int treeItemNumber, Object[] objects )
    {
        super( objects );
        this.treeItemNumber = treeItemNumber;
    }

    @Override
    public Object getValueAt( int columnIndex )
    {
        return getUserObject()[ columnIndex ];
    }

    @Override
    public void setValueAt( Object value, int columnIndex )
    {
        getUserObject()[ columnIndex ] = value;
    }

    @Override
    public int getColumnCount()
    {
        return getData().length;
    }

    @Override
    public Object[] getUserObject()
    {
        return ( Object[] ) super.getUserObject();
    }

    @Override
    public boolean isEditable( int column )
    {
        return column != 0;
    }

    public Object[] getData()
    {
        return ( Object[] ) getUserObject();
    }
}
