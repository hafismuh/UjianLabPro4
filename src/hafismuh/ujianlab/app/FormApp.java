package hafismuh.ujianlab.app;

import hafismuh.ujianlab.koneksi.KoneksiMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hafismuh
 */
public class FormApp extends javax.swing.JFrame {
    private final DefaultTableModel model;

    /**
     * Creates new form FormApp
     */
    public FormApp() {
        initComponents();
        model = new DefaultTableModel();
        jTableHotel.setModel(model);
        model.addColumn("No. Pembayaran");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("Kode Kamar");
        model.addColumn("Nama Kamar");
        model.addColumn("Harga Kamar");
        model.addColumn("Lama Inap");
        model.addColumn("Jumlah Bayar");
        model.addColumn("Diskon");
        model.addColumn("Total");

        loadData();
    }
    
    public void loadData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try{
            Connection c = KoneksiMySQL.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM tb_hotel";
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                Object [] o = new Object[10];
                o [0] = rs.getString("no_pembayaran");
                o [1] = rs.getString("nama");
                o [2] = rs.getString("alamat");
                o [3] = rs.getString("kode_kamar");
                o [4] = rs.getString("nama_kamar");
                o [5] = rs.getInt("harga_kamar");
                o [6] = rs.getInt("lama_inap");
                o [7] = rs.getInt("jumlah_harga");
                o [8] = rs.getInt("diskon");
                o [9] = rs.getInt("total");
                model.addRow(o);
            }
        }catch(SQLException ex){
            System.out.println("Gagal Menampilkan Data Tabel");
        }
    }
    
    public void hitungHarga(){
        int jml_bayar = 0;
        //int lama_inap = 0;
        
        int harga = Integer.valueOf(jTextFieldHrgKamar.getText());
        int lama_inap = Integer.valueOf(jTextFieldLamaInap.getText());
        jTextFieldLamaInap.setText(String.valueOf(lama_inap));
        jTextFieldJmlBayar.setText(String.valueOf(jml_bayar));
        jml_bayar = harga * lama_inap;
        jTextFieldLamaInap.setText(String.valueOf(lama_inap));
        jTextFieldJmlBayar.setText(String.valueOf(jml_bayar));
    }
    
    private void jButtonTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTambahActionPerformed
        // TODO add your handling code here:
        String no_pemby = jTextFieldNoPemby.getText();
        String nama = jTextFieldNama.getText();
        String alamat = jTextFieldAlamat.getText();
        String kd_kamar = (String) jComboBoxKdKamar.getSelectedItem();
        String nm_kamar = jTextFieldNmKamar.getText();
        int hrg_kamar = Integer.valueOf(jTextFieldHrgKamar.getText());
        int lama_inap = Integer.valueOf(jTextFieldLamaInap.getText());
        int jml_bayar = Integer.valueOf(jTextFieldJmlBayar.getText());
        int diskon = Integer.valueOf(jTextFieldDiskon.getText());
        int total = Integer.valueOf(jTextFieldTotal.getText());
        
        try{
            Connection c = KoneksiMySQL.getKoneksi();
            String sql = "INSERT INTO tb_hotel values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, no_pemby);
            ps.setString(2, nama);
            ps.setString(3, alamat);
            ps.setString(4, kd_kamar);
            ps.setString(5, nm_kamar);
            ps.setInt(6, hrg_kamar);
            ps.setInt(7, lama_inap);
            ps.setInt(8, jml_bayar);
            ps.setInt(9, diskon);
            ps.setInt(10, total);
            ps.executeUpdate();
            ps.close();
        }catch(SQLException ex){
            System.out.println("Gagal Menyimpan");
        }finally{
            loadData();
        }
    };//GEN-LAST:event_jButtonTambahActionPerformed

    private void jComboBoxKdKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxKdKamarActionPerformed
        // TODO add your handling code here:
        String nm_kamar = null;
        int hrg_kamar = 0;
        
        if("KK001".equals(jComboBoxKdKamar.getSelectedItem())){
            nm_kamar = "KELAS EKONOMI";
            hrg_kamar = 80000;
        }else if("KK002".equals(jComboBoxKdKamar.getSelectedItem())){
            nm_kamar = "KELAS EKSKLUSIF";
            hrg_kamar = 100000;
        }
        jTextFieldNmKamar.setText(nm_kamar);
        jTextFieldHrgKamar.setText(String.valueOf(hrg_kamar));
        
    }//GEN-LAST:event_jComboBoxKdKamarActionPerformed

    private void jButtonHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHitungActionPerformed
        // TODO add your handling code here:
        hitungHarga();
    }//GEN-LAST:event_jButtonHitungActionPerformed

    private void jR10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jR10ActionPerformed
        // TODO add your handling code here:
        int total = 0;
        int diskon = 0;
        if(jR10.isSelected()){
            int jml_bayar = Integer.valueOf(jTextFieldJmlBayar.getText());
            jTextFieldDiskon.setText(String.valueOf(diskon));
            jTextFieldTotal.setText(String.valueOf(total));
            
            if(jml_bayar > 100000){
                diskon = jml_bayar * 10 / 100;
                total = jml_bayar - diskon;
            }else{
                diskon = 0;
                total = jml_bayar - diskon;
            }
        }
        jTextFieldDiskon.setText(String.valueOf(diskon));
        jTextFieldTotal.setText(String.valueOf(total));
    }//GEN-LAST:event_jR10ActionPerformed

    private void jR20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jR20ActionPerformed
        // TODO add your handling code here:
        int total = 0;
        int diskon = 0;
        if(jR20.isSelected()){
            int jml_bayar = Integer.valueOf(jTextFieldJmlBayar.getText());
            jTextFieldDiskon.setText(String.valueOf(diskon));
            jTextFieldTotal.setText(String.valueOf(total));
            
            if(jml_bayar > 200000){
                diskon = jml_bayar * 20 / 100;
                total = jml_bayar - diskon;
            }else{
                diskon = 0;
                total = jml_bayar - diskon;
            }
        }
        jTextFieldDiskon.setText(String.valueOf(diskon));
        jTextFieldTotal.setText(String.valueOf(total));
    }//GEN-LAST:event_jR20ActionPerformed

    private void jR30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jR30ActionPerformed
        // TODO add your handling code here:
        int total = 0;
        int diskon = 0;
        if(jR30.isSelected()){
            int jml_bayar = Integer.valueOf(jTextFieldJmlBayar.getText());
            jTextFieldDiskon.setText(String.valueOf(diskon));
            jTextFieldTotal.setText(String.valueOf(total));
            
            if(jml_bayar > 300000){
                diskon = jml_bayar * 30 / 100;
                total = jml_bayar - diskon;
            }else{
                diskon = 0;
                total = jml_bayar - diskon;
            }
        }
        jTextFieldDiskon.setText(String.valueOf(diskon));
        jTextFieldTotal.setText(String.valueOf(total));
    }//GEN-LAST:event_jR30ActionPerformed

    private void jButtonKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonKeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButtonKeluarActionPerformed

