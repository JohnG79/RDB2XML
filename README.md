# RDB2XML
Tool for translating MySQL relational DB to XML + XSD.

In Progress 28/11/2015.

- Connects to MySQL relational database.
- Queries schema, relation, key & non key names and builds a simple object structure of 
  AbstractMutableTreeNodes and displayed as a tree in a JXTreeTable.
- A "document builder" object will negotiate the tree ("object structure") and construct a XSD. 
  this will be written to a RSyntaxTextArea. The user can inspect and edit this before it is 
  written to a file.
  
- A similar process will be followed for extracting actual data and presenting and writing XML to a file.
  It is not practical however to show the data in a JXTreeTable - Only the schema is illustrated
  in this way.
  


- By the end of November This readMe file will reference another repository of a working prototype of the
  data-tree and builder idea described above. The goal here is to finsih a first release.
  

This project is due by the end of 2015.
