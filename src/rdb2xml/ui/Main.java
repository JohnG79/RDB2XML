package rdb2xml.ui;

import control.Controller;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.FontHighlighter;

public class Main extends javax.swing.JFrame
{

    private Controller controller;

    private Main()
    {
        initComponents();
    }

    public Main( Controller controller )
    {
        this.controller = controller;
        initComponents();
    }

    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        tabbedPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jXTreeTable1 = new org.jdesktop.swingx.JXTreeTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        connectMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        schemaExportMenuItem = new javax.swing.JMenuItem();
        dataMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RDB to XML");
        setIconImage(new ImageIcon("C:\\Users\\johng\\Documents\\NetBeansProjects\\RDB2XML\\Resources\\xml-icon.png"));

        tabbedPane.setToolTipText("");
        tabbedPane.setAutoscrolls(true);
        tabbedPane.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jPanel1.setForeground(new java.awt.Color(240, 240, 240));

        jScrollPane2.setViewportView(jXTreeTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        tabbedPane.addTab("Database", jPanel1);

        jMenu1.setText("File");
        jMenu1.addMenuListener(new javax.swing.event.MenuListener()
        {
            public void menuCanceled(javax.swing.event.MenuEvent evt)
            {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt)
            {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt)
            {
                jMenu1MenuSelected(evt);
            }
        });

        jMenuItem1.setText("New");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        connectMenuItem.setText("Import");
        connectMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                connectMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(connectMenuItem);

        saveAsMenuItem.setText("Save As");
        saveAsMenuItem.setEnabled(false);
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveAsMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(saveAsMenuItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Export");
        jMenu2.setEnabled(false);
        jMenu2.addMenuListener(new javax.swing.event.MenuListener()
        {
            public void menuCanceled(javax.swing.event.MenuEvent evt)
            {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt)
            {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt)
            {
                jMenu2MenuSelected(evt);
            }
        });

        schemaExportMenuItem.setText("Schema");
        schemaExportMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                schemaExportMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(schemaExportMenuItem);

        dataMenuItem.setText("Data");
        dataMenuItem.setEnabled(false);
        dataMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                dataMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(dataMenuItem);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        tabbedPane.getAccessibleContext().setAccessibleName("tabbedPane");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connectMenuItemActionPerformed( java.awt.event.ActionEvent evt )
    {//GEN-FIRST:event_connectMenuItemActionPerformed
        controller.openConnectionDialog();
        tabbedPane.setSelectedIndex( 0 );
    }//GEN-LAST:event_connectMenuItemActionPerformed

    private void jMenu1MenuSelected( javax.swing.event.MenuEvent evt )//GEN-FIRST:event_jMenu1MenuSelected
    {//GEN-HEADEREND:event_jMenu1MenuSelected
        if ( !tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() ).equals( "Database" ) )
        {
            saveAsMenuItem.setEnabled( true );
        }
        else
        {
            saveAsMenuItem.setEnabled( false );
        }
    }//GEN-LAST:event_jMenu1MenuSelected

    private static File getSelectedFileWithExtension( JFileChooser c )
    {
        File file = c.getSelectedFile();
        if ( c.getFileFilter() instanceof FileNameExtensionFilter )
        {
            String[] exts = ( ( FileNameExtensionFilter ) c.getFileFilter() ).getExtensions();
            String nameLower = file.getName().toLowerCase();
            for ( String ext : exts )
            {
                if ( nameLower.endsWith( '.' + ext.toLowerCase() ) )
                {
                    return file;
                }
            }
            file = new File( file.toString() + '.' + exts[ 0 ] );
        }
        return file;
    }

    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_saveAsMenuItemActionPerformed
    {//GEN-HEADEREND:event_saveAsMenuItemActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile( new File( "new file" ) );

        fileChooser.addChoosableFileFilter( new FileNameExtensionFilter( "xml files (*.xml)", "xml" ) );
        fileChooser.addChoosableFileFilter( new FileNameExtensionFilter( "xml schema files (*.xsd)", "xsd" ) );

        if ( fileChooser.showSaveDialog( this ) == JFileChooser.APPROVE_OPTION )
        {
            File file;

            JPanel jPanel_1 = ( JPanel ) tabbedPane.getSelectedComponent();
            JPanel jPanel_2 = ( JPanel ) jPanel_1.getComponents()[ 0 ];
            JScrollPane jScrollPane = ( JScrollPane ) jPanel_2.getComponents()[ 0 ];
            JViewport jViewport = ( JViewport ) jScrollPane.getComponents()[ 0 ];
            RSyntaxTextArea rSyntaxTextArea = ( RSyntaxTextArea ) jViewport.getComponents()[ 0 ];

            System.out.println( rSyntaxTextArea );

            controller.save( rSyntaxTextArea, file = getSelectedFileWithExtension( fileChooser ) );
            tabbedPane.setTitleAt( tabbedPane.getSelectedIndex(), file.getName() );
        }
    }//GEN-LAST:event_saveAsMenuItemActionPerformed

    private void dataMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_dataMenuItemActionPerformed
    {//GEN-HEADEREND:event_dataMenuItemActionPerformed

    }//GEN-LAST:event_dataMenuItemActionPerformed

    private void schemaExportMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_schemaExportMenuItemActionPerformed
    {//GEN-HEADEREND:event_schemaExportMenuItemActionPerformed
        RSyntaxTextArea rSyntaxTextArea = newTab();
        controller.exportSchema( rSyntaxTextArea );
        tabbedPane.setSelectedIndex( tabbedPane.getTabCount() - 1 );
    }//GEN-LAST:event_schemaExportMenuItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
        newTab();
        tabbedPane.setSelectedIndex( tabbedPane.getTabCount() - 1 );
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenu2MenuSelected(javax.swing.event.MenuEvent evt)//GEN-FIRST:event_jMenu2MenuSelected
    {//GEN-HEADEREND:event_jMenu2MenuSelected
        if ( tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() ).equals( "Database" ) )
        {
            schemaExportMenuItem.setEnabled( true );
        }
        else
        {
            schemaExportMenuItem.setEnabled( false );
        }
    }//GEN-LAST:event_jMenu2MenuSelected

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem connectMenuItem;
    private javax.swing.JMenuItem dataMenuItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXTreeTable jXTreeTable1;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem schemaExportMenuItem;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables

