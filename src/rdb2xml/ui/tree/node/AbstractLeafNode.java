package rdb2xml.ui.tree.node;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

import javax.swing.tree.TreeNode;

public abstract class AbstractLeafNode extends AbstractNode
{

    public AbstractLeafNode( int treeItemNumber, Object[] objects )
    {
        super( treeItemNumber, objects );
    }

    @Override
    public void add( MutableTreeTableNode mttn )
    {
        throw new UnsupportedOperationException( "Leaf nodes do not have children." );
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
    public boolean getAllowsChildren()
    {
        return false;
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
        throw new UnsupportedOperationException( "Leaf nodes do not have children." );
    }

    @Override
    public boolean isLeaf()
    {
        return true;
    }

    @Override
    public void remove( int index )
    {
        throw new UnsupportedOperationException( "Leaf nodes do not have children." );
    }

    @Override
    public void remove( MutableTreeTableNode node )
    {
        throw new UnsupportedOperationException( "Leaf nodes do not have children." );
    }

    @Override
    public void setAllowsChildren( boolean allowsChildren )
    {
        throw new UnsupportedOperationException( "Leaf nodes do not have children." );
    }

    public int getTreeItemNumber()
    {
        return treeItemNumber;
    }

}
