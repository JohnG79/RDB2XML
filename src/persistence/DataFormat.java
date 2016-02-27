package persistence;

public enum DataFormat {

    OWL(new String[]{
        "Schema", "Concept / Property", "Property Range"
    }),
    XSD(new String[]{
        "Schema", "Datatype"
    }),
    XML(new String[]{
        ""
    });

    private final String[] treeTableHeadings;

    private DataFormat(String[] treeTableHeadings) {
        this.treeTableHeadings = treeTableHeadings;
    }

    public String[] treeTableHeadings() {
        return treeTableHeadings;
    }
}
