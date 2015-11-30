package rdb2xml.ui.tree.node;

import Visitor.Visitor;
import java.util.ArrayList;
import static java.util.Collections.enumeration;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.TreeNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

public class SchemaNode extends AbstractNonLeafNode implements SchemaObject
{

    private final List<RelationNode> children;

    public SchemaNode( int treeItemNumber, Object[] objects )
    {
        super( treeItemNumber, objects );
        this.userObject = objects;
        this.allowsChildren = true;
        children = new ArrayList<>();
    }

    public Iterable<RelationNode> getRelations()
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

        if ( children.contains( ( RelationNode ) child ) )
        {
            children.remove( ( RelationNode ) child );
            index--;
        }

        children.add( index, ( RelationNode ) child );

        if ( child.getParent() != this )
        {
            child.setParent( this );
        }
    }

    @Override
    public void remove( int index )
    {
        children.remove( index ).setParent( null );
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
        ArrayList<MutableTreeTableNode> children = new ArrayList<>();
        for ( RelationNode relation : this.children )
        {
            children.add( ( AbstractMutableTreeTableNode ) relation );
        }
        return enumeration( children );
    }

    @Override
    public int getChildCount()
    {
        return children.size();
    }

    @Override
    public boolean isEditable( int column )
    {
        return false;
    }

    @Override
    public void setValueAt( Object aValue, int column )
    {
    }

    @Override
    public void acceptVisitor( Visitor visitor )
    {
        visitor.visit( this );
    }
}
