package control;

import Processor.XMLDOMBuilder;
import extraction.DataImporter;
import java.util.List;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import rdb2xml.ui.tree.node.RelationNode;
import rdb2xml.ui.tree.node.SchemaNode;
import rdb2xml.ui.tree.node.Tuple;

public class DataController extends ImportController {

    private final SchemaNode schemaNode;
    private final RSyntaxTextArea syntaxTextArea;

    public DataController(SchemaNode schemaNode, RSyntaxTextArea syntaxTextArea) {
        super();
        this.schemaNode = schemaNode;
        this.syntaxTextArea = syntaxTextArea;
    }

    @Override
    public void run() {
        XMLDOMBuilder dataBuilder = new XMLDOMBuilder();
        schemaNode.acceptProcessor(dataBuilder);
        DataImporter dataImporter = new DataImporter(sqlConnection);

        List< RelationNode> relationNodes = schemaNode.getRelations();
        for (RelationNode relationNode : relationNodes) {
            dataImporter.importData(relationNode);
            relationNode.acceptProcessor(dataBuilder);
            for (Tuple tuple : relationNode.getTuples()) {
                tuple.acceptProcessor(dataBuilder);
            }
            relationNode.removeTuples();
        }
        dataBuilder.serialise(syntaxTextArea);
        this.stop();
    }
}
