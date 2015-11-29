package rdb2xml.ui.tree.node;

public class PrimaryKeyNode extends AbstractLeafNode implements Primary
{

    public PrimaryKeyNode( int treeItemNumber, Object[] object )
    {
        super( treeItemNumber, object );
    }

    @Override
    /**
     * Are the rows that represent PrimaryKeyNodes editable?
     */
    public boolean isEditable( int column )
    {
        return column != 0 && column != 2;
    }

}
