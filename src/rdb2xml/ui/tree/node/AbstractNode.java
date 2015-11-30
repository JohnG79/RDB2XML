package rdb2xml.ui.tree.node;

public abstract class AbstractNode extends AbstractMutableTreeTableNode
{

    private AbstractNode()
    {
    }

    private AbstractNode( Object[] objects )
    {
    }

    protected int treeItemNumber;

    public AbstractNode( int treeItemNumber, Object[] objects )
    {
        super( objects );
        this.treeItemNumber = treeItemNumber;
    }

    @Override
    public Object getValueAt( int columnIndex )
    {
        return getUserObject()[ columnIndex ];
    }

    @Override
    public void setValueAt( Object value, int columnIndex )
    {
        ( ( Object[] ) userObject )[ columnIndex ] = value;
    }

    @Override
    public int getColumnCount()
    {
        return getData().length;
    }

    @Override
    public Object[] getUserObject()
    {
        return ( Object[] ) super.getUserObject();
    }

    @Override
    public void setUserObject( Object object )
    {
        userObject = object;
    }

    @Override
    public boolean isEditable( int column )
    {
        return column != 0;
    }

    public Object[] getData()
    {
        return getUserObject();
    }

    public int getObjectCount()
    {
        return treeItemNumber;
    }

    public String getName()
    {
        return ( String ) ( ( Object[] ) super.getUserObject() )[ 0 ];
    }
}
