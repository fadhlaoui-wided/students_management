/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.etudiant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author unkown
 */
public class Note extends javax.swing.JFrame {

    private String idEtudiant;
    private String idMatiere;

    public Note() {
        initComponents();
        getAll();
        jPanel1.setVisible(false);
        jTable2.getColumnModel().getColumn(0).setMinWidth(0);
        jTable2.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable2.getColumnModel().getColumn(0).setWidth(0);

        jTable2.getModel().addTableModelListener(
                new TableModelListener() {
            public void tableChanged(TableModelEvent evt) {

                if (jTable2.getSelectedRow() > -1) {
                    try{
                    idMatiere = jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString();
                    }catch(java.lang.ArrayIndexOutOfBoundsException e){
                        
                    }
                    try {
                        ConnectionDB connectionDB = new ConnectionDB();
                        String sql2 = "select * from note where id_etudiant=? and id_matiere= ?";
                        PreparedStatement statement2 = connectionDB.getConnection().prepareStatement(sql2);
                        statement2.setString(1, idEtudiant);
                        statement2.setString(2, idMatiere);
                        ResultSet result2 = statement2.executeQuery();
                     
                            double controle = Double.parseDouble(jTable2.getValueAt(jTable2.getSelectedRow(), 3).toString());
                            double examen = Double.parseDouble(jTable2.getValueAt(jTable2.getSelectedRow(), 4).toString());
                            double moy = controle * 0.4 + examen * 0.6;
                        if (result2.next()) {
                            updateNote(controle, examen, moy);
                          
                        } else {  
                           
                            addNote(controle, examen, moy);   
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        });

    }

    private void addNote(double controle, double examen, double moy) {
        try {
            ConnectionDB connectionDB = new ConnectionDB();
            String sql = "insert into note(id_etudiant,id_matiere, controle, examen, moyenne) values(?,?,?,?,?)";
            PreparedStatement statement = connectionDB.getConnection().prepareStatement(sql);
            statement.setString(1, idEtudiant);
            statement.setString(2, idMatiere);
            statement.setDouble(3, controle);
            statement.setDouble(4, examen);
            statement.setDouble(5, moy);
            statement.execute();
            getAllNote();
        } catch (SQLException ex) {
            Logger.getLogger(Matiere.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
     private void updateNote(double controle, double examen, double moy) {
        try {
            ConnectionDB connectionDB = new ConnectionDB();
            String sql = "update note set controle=?, examen=?, moyenne =? where id_etudiant=? and id_matiere=?";
            PreparedStatement statement = connectionDB.getConnection().prepareStatement(sql);
            statement.setString(4, idEtudiant);
            statement.setString(5, idMatiere);
            statement.setDouble(1, controle);
            statement.setDouble(2, examen);
            statement.setDouble(3, moy);
            statement.execute();
            getAllNote();
        } catch (SQLException ex) {
            Logger.getLogger(Matiere.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void getAllNote() {

        try {
            double somme =0;
            double sommeCoef=0;
            int rowSelected = jTable1.getSelectedRow();
            idEtudiant = jTable1.getModel().getValueAt(rowSelected, 0).toString();
            ConnectionDB connectionDB = new ConnectionDB();
            String sql = "select * from matiere";
            PreparedStatement statement = connectionDB.getConnection().prepareStatement(sql);
            ResultSet res = statement.executeQuery();
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);

            while (res.next()) {
                String sql2 = "select * from note where id_etudiant=? and id_matiere= ?";
                PreparedStatement statement2 = connectionDB.getConnection().prepareStatement(sql2);
                statement2.setString(1, idEtudiant);
                statement2.setInt(2, res.getInt(1));
                ResultSet result2 = statement2.executeQuery();
                sommeCoef += res.getDouble(3);
                if (result2.next()) {
                    double total = res.getDouble(3) * result2.getDouble(6);
                    somme += total;
                    model.addRow(new Object[]{res.getInt(1), res.getString(2), res.getDouble(3), result2.getDouble(4), result2.getDouble(5), result2.getDouble(6), total});
                } else {
                    model.addRow(new Object[]{res.getInt(1), res.getString(2), res.getDouble(3), 0, 0, 0, 0});
                }

            }
            double result = somme / sommeCoef;
             DecimalFormat df = new DecimalFormat("#.##");
            jLabel2.setText(df.format(result));
            if(result>=10){
                jLabel4.setText("Admis");
            }else {
                   jLabel4.setText("Refus�");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Matiere.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getAll() {

        try {

            ConnectionDB connectionDB = new ConnectionDB();
            String sql = "select * from etudiant";
            PreparedStatement statement = connectionDB.getConnection().prepareStatement(sql);
            ResultSet res = statement.executeQuery();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            while (res.next()) {
                model.addRow(new Object[]{res.getInt(1), res.getString(2), res.getString(3), res.getString(4)});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Matiere.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nom", "Pr�nom", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Matiere", "Coefficient", "Controle", "Examen", "Moyenne", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, true, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTable2KeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel1.setText("R�sultat");

        jLabel2.setText("jLabel2");

        jLabel3.setText("Obs�rvation");

        jLabel4.setText("jLabel4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        getAllNote();
        jPanel1.setVisible(true);
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyTyped


    }//GEN-LAST:event_jTable2KeyTyped

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
            java.util.logging.Logger.getLogger(Note.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Note.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Note.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Note.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Note().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
