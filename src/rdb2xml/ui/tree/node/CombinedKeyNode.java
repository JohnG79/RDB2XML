package rdb2xml.ui.tree.node;

import Visitor.Visitor;

public class CombinedKeyNode extends AbstractLeafNode implements Primary, Foreign
{

    private final String constraintName;
    private final Primary referencedPrimary;

    public CombinedKeyNode( int treeItemNumber, Object[] objects, String referencedRelationName, String referencedAttributeName, String constraintName, Primary referencedPrimary )
    {
        super( treeItemNumber, objects );
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
}
