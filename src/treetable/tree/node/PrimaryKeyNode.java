package treetable.tree.node;

public class PrimaryKeyNode extends treetable.tree.node.ChildNode implements VisitorAcceptor
{
    public PrimaryKeyNode( Object[] objects )
    {
        super( objects );
    }
    
    @Override
    public boolean isEditable( int column )
    {
        return column != 0 && column != 2;
    }
    
}
