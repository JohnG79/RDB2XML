package rdb2xml.ui.tree.node;

public class NonKeyNode extends AbstractLeafNode implements Attribute
{

    public NonKeyNode( int treeItemNumber, Object[] objects )
    {
        super( treeItemNumber, objects );
    }

    public String getAttributeName()
    {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }
}
