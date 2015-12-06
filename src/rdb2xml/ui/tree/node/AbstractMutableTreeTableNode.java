package rdb2xml.ui.tree.node;

import java.util.ArrayList;
import static java.util.Collections.enumeration;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.TreeNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

public abstract class AbstractMutableTreeTableNode implements MutableTreeTableNode
{

    protected MutableTreeTableNode parent;
    protected final List<MutableTreeTableNode> children;
    protected transient Object userObject;
    protected boolean allowsChildren;

    public AbstractMutableTreeTableNode()
    {
        this( null );
    }

    public AbstractMutableTreeTableNode( Object userObject )
    {
        this( userObject, true );
    }

    public AbstractMutableTreeTableNode( Object userObject,
            boolean allowsChildren )
    {
        this.userObject = userObject;
        this.allowsChildren = allowsChildren;
        children = createChildrenList();
    }

    protected List<MutableTreeTableNode> createChildrenList()
    {
        return new ArrayList<>();
    }

    public abstract void add( MutableTreeTableNode child );

    @Override
    public abstract void insert( MutableTreeTableNode child, int index );

    @Override
    public abstract void remove( int index );

    @Override
    public abstract void remove( MutableTreeTableNode node );

    @Override
    public void removeFromParent()
    {
        parent.remove( this );
    }

    @Override
    public final void setParent( MutableTreeTableNode newParent )
    {
        if ( newParent == null || newParent.getAllowsChildren() )
        {
            if ( parent != null && parent.getIndex( this ) != -1 )
            {
                parent.remove( this );
            }
        }
        else
        {
            throw new IllegalArgumentException(
                    "newParent does not allow children" );
        }

        parent = newParent;

        if ( parent != null && parent.getIndex( this ) == -1 )
        {
            parent.insert( this, parent.getChildCount() );
        }
    }

    @Override
    public Object[] getUserObject()
    {
        return ( Object[] ) userObject;
    }

    @Override
    public void setUserObject( Object object )
    {
        userObject = object;
    }

    @Override
    public TreeTableNode getChildAt( int childIndex )
    {
        return children.get( childIndex );
    }

    @Override
    public int getIndex( TreeNode node )
    {
        return children.indexOf( node );
    }

    @Override
    public Enumeration<? extends MutableTreeTableNode> children()
    {
        return enumeration( children );
    }

    @Override
    public boolean getAllowsChildren()
    {
        return allowsChildren;
    }

    public void setAllowsChildren( boolean allowsChildren )
    {
        this.allowsChildren = allowsChildren;

        if ( !this.allowsChildren )
        {
            children.clear();
        }
    }

    @Override
    public int getChildCount()
    {
        return children.size();
    }

    @Override
    public boolean isLeaf()
    {
        return getChildCount() == 0;
    }

    @Override
    public abstract boolean isEditable( int column );

    @Override
    public String toString()
    {
        if ( userObject == null )
        {
            return "";
        }
        else
        {
            return ( ( Object[] ) userObject )[ 0 ].toString();
        }
    }

}
