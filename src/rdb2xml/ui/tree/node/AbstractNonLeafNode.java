package rdb2xml.ui.tree.node;

public abstract class AbstractNonLeafNode extends AbstractNode
{

    public AbstractNonLeafNode( int treeItemNumber, Object[] objects )
    {
        super( treeItemNumber, objects );
    }

    public int getTreeItemNumber()
    {
        return treeItemNumber;
    }

    @Override
    public boolean getAllowsChildren()
    {
        return true;
    }

    @Override
    public void setAllowsChildren( boolean allowsChildren )
    {

    }

    @Override
    public boolean isLeaf()
    {
        return false;
    }
}
