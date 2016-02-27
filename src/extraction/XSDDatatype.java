package extraction;

public enum XSDDatatype {

    INTEGER("xsd:integer"),
    DECIMAL("xsd:decimal"),
    DATETIME("xsd:dateTime"),
    DATE("xsd:date"),
    TIME("xsd:time"),
    STRING("xsd:string"),
    anyURL("xsd:anyURI");

    public String string;

    private XSDDatatype(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

    public static XSDDatatype get(String databaseNotation) {
        if (databaseNotation.toLowerCase().contains("int")) {
            return INTEGER;
        } else if (databaseNotation.toLowerCase().contains("dec")) {
            return DECIMAL;
        } else if (databaseNotation.toLowerCase().contains("datetime")) {
            return DATETIME;
        } else if (databaseNotation.toLowerCase().contains("date")) {
            return DATE;
        } else if (databaseNotation.toLowerCase().contains("time")) {
            return TIME;
        } else if (databaseNotation.toLowerCase().contains("uri")) {
            return anyURL;
        } else {
            return STRING;
        }
    }
}
