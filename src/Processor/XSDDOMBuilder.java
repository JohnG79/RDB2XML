package Processor;

import extraction.AttributeItem;
import static extraction.AttributeItem.ATTRIBUTE_NAME;
import static extraction.AttributeItem.PARENT_RELATION_NAME;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import static org.w3c.dom.bootstrap.DOMImplementationRegistry.newInstance;
import rdb2xml.ui.SyntaxAreaOutputStream;
import rdb2xml.ui.tree.node.Attribute;
import rdb2xml.ui.tree.node.CombinedKeyNode;
import rdb2xml.ui.tree.node.ForeignKeyNode;
import rdb2xml.ui.tree.node.Key;
import rdb2xml.ui.tree.node.NonKeyNode;
import rdb2xml.ui.tree.node.PrimaryKeyNode;
import rdb2xml.ui.tree.node.RelationNode;
import rdb2xml.ui.tree.node.SchemaNode;
import rdb2xml.ui.tree.node.Tuple;

public class XSDDOMBuilder implements Processor {

    private DOMImplementation dom_implementation;
    private Document document;
    private Element root_element;
    private Element constraints_root;
    private Element relation_declarations_root;
    private Element current_attributes_root;

    private final String xsd_namespace_prefix = "xsd";

    private final String targetNamespace_prefix;
    private final String targetNamespace_prefix_declation;
    private final String targetNamespace;

    private final String prefixed_key_tag_name = "xsd:key";
    private final String prefixed_attribute_tag_name = "xsd:attribute";

    public XSDDOMBuilder() {
        targetNamespace_prefix = "un";
        targetNamespace_prefix_declation = "xmlns:un";
        targetNamespace = "http://www.example.com/university";

        try {
            dom_implementation = newInstance().getDOMImplementation("XML 1.0 Traversal +Events 2.0");
        } catch (DOMException e) {
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException ex) {
        }

    }

    @Override
    public void visit(Tuple tuple) {
    }

    private Element get_xsd_key_element(Element dom_element, String _constraintName) {
        NodeList child_nodes = dom_element.getChildNodes();
        for (int i = 0; i < child_nodes.getLength(); i++) {
            String node_name = child_nodes.item(i).getNodeName();
            if (node_name.equals(prefixed_key_tag_name)) {
                NamedNodeMap attributes = child_nodes.item(i).getAttributes();
                String attribute_name = "name";
                if (attributes.getNamedItem(attribute_name).getNodeValue().equals(_constraintName)) {
                    return (Element) child_nodes.item(i);
                }
            }
        }
        return null;
    }

    private Element add_xsd_element_element(Element parent_element, String name, String minOccurs_value, String maxOccurs_value) {
        Element element_element = add_xsd_element_element(parent_element, name);
        element_element.setAttribute("minOccurs", minOccurs_value);
        element_element.setAttribute("maxOccurs", maxOccurs_value);
        return element_element;
    }

    private Element add_xsd_element_element(Element parent_element, String name, String type) {
        Element element_element = add_xsd_element_element(parent_element, name);
        element_element.setAttribute("type", type);
        return element_element;
    }

    private Element add_xsd_element_element(Element parent_element, String name) {
        Element element_element = add_xsd_element_element(parent_element);
        element_element.setAttribute("name", name);
        return element_element;
    }

    private Element add_xsd_element_element(Element parent_element) {
        String prefixed_element_tag_name = "xsd:element";
        Element element_element = add_element(parent_element, prefixed_element_tag_name);
        parent_element.appendChild(element_element);
        return element_element;
    }

    private Element add_xsd_complexType_element(Element parent_element, String name) {
        Element complexType_element = add_xsd_complexType_element(parent_element);
        complexType_element.setAttribute("name", name);
        return complexType_element;
    }

    private Element add_xsd_complexType_element(Element parent_element) {
        String prefixed_complexType_tag_name = "xsd:complexType";
        Element complexType_element = add_element(parent_element, prefixed_complexType_tag_name);
        parent_element.appendChild(complexType_element);
        return complexType_element;
    }

    private Element add_xsd_sequence_element(Element parent_element, String minOccurs_value, String maxOccurs_value) {
        Element sequence_element = add_xsd_sequence_element(parent_element);
        sequence_element.setAttribute("minOccurs", minOccurs_value);
        sequence_element.setAttribute("maxOccurs", maxOccurs_value);
        return sequence_element;
    }

    private Element add_xsd_sequence_element(Element parent_element) {
        String prefixed_sequence_tag_name = "xsd:sequence";
        Element sequence_element = add_element(parent_element, prefixed_sequence_tag_name);
        parent_element.appendChild(sequence_element);
        return sequence_element;
    }

    private Element add_xsd_key_element(Element parent_element, String name) {
        Element key_element = add_xsd_key_element(parent_element);
        key_element.setAttribute("name", name);
        return key_element;
    }

