package treetable.tree.node;

public class CombinedKeyNode extends ForeignKeyNode implements VisitorAcceptor
{

    public CombinedKeyNode( int treeItemNumber, Object[] objects )
    {
        super( treeItemNumber, objects );
    }
    
    public CombinedKeyNode( int treeItemNumber, Object[] objects, String referencedTableName, String referencedColumnName )
    {
        super( treeItemNumber, objects, referencedTableName, referencedColumnName );
    }   
}
