package rdb2xml.ui.tree.node;

import Processor.Processor;
import java.util.ArrayList;
import static java.util.Collections.enumeration;
import static java.util.Collections.unmodifiableList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

public class RelationNode extends AbstractNonLeafNode implements SchemaObject
{

    private ArrayList<Tuple> tuples;

    private final String relationName;
    private String conceptName;
    private final List<Attribute> attributeNodes;

    public RelationNode( String relationName, String conceptName )
    {
        super( null );
        this.relationName = relationName;
        this.conceptName = conceptName;
        this.allowsChildren = true;
        attributeNodes = new ArrayList<>();

        this.tuples = new ArrayList<>();
    }

    public void addTuple( HashMap<String, String> data )
    {
        HashMap<Attribute, String> tupleTemp = new HashMap<>();

        if ( data.size() != attributeNodes.size() )
        {
            throw new IllegalArgumentException( "tuple length doesn't match number of attributes in this relation." );
        }
        Set<String> attributeNames = data.keySet();
        for ( String attributeName : attributeNames )
        {
            tupleTemp.put( getAttribute( attributeName ), data.get( attributeName ) );
        }
        Tuple newTuple = new Tuple( this );
        newTuple.setData( tupleTemp );
        this.tuples.add( newTuple );
    }

    public void removeTuples()
    {
        this.tuples = new ArrayList<>();
    }

    private Attribute getAttribute( String attributeName )
    {
        Attribute attributeTemp = null;
        for ( Attribute attribute : attributeNodes )
        {
            if ( attribute.getName().equals( attributeName ) )
            {
                attributeTemp = attribute;
            }
        }
        return attributeTemp;
    }

    @Override
    public int getOrderNumber()
    {
        int tally = 0;
        SchemaNode parentTemp = ( SchemaNode ) getParent();
        tally += parentTemp.getOrderNumber();
        List<RelationNode> relationNodes = parentTemp.getRelations();
        for ( int i = 0; i < parentTemp.getIndex( this ); i++ )
        {
            tally += 1;
            tally += relationNodes.get( i ).getChildCount();
        }
        return tally;
    }

    public final List<Attribute> getAttributes()
    {
        return unmodifiableList( attributeNodes );
    }

    @Override
    public void insert( MutableTreeTableNode child, int index )
    {
        if ( !allowsChildren )
        {
            throw new IllegalStateException( "this node cannot accept children" );
        }

        if ( attributeNodes.contains( child ) )
        {
            attributeNodes.remove( child );
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
        attributeNodes.remove( node );
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
            attributeNodesTemp.add( ( MutableTreeTableNode ) attribute );
        }

        return enumeration( attributeNodesTemp );
    }

    @Override
    public int getChildCount()
    {
        return attributeNodes.size();
    }

    @Override
    public void acceptProcessor( Processor visitor )
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

    public void clearData()
    {
        tuples = new ArrayList<>();
    }

    public ArrayList<Tuple> getTuples()
    {
        return this.tuples;
    }

}
