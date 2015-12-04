package rdb2xml.ui.tree.node;

import Visitor.Visitor;

public class CombinedKeyNode extends AbstractLeafNode implements Primary, Foreign
{

    private final String constraintName;
    private final Primary referencedPrimary;

    public CombinedKeyNode( int treeItemNumber, Object[] objects, String referencedRelationName, String referencedAttributeName, String constraintName, Primary referencedPrimary, String dataType )
    {
        super( treeItemNumber, objects );
        super.setValueAt( referencedRelationName.substring( 0, 1 ).toUpperCase() + referencedRelationName.substring( 1 ), 2 );
        super.setValueAt( dataType, 3 );
        this.userObject = objects;
        this.allowsChildren = false;
        this.constraintName = constraintName;
        this.referencedPrimary = referencedPrimary;
    }

    @Override
    public String getConstraintName()
    {
        return constraintName;
    }

    @Override
    public Primary getReferencedPrimary()
    {
        return referencedPrimary;
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
