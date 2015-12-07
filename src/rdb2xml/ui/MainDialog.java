package rdb2xml.ui;

import control.Controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import javax.swing.*;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import static org.fife.ui.rsyntaxtextarea.SyntaxConstants.SYNTAX_STYLE_XML;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.FontHighlighter;
import static rdb2xml.ui.DialogHelper.GetScreenWorkingHeight;
import static rdb2xml.ui.DialogHelper.GetScreenWorkingWidth;

public final class MainDialog extends javax.swing.JFrame
{

    private final Controller controller;
    private final Color darkGray = new Color( 102, 102, 102 );
    private final Color lightGray = new Color( 245, 245, 245 );

    private MainDialog()
    {
        controller = null;
    }

    public MainDialog( Controller controller )
    {
        this.controller = controller;
        initComponents();
        showSchemaTab( false );

        this.getContentPane().setBackground( darkGray );
        jMenuBar1.setBackground( darkGray );
        jMenuBar1.setForeground( lightGray );
        fileMenu.setBackground( darkGray );
        fileMenu.setForeground( lightGray );
        new_menuItem.setBackground( darkGray );
        new_menuItem.setForeground( lightGray );
        jMenuItem1.setBackground( darkGray );
        jMenuItem1.setForeground( lightGray );
        import_menuItem.setBackground( darkGray );
        import_menuItem.setForeground( lightGray );
        saveAs_menuItem.setBackground( darkGray );
        saveAs_menuItem.setForeground( lightGray );
        exportMenu.setBackground( darkGray );
        exportMenu.setForeground( lightGray );
        exportSchema_menuItem.setBackground( darkGray );
        exportSchema_menuItem.setForeground( lightGray );
        exportData_menuItem.setBackground( darkGray );
        exportData_menuItem.setForeground( lightGray );
    }

    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        tabbedPane = new javax.swing.JTabbedPane();
        schemaTab = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        treeTable = new org.jdesktop.swingx.JXTreeTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        new_menuItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        import_menuItem = new javax.swing.JMenuItem();
        saveAs_menuItem = new javax.swing.JMenuItem();
        exportMenu = new javax.swing.JMenu();
        exportSchema_menuItem = new javax.swing.JMenuItem();
        exportData_menuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RDB to XML");

        setIconImage(new ImageIcon("C:\\Users\\johng\\Documents\\NetBeansProjects\\RDB2XML\\Resources\\xml-icon.png"));

        tabbedPane.setToolTipText("");
        tabbedPane.setAutoscrolls(true);
        tabbedPane.setFont(new Font("Courier New", 1, 16)); // NOI18N

        jScrollPane2.setPreferredSize(new Dimension(800, 400));
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


