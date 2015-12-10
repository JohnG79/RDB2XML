package rdb2xml.ui;

import control.Controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import javax.swing.*;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import javax.swing.event.MenuEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import static org.fife.ui.rsyntaxtextarea.SyntaxConstants.SYNTAX_STYLE_XML;
import org.jdesktop.swingx.JXTreeTable;
import static rdb2xml.ui.DialogHelper.GetScreenWorkingHeight;
import static rdb2xml.ui.DialogHelper.GetScreenWorkingWidth;

public final class MainInterface extends javax.swing.JFrame
{

    private final Controller controller;
    private final int minWidth = 700;
    private final int minHeight = 500;
    private final Dimension maxDimension;
    private final Dimension minDimension;
    private final Font defaultFont;
    private final Color texAreaBackgroundColor = new Color( 245, 245, 245 );
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu mainMenu_file;
    private javax.swing.JMenu mainMenu_serialise;
    private javax.swing.JPanel treeTableTab;
    private javax.swing.JMenuItem subMenu_import;
    private javax.swing.JMenuItem subMenu_new;
    private javax.swing.JMenuItem subMenu_open;
    private javax.swing.JMenuItem subMenu_saveAs;
    private javax.swing.JMenuItem subMenu_serialiseData;
    private javax.swing.JMenuItem subMenu_serialiseSchema;
    private javax.swing.JTabbedPane tabbedPane;

    public MainInterface( Controller controller )
    {
        this.defaultFont = new Font( "Aller Sans", Font.PLAIN, 18 );
        this.controller = controller;
        this.maxDimension = new Dimension( GetScreenWorkingHeight(), GetScreenWorkingWidth() );
        this.minDimension = new Dimension( minWidth + 100, minHeight + 100 );

        initComponents();

        setInterfaceColors( new Color( 102, 102, 102 ), new Color( 245, 245, 245 ) );
        setLookAndFeel( defaultFont );
        this.setMinimumSize( minDimension );
        pack();

        showTreeTableTab( false );
    }

    private MainInterface()
    {
        this.defaultFont = null;
        controller = null;
        maxDimension = null;
        minDimension = null;
    }

    public void setTreeTable( JXTreeTable jXTreeTable )
    {
        jScrollPane2.setViewportView( jXTreeTable );
        jXTreeTable.getTableHeader().setFont( defaultFont );
        mainMenu_serialise.setEnabled( true );
    }

    public void showTreeTableTab( boolean add )
    {
        if ( !add && tabbedPane.getComponentZOrder( treeTableTab ) != -1 )
        {
            tabbedPane.remove( treeTableTab );
            mainMenu_serialise.setEnabled( false );
        }
        else if ( add )
        {
            tabbedPane.addTab( "schema", treeTableTab );
            int index;
            setTabTitleComponents( index = tabbedPane.getTabCount() - 1, "schema" );
            tabbedPane.setSelectedIndex( index );
        }
    }

    private void setLookAndFeel( Font defaultFont )
    {
        UIDefaults laf = UIManager.getLookAndFeelDefaults();
        laf.put( "defaultFont", defaultFont );
    }

    private void setInterfaceColors( Color backgroundColor, Color foregroundColor )
    {
        menuBar.setBackground( backgroundColor );
        menuBar.setForeground( foregroundColor );
        mainMenu_file.setBackground( backgroundColor );
        mainMenu_file.setForeground( foregroundColor );
        subMenu_new.setBackground( backgroundColor );
        subMenu_new.setForeground( foregroundColor );
        subMenu_open.setBackground( backgroundColor );
        subMenu_open.setForeground( foregroundColor );
        subMenu_import.setBackground( backgroundColor );
        subMenu_import.setForeground( foregroundColor );
        subMenu_saveAs.setBackground( backgroundColor );
        subMenu_saveAs.setForeground( foregroundColor );
        mainMenu_serialise.setBackground( backgroundColor );
        mainMenu_serialise.setForeground( foregroundColor );
        subMenu_serialiseSchema.setBackground( backgroundColor );
        subMenu_serialiseSchema.setForeground( foregroundColor );
        subMenu_serialiseData.setBackground( backgroundColor );
        subMenu_serialiseData.setForeground( foregroundColor );
    }

    private void setDimensions()
    {
        tabbedPane.setMinimumSize( minDimension );
        tabbedPane.setMaximumSize( maxDimension );
        treeTableTab.setMinimumSize( minDimension );
        treeTableTab.setMaximumSize( maxDimension );
        jScrollPane2.setMinimumSize( minDimension );
        jScrollPane2.setMaximumSize( maxDimension );
    }

    @SuppressWarnings( "unchecked" )

