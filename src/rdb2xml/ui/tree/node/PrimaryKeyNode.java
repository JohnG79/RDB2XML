package rdb2xml.ui.tree.node;

import Processor.Processor;
import Processor.ReferencingKeyFinder;
import extraction.AttributeItem;
import static extraction.AttributeItem.ATTRIBUTE_NAME;
import static extraction.AttributeItem.PARENT_RELATION_NAME;
import extraction.XSDDatatype;
import static extraction.XSDDatatype.get;
import java.util.HashMap;
import java.util.ArrayList;

public class PrimaryKeyNode extends AbstractLeafNode implements Primary {

    private XSDDatatype xsdDatatype;
    private KeyConstraint keyConstraint;
    private final String keyName;
    private String termName;

    public PrimaryKeyNode(HashMap<AttributeItem, String> attributeItems, XSDDatatype xsdDatatype) {
        super(null);
        this.keyName = attributeItems.get(ATTRIBUTE_NAME);
        this.termName = attributeItems.get(ATTRIBUTE_NAME);
        setConstraint(attributeItems);
        this.allowsChildren = false;
        this.xsdDatatype = xsdDatatype;
    }

    public ArrayList<Foreign> getReferencingKeys() {
        SchemaNode schemaNode = ((SchemaNode) getParent().getParent());

        ReferencingKeyFinder referencingKeyFinder = new ReferencingKeyFinder(this);
        schemaNode.acceptProcessor(referencingKeyFinder);
        return referencingKeyFinder.referencingKeys;
    }

    @Override
    public RelationNode getParent() {
        return (RelationNode) parent;
    }

    @Override
    public void setValueAt(Object o, int column) {
        switch (column) {
            case 1: {
                termName = (String) o;
                break;
            }
        }
    }

    @Override
    public int getOrderNumber() {
        RelationNode parent = getParent();
        return parent.getOrderNumber() + parent.getIndex(this) + 1;
    }

    @Override
    public void setDatatype(String datatype) {
        xsdDatatype = get(datatype);
    }

    private void setConstraint(HashMap<AttributeItem, String> attributeItems) {
        this.keyConstraint = new KeyConstraint("PK");
        this.keyConstraint.setRefRelationName(attributeItems.get(PARENT_RELATION_NAME));
    }

    @Override
    public KeyConstraint getKeyConstraint() {
        return keyConstraint;
    }

    public void setParent(RelationNode relationNode) {
        parent = relationNode;
    }

    @Override
    public void acceptProcessor(Processor visitor) {
        visitor.visit(this);
    }

    @Override
    public XSDDatatype getDatatype() {
        return this.xsdDatatype;
    }

    public String getTermName() {
        return termName;
    }

    @Override
    public Object getValueAt(int column) {
        switch (column) {
            case 0: {
                return keyName;
            }
            case 1: {
                return termName;
            }
            case 3: {
                return xsdDatatype.toString();
            }
        }
        return "";
    }

    @Override
    public String getName() {
        return keyName;
    }
}
