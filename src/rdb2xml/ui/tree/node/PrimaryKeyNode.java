package rdb2xml.ui.tree.node;

import Visitor.Visitor;

public class PrimaryKeyNode extends AbstractLeafNode implements Primary
{

    private final String constraintName;

    public PrimaryKeyNode( int treeItemNumber, Object[] objects, String constaintName, String dataType )
    {
        super( treeItemNumber, objects );
        super.setValueAt( dataType, 3 );
        this.userObject = objects;
        this.allowsChildren = false;
        this.constraintName = constaintName;
    }

    @Override
    public String getConstraintName()
    {
        return constraintName;
    }

    public void setParent( RelationNode relationNode )
    {
        parent = relationNode;
    }

    @Override
    public boolean isEditable( int column )
    {
        return column != 0 && column != 2;
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