    private void initComponents()
    {

        tabbedPane = new javax.swing.JTabbedPane();
        treeTableTab = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        menuBar = new javax.swing.JMenuBar();
        mainMenu_file = new javax.swing.JMenu();
        subMenu_new = new javax.swing.JMenuItem();
        subMenu_open = new javax.swing.JMenuItem();
        subMenu_import = new javax.swing.JMenuItem();
        subMenu_saveAs = new javax.swing.JMenuItem();
        mainMenu_serialise = new javax.swing.JMenu();
        subMenu_serialiseSchema = new javax.swing.JMenuItem();
        subMenu_serialiseData = new javax.swing.JMenuItem();

        setDefaultCloseOperation( javax.swing.WindowConstants.EXIT_ON_CLOSE );
        setTitle( "RDB to XML" );

        setIconImage( new ImageIcon( "C:\\Users\\johng\\Documents\\NetBeansProjects\\RDB2XML\\Resources\\xml-icon.png" ) );

        tabbedPane.setToolTipText( "" );
        tabbedPane.setAutoscrolls( true );

        GroupLayout schemaTabLayout = new GroupLayout( treeTableTab );
        treeTableTab.setLayout( schemaTabLayout );
        schemaTabLayout.setHorizontalGroup(
                schemaTabLayout.createParallelGroup( GroupLayout.Alignment.LEADING )
                .addComponent( jScrollPane2, minWidth, minWidth, GetScreenWorkingWidth() )
        );
        schemaTabLayout.setVerticalGroup(
                schemaTabLayout.createParallelGroup( GroupLayout.Alignment.LEADING )
                .addGroup( schemaTabLayout.createSequentialGroup()
                        .addGap( 0, 0, 0 )
                        .addComponent( jScrollPane2, minHeight, minHeight, GetScreenWorkingHeight() )
                        .addGap( 0, 0, 0 ) )
        );

        mainMenu_file.setText( "File" );
        mainMenu_file.addMenuListener( new javax.swing.event.MenuListener()
        {
            @Override
            public void menuCanceled( MenuEvent evt )
            {
            }

            @Override
            public void menuDeselected( MenuEvent evt )
            {
            }

            @Override
            public void menuSelected( MenuEvent evt )
            {
                mainMenu_fileMenuSelected( evt );
            }
        } );

        subMenu_new.setText( "New" );
        subMenu_new.addActionListener( this::subMenu_newActionPerformed );
        mainMenu_file.add( subMenu_new );

        subMenu_open.setText( "Open" );
        subMenu_open.addActionListener( this::subMenu_openActionPerformed );
        mainMenu_file.add( subMenu_open );

        subMenu_import.setText( "Import" );
        subMenu_import.addActionListener( this::subMenu_importActionPerformed );
        mainMenu_file.add( subMenu_import );

        subMenu_saveAs.setText( "Save As" );
        subMenu_saveAs.setEnabled( false );
        subMenu_saveAs.addActionListener( this::subMenu_saveAsActionPerformed );
        mainMenu_file.add( subMenu_saveAs );

        menuBar.add( mainMenu_file );

        mainMenu_serialise.setText( "Serialise" );
        mainMenu_serialise.setEnabled( false );
        mainMenu_serialise.addMenuListener( new javax.swing.event.MenuListener()
        {
            @Override
            public void menuCanceled( MenuEvent evt )
            {
            }

            @Override
            public void menuDeselected( MenuEvent evt )
            {
            }

            @Override
            public void menuSelected( MenuEvent evt )
            {
                mainMenu_serialiseMenuSelected( evt );
            }
        } );

        subMenu_serialiseSchema.setText( "Schema" );
        subMenu_serialiseSchema.addActionListener( this::subMenu_serialiseSchemaActionPerformed );
        mainMenu_serialise.add( subMenu_serialiseSchema );

        subMenu_serialiseData.setText( "Data" );
        subMenu_serialiseData.addActionListener( this::subMenu_serialiseDataActionPerformed );
        mainMenu_serialise.add( subMenu_serialiseData );

        menuBar.add( mainMenu_serialise );

        setJMenuBar( menuBar );

        GroupLayout layout = new GroupLayout( getContentPane() );
        getContentPane().setLayout( layout );
        layout.setHorizontalGroup(
                layout.createParallelGroup( GroupLayout.Alignment.LEADING )
                .addComponent( tabbedPane )
        );
        layout.setVerticalGroup(
                layout.createParallelGroup( GroupLayout.Alignment.LEADING )
                .addComponent( tabbedPane, GroupLayout.Alignment.TRAILING )
        );

        tabbedPane.getAccessibleContext().setAccessibleName( "tabbedPane" );

        pack();
    }

