package rdb2xml.ui;

import control.Controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import static org.fife.ui.rsyntaxtextarea.SyntaxConstants.SYNTAX_STYLE_XML;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.FontHighlighter;
import static rdb2xml.ui.DialogHelper.GetScreenWorkingHeight;
import static rdb2xml.ui.DialogHelper.GetScreenWorkingWidth;
import static java.lang.Integer.parseInt;
import static java.util.regex.Pattern.compile;
import static javax.swing.BorderFactory.createEmptyBorder;

public final class MainInterface extends javax.swing.JFrame
{

    private final Controller controller;
    private final Color darkGray = new Color( 102, 102, 102 );
    private final Color lightGray = new Color( 245, 245, 245 );

    private MainInterface()
    {
        controller = null;
    }

    public MainInterface( Controller controller )
    {
        this.controller = controller;
        initComponents();
        showSchemaTab( false );
        this.setMinimumSize( new Dimension( 800, 600 ) );
        this.getContentPane().setBackground( darkGray );
        jMenuBar1.setBackground( darkGray );
        jMenuBar1.setForeground( lightGray );
        mainMenu_file.setBackground( darkGray );
        mainMenu_file.setForeground( lightGray );
        subMenu_new.setBackground( darkGray );
        subMenu_new.setForeground( lightGray );
        subMenu_open.setBackground( darkGray );
        subMenu_open.setForeground( lightGray );
        subMenu_import.setBackground( darkGray );
        subMenu_import.setForeground( lightGray );
        subMenu_saveAs.setBackground( darkGray );
        subMenu_saveAs.setForeground( lightGray );
        mainMenu_serialise.setBackground( darkGray );
        mainMenu_serialise.setForeground( lightGray );
        subMenu_serialiseSchema.setBackground( darkGray );
        subMenu_serialiseSchema.setForeground( lightGray );
        subMenu_serialiseData.setBackground( darkGray );
        subMenu_serialiseData.setForeground( lightGray );
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
        mainMenu_file = new javax.swing.JMenu();
        subMenu_new = new javax.swing.JMenuItem();
        subMenu_open = new javax.swing.JMenuItem();
        subMenu_import = new javax.swing.JMenuItem();
        subMenu_saveAs = new javax.swing.JMenuItem();
        mainMenu_serialise = new javax.swing.JMenu();
        subMenu_serialiseSchema = new javax.swing.JMenuItem();
        subMenu_serialiseData = new javax.swing.JMenuItem();

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
		jScrollPane2.setMinimumSize(new java.awt.Dimension(800, 600));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(800, 600));
        jScrollPane2.setViewportView(treeTable);

