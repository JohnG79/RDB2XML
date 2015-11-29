package rdb2xml.ui.tree.node;

public class CombinedKeyNode extends AbstractLeafNode implements Primary, Foreign
{

    private final String referencedRelationName;
    private final String referencedAttributeName;

    public CombinedKeyNode( int treeItemNumber, Object[] objects )
    {
        super( treeItemNumber, objects );
        referencedRelationName = "REFERENCED_RELATIONSCHEMA_NOT_SET";
        referencedAttributeName = "REFERENCED_ATTRIBUTE_NOT_SET";
    }

    public CombinedKeyNode( int treeItemNumber, Object[] objects, String referencedRelationName, String referencedAttributeName )
    {
        super( treeItemNumber, objects );
        this.referencedRelationName = referencedRelationName;
        this.referencedAttributeName = referencedAttributeName;
    }

    @Override
    public Primary getReferencedPrimary()
    {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

}
