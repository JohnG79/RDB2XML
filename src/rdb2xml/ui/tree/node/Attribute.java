package rdb2xml.ui.tree.node;

import extraction.XSDDatatype;

public interface Attribute extends SchemaObject {

    public RelationNode getParent();

    public void setDatatype(String datatype);

    public XSDDatatype getDatatype();
}
