package rdb2xml.ui.tree.node;

import Visitor.Visitor;
import extraction.AttributeItem;
import static extraction.AttributeItem.ATTRIBUTE_NAME;
import static extraction.AttributeItem.PARENT_RELATION_NAME;
import static extraction.AttributeItem.REFERENCED_ATTRIBUTE_NAME;
import static extraction.AttributeItem.REFERENCED_RELATION_NAME;
import extraction.XSDDatatype;
import static extraction.XSDDatatype.get;
import java.util.HashMap;

public class CombinedKeyNode extends AbstractLeafNode implements Primary, Foreign
{

    private KeyConstraint keyConstraint;
    private final Primary referencedPrimary;
    private XSDDatatype xsdDatatype;
    private final String keyName;
    private String newTermName;
    private final String defaultPropertyRange;

    public CombinedKeyNode( HashMap<AttributeItem, String> attributeItems, Primary referencedPrimary, XSDDatatype xsdDatatype )
    {
        super( null );
        this.keyName = attributeItems.get( ATTRIBUTE_NAME );
        this.newTermName = attributeItems.get( ATTRIBUTE_NAME );
        this.defaultPropertyRange = attributeItems.get( REFERENCED_RELATION_NAME ).substring( 0, 1 ).toUpperCase() + attributeItems.get( REFERENCED_RELATION_NAME ).substring( 1 );
        this.allowsChildren = false;
        setConstraint( attributeItems );
        this.referencedPrimary = referencedPrimary;
        this.xsdDatatype = xsdDatatype;
    }

    @Override
    public void setValueAt( Object o, int column )
    {
        switch ( column )
        {
            case 1:
            {
                newTermName = ( String ) o;
                break;
            }
        }
    }

    @Override
    public void setDatatype( String datatype )
    {
        xsdDatatype = get( datatype );
    }

    @Override
    public RelationNode getParent()
    {
        return ( RelationNode ) parent;
    }

    @Override
    public int getOrderNumber()
    {
        RelationNode parent = getParent();
        return parent.getOrderNumber() + parent.getIndex( this ) + 1;
    }

    private void setConstraint( HashMap<AttributeItem, String> attributeItems )
    {
        this.keyConstraint = new KeyConstraint( "FK" );
        this.keyConstraint.setRefRelationName( attributeItems.get( PARENT_RELATION_NAME ) );
        this.keyConstraint.setRefKeyName( attributeItems.get( ATTRIBUTE_NAME ) );
    }

    @Override
    public KeyConstraint getKeyConstraint()
    {
        return keyConstraint;
    }

    @Override
    public Primary getReferencedKey()
    {
        return referencedPrimary;
    }

    @Override
    public void acceptVisitor( Visitor visitor )
    {
        visitor.visit( this );
    }

    @Override
    public XSDDatatype getDatatype()
    {
        return this.xsdDatatype;
    }

    @Override
    public Object getValueAt( int column )
    {
        switch ( column )
        {
            case 0:
            {
                return keyName;
            }
            case 1:
            {
                return newTermName;
            }
            case 3:
            {
                return xsdDatatype.toString();
            }
        }
        return "";
    }

    @Override
    public String getName()
    {
        return keyName;
    }
}
