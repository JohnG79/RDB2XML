package rdb2xml.ui.tree.node;

import Visitor.Visitor;

public class NonKeyNode extends AbstractLeafNode implements Attribute
{

    public NonKeyNode( int treeItemNumber, Object[] objects, String dataType )
    {
        super( treeItemNumber, objects );
        super.setValueAt( "xsd:string", 2 );
        super.setValueAt( dataType, 3 );
        this.userObject = objects;
        this.allowsChildren = false;
    }

    @Override
    public void acceptVisitor( Visitor visitor )
    {
        visitor.visit( this );
    }

    @Override
    public String getDatatype()
    {
        return ( String ) getValueAt( 3 );
    }

    @Override
    public void setDatatype( String dataType )
    {
        setValueAt( dataType, 3 );
    }
}
