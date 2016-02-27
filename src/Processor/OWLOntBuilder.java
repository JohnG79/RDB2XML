package Processor;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.coode.owlapi.turtle.TurtleOntologyFormat;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

import rdb2xml.ui.tree.node.CombinedKeyNode;
import rdb2xml.ui.tree.node.ForeignKeyNode;
import rdb2xml.ui.tree.node.Key;
import rdb2xml.ui.tree.node.NonKeyNode;
import rdb2xml.ui.tree.node.RelationNode;
import rdb2xml.ui.tree.node.SchemaNode;
import rdb2xml.ui.tree.node.Tuple;

public class OWLOntBuilder implements Processor {

    private static IRI baseIRI;
    private static IRI iri2;
    private OWLDataFactory dataFactory;
    private OWLOntologyManager owlManager;
    private OWLOntology ontology;

    public OWLOntBuilder() {
        try {
            initialiseModel("http://someBaseIRI");

        } catch (Exception e) {

        }
    }

    public void initialiseModel(String baseIRI) throws OWLOntologyCreationException {
        OWLOntBuilder.baseIRI = IRI.create(baseIRI);
        dataFactory = OWLManager.getOWLDataFactory();

        owlManager = OWLManager.createOWLOntologyManager();
        owlManager.addIRIMapper(new AutoIRIMapper(new File("materializedOntologies"), true));

        ontology = owlManager.createOntology(OWLOntBuilder.baseIRI);
    }

    public void addClass(String className) {
        OWLClass newClass = dataFactory.getOWLClass(IRI.create(baseIRI + "#" + className));
        OWLAxiom newAxiom = dataFactory.getOWLDeclarationAxiom(newClass);
        owlManager.addAxiom(ontology, newAxiom);
    }

    public void addObjectProperty(String domainClassName, String propertyName, String rangeClassName) {
        OWLClass newRangeClass = dataFactory.getOWLClass(IRI.create(baseIRI + "#" + rangeClassName));
        OWLObjectProperty newObjectProperty = dataFactory.getOWLObjectProperty(IRI.create(baseIRI + "#" + propertyName));
        Set<OWLObjectPropertyDomainAxiom> allDomainAxioms = ontology.getObjectPropertyDomainAxioms(newObjectProperty);
        OWLClass newDomainClass = dataFactory.getOWLClass(IRI.create(baseIRI + "#" + domainClassName));

        Iterator<OWLObjectPropertyDomainAxiom> PropertyDomainAxiom_itr;
        if ((PropertyDomainAxiom_itr = allDomainAxioms.iterator()).hasNext()) {
            OWLObjectPropertyDomainAxiom propertyDomainAxiom;
            Set<OWLClass> existingDomainClasses = (propertyDomainAxiom = PropertyDomainAxiom_itr.next()).getClassesInSignature();

            owlManager.removeAxiom(ontology, propertyDomainAxiom);
            existingDomainClasses.add(newDomainClass);
            appendToObjectPropertyDomainAxiom(existingDomainClasses, propertyDomainAxiom, newObjectProperty);

            return;
        }

        addObjectPropertyDomainAxiom(newObjectProperty, dataFactory.getOWLClass(IRI.create(baseIRI + "#" + domainClassName)));
        addObjectPropertyRangeAxiom(newObjectProperty, newRangeClass);
    }

