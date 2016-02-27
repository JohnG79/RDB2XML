package rdb2xml.ui.tree.node;

import Processor.Processor;
import java.util.HashMap;
import java.util.Set;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.openrdf.model.vocabulary.RDF;

public class Tuple {

    private final RelationNode parentRelation;
    private HashMap<Attribute, String> data;

    public Tuple(RelationNode parentRelation) {
        this.parentRelation = parentRelation;
    }

    public String getName() {
        return parentRelation.getName();
    }

    public void setData(HashMap<Attribute, String> data) {
        this.data = data;
    }

    public HashMap<Attribute, String> get_data() {
        return this.data;
    }

    public void acceptProcessor(Processor visitor) {
        visitor.visit(this);
    }

    public RelationNode getParent() {
        return parentRelation;
    }

    public Model addToRDFModel(String NS, Model model, PrimaryKeyNode primaryKeyNode) {

        String primaryKeyValue;
        String className = parentRelation.getValueAt(1).toString();
        Resource subject;

        if (primaryKeyNode != null) {
            primaryKeyValue = data.get(primaryKeyNode);

            data.remove(primaryKeyNode);

            subject = model.createResource(parentRelation.getDataNS() + parentRelation.getName() + primaryKeyValue);

        } else {

            subject = model.createResource();
        }
        subject.addProperty(model.createProperty(RDF.TYPE.toString()), model.createResource(parentRelation.getOntologyNS() + className));

        Set< Attribute> attributes = data.keySet();
        Property property;

        for (Attribute attribute : attributes) {
            property = model.createProperty(parentRelation.getOntologyNS() + attribute.getName());

            if (attribute instanceof ForeignKeyNode) {
                subject.addProperty(property, model.createResource(parentRelation.getDataNS() + ((ForeignKeyNode) attribute).getReferencedKey().getParent().getName() + data.get(attribute)));
            } else if (attribute instanceof CombinedKeyNode) {
                subject.addProperty(property, model.createResource(parentRelation.getDataNS() + ((CombinedKeyNode) attribute).getReferencedKey().getParent().getName() + data.get(attribute)));
            } else {
                subject.addProperty(property, data.get(attribute), "");
            }
        }
        return model;
    }
}
