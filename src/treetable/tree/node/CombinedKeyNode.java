package treetable.tree.node;

public class CombinedKeyNode extends ForeignKeyNode implements VisitorAcceptor
{

    public CombinedKeyNode( Object[] objects )
    {
        super( objects );
    }
    public CombinedKeyNode( Object[] objects, String referencedTableName, String referencedColumnName )
    {
        super( objects, referencedTableName, referencedColumnName );
    }   
}