    private void subMenu_importActionPerformed( ActionEvent evt )
    {
        controller.importSchema();
    }

    private void mainMenu_fileMenuSelected( MenuEvent evt )
    {
        String title;
        if ( tabbedPane.getTabCount() != 0
                && ( title = tabbedPane.getTitleAt( tabbedPane.getSelectedIndex() ) ) != null
                && !title.equals( "schema" )
                && !( ( CustomPanel ) tabbedPane.getSelectedComponent() ).isSaved() )
        {
            subMenu_saveAs.setEnabled( true );
        }
        else
        {
            subMenu_saveAs.setEnabled( false );
        }
    }

    private JLabel getSelectedTabLabel()
    {
        return ( JLabel ) ( ( JPanel ) tabbedPane.getTabComponentAt( tabbedPane.getSelectedIndex() ) ).getComponents()[ 0 ];
    }

    private void subMenu_saveAsActionPerformed( ActionEvent evt )
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle( "Save As" );
        JLabel selectedTab_label = getSelectedTabLabel();

        fileChooser.setSelectedFile( new File( selectedTab_label.getText().trim() ) );
        fileChooser.addChoosableFileFilter( new FileNameExtensionFilter( "xml files (*.xml)", "xml" ) );
        fileChooser.addChoosableFileFilter( new FileNameExtensionFilter( "xml schema files (*.xsd)", "xsd" ) );

