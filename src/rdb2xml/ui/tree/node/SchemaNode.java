package rdb2xml.ui.tree.node;

import Processor.Processor;
import java.util.ArrayList;
import static java.util.Collections.enumeration;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.TreeNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

public class SchemaNode extends AbstractNonLeafNode implements SchemaObject
{

    private final List<RelationNode> relationNodes;
    private String schemaName;

    public SchemaNode( String schemaName )
    {
        super( null );
        this.schemaName = schemaName;
        this.allowsChildren = true;
        relationNodes = new ArrayList<>();
    }

    public List<RelationNode> getRelations()
    {
        return relationNodes;
    }

    @Override
    public void insert( MutableTreeTableNode child, int index )
    {
        if ( !allowsChildren )
        {
            throw new IllegalStateException( "this node cannot accept children" );
        }

        if ( relationNodes.contains( ( RelationNode ) child ) )
        {
            relationNodes.remove( ( RelationNode ) child );
            index--;
        }

        relationNodes.add( index, ( RelationNode ) child );

        if ( child.getParent() != this )
        {
            child.setParent( this );
        }
    }

    @Override
    public void remove( int index )
    {
        relationNodes.remove( index ).setParent( null );
    }

    @Override
    public void remove( MutableTreeTableNode node )
    {
        relationNodes.remove( ( RelationNode ) node );
        node.setParent( null );
    }

    @Override
    public TreeTableNode getChildAt( int childIndex )
    {
        return relationNodes.get( childIndex );
    }

    @Override
    public int getIndex( TreeNode node )
    {
        return relationNodes.indexOf( node );
    }

    @Override
    public Enumeration<? extends MutableTreeTableNode> children()
    {
        ArrayList<MutableTreeTableNode> relationNodesTemp = new ArrayList<>();
        for ( RelationNode relation : this.relationNodes )
        {
            relationNodesTemp.add( ( AbstractMutableTreeTableNode ) relation );
        }
        return enumeration( relationNodesTemp );
    }

    @Override
    public int getChildCount()
    {
        return relationNodes.size();
    }

    @Override
    public void setValueAt( Object value, int column )
    {
    }

    @Override
    public Object getValueAt( int column )
    {
        if ( column == 0 )
        {
            return schemaName;
        }
        return "";
    }

    @Override
    public void acceptProcessor( Processor visitor )
    {
        visitor.visit( this );
    }

    @Override
    public String getName()
    {
        return schemaName;
    }

    @Override
    public int getOrderNumber()
    {
        return 0;
    }

}
