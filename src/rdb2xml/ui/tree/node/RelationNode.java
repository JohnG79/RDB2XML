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

    private String relationName;
    private String conceptName;
    private final List<Attribute> attributeNodes;

    public RelationNode( String relationName, String conceptName )
    {
        super( null );
        this.relationName = relationName;
        this.conceptName = conceptName;
        this.allowsChildren = true;
        attributeNodes = new ArrayList<>();
    }

    @Override
    public int getOrderNumber()
    {
        int tally = 0;
        SchemaNode parent = ( SchemaNode ) getParent();
        tally += parent.getOrderNumber();
        List<RelationNode> relationNodes = parent.getRelations();
        for ( int i = 0; i < parent.getIndex( this ); i++ )
        {
            tally += 1;
            tally += ( ( RelationNode ) relationNodes.get( i ) ).getChildCount();
        }
        return tally;
    }

    public List<Attribute> getAttributes()
    {
        return attributeNodes;
    }

    @Override
    public void insert( MutableTreeTableNode child, int index )
    {
        if ( !allowsChildren )
        {
            throw new IllegalStateException( "this node cannot accept children" );
        }

        if ( attributeNodes.contains( ( Attribute ) child ) )
        {
            attributeNodes.remove( ( Attribute ) child );
            index--;
        }

        attributeNodes.add( index, ( Attribute ) child );

        if ( child.getParent() != this )
        {
            child.setParent( this );
        }
    }

    @Override
    public void remove( int index )
    {
        MutableTreeTableNode a = ( MutableTreeTableNode ) attributeNodes.remove( index );
        a.setParent( null );
    }

    @Override
    public void remove( MutableTreeTableNode node )
    {
        attributeNodes.remove( ( Attribute ) node );
        node.setParent( null );
    }

    @Override
    public TreeTableNode getChildAt( int childIndex )
    {
        return ( TreeTableNode ) attributeNodes.get( childIndex );
    }

    @Override
    public int getIndex( TreeNode node )
    {
        return attributeNodes.indexOf( node );
    }

    @Override
    public Enumeration<? extends MutableTreeTableNode> children()
    {
        ArrayList<MutableTreeTableNode> attributeNodesTemp = new ArrayList<>();
        for ( Attribute attribute : this.attributeNodes )
        {
            attributeNodesTemp.add( ( AbstractMutableTreeTableNode ) attribute );
        }

        return enumeration( attributeNodesTemp );
    }

    @Override
    public int getChildCount()
    {
        return attributeNodes.size();
    }

    @Override
    public void acceptVisitor( Visitor visitor )
    {
        visitor.visit( this );
    }

    @Override
    public void setValueAt( Object value, int column )
    {
        switch ( column )
        {
            case 1:
            {
                conceptName = ( String ) value;
                break;
            }
        }
    }

    @Override
    public Object getValueAt( int column )
    {
        switch ( column )
        {
            case 0:
            {
                return relationName;
            }
            case 1:
            {
                return conceptName;
            }
        }
        return "";
    }

    @Override
    public String getName()
    {
        return relationName;
    }

}
