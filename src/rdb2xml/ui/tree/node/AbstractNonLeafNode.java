package rdb2xml.ui.tree.node;

import java.util.ArrayList;
import java.util.List;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

public abstract class AbstractNonLeafNode extends AbstractNode
{

    public AbstractNonLeafNode( Object[] objects )
    {
        super( objects );
    }

    @Override
    public TreeTableNode getParent()
    {
        return parent;
    }

    @Override
    public Object[] getUserObject()
    {
        return super.getUserObject();
    }

    @Override
    public void setUserObject( Object object )
    {
        super.setUserObject( object );
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
        return getChildCount() == 0;
    }

    @Override
    protected List<MutableTreeTableNode> createChildrenList()
    {
        return new ArrayList<>();
    }

    @Override
    public void add( MutableTreeTableNode child )
    {
        insert( child, getChildCount() );
    }
}