        javax.swing.GroupLayout schemaTabLayout = new javax.swing.GroupLayout(schemaTab);
        schemaTab.setLayout(schemaTabLayout);
        schemaTabLayout.setHorizontalGroup(
            schemaTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, 800, 800, Short.MAX_VALUE)
        );
        schemaTabLayout.setVerticalGroup(
            schemaTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(schemaTabLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, 600, 600, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        tabbedPane.addTab("Schema", schemaTab);

        jMenuBar1.setBackground(new java.awt.Color(102, 102, 102));
        jMenuBar1.setForeground(new java.awt.Color(245, 245, 245));

        mainMenu_file.setBackground(new java.awt.Color(102, 102, 102));
        mainMenu_file.setForeground(new java.awt.Color(245, 245, 245));
        mainMenu_file.setText("File");
        mainMenu_file.addMenuListener(new javax.swing.event.MenuListener()
        {
            public void menuCanceled(javax.swing.event.MenuEvent evt)
            {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt)
            {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt)
            {
                mainMenu_fileMenuSelected(evt);
            }
        });

        subMenu_new.setBackground(new java.awt.Color(102, 102, 102));
        subMenu_new.setForeground(new java.awt.Color(245, 245, 245));
        subMenu_new.setText("New");
        subMenu_new.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                subMenu_newActionPerformed(evt);
            }
        });
        mainMenu_file.add(subMenu_new);

        subMenu_open.setBackground(new java.awt.Color(102, 102, 102));
        subMenu_open.setForeground(new java.awt.Color(245, 245, 245));
        subMenu_open.setText("Open");
        subMenu_open.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                subMenu_openActionPerformed(evt);
            }
        });
        mainMenu_file.add(subMenu_open);

        subMenu_import.setBackground(new java.awt.Color(102, 102, 102));
        subMenu_import.setForeground(new java.awt.Color(245, 245, 245));
        subMenu_import.setText("Import");
        subMenu_import.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                subMenu_importActionPerformed(evt);
            }
        });
        mainMenu_file.add(subMenu_import);

        subMenu_saveAs.setBackground(new java.awt.Color(102, 102, 102));
        subMenu_saveAs.setForeground(new java.awt.Color(245, 245, 245));
        subMenu_saveAs.setText("Save As");
        subMenu_saveAs.setEnabled(false);
        subMenu_saveAs.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                subMenu_saveAsActionPerformed(evt);
            }
        });
        mainMenu_file.add(subMenu_saveAs);

        jMenuBar1.add(mainMenu_file);

        mainMenu_serialise.setBackground(new java.awt.Color(102, 102, 102));
        mainMenu_serialise.setForeground(new java.awt.Color(245, 245, 245));
        mainMenu_serialise.setText("Serialise");
        mainMenu_serialise.setActionCommand("Serialise");
        mainMenu_serialise.setEnabled(false);
        mainMenu_serialise.addMenuListener(new javax.swing.event.MenuListener()
        {
            public void menuCanceled(javax.swing.event.MenuEvent evt)
            {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt)
            {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt)
            {
                mainMenu_serialiseMenuSelected(evt);
            }
        });

        subMenu_serialiseSchema.setBackground(new java.awt.Color(102, 102, 102));
        subMenu_serialiseSchema.setForeground(new java.awt.Color(245, 245, 245));
        subMenu_serialiseSchema.setText("Schema");
        subMenu_serialiseSchema.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                subMenu_serialiseSchemaActionPerformed(evt);
            }
        });
        mainMenu_serialise.add(subMenu_serialiseSchema);

        subMenu_serialiseData.setBackground(new java.awt.Color(102, 102, 102));
        subMenu_serialiseData.setForeground(new java.awt.Color(245, 245, 245));
        subMenu_serialiseData.setText("Data");
        subMenu_serialiseData.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                subMenu_serialiseDataActionPerformed(evt);
            }
        });
        mainMenu_serialise.add(subMenu_serialiseData);

        jMenuBar1.add(mainMenu_serialise);

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

    private void subMenu_importActionPerformed( ActionEvent evt )
    {//GEN-FIRST:event_connectMenuItemActionPerformed
        controller.importSchema();
    }//GEN-LAST:event_connectMenuItemActionPerformed

    private void mainMenu_fileMenuSelected( javax.swing.event.MenuEvent evt )//GEN-FIRST:event_mainMenu_fileMenuSelected
    {//GEN-HEADEREND:event_mainMenu_fileMenuSelected
        if ( tabbedPane.getTabCount() != 0
                && tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() ) != null
                && !tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() ).equals( "schema" )
                && !( ( CustomPanel ) tabbedPane.getSelectedComponent() ).isSaved() )
        {
            subMenu_saveAs.setEnabled( true );
        }
        else
        {
            subMenu_saveAs.setEnabled( false );
        }
    }//GEN-LAST:event_mainMenu_fileMenuSelected

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

    private void subMenu_saveAsActionPerformed(ActionEvent evt)//GEN-FIRST:event_subMenu_saveAsActionPerformed
    {//GEN-HEADEREND:event_subMenu_saveAsActionPerformed

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
            ( ( CustomPanel ) jPanel_1 ).setSavedStatus( true );
        }
    }//GEN-LAST:event_subMenu_saveAsActionPerformed

    private void subMenu_serialiseDataActionPerformed(ActionEvent evt)//GEN-FIRST:event_subMenu_serialiseDataActionPerformed
    {//GEN-HEADEREND:event_subMenu_serialiseDataActionPerformed
        RSyntaxTextArea rSyntaxTextArea = addNewSyntaxTab( null );
        controller.serialiseData( rSyntaxTextArea );
        tabbedPane.setSelectedIndex( tabbedPane.getTabCount() - 1 );
    }//GEN-LAST:event_subMenu_serialiseDataActionPerformed

    private void subMenu_serialiseSchemaActionPerformed(ActionEvent evt)//GEN-FIRST:event_subMenu_serialiseSchemaActionPerformed
    {//GEN-HEADEREND:event_subMenu_serialiseSchemaActionPerformed
        RSyntaxTextArea rSyntaxTextArea = addNewSyntaxTab( null );
        controller.serialiseSchema( rSyntaxTextArea );
        tabbedPane.setSelectedIndex( tabbedPane.getTabCount() - 1 );
    }//GEN-LAST:event_subMenu_serialiseSchemaActionPerformed

    private void subMenu_newActionPerformed(ActionEvent evt)//GEN-FIRST:event_subMenu_newActionPerformed
    {//GEN-HEADEREND:event_subMenu_newActionPerformed
        addNewSyntaxTab( null );
        tabbedPane.setSelectedIndex( tabbedPane.getTabCount() - 1 );
    }//GEN-LAST:event_subMenu_newActionPerformed

    private void mainMenu_serialiseMenuSelected(javax.swing.event.MenuEvent evt)//GEN-FIRST:event_mainMenu_serialiseMenuSelected
    {//GEN-HEADEREND:event_mainMenu_serialiseMenuSelected
        if ( tabbedPane.getTabCount() != 0 && tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() ).equals( "schema" ) )
        {
            subMenu_serialiseSchema.setEnabled( true );
            subMenu_serialiseData.setEnabled( true );
        }
        else
        {
            subMenu_serialiseSchema.setEnabled( false );
            subMenu_serialiseData.setEnabled( false );
        }
    }//GEN-LAST:event_mainMenu_serialiseMenuSelected

    private void subMenu_openActionPerformed(ActionEvent evt)//GEN-FIRST:event_subMenu_openActionPerformed
    {//GEN-HEADEREND:event_subMenu_openActionPerformed
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

                addNewSyntaxTab( file.getName() ).setText( buffer.toString().substring( 0, buffer.toString().length() - 1 ) );

            }
            catch ( IOException ioe )
            {

            }
        }
    }//GEN-LAST:event_subMenu_openActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu mainMenu_file;
    private javax.swing.JMenu mainMenu_serialise;
    private javax.swing.JPanel schemaTab;
    private javax.swing.JMenuItem subMenu_import;
    private javax.swing.JMenuItem subMenu_new;
    private javax.swing.JMenuItem subMenu_open;
    private javax.swing.JMenuItem subMenu_saveAs;
    private javax.swing.JMenuItem subMenu_serialiseData;
    private javax.swing.JMenuItem subMenu_serialiseSchema;
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
        mainMenu_serialise.setEnabled( true );
    }

    private RSyntaxTextArea addNewSyntaxTab( String name )
    {
        Font font = new Font( "Courier new", Font.BOLD, 16 );
        String newTabName = name == null ? createUniqueTabName() : name;
        int width = tabbedPane.getWidth();
        int height = tabbedPane.getHeight();

        CustomPanel outerJPanel = new CustomPanel();

        outerJPanel.setSavedStatus( false );

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
        newXsdSyntaxArea.addKeyListener( new KeyListener()
        {
            @Override
            public void keyTyped( KeyEvent e )
            {
                outerJPanel.setSavedStatus( false );
            }

            @Override
            public void keyPressed( KeyEvent e )
            {

            }

            @Override
            public void keyReleased( KeyEvent e )
            {

            }
        } );
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
        JLabel lblTitle = new JLabel( heading );
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
        tab.add( new JLabel( "   " ), gbc );
        gbc.gridx++;
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
                else if ( ( ( CustomPanel ) tabbedPane.getComponentAt( index ) ).isSaved() )
                {
                    tabbedPane.remove( index );
                }
                else
                {

                    Object[] options =
                    {
                        "Yes",
                        "No",
                        "Cancel"
                    };
                    int n = JOptionPane.showOptionDialog( null,
                            "Save '" + tabbedPane.getTitleAt( index ) + "' ?",
                            "",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[ 2 ] );

                    switch ( n )
                    {
                        case 0:
                        {
                            subMenu_saveAsActionPerformed( null );
                        }
                        case 1:
                        {
                            tabbedPane.remove( index );
                        }
                        case 2:
                        {

                        }
                    }
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
            mainMenu_serialise.setEnabled( false );
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
