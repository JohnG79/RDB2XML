package Processor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import rdb2xml.ui.tree.node.Attribute;
import rdb2xml.ui.tree.node.CombinedKeyNode;
import rdb2xml.ui.tree.node.ForeignKeyNode;
import rdb2xml.ui.tree.node.Key;
import rdb2xml.ui.tree.node.NonKeyNode;
import rdb2xml.ui.tree.node.RelationNode;
import rdb2xml.ui.tree.node.SchemaNode;
import rdb2xml.ui.tree.node.Tuple;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import rdb2xml.ui.tree.node.PrimaryKeyNode;

public class RDFDocBuilder implements Processor {

    private Map<RelationNode, PrimaryKeyNode> primaryKeys;
    private Model model;
    private String NS = "http://www.example-university.com/some-resource";

    public RDFDocBuilder() {
        primaryKeys = new HashMap<>();
        model = ModelFactory.createDefaultModel();
    }

    @Override
    public void visit(Tuple tuple) {
        PrimaryKeyNode primaryKeyNode = primaryKeys.get(tuple.getParent());
        model = tuple.addToRDFModel(NS, model, primaryKeyNode);
    }

    @Override
    public void visit(SchemaNode data_schema) {
        /*  
            scoop up all primary keys.
            need to know which attributes are primary keys - without using
            instanceof keyword.
            when tuples are transformed to RDF subgraphs (sets of triples),
            the primary key value makes up part of the subject's URI;
         */
        PrimaryKeysFinder primaryKeysFinder = new PrimaryKeysFinder();
        for (RelationNode relationNode : data_schema.getRelations()) {
            relationNode.acceptProcessor(primaryKeysFinder);
            for (Attribute attribute : relationNode.getAttributes()) {
                attribute.acceptProcessor(primaryKeysFinder);
            }
        }
        primaryKeys = primaryKeysFinder.primaryKeys;

        HashMap<String, String> map = new HashMap<>();
        map.putAll(data_schema.getDataNamespaces());
        map.putAll(data_schema.getOntologyNamespaces());
        model.setNsPrefixes(map);
    }

    @Override
    public void visit(RelationNode relation_schema) {
    }

    @Override
    public void visit(NonKeyNode non_key) {
    }

    @Override
    public void visit(Key key) {
    }

    @Override
    public void visit(ForeignKeyNode foreign_key) {
    }

    @Override
    public void visit(CombinedKeyNode combined_key) {
    }

    public void serialise(RSyntaxTextArea syntaxTextArea) {

        OutputStream o = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                String s = String.valueOf((char) b);
                syntaxTextArea.append(s);
            }
        };
        syntaxTextArea.setText("");
        RDFDataMgr.write(o, model, RDFFormat.JSONLD);
    }
}
