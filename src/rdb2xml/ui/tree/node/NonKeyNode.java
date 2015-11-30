package rdb2xml.ui.tree.node;

import Visitor.Visitor;

public class NonKeyNode extends AbstractLeafNode implements Attribute
{

    public NonKeyNode( int treeItemNumber, Object[] objects )
    {
        super( treeItemNumber, objects );
        super.setValueAt( "xsd:string", 2 );
        this.userObject = objects;
        this.allowsChildren = false;
    }

    @Override
    public void acceptVisitor( Visitor visitor )
    {
        visitor.visit( this );
    }
}
