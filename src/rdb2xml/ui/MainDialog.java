package rdb2xml.ui;

import control.Controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Font.BOLD;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import static org.fife.ui.rsyntaxtextarea.SyntaxConstants.SYNTAX_STYLE_XML;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.FontHighlighter;

public class MainDialog extends javax.swing.JFrame
{

    private Controller controller;

    private MainDialog()
    {
        initComponents();
    }

    public MainDialog( Controller controller )
    {
        this.controller = controller;
        initComponents();
        this.setMinimumSize( new Dimension( 850, 550 ) );
        showSchemaTab( false );
        fileMenu.setForeground( Color.LIGHT_GRAY );
        exportMenu.setForeground( Color.LIGHT_GRAY );
        this.getContentPane().setBackground( new Color( 102, 102, 102 ) );
        

    }

    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
		UIManager.put("TabbedPane.selected", new Color( 245, 245, 245 ));
        tabbedPane=new JTabbedPane();
        schemaTab = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        treeTable = new org.jdesktop.swingx.JXTreeTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        new_menuItem = new javax.swing.JMenuItem();
        import_menuItem = new javax.swing.JMenuItem();
        saveAs_menuItem = new javax.swing.JMenuItem();
        exportMenu = new javax.swing.JMenu();
        exportSchema_menuItem = new javax.swing.JMenuItem();
        exportData_menuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RDB to XML");
        setBackground(new java.awt.Color(204, 204, 204));
        setIconImage(new ImageIcon("C:\\Users\\johng\\Documents\\NetBeansProjects\\RDB2XML\\Resources\\xml-icon.png"));

        tabbedPane.setBackground(new java.awt.Color(204, 204, 204));
        tabbedPane.setForeground(new java.awt.Color(240, 240, 240));
        tabbedPane.setToolTipText("");
        tabbedPane.setAutoscrolls(true);
        tabbedPane.setFont(new java.awt.Font("Courier New", 1, 16)); // NOI18N

        schemaTab.setForeground(new java.awt.Color(240, 240, 240));

