package rdb2xml.ui.tree.node;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.TreeNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

public abstract class AbstractLeafNode extends AbstractNode
{

    public AbstractLeafNode( int treeItemNumber, Object[] objects )
    {
        super( treeItemNumber, objects );
    }

    @Override
    public void add( MutableTreeTableNode mttn )
    {
    }

    @Override
    public Enumeration<? extends MutableTreeTableNode> children()
    {
        throw new UnsupportedOperationException( "Leaf nodes do not have children." );
    }

    @Override
    protected List<MutableTreeTableNode> createChildrenList()
    {
        return new ArrayList<>();
    }

    @Override
    public TreeTableNode getChildAt( int index )
    {
        throw new UnsupportedOperationException( "Leaf nodes do not have children." );
    }

    @Override
    public int getChildCount()
    {
        return 0;
    }

    @Override
    public int getIndex( TreeNode node )
    {
        throw new UnsupportedOperationException( "Leaf nodes do not have children." );
    }

    @Override
    public void insert( MutableTreeTableNode child, int index )
    {
    }

    @Override
    public boolean isLeaf()
    {
        return true;
    }

    @Override
    public void remove( int index )
    {
    }

    @Override
    public void remove( MutableTreeTableNode node )
    {
    }

    @Override
    public void setAllowsChildren( boolean allowsChildren )
    {
    }

    @Override
    public boolean getAllowsChildren()
    {
        return false;
    }

    public int getTreeItemNumber()
    {
        return treeItemNumber;
    }

    @Override
    public RelationNode getParent()
    {
        return ( RelationNode ) parent;
    }

}
