package rdb2xml.ui.tree.node;

import Processor.Processor;
import java.util.HashMap;

public class Tuple
{

    private final RelationNode parentRelation;
    private HashMap<Attribute, String> data;

    public Tuple( RelationNode parentRelation )
    {
        this.parentRelation = parentRelation;
    }

    public String getName()
    {
        return parentRelation.getName();
    }

    public void setData( HashMap<Attribute, String> data )
    {
        this.data = data;
    }

    public HashMap<Attribute, String> get_data()
    {
        return this.data;
    }

    public void acceptProcessor( Processor visitor )
    {
        visitor.visit( this );
    }
}
