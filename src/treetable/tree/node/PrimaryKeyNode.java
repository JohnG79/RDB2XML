package treetable.tree.node;

public class PrimaryKeyNode extends treetable.tree.node.ChildNode implements VisitorAcceptor
{

    public PrimaryKeyNode( int treeItemNumber, Object[] object )
    {
        super( treeItemNumber, object );
    }
    
    @Override
    public boolean isEditable( int column )
    {
        return column != 0 && column != 2;
    }
    
}
