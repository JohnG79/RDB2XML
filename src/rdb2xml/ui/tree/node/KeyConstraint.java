package rdb2xml.ui.tree.node;

public class KeyConstraint {

    private final String string;
    private String string1 = "NOT_SET";
    private String string2 = "";

    public KeyConstraint(String string) {
        this.string = string;
    }

    @Override
    public String toString() {

        return string + string1 + string2;

    }

    public void setRefRelationName(String refRelationName) {
        this.string1 = refRelationName;
    }

    public void setRefKeyName(String refKeyName) {
        this.string2 = "_" + refKeyName;
    }

    public String getRefRelationName() {
        if (this.string1 != null) {
            return this.string1;
        }
        throw new UnsupportedOperationException("REFERENCED_RELATION_NAME_NOT_SET");
    }

    public String getRefKeyName() {
        if (this.string2 != null) {
            return this.string2;
        }
        throw new UnsupportedOperationException("REFERENCED_ATTRIBUTE_NAME_NOT_SET");
    }

    public String getConstraintName() {
        return this.string + this.string1 + this.string2;
    }
}
