package rdb2xml.ui;

import control.Controller;
import java.util.HashMap;
import javax.swing.ImageIcon;
import persistence.ConnectionParameter;
import static persistence.ConnectionParameter.HOST;
import static persistence.ConnectionParameter.PASSWORD;
import static persistence.ConnectionParameter.PORT;
import static persistence.ConnectionParameter.SCHEMA;
import static persistence.ConnectionParameter.USERNAME;
import persistence.DataFormat;
import static persistence.DataFormat.OWL;
import static persistence.DataFormat.XSD;

public class ConnectDialog extends javax.swing.JFrame
{

    private Controller controller;

    private ConnectDialog()
    {
    }

    public ConnectDialog( Controller controller )
    {
        this.controller = controller;
        initComponents();
        setDefaultCloseOperation( ConnectDialog.HIDE_ON_CLOSE );
    }

    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        formatButtonGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        host = new javax.swing.JTextField();
        port = new javax.swing.JTextField();
        database = new javax.swing.JTextField();
        username = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        cancel = new javax.swing.JButton();
        connectButton = new javax.swing.JButton();
        rdfRadioButton = new javax.swing.JRadioButton();
        xmlRadioButton = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MySQL Connection");
        setIconImage(new ImageIcon("C:\\Users\\johng\\Documents\\NetBeansProjects\\RDB2XML\\Resources\\mysql-icon.jpg"));
        setResizable(false);

        jLabel1.setText("Host Name");

        jLabel2.setText("Port");

        jLabel3.setText("Database Name");

        jLabel4.setText("Username");

        jLabel5.setText("Password");

        host.setText("localhost");

        port.setText("3306");

        database.setText("university");

        username.setText("root");

        password.setText("root");

        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        formatButtonGroup.add(rdfRadioButton);
        rdfRadioButton.setText("RDF");

        formatButtonGroup.add(xmlRadioButton);
        xmlRadioButton.setSelected(true);
        xmlRadioButton.setText("XML");
        xmlRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xmlRadioButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Format");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(port, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(database, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                    .addComponent(username, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(password, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(host)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(xmlRadioButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdfRadioButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(connectButton)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(host, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(database, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdfRadioButton)
                    .addComponent(xmlRadioButton)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectButton)
                    .addComponent(cancel))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed

        HashMap<ConnectionParameter, String> connectionParams = new HashMap<ConnectionParameter, String>();
        connectionParams.put( HOST, host.getText() );
        connectionParams.put( PORT, port.getText() );
        connectionParams.put( SCHEMA, database.getText() );
        connectionParams.put( USERNAME, username.getText() );
        connectionParams.put( PASSWORD, password.getText() );
        putConnectionParams( connectionParams );

    }//GEN-LAST:event_connectButtonActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        this.setVisible( false );
        controller.enableMainForm();
    }//GEN-LAST:event_cancelActionPerformed

    private void xmlRadioButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_xmlRadioButtonActionPerformed
    {//GEN-HEADEREND:event_xmlRadioButtonActionPerformed

    }//GEN-LAST:event_xmlRadioButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JButton connectButton;
    private javax.swing.JTextField database;
    private javax.swing.ButtonGroup formatButtonGroup;
    private javax.swing.JTextField host;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPasswordField password;
    private javax.swing.JTextField port;
    private javax.swing.JRadioButton rdfRadioButton;
    private javax.swing.JTextField username;
    private javax.swing.JRadioButton xmlRadioButton;
    // End of variables declaration//GEN-END:variables

    private void setIconImage( ImageIcon imageIcon )
    {
        this.setIconImage( imageIcon.getImage() );
    }
    private HashMap<ConnectionParameter, String> connectionParams;
    private boolean available = false;

    public synchronized HashMap<ConnectionParameter, String> getConnectionParams()
    {
        while ( available == false )
        {
            try
            {
                wait();
            }
            catch ( InterruptedException e )
            {
            }
        }
        available = false;
        notifyAll();
        return this.connectionParams;
    }

    private synchronized void putConnectionParams( HashMap<ConnectionParameter, String> connectionParams )
    {
        while ( available == true )
        {
            try
            {
                wait();
            }
            catch ( InterruptedException e )
            {
            }
        }
        this.connectionParams = connectionParams;
        available = true;
        notifyAll();
    }

    public DataFormat getDataFormat()
    {
        return ( xmlRadioButton.isSelected() ? XSD : OWL );
    }
}