    private void setIconImage( ImageIcon imageIcon )
    {
        this.setIconImage( imageIcon.getImage() );
    }

    public void setSchema( JXTreeTable jXTreeTable )
    {
        jScrollPane2.setViewportView( jXTreeTable );
        jXTreeTable.getTableHeader().setFont( new Font( "Courier new", Font.BOLD, 16 ) );
        jXTreeTable.setFont( new Font( "Courier new", Font.PLAIN, 16 ) );
        jXTreeTable.getColumnExt( 0 ).setHighlighters( new FontHighlighter( new Font( "Courier new", Font.BOLD, 16 ) ) );
        jMenu2.setEnabled( true );
    }

    private RSyntaxTextArea newTab()
    {
        JPanel outerJPanel = new javax.swing.JPanel();

        JPanel innerJPanel = new javax.swing.JPanel();
        JScrollPane innerJScrollPane = new JScrollPane();

        RSyntaxTextArea newXsdSyntaxArea = new RSyntaxTextArea();

        newXsdSyntaxArea.setColumns( 20 );
        newXsdSyntaxArea.setRows( 5 );
        newXsdSyntaxArea.setSyntaxEditingStyle( SyntaxConstants.SYNTAX_STYLE_XML );
        newXsdSyntaxArea.setCodeFoldingEnabled( true );
        newXsdSyntaxArea.setFont( new Font( "Courier new", Font.PLAIN, 16 ) );

        innerJScrollPane.setViewportView( newXsdSyntaxArea );

        GroupLayout innerJPanelLayout = new GroupLayout( innerJPanel );
        innerJPanel.setLayout( innerJPanelLayout );
        innerJPanelLayout.setHorizontalGroup(
                innerJPanelLayout.createParallelGroup( Alignment.LEADING )
                .addComponent( innerJScrollPane, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE )
        );
        innerJPanelLayout.setVerticalGroup(
                innerJPanelLayout.createParallelGroup( Alignment.LEADING )
                .addComponent( innerJScrollPane, GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE )
        );

        GroupLayout outerJPanelLayout = new GroupLayout( outerJPanel );
        outerJPanel.setLayout( outerJPanelLayout );
        outerJPanelLayout.setHorizontalGroup(
                outerJPanelLayout.createParallelGroup( Alignment.LEADING )
                .addComponent( innerJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
        );
        outerJPanelLayout.setVerticalGroup(
                outerJPanelLayout.createParallelGroup( Alignment.LEADING )
                .addGroup( outerJPanelLayout.createSequentialGroup()
                        .addComponent( innerJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
                        .addGap( 0, 0, 0 ) )
        );
        tabbedPane.addTab( "new file", outerJPanel );
        tabbedPane.getAccessibleContext().setAccessibleName( "tabbedPane" );
        pack();

        return newXsdSyntaxArea;
    }
}