        jScrollPane2.setBackground(new java.awt.Color(102, 102, 102));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(800, 400));
        jScrollPane2.setViewportView(treeTable);

        javax.swing.GroupLayout schemaTabLayout = new javax.swing.GroupLayout(schemaTab);
        schemaTab.setLayout(schemaTabLayout);
        schemaTabLayout.setHorizontalGroup(
            schemaTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        schemaTabLayout.setVerticalGroup(
            schemaTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(schemaTabLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        tabbedPane.addTab("Schema", schemaTab);

        jMenuBar1.setBackground(new java.awt.Color(102, 102, 102));
        jMenuBar1.setForeground(new java.awt.Color(245, 245, 245));

        fileMenu.setBackground(new java.awt.Color(102, 102, 102));
        fileMenu.setForeground(new java.awt.Color(245, 245, 245));
        fileMenu.setText("File");
        fileMenu.addMenuListener(new javax.swing.event.MenuListener()
        {
            public void menuCanceled(javax.swing.event.MenuEvent evt)
            {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt)
            {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt)
            {
                fileMenuMenuSelected(evt);
            }
        });

        new_menuItem.setBackground(new java.awt.Color(102, 102, 102));
        new_menuItem.setForeground(new java.awt.Color(245, 245, 245));
        new_menuItem.setText("New");
        new_menuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                new_menuItemActionPerformed(evt);
            }
        });
        fileMenu.add(new_menuItem);

        import_menuItem.setBackground(new java.awt.Color(102, 102, 102));
        import_menuItem.setForeground(new java.awt.Color(245, 245, 245));
        import_menuItem.setText("Import");
        import_menuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                import_menuItemActionPerformed(evt);
            }
        });
        fileMenu.add(import_menuItem);

        saveAs_menuItem.setBackground(new java.awt.Color(102, 102, 102));
        saveAs_menuItem.setForeground(new java.awt.Color(245, 245, 245));
        saveAs_menuItem.setText("Save As");
        saveAs_menuItem.setEnabled(false);
        saveAs_menuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveAs_menuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAs_menuItem);

        jMenuBar1.add(fileMenu);

        exportMenu.setBackground(new java.awt.Color(102, 102, 102));
        exportMenu.setForeground(new java.awt.Color(245, 245, 245));
        exportMenu.setText("Export");
        exportMenu.setEnabled(false);
        exportMenu.addMenuListener(new javax.swing.event.MenuListener()
        {
            public void menuCanceled(javax.swing.event.MenuEvent evt)
            {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt)
            {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt)
            {
                exportMenuMenuSelected(evt);
            }
        });

        exportSchema_menuItem.setBackground(new java.awt.Color(102, 102, 102));
        exportSchema_menuItem.setForeground(new java.awt.Color(245, 245, 245));
        exportSchema_menuItem.setText("Schema");
        exportSchema_menuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                exportSchema_menuItemActionPerformed(evt);
            }
        });
        exportMenu.add(exportSchema_menuItem);

        exportData_menuItem.setBackground(new java.awt.Color(102, 102, 102));
        exportData_menuItem.setForeground(new java.awt.Color(245, 245, 245));
        exportData_menuItem.setText("Data");
        exportData_menuItem.setEnabled(false);
        exportData_menuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                exportData_menuItemActionPerformed(evt);
            }
        });
        exportMenu.add(exportData_menuItem);

        jMenuBar1.add(exportMenu);

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

    private void import_menuItemActionPerformed( java.awt.event.ActionEvent evt )
    {//GEN-FIRST:event_connectMenuItemActionPerformed

        controller.openConnectionDialog();
    }//GEN-LAST:event_connectMenuItemActionPerformed

    private void fileMenuMenuSelected( javax.swing.event.MenuEvent evt )//GEN-FIRST:event_fileMenuMenuSelected
    {//GEN-HEADEREND:event_fileMenuMenuSelected

        if ( tabbedPane.getTabCount() != 0 && tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() ) != null && !tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() ).equals( "schema" ) )
        {
            saveAs_menuItem.setEnabled( true );
        }
        else
        {
            saveAs_menuItem.setEnabled( false );
        }
    }//GEN-LAST:event_fileMenuMenuSelected

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

    private JLabel getSelectedTabJLabel()
    {
        return ( JLabel ) ( ( JPanel ) tabbedPane.getTabComponentAt( tabbedPane.getSelectedIndex() ) ).getComponents()[ 0 ];
    }

    private void saveAs_menuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_saveAs_menuItemActionPerformed
    {//GEN-HEADEREND:event_saveAs_menuItemActionPerformed

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle( "Save As" );
        JLabel jLabel = getSelectedTabJLabel();

        fileChooser.setSelectedFile( new File( jLabel.getText().trim() ) );

        fileChooser.addChoosableFileFilter( new FileNameExtensionFilter( "xml files (*.xml)", "xml" ) );
        fileChooser.addChoosableFileFilter( new FileNameExtensionFilter( "xml schema files (*.xsd)", "xsd" ) );

        if ( fileChooser.showSaveDialog( this ) == APPROVE_OPTION )
        {
            File file;

            JPanel jPanel_1 = ( JPanel ) tabbedPane.getSelectedComponent();
            JPanel jPanel_2 = ( JPanel ) jPanel_1.getComponents()[ 0 ];
            JScrollPane jScrollPane = ( JScrollPane ) jPanel_2.getComponents()[ 0 ];
            JViewport jViewport = ( JViewport ) jScrollPane.getComponents()[ 0 ];
            RSyntaxTextArea rSyntaxTextArea = ( RSyntaxTextArea ) jViewport.getComponents()[ 0 ];
            controller.save( rSyntaxTextArea, file = getSelectedFileWithExtension( fileChooser ) );
            getSelectedTabJLabel().setText( file.getName() );
        }
    }//GEN-LAST:event_saveAs_menuItemActionPerformed

    private void exportData_menuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_exportData_menuItemActionPerformed
    {//GEN-HEADEREND:event_exportData_menuItemActionPerformed

    }//GEN-LAST:event_exportData_menuItemActionPerformed

    private void exportSchema_menuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_exportSchema_menuItemActionPerformed
    {//GEN-HEADEREND:event_exportSchema_menuItemActionPerformed
        RSyntaxTextArea rSyntaxTextArea = addNewEditorTab();
        controller.exportSchema( rSyntaxTextArea );
        tabbedPane.setSelectedIndex( tabbedPane.getTabCount() - 1 );
    }//GEN-LAST:event_exportSchema_menuItemActionPerformed

    private void new_menuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_new_menuItemActionPerformed
    {//GEN-HEADEREND:event_new_menuItemActionPerformed
        addNewEditorTab();
        tabbedPane.setSelectedIndex( tabbedPane.getTabCount() - 1 );
    }//GEN-LAST:event_new_menuItemActionPerformed

    private void exportMenuMenuSelected(javax.swing.event.MenuEvent evt)//GEN-FIRST:event_exportMenuMenuSelected
    {//GEN-HEADEREND:event_exportMenuMenuSelected
        if ( tabbedPane.getTabCount() != 0 && tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() ).equals( "schema" ) )
        {
            exportSchema_menuItem.setEnabled( true );
        }
        else
        {
            exportSchema_menuItem.setEnabled( false );
        }
    }//GEN-LAST:event_exportMenuMenuSelected

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem exportData_menuItem;
    private javax.swing.JMenu exportMenu;
    private javax.swing.JMenuItem exportSchema_menuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem import_menuItem;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem new_menuItem;
    private javax.swing.JMenuItem saveAs_menuItem;
    private javax.swing.JPanel schemaTab;
    private javax.swing.JTabbedPane tabbedPane;
    private org.jdesktop.swingx.JXTreeTable treeTable;
    // End of variables declaration//GEN-END:variables

    private void setIconImage( ImageIcon imageIcon )
    {
        this.setIconImage( imageIcon.getImage() );
    }

    public void setSchema( JXTreeTable jXTreeTable )
    {
        Font font = new Font( "Courier new", BOLD, 16 );
        jScrollPane2.setViewportView( jXTreeTable );
        jXTreeTable.getTableHeader().setFont( font );
        jXTreeTable.setFont( font );
        jXTreeTable.getColumnExt( 0 ).setHighlighters( new FontHighlighter( font ) );
        exportMenu.setEnabled( true );
    }

    private RSyntaxTextArea addNewEditorTab()
    {
        int index = tabbedPane.getTabCount() - 1;
        Font font = new Font( "Courier new", BOLD, 16 );
        String newTabName = newUniqueTabName();
        int width = tabbedPane.getWidth();
        int height = tabbedPane.getHeight();

        JPanel outerJPanel = new javax.swing.JPanel();
        JPanel innerJPanel = new javax.swing.JPanel();
        JScrollPane innerJScrollPane = new JScrollPane();

        RSyntaxTextArea newXsdSyntaxArea = new RSyntaxTextArea();

        newXsdSyntaxArea.setSyntaxEditingStyle( SYNTAX_STYLE_XML );
        newXsdSyntaxArea.setCodeFoldingEnabled( true );
        newXsdSyntaxArea.setFont( font );

        innerJScrollPane.setViewportView( newXsdSyntaxArea );

        GroupLayout innerJPanelLayout = new GroupLayout( innerJPanel );
        innerJPanel.setLayout( innerJPanelLayout );
        innerJPanelLayout.setHorizontalGroup( innerJPanelLayout.createParallelGroup( LEADING )
                .addComponent( innerJScrollPane, 750, DEFAULT_SIZE, GetScreenWorkingWidth() )
        );
        innerJPanelLayout.setVerticalGroup( innerJPanelLayout.createParallelGroup( LEADING )
                .addComponent( innerJScrollPane, 350, DEFAULT_SIZE, GetScreenWorkingHeight() )
        );

        GroupLayout outerJPanelLayout = new GroupLayout( outerJPanel );
        outerJPanel.setLayout( outerJPanelLayout );
        outerJPanelLayout.setHorizontalGroup( outerJPanelLayout.createParallelGroup( LEADING )
                .addComponent( innerJPanel, 750, tabbedPane.getWidth(), GetScreenWorkingWidth() )
        );
        outerJPanelLayout.setVerticalGroup( outerJPanelLayout.createParallelGroup( LEADING )
                .addGroup( outerJPanelLayout.createSequentialGroup()
                        .addComponent( innerJPanel, 350, tabbedPane.getHeight(), GetScreenWorkingHeight() )
                        .addGap( 0, 0, 0 ) )
        );

        tabbedPane.addTab( newTabName, outerJPanel );
        tabbedPane.setTabComponentAt( tabbedPane.getTabCount() - 1, createNewTab( newTabName ) );
        tabbedPane.setBackground( new java.awt.Color( 204, 204, 204 ) );
        tabbedPane.getAccessibleContext().setAccessibleName( newTabName );
        tabbedPane.setPreferredSize( new Dimension( width, height ) );
        pack();
        newXsdSyntaxArea.setBackground( new java.awt.Color( 245, 245, 245 ) );
        
        
        return newXsdSyntaxArea;
    }
    
    private String newUniqueTabName()
    {
        int freeNumber;
        ArrayList<Integer> takenNumbers = new ArrayList<>();
        for ( int i = 0; i < tabbedPane.getTabCount(); i++ )
        {
            JLabel jLabel = ( JLabel ) ( ( JPanel ) tabbedPane.getTabComponentAt( i ) ).getComponents()[ 0 ];
            if ( jLabel.getText().contains( "new file " + i ) )
            {
                takenNumbers.add( i );
            }
            else
            {
                return "new file " + i;
            }
        }
        freeNumber = 0;
        while ( takenNumbers.contains( freeNumber ) )
        {
            freeNumber++;
        }
        return "new file " + freeNumber;
    }

    private JPanel createNewTab( String heading )
    {
        JPanel tab = new JPanel( new GridBagLayout() );
        
        tab.setOpaque( false );
        JLabel lblTitle = new JLabel( heading + "   " );
        JButton tabCloseButton = new JButton( "x" );
        tabCloseButton.setMargin( new java.awt.Insets( 0, 0, 0, 0 ) );
        tabCloseButton.setMaximumSize( new Dimension( 1, 1 ) );
        tabCloseButton.setBorder( BorderFactory.createEmptyBorder( 1, 1, 1, 1 ) );
        tabCloseButton.setForeground( Color.RED );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        tab.add( lblTitle, gbc );

        gbc.gridx++;
        gbc.weightx = 0;
        tab.add( tabCloseButton, gbc );

        tabCloseButton.addMouseListener( new MouseListener()
        {
            @Override
            public void mouseClicked( MouseEvent e )
            {
                tabbedPane.remove( tabbedPane.indexOfTabComponent( tabCloseButton.getParent() ) );
            }

            @Override
            public void mousePressed( MouseEvent e )
            {
            }

            @Override
            public void mouseReleased( MouseEvent e )
            {
            }

            @Override
            public void mouseEntered( MouseEvent e )
            {
            }

            @Override
            public void mouseExited( MouseEvent e )
            {
            }
        } );
		tab.setBackground(Color.RED);
        return tab;
    }

    public static int GetScreenWorkingWidth()
    {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    }

    public static int GetScreenWorkingHeight()
    {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    }

    public void showSchemaTab( boolean add )
    {
        if ( !add )
        {
            tabbedPane.remove( schemaTab );
        }
        else
        {
            tabbedPane.addTab( "schema", schemaTab );
            int index;
            tabbedPane.setTabComponentAt( index = tabbedPane.getTabCount() - 1, createNewTab( "schema" ) );
            tabbedPane.setSelectedIndex( index );            
        }
    }


}
