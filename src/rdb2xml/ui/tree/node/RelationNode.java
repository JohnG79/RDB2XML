package rdb2xml.ui.tree.node;

import Visitor.Visitor;
import java.util.ArrayList;
import static java.util.Collections.enumeration;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.TreeNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

public class RelationNode extends AbstractNonLeafNode implements SchemaObject
{

    private final List<Attribute> children;

    public RelationNode( int treeItemNumber, Object[] objects )
    {
        super( treeItemNumber, objects );
        this.userObject = objects;
        this.allowsChildren = true;
        children = new ArrayList<>();
    }

    public List<Attribute> getAttributes()
    {
        return children;
    }

    @Override
    public void insert( MutableTreeTableNode child, int index )
    {
        if ( !allowsChildren )
        {
            throw new IllegalStateException( "this node cannot accept children" );
        }

        if ( children.contains( child ) )
        {
            children.remove( child );
            index--;
        }

        children.add( index, ( Attribute ) child );

        if ( child.getParent() != this )
        {
            child.setParent( this );
        }
    }

    @Override
    public void remove( int index )
    {
        MutableTreeTableNode a = ( MutableTreeTableNode ) children.remove( index );
        a.setParent( null );
    }

    @Override
    public void remove( MutableTreeTableNode node )
    {
        children.remove( ( RelationNode ) node );
        node.setParent( null );
    }

    @Override
    public TreeTableNode getChildAt( int childIndex )
    {
        return ( TreeTableNode ) children.get( childIndex );
    }

    @Override
    public int getIndex( TreeNode node )
    {
        return children.indexOf( node );
    }

    @Override
    public Enumeration<? extends MutableTreeTableNode> children()
    {
        ArrayList<MutableTreeTableNode> children = new ArrayList<>();
        for ( Attribute attribute : this.children )
        {
            children.add( ( AbstractMutableTreeTableNode ) attribute );
        }

        return enumeration( children );
    }

    @Override
    public int getChildCount()
    {
        return children.size();
    }

    @Override
    public void acceptVisitor( Visitor visitor )
    {
        visitor.visit( this );
    }
}