    public void addDataProperty(String propertyName, String domainClassName, XSDVocabulary xsdVocabulary) {
        OWLDataProperty newDataProperty = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "#" + propertyName));
        OWLDatatype newDatatype = dataFactory.getOWLDatatype(xsdVocabulary.getIRI());

        Set<OWLDataPropertyDomainAxiom> allDomainAxioms = ontology.getDataPropertyDomainAxioms(newDataProperty);
        OWLClass newClass = dataFactory.getOWLClass(IRI.create(baseIRI + "#" + domainClassName));

        Iterator<OWLDataPropertyDomainAxiom> PropertyDomainAxiom_itr;
        if ((PropertyDomainAxiom_itr = allDomainAxioms.iterator()).hasNext()) {
            OWLDataPropertyDomainAxiom propertyDomainAxiom;
            Set<OWLClass> existingDomainClasses = (propertyDomainAxiom = PropertyDomainAxiom_itr.next()).getClassesInSignature();

            owlManager.removeAxiom(ontology, propertyDomainAxiom);
            existingDomainClasses.add(newClass);
            appendToDataPropertyDomainAxiom(existingDomainClasses, propertyDomainAxiom, newDataProperty);

            Set<OWLDataPropertyRangeAxiom> allRangeAxioms = ontology.getDataPropertyRangeAxioms(newDataProperty);
            Iterator<OWLDataPropertyRangeAxiom> PropertyRangeAxiom_itr;
            if ((PropertyRangeAxiom_itr = allRangeAxioms.iterator()).hasNext()) {
                OWLDataPropertyRangeAxiom propertyRangeAxiom;
                Set<OWLDatatype> existingDatatypes = (propertyRangeAxiom = PropertyRangeAxiom_itr.next()).getDatatypesInSignature();

                existingDatatypes.add(newDatatype);
                appendToDataPropertyRangeAxiom(existingDatatypes, propertyRangeAxiom, newDataProperty);
            }
            return;
        }

        addDataPropertyDomainAxiom(newDataProperty, dataFactory.getOWLClass(IRI.create(baseIRI + "#" + domainClassName)));
        addDataPropertyRangeAxiom(newDataProperty, newDatatype);
    }

    private void addDataPropertyDomainAxiom(OWLDataProperty owlDataProperty, OWLClass owlClass) {
        OWLAxiom newAxiom = dataFactory.getOWLDataPropertyDomainAxiom(owlDataProperty, owlClass);
        owlManager.addAxiom(ontology, newAxiom);
    }

    private void addObjectPropertyDomainAxiom(OWLObjectProperty owlObjectProperty, OWLClass owlClass) {
        OWLAxiom newAxiom = dataFactory.getOWLObjectPropertyDomainAxiom(owlObjectProperty, owlClass);
        owlManager.addAxiom(ontology, newAxiom);
    }

    private void addDataPropertyRangeAxiom(OWLDataProperty owlDataProperty, OWLDatatype dataType) {
        OWLAxiom PropertyRangeAxiom = dataFactory.getOWLDataPropertyRangeAxiom(owlDataProperty, dataType);
        owlManager.addAxiom(ontology, PropertyRangeAxiom);
    }

    private void addObjectPropertyRangeAxiom(OWLObjectProperty owlObjectProperty, OWLClass rangeClass) {

        OWLAxiom PropertyRangeAxiom = dataFactory.getOWLObjectPropertyRangeAxiom(owlObjectProperty, rangeClass);
        owlManager.addAxiom(ontology, PropertyRangeAxiom);
    }

    private void appendToDataPropertyDomainAxiom(Set<OWLClass> owlClasses, OWLDataPropertyDomainAxiom existingAxiom, OWLDataProperty owlDataProperty) {
        OWLClassExpression oe = dataFactory.getOWLObjectUnionOf(owlClasses);
        owlManager.removeAxiom(ontology, existingAxiom);
        existingAxiom = dataFactory.getOWLDataPropertyDomainAxiom(owlDataProperty, oe);
        owlManager.addAxiom(ontology, existingAxiom);
    }

    private void appendToObjectPropertyDomainAxiom(Set<OWLClass> owlClasses, OWLObjectPropertyDomainAxiom existingAxiom, OWLObjectProperty owlObjectProperty) {
        OWLClassExpression oe = dataFactory.getOWLObjectUnionOf(owlClasses);
        owlManager.removeAxiom(ontology, existingAxiom);
        existingAxiom = dataFactory.getOWLObjectPropertyDomainAxiom(owlObjectProperty, oe);
        owlManager.addAxiom(ontology, existingAxiom);
    }

    private void appendToDataPropertyRangeAxiom(Set<OWLDatatype> existingOwlDatatypes, OWLDataPropertyRangeAxiom existingAxiom, OWLDataProperty owlDataProperty) {
        OWLDataRange owlDataRange = dataFactory.getOWLDataUnionOf(existingOwlDatatypes);
        owlManager.removeAxiom(ontology, existingAxiom);
        existingAxiom = dataFactory.getOWLDataPropertyRangeAxiom(owlDataProperty, owlDataRange);
        owlManager.addAxiom(ontology, existingAxiom);
    }

    public void serialiseModel(org.fife.ui.rsyntaxtextarea.RSyntaxTextArea syntaxArea) throws OWLOntologyStorageException {
        OutputStream o = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                String s = String.valueOf((char) b);
                syntaxArea.append(s);
            }
        };
        syntaxArea.setText("");
        owlManager.saveOntology(ontology, new TurtleOntologyFormat(), o);
    }

    public void print() {
        File file = new File("C:\\Users\\John\\Documents\\NetBeans\\Projects\\Program\\output-files\\output.owl");
        IRI documentIRI2 = IRI.create(file);
        try {
            owlManager.saveOntology(ontology, new TurtleOntologyFormat(), documentIRI2);
        } catch (OWLOntologyStorageException ex) {
            Logger.getLogger(OWLOntBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void visit(RelationNode relation) {
    }

    @Override
    public void visit(NonKeyNode nonKeyNode) {
        String domainClassName = nonKeyNode.getParent().getValueAt(1).toString();
        String propertyName = nonKeyNode.getValueAt(1).toString();
        String datatypeName = nonKeyNode.getValueAt(3).toString();
        addDataProperty(propertyName, domainClassName, getVocabulary(datatypeName));
    }

    @Override
    public void visit(ForeignKeyNode foreignKey) {
        String domainClassName = foreignKey.getParent().getValueAt(1).toString();
        String propertyName = foreignKey.getValueAt(1).toString();
        String rangeClassName = foreignKey.getPropertyRange();
        addObjectProperty(domainClassName, propertyName, rangeClassName);
    }

    private XSDVocabulary getVocabulary(String vocabularyName) {
        switch (vocabularyName) {
            case "xsd:string": {
                return XSDVocabulary.STRING;
            }
            case "xsd:integer": {
                return XSDVocabulary.INTEGER;
            }
            case "xsd:decimal": {
                return XSDVocabulary.DECIMAL;
            }
            case "xsd:anyURI": {
                return XSDVocabulary.ANY_URI;
            }
            default: {
                return XSDVocabulary.ANY_TYPE;
            }
        }
    }

    @Override
    public void visit(Tuple tuple) {

    }

    @Override
    public void visit(SchemaNode data_schema) {
    }

    @Override
    public void visit(Key key) {
    }

    @Override
    public void visit(CombinedKeyNode combined_key) {
        String domainClassName = combined_key.getParent().getValueAt(1).toString();
        String propertyName = combined_key.getValueAt(1).toString();
        String rangeClassName = combined_key.getPropertyRange();
        addObjectProperty(domainClassName, propertyName, rangeClassName);
    }
}
