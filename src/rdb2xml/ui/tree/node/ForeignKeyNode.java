package rdb2xml.ui.tree.node;

public class ForeignKeyNode extends AbstractLeafNode implements Foreign
{

    private final String referencedRelationName;
    private final String referencedAttributeName;

    public ForeignKeyNode( int treeItemNumber, Object[] objects )
    {
        super( treeItemNumber, objects );
        referencedRelationName = "REFERENCED_RELATIONSCHEMA_NOT_SET";
        referencedAttributeName = "REFERENCED_ATTRIBUTE_NOT_SET";
    }

    public ForeignKeyNode( int treeItemNumber, Object[] objects, String referencedRelationSchemaName, String referencedAttributeName )
    {
        super( treeItemNumber, objects );
        this.referencedRelationName = referencedRelationSchemaName;
        this.referencedAttributeName = referencedAttributeName;
        super.setValueAt( referencedRelationSchemaName.substring( 0, 1 ).toUpperCase() + referencedRelationSchemaName.substring( 1 ), 2 );
    }

    public String getReferencedRelationSchemaName()
    {
        return referencedRelationName;
    }

    @Override
    public Primary getReferencedPrimary()
    {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

}