    private Element add_xsd_key_element(Element parent_element) {
        Element key_element = add_element(parent_element, prefixed_key_tag_name);
        parent_element.appendChild(key_element);
        return key_element;
    }

    private Element add_xsd_keyref_element(Element parent_element, String name, String _refer) {
        Element keyref_element = add_xsd_keyref_element(parent_element, name);
        keyref_element.setAttribute("refer", targetNamespace_prefix + ":" + _refer);
        return keyref_element;
    }

    private Element add_xsd_keyref_element(Element parent_element, String name) {
        Element keyref_element = add_xsd_keyref_element(parent_element);
        keyref_element.setAttribute("name", name);
        return keyref_element;
    }

    private Element add_xsd_keyref_element(Element parent_element) {
        String prefixed_keyref_tag_name = "xsd:keyref";
        Element keyref_element = add_element(parent_element, prefixed_keyref_tag_name);
        parent_element.appendChild(keyref_element);
        return keyref_element;
    }

    private Element add_element(Element parent_element, String _tag_name) {
        Element element = document.createElement(_tag_name);
        parent_element.appendChild(element);
        return element;
    }

    private Element add_xsd_selector_element(Element parent_element, String xpath) {
        Element selector_element = add_xsd_selector_element(parent_element);
        selector_element.setAttribute("xpath", xpath);
        return selector_element;
    }

    private Element add_xsd_selector_element(Element parent_element) {
        String prefixed_selector_tag_name = "xsd:selector";
        Element selector_element = add_element(parent_element, prefixed_selector_tag_name);
        parent_element.appendChild(selector_element);
        return selector_element;
    }

    private Element add_xsd_field_element(Element parent_element, String xpath) {
        Element field_element = add_xsd_field_element(parent_element);
        field_element.setAttribute("xpath", xpath);
        return field_element;
    }

    private Element add_xsd_field_element(Element parent_element) {
        String prefixed_field_tag_name = "xsd:field";
        Element field_element = add_element(parent_element, prefixed_field_tag_name);
        parent_element.appendChild(field_element);
        return field_element;
    }

    private Element add_xsd_all_element(Element parent_element) {
        String prefixed_all_tag_name = "xsd:all";
        Element all_element = add_element(parent_element, prefixed_all_tag_name);
        parent_element.appendChild(all_element);
        return all_element;
    }

    private Element add_xsd_attribute_element(Element parent_element, String name, String type, String use) {
        Element attribute_element = add_xsd_attribute_element(parent_element, name, type);
        attribute_element.setAttribute("use", use);
        parent_element.appendChild(attribute_element);
        return attribute_element;
    }

    private Element add_xsd_attribute_element(Element parent_element, String name, String type) {
        Element attribute_element = add_element(parent_element, prefixed_attribute_tag_name);
        attribute_element.setAttribute("name", name);
        attribute_element.setAttribute("type", type);
        parent_element.appendChild(attribute_element);
        return attribute_element;
    }

    void add_relation_declaration(String relation_name) {
        String schema_name = targetNamespace_prefix + ":" + relation_name + "_schema";
        add_xsd_element_element(relation_declarations_root, relation_name + "_relation", schema_name);
    }

    void build_relation_schema_definition(String relation_name) {
        String schema_name = relation_name + "_schema";

        Element complexType_element1 = add_xsd_complexType_element(root_element, schema_name);
        Element sequence_element = add_xsd_sequence_element(complexType_element1);
        Element element_element = add_xsd_element_element(sequence_element, relation_name, "0", "unbounded");
        Element complexType_element2 = add_xsd_complexType_element(element_element);
        current_attributes_root = add_xsd_all_element(complexType_element2);
    }

    public void serialise(RSyntaxTextArea syntaxTextArea) {
        RSyntaxTextArea r = new RSyntaxTextArea();

        try {
            OutputFormat format = new OutputFormat(document);
            format.setIndenting(true);

            XMLSerializer serializer = new XMLSerializer(
                    (new SyntaxAreaOutputStream(syntaxTextArea)), format);

            serializer.serialize(document);
        } catch (IOException ioe) {
        }
    }

    public void print(File file) {
        try {
            OutputFormat format = new OutputFormat(document);
            format.setIndenting(true);

            XMLSerializer serializer = new XMLSerializer(
                    new FileOutputStream(file), format);

            serializer.serialize(document);
        } catch (IOException ioe) {
        }
    }

    @Override
    public void visit(SchemaNode schemaNode) {
        String prefixed_schema_tag_name = "xsd:schema";
        String xsd_namespace = "http://www.w3.org/2001/XMLSchema";

        document = dom_implementation.createDocument(xsd_namespace, prefixed_schema_tag_name, null);

        root_element = document.getDocumentElement();
        root_element.setAttribute("targetNamespace", targetNamespace);

        String xsd_namespace_prefix_declation = "xmlns:xsd";

        root_element.setAttribute(xsd_namespace_prefix_declation, xsd_namespace);
        root_element.setAttribute(targetNamespace_prefix_declation,
                targetNamespace);

        constraints_root = add_xsd_element_element(root_element, schemaNode.getName());
        relation_declarations_root = add_xsd_sequence_element(add_xsd_complexType_element(constraints_root), "1", "1");

    }

