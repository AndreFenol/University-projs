/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package loginform;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NUD-Student
 */
public class Homepage extends javax.swing.JFrame {

    DefaultTableModel model;
    public Homepage() {
        initComponents();
        model = (DefaultTableModel) table.getModel();
        Connect();
    }
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    public void Connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/phpmyadmin", "root", "");
        }
        catch (ClassNotFoundException ex){
            Logger.getLogger(Homepage.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex){
            Logger.getLogger(Homepage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void Reload_Table(){
        try{
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int rows = model.getRowCount() -1;
            for(int i = rows; i >= 0; i--){
                model.removeRow(i);
            }
            pst = con.prepareStatement("Select * from inventory");
            rs = pst.executeQuery();
            
            while(rs.next()){
                
                String p = txtprod.getText();
                String id = txtid.getText();
                String quantity = txtquantity.getText();
                String price = txtprice.getText();
                model.addRow(new Object[]{p, id, quantity, price});
            }
        }
        catch(SQLException ex){
            Logger.getLogger(Homepage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtprod = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        btnadd = new javax.swing.JButton();
        txtquantity = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        txtprice = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnupdate = new javax.swing.JButton();
        btndel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Product");

        jLabel2.setText("ID");

        btnadd.setText("add");
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });

        txtquantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtquantityActionPerformed(evt);
            }
        });

        jLabel3.setText("Quantity");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product", "ID", "Quantity", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.getTableHeader().setReorderingAllowed(false);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        txtprice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpriceActionPerformed(evt);
            }
        });

        jLabel4.setText("Price");

        btnupdate.setText("update");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        btndel.setText("delete");
        btndel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(163, 163, 163))
            .addGroup(layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(36, 36, 36)
                        .addComponent(txtprod, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtid)
                            .addComponent(txtquantity))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jLabel4)
                        .addGap(29, 29, 29)
                        .addComponent(txtprice, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                        .addGap(308, 308, 308))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnadd)
                        .addGap(18, 18, 18)
                        .addComponent(btnupdate)
                        .addGap(18, 18, 18)
                        .addComponent(btndel)
                        .addGap(74, 74, 74))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txtprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnadd)
                    .addComponent(btnupdate)
                    .addComponent(btndel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtquantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        try{
            String p = txtprod.getText();
            String id = txtid.getText();
            String quantity = txtquantity.getText();
            String price = txtprice.getText();
            pst = con.prepareStatement("insert into inventory(Product,idd,Quantity,Price)values(?,?,?,?)");
            pst.setString(1, p);
            pst.setString(2, id);
            pst.setString(3, quantity);
            pst.setString(4, price);
            int k = pst.executeUpdate();
            
            if(k == 1){
                 JOptionPane.showMessageDialog(this, "added successfully");
                 txtprod.setText("");
                 txtid.setText("");
                 txtquantity.setText("");
                 txtprice.setText("");
                 model.insertRow(model.getRowCount(), new Object[]{p,id,quantity,price});
            }
            else{
                JOptionPane.showMessageDialog(this, "failed");
            }
            
        }
        catch(SQLException ex){
             Logger.getLogger(Homepage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnaddActionPerformed

    private void txtquantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtquantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtquantityActionPerformed

    private void txtpriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpriceActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
            
            //complete 
        try{
            String p = txtprod.getText();
            String id = txtid.getText();
            String quantity = txtquantity.getText();
            String price = txtprice.getText();
            
           
            
            
           if(p.isEmpty() || id.isEmpty() || quantity.isEmpty() || price.isEmpty()){
               JOptionPane.showMessageDialog(this, "complete kdkkdk");
           }
           else{
               String query = "Update inventory set idd = ?, Quantity = ?, Price = ? where Product = ?";
               pst = con.prepareStatement(query);
               pst.setString(1, id);
               pst.setString(2, quantity);
               pst.setString(3, price);
               pst.setString(4, p);
               
               pst.executeUpdate();
               JOptionPane.showMessageDialog(this, "update");
               Reload_Table();
               
           }
        }
        catch(SQLException ex){
             Logger.getLogger(Homepage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            
        
            
        
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btndelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndelActionPerformed
        //complete
        try{
            String p = txtprod.getText();
            
            
            String query = "Delete from inventory where Product = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, p);
            
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Delete successfully");
            Reload_Table();
        }
        catch (SQLException ex){
            Logger.getLogger(Homepage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btndelActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
            
            String Product = model.getValueAt(table.getSelectedRow(), 0).toString();
            String ID = model.getValueAt(table.getSelectedRow(), 1).toString();
            String Quantity = model.getValueAt(table.getSelectedRow(), 2).toString();
            String Price = model.getValueAt(table.getSelectedRow(), 3).toString();
            
            txtprod.setText(Product);
            txtid.setText(ID);
            txtquantity.setText(Quantity);
            txtprice.setText(Price);
            
    }//GEN-LAST:event_tableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Homepage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnadd;
    private javax.swing.JButton btndel;
    private javax.swing.JButton btnupdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtprice;
    private javax.swing.JTextField txtprod;
    private javax.swing.JTextField txtquantity;
    // End of variables declaration//GEN-END:variables
}