        if ( fileChooser.showSaveDialog( this ) == APPROVE_OPTION )
        {
            File file;
            CustomPanel tab;
            RSyntaxTextArea rSyntaxTextArea = getSyntaxAreaFromTab( tab = ( CustomPanel ) tabbedPane.getSelectedComponent() );
            controller.save( rSyntaxTextArea, file = DialogHelper.getSelectedFileWithExtension( fileChooser ) );
            getSelectedTabLabel().setText( file.getName() );
            tab.setSavedStatus( true );
        }
    }

    private RSyntaxTextArea getSyntaxAreaFromTab( CustomPanel tab )
    {
        JPanel tab_innerPanel = ( JPanel ) tab.getComponents()[ 0 ];
        JScrollPane tab_scrollPane = ( JScrollPane ) tab_innerPanel.getComponents()[ 0 ];
        JViewport jViewport = ( JViewport ) tab_scrollPane.getComponents()[ 0 ];
        return ( RSyntaxTextArea ) jViewport.getComponents()[ 0 ];
    }

    private void subMenu_serialiseDataActionPerformed( ActionEvent evt )
    {
        RSyntaxTextArea rSyntaxTextArea = addNewSyntaxTab( null );
        controller.importAndSerialiseData( rSyntaxTextArea );
        tabbedPane.setSelectedIndex( tabbedPane.getTabCount() - 1 );
    }

    private void subMenu_serialiseSchemaActionPerformed( ActionEvent evt )
    {
        RSyntaxTextArea rSyntaxTextArea = addNewSyntaxTab( null );
        controller.serialiseSchema( rSyntaxTextArea );
        tabbedPane.setSelectedIndex( tabbedPane.getTabCount() - 1 );
    }

    private void subMenu_newActionPerformed( ActionEvent evt )
    {
        addNewSyntaxTab( null );
        tabbedPane.setSelectedIndex( tabbedPane.getTabCount() - 1 );
    }

    private void mainMenu_serialiseMenuSelected( MenuEvent evt )
    {
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
    }

    private void subMenu_openActionPerformed( ActionEvent evt )
    {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.addChoosableFileFilter( new FileNameExtensionFilter( "xml files (*.xml)", "xml" ) );
        jFileChooser.addChoosableFileFilter( new FileNameExtensionFilter( "xml schema files (*.xsd)", "xsd" ) );

        int result = jFileChooser.showOpenDialog( this );

        if ( result == APPROVE_OPTION )
        {
            try
            {
                FileReader reader;
                File file = jFileChooser.getSelectedFile();
                reader = new FileReader( file );
                int i = 1;
                RSyntaxTextArea rSyntaxTextArea = addNewSyntaxTab( file.getName() );
                while ( i != -1 )
                {
                    i = reader.read();
                    char ch = ( char ) i;
                    rSyntaxTextArea.append( ch + "" );
                }
            }
            catch ( IOException ioe )
            {
                out.println( ioe.getMessage() );
            }
        }
    }

    private void setIconImage( ImageIcon imageIcon )
    {
        setIconImage( imageIcon.getImage() );
    }

    private RSyntaxTextArea addNewSyntaxTab( String TabName )
    {
        String TabName_string = TabName == null ? createUniqueTabName() : TabName;

        CustomPanel tab = new CustomPanel();
        tab.setSavedStatus( false );

        RSyntaxTextArea tab_textArea = new RSyntaxTextArea();
        tab_textArea.setBackground( texAreaBackgroundColor );
        tab_textArea.setSyntaxEditingStyle( SYNTAX_STYLE_XML );
        tab_textArea.setCodeFoldingEnabled( true );
        tab_textArea.setFont( defaultFont );
        tab_textArea.setAutoscrolls( true );
        tab_textArea.addMouseWheelListener( new ScrollFontListener( tab_textArea ) );

        JScrollPane innerScrollPane = new JScrollPane();
        innerScrollPane.setViewportView( tab_textArea );

        JPanel tab_innerPanel = new javax.swing.JPanel();

        GroupLayout innerJPanelLayout = new GroupLayout( tab_innerPanel );
        tab_innerPanel.setLayout( innerJPanelLayout );
        innerJPanelLayout.setHorizontalGroup( innerJPanelLayout.createParallelGroup( LEADING )
                .addComponent( innerScrollPane, minWidth, tabbedPane.getWidth(), GetScreenWorkingWidth() )
        );
        innerJPanelLayout.setVerticalGroup( innerJPanelLayout.createParallelGroup( LEADING )
                .addComponent( innerScrollPane, minHeight, tabbedPane.getHeight(), GetScreenWorkingHeight() )
        );

        GroupLayout outerJPanelLayout = new GroupLayout( tab );
        tab.setLayout( outerJPanelLayout );
        outerJPanelLayout.setHorizontalGroup( outerJPanelLayout.createParallelGroup( LEADING )
                .addComponent( tab_innerPanel, minWidth, tabbedPane.getWidth(), GetScreenWorkingWidth() )
        );
        outerJPanelLayout.setVerticalGroup( outerJPanelLayout.createParallelGroup( LEADING )
                .addGroup( outerJPanelLayout.createSequentialGroup()
                        .addComponent( tab_innerPanel, minHeight, tabbedPane.getHeight(), GetScreenWorkingHeight() )
                        .addGap( 0, 0, 0 ) )
        );

        tabbedPane.addTab( TabName_string, tab );
        setTabTitleComponents( tabbedPane.getTabCount() - 1, TabName_string );
        tabbedPane.getAccessibleContext().setAccessibleName( TabName_string );
        tabbedPane.setPreferredSize( new Dimension( tabbedPane.getWidth(), tabbedPane.getHeight() ) );

        tabbedPane.setSelectedIndex( tabbedPane.getTabCount() - 1 );
        tab_textArea.addKeyListener( new KeyListener()
        {
            @Override
            public void keyTyped( KeyEvent e )
            {
                tab.setSavedStatus( false );
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

        pack();
        return tab_textArea;
    }

    private void setTabTitleComponents( int tabPanel_index, String heading )
    {
        JPanel tab = new JPanel( new GridBagLayout() );

        tab.setOpaque( false );
        JLabel tabLabel = new JLabel( heading );

        JButton closeButton = new JButton( "x" );
        closeButton.setBorder( createEmptyBorder( -1, 5, 1, 5 ) );
        closeButton.setForeground( Color.RED );

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0;

        tab.add( tabLabel, constraints );

        constraints.gridx++;
        constraints.weightx = 0;
        tab.add( new JLabel( "  " ), constraints );
        constraints.gridx++;
        tab.add( closeButton, constraints );

        JFrame jFrame = this;
        closeButton.addMouseListener( new MouseListener()
        {

            @Override
            public void mouseClicked( MouseEvent e )
            {
                int index = tabbedPane.indexOfTabComponent( closeButton.getParent() );

                if ( tabbedPane.getTitleAt( index ).equals( "schema" ) )
                {
                    showTreeTableTab( false );
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
                    int userChoice = JOptionPane.showOptionDialog( jFrame,
                            "Save '" + tabbedPane.getTitleAt( index ) + "' ?",
                            "",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[ 2 ] );

                    switch ( userChoice )
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

        tabbedPane.setTabComponentAt( tabPanel_index, tab );
    }

    private String createUniqueTabName()
    {
        int freeNumber;

        ArrayList<Integer> takenNumbers = new ArrayList<>();
        for ( int i = 0; i < tabbedPane.getTabCount(); i++ )
        {
            String text = ( ( JLabel ) ( ( JPanel ) tabbedPane.getTabComponentAt( i ) ).getComponents()[ 0 ] ).getText();
            Pattern pattern = compile( "new file.*[0-9]" );
            Matcher m = pattern.matcher( text );
            if ( m.find() )
            {
                pattern = compile( "[0-9]+" );
                m = pattern.matcher( text );
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

}