    @Override
    public void visit(RelationNode relationNode) {
        String relation_schema_name = relationNode.getName();
        add_relation_declaration(relation_schema_name);
        build_relation_schema_definition(relation_schema_name);
    }

    @Override
    public void visit(NonKeyNode visiting_non_key) {
        String attribute_name = visiting_non_key.getName();
        add_xsd_element_element(current_attributes_root, attribute_name, visiting_non_key.getDatatype().toString());
    }

    @Override
    public void visit(Key key) {
        String keyName = ((PrimaryKeyNode) key).getName();
        add_xsd_attribute_element((Element) current_attributes_root.getParentNode(), keyName, ((PrimaryKeyNode) key).getDatatype().toString(), "required");

        String constraintName = key.getKeyConstraint().toString();
        String parentRelation = key.getParent().getName();
        String xpath = "./" + parentRelation + "_relation/" + parentRelation;
        String constraintName_;
        int index = constraintName.indexOf('_');
        if (index == -1) {
            index = constraintName.length();
        }
        constraintName_ = constraintName.substring(0, index);
        Element key_element = get_xsd_key_element(constraints_root, constraintName_);
        if (key_element == null) {
            key_element = add_xsd_key_element(constraints_root, constraintName_);
            add_xsd_selector_element(key_element, xpath);
        }

        add_xsd_field_element(key_element, "@" + keyName);
    }

    @Override
    public void visit(ForeignKeyNode foreignKeyNode) {
        String foreign_key_name;
        String attribute_name = foreignKeyNode.getName();
        add_xsd_element_element(current_attributes_root, attribute_name, foreignKeyNode.getDatatype().toString());
        foreign_key_name = foreignKeyNode.getName();

        String constraint_name = foreignKeyNode.getKeyConstraint().toString();
        PrimaryKeyNode referenced_primary_key = (PrimaryKeyNode) foreignKeyNode.getReferencedKey();
        String referenced_constraint_name = referenced_primary_key.getKeyConstraint().toString();
        String parent_relation_schema = foreignKeyNode.getParent().getName();
        String xpath = "./" + parent_relation_schema + "_relation/" + parent_relation_schema;
        Element keyref_element = add_xsd_keyref_element(constraints_root, constraint_name, referenced_constraint_name);
        add_xsd_selector_element(keyref_element, xpath);
        add_xsd_field_element(keyref_element, foreign_key_name);
    }

    @Override
    public void visit(CombinedKeyNode combinedKeyNode) {
        String foreign_key_name;

        String attribute_name = combinedKeyNode.getName();
        add_xsd_attribute_element((Element) current_attributes_root.getParentNode(), attribute_name, combinedKeyNode.getDatatype().toString());
        foreign_key_name = "@" + combinedKeyNode.getName();

        String constraintName = combinedKeyNode.getKeyConstraint().toString();
        PrimaryKeyNode referenced_primary_key = (PrimaryKeyNode) combinedKeyNode.getReferencedKey();
        String referenced_constraintName = referenced_primary_key.getKeyConstraint().toString();
        String parent_relation_schema = combinedKeyNode.getParent().getName();
        String xpath = "./" + parent_relation_schema + "_relation/" + parent_relation_schema;
        Element keyref_element = add_xsd_keyref_element(constraints_root, constraintName, referenced_constraintName);
        add_xsd_selector_element(keyref_element, xpath);
        add_xsd_field_element(keyref_element, foreign_key_name);

        HashMap<AttributeItem, String> attributeItems = new HashMap<>();
        attributeItems.put(ATTRIBUTE_NAME, combinedKeyNode.getName());
        attributeItems.put(PARENT_RELATION_NAME, combinedKeyNode.getParent().getName());

        PrimaryKeyNode key = new PrimaryKeyNode(attributeItems, combinedKeyNode.getDatatype());
        key.setParent(combinedKeyNode.getParent());

        String keyName = key.getName();

        constraintName = key.getKeyConstraint().toString();
        String parentRelation = key.getParent().getName();
        xpath = "./" + parentRelation + "_relation/" + parentRelation;
        String constraintName_;
        int index = constraintName.indexOf('_');
        if (index == -1) {
            index = constraintName.length();
        }
        constraintName_ = constraintName.substring(0, index);
        Element key_element = get_xsd_key_element(constraints_root, constraintName_);
        if (key_element == null) {
            key_element = add_xsd_key_element(constraints_root, constraintName_);
            add_xsd_selector_element(key_element, xpath);
        }

        add_xsd_field_element(key_element, "@" + keyName);

    }
}
