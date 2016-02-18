package rdb2xml.ui.tree.node;

import Processor.Processor;
import extraction.XSDDatatype;
import static extraction.XSDDatatype.get;

public class NonKeyNode extends AbstractLeafNode implements Attribute
{

    private XSDDatatype xsdDatatype;
    private final String nonKeyName;
    private String termName;

    public NonKeyNode( String nonKeyName, XSDDatatype xsdDatatype )
    {
        super( null );
        this.nonKeyName = nonKeyName;
        this.termName = nonKeyName;
        this.allowsChildren = false;
        this.xsdDatatype = xsdDatatype;
    }

    @Override
    public void setValueAt( Object o, int column )
    {
        switch ( column )
        {
            case 1:
            {
                termName = ( String ) o;
                break;
            }
        }
    }
    
    public String getTermName()
    {
        return termName;
    }
    
    @Override
    public int getOrderNumber()
    {
        RelationNode parent = getParent();
        return parent.getOrderNumber() + parent.getIndex( this ) + 1;
    }

    @Override
    public RelationNode getParent()
    {
        return ( RelationNode ) parent;
    }

    @Override
    public void setDatatype( String datatype )
    {
        xsdDatatype = get( datatype );
    }

    @Override
    public void acceptProcessor( Processor visitor )
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
                return nonKeyName;
            }
            case 1:
            {
                return termName;
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
        return nonKeyName;
    }
}