        new_menuItem.setText("New");
        new_menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                new_menuItemActionPerformed(evt);
            }
        });
        fileMenu.add(new_menuItem);


        jMenuItem1.setText("Open");
        jMenuItem1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                jMenuItem1ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem1);


        import_menuItem.setText("Import");
        import_menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                import_menuItemActionPerformed(evt);
            }
        });
        fileMenu.add(import_menuItem);


        saveAs_menuItem.setText("Save As");
        saveAs_menuItem.setEnabled(false);
        saveAs_menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                saveAs_menuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAs_menuItem);

        jMenuBar1.add(fileMenu);


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

        exportSchema_menuItem.setText("Schema");
        exportSchema_menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                exportSchema_menuItemActionPerformed(evt);
            }
        });
        exportMenu.add(exportSchema_menuItem);

        exportData_menuItem.setText("Data");
        exportData_menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
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

    private void import_menuItemActionPerformed( ActionEvent evt )
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

    private void saveAs_menuItemActionPerformed(ActionEvent evt)//GEN-FIRST:event_saveAs_menuItemActionPerformed
    {//GEN-HEADEREND:event_saveAs_menuItemActionPerformed

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle( "Save As" );
        JLabel jLabel = ( JLabel ) ( ( JPanel ) tabbedPane.getTabComponentAt( tabbedPane.getSelectedIndex() ) ).getComponents()[ 0 ];

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
            ( ( JLabel ) ( ( JPanel ) tabbedPane.getTabComponentAt( tabbedPane.getSelectedIndex() ) ).getComponents()[ 0 ] ).setText( file.getName() );
        }
    }//GEN-LAST:event_saveAs_menuItemActionPerformed

    private void exportData_menuItemActionPerformed(ActionEvent evt)//GEN-FIRST:event_exportData_menuItemActionPerformed
    {//GEN-HEADEREND:event_exportData_menuItemActionPerformed
        RSyntaxTextArea rSyntaxTextArea = addNewSyntaxArea( null );
        controller.exportData( rSyntaxTextArea );
        tabbedPane.setSelectedIndex( tabbedPane.getTabCount() - 1 );
    }//GEN-LAST:event_exportData_menuItemActionPerformed

    private void exportSchema_menuItemActionPerformed(ActionEvent evt)//GEN-FIRST:event_exportSchema_menuItemActionPerformed
    {//GEN-HEADEREND:event_exportSchema_menuItemActionPerformed
        RSyntaxTextArea rSyntaxTextArea = addNewSyntaxArea( null );
        controller.exportSchema( rSyntaxTextArea );
        tabbedPane.setSelectedIndex( tabbedPane.getTabCount() - 1 );
    }//GEN-LAST:event_exportSchema_menuItemActionPerformed

    private void new_menuItemActionPerformed(ActionEvent evt)//GEN-FIRST:event_new_menuItemActionPerformed
    {//GEN-HEADEREND:event_new_menuItemActionPerformed
        addNewSyntaxArea( null );
        tabbedPane.setSelectedIndex( tabbedPane.getTabCount() - 1 );
    }//GEN-LAST:event_new_menuItemActionPerformed

    private void exportMenuMenuSelected(javax.swing.event.MenuEvent evt)//GEN-FIRST:event_exportMenuMenuSelected
    {//GEN-HEADEREND:event_exportMenuMenuSelected
        if ( tabbedPane.getTabCount() != 0 && tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() ).equals( "schema" ) )
        {
            exportSchema_menuItem.setEnabled( true );
            exportData_menuItem.setEnabled( true );
        }
        else
        {
            exportSchema_menuItem.setEnabled( false );
            exportData_menuItem.setEnabled( false );
        }
    }//GEN-LAST:event_exportMenuMenuSelected

    private void jMenuItem1ActionPerformed(ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.addChoosableFileFilter( new FileNameExtensionFilter( "xml files (*.xml)", "xml" ) );
        jFileChooser.addChoosableFileFilter( new FileNameExtensionFilter( "xml schema files (*.xsd)", "xsd" ) );
        StringBuffer buffer;
        buffer = new StringBuffer();

        int result = jFileChooser.showOpenDialog( this );

        if ( result == APPROVE_OPTION )
        {
            try
            {
                FileReader reader;
                File file = jFileChooser.getSelectedFile();
                reader = new FileReader( file );
                int i = 1;
                while ( i != -1 )
                {
                    i = reader.read();
                    char ch = ( char ) i;
                    buffer.append( ch );
                }

                addNewSyntaxArea( file.getName() ).setText( buffer.toString() );
            }
            catch ( IOException ioe )
            {

            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem exportData_menuItem;
    private javax.swing.JMenu exportMenu;
    private javax.swing.JMenuItem exportSchema_menuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem import_menuItem;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
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
        Font font = new Font( "Courier new", Font.BOLD, 16 );
        jScrollPane2.setViewportView( jXTreeTable );
        jXTreeTable.getTableHeader().setFont( font );
        jXTreeTable.setFont( font );
        jXTreeTable.getColumnExt( 0 ).setHighlighters( new FontHighlighter( font ) );
        exportMenu.setEnabled( true );
    }

    private RSyntaxTextArea addNewSyntaxArea( String name )
    {
        Font font = new Font( "Courier new", Font.BOLD, 16 );
        String newTabName = name == null ? createUniqueTabName() : name;
        int width = tabbedPane.getWidth();
        int height = tabbedPane.getHeight();

        JPanel outerJPanel = new javax.swing.JPanel();
        JPanel innerJPanel = new javax.swing.JPanel();
        JScrollPane innerJScrollPane = new JScrollPane();

        RSyntaxTextArea newXsdSyntaxArea = new RSyntaxTextArea();
        newXsdSyntaxArea.setBackground( new Color( 245, 245, 245 ) );
        newXsdSyntaxArea.setSyntaxEditingStyle( SYNTAX_STYLE_XML );
        newXsdSyntaxArea.setCodeFoldingEnabled( true );
        newXsdSyntaxArea.setFont( font );
        newXsdSyntaxArea.setAutoscrolls( true );
        newXsdSyntaxArea.addMouseWheelListener( new ScrollFontListener( newXsdSyntaxArea ) );

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
        tabbedPane.setTabComponentAt( tabbedPane.getTabCount() - 1, createPanelTab( newTabName ) );
        tabbedPane.getAccessibleContext().setAccessibleName( newTabName );
        tabbedPane.setPreferredSize( new Dimension( width, height ) );
        pack();
        tabbedPane.setSelectedIndex( tabbedPane.getTabCount() - 1 );
        return newXsdSyntaxArea;

    }

    private String createUniqueTabName()
    {
        int freeNumber;

        ArrayList<Integer> takenNumbers = new ArrayList<>();
        for ( int i = 0; i < tabbedPane.getTabCount(); i++ )
        {
            String text = ( ( JLabel ) ( ( JPanel ) tabbedPane.getTabComponentAt( i ) ).getComponents()[ 0 ] ).getText();
            Pattern p = compile( "new file.*[0-9]" );
            Matcher m = p.matcher( text );
            if ( m.find() )
            {
                p = compile( "[0-9]+" );
                m = p.matcher( text );
                if ( m.find() )
                {
                    takenNumbers.add( parseInt( m.group() ) );
                }
            }
        }
        freeNumber = 0;
        while ( takenNumbers.contains( freeNumber ) )
        {
            freeNumber++;
        }
        return "new file " + freeNumber;
    }

    private JPanel createPanelTab( String heading )
    {
        JPanel tab = new JPanel( new GridBagLayout() );

        tab.setOpaque( false );
        JLabel lblTitle = new JLabel( heading + "   " );
        JButton tabCloseButton = new JButton( "x" );
        tabCloseButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        tabCloseButton.setMaximumSize( new Dimension( 1, 1 ) );
        tabCloseButton.setBorder( createEmptyBorder( 1, 1, 1, 1 ) );
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
                int index = tabbedPane.indexOfTabComponent( tabCloseButton.getParent() );
                boolean isSchemaTab = tabbedPane.getTitleAt( index ).equals( "schema" );
                if ( isSchemaTab )
                {
                    showSchemaTab( false );
                }
                else
                {
                    tabbedPane.remove( index );
                }
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
        return tab;
    }

    public void showSchemaTab( boolean add )
    {
        if ( !add )
        {
            tabbedPane.remove( schemaTab );
            exportMenu.setEnabled( false );
        }
        else
        {
            tabbedPane.addTab( "schema", schemaTab );
            int index;
            tabbedPane.setTabComponentAt( index = tabbedPane.getTabCount() - 1, createPanelTab( "schema" ) );
            tabbedPane.setSelectedIndex( index );
        }
    }
}
