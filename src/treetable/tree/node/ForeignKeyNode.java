package treetable.tree.node;

public class ForeignKeyNode extends treetable.tree.node.ChildNode implements VisitorAcceptor
{

    protected String referencedRelationSchemaName;
    protected String referencedAttributeName;

    public ForeignKeyNode( Object[] objects )
    {
        super( objects );
        referencedRelationSchemaName = "REFERENCED_RELATIONSCHEMA_NOT_SET";
        referencedAttributeName = "REFERENCED_ATTRIBUTE_NOT_SET";
    }

    public ForeignKeyNode( Object[] objects, String referencedRelationSchemaName, String referencedAttributeName )
    {
        super( objects );
        this.referencedRelationSchemaName = referencedRelationSchemaName;
        this.referencedAttributeName = referencedAttributeName;
        setValueAt(referencedRelationSchemaName.substring( 0, 1 ).toUpperCase() + referencedRelationSchemaName.substring( 1 ), 2);
    }

    public String getReferencedRelationSchemaName()
    {
        return referencedRelationSchemaName;
    }
}
