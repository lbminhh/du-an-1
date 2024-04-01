/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Dialog.java to edit this template
 */
package views;

import components.EventPagination;
import components.PaginationItemRenderStyle1;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import request.CustomerRequest;
import request.CustomerSearchRequest;
import response.CustomerReponse;
import service.impl.CustomerServiceImpl;

/**
 *
 * @author admin
 */
public class ViewChooseKhachHang extends java.awt.Dialog {

    /**
     * Creates new form ViewChooseKhachHang
     */
    DefaultTableModel tableModelCustomer = new DefaultTableModel();
    private List<CustomerReponse> listCustomers = new ArrayList<>();
    private List<CustomerReponse> listToTableCustomer = new ArrayList<>();
    private CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
    public ViewChooseKhachHang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        pnaListCustomer.setPaginationItemRender(new PaginationItemRenderStyle1());
        pnaListCustomer.setPagegination(1, 10);
        listCustomers = customerServiceImpl.getAllCustomer();
        showListCustomerByPage(1, listCustomers);
        setUpAllPagination();
        setAllEventComponent();
    }
    
    private void setUpAllPagination() {
        pnaListCustomer.setPaginationItemRender(new PaginationItemRenderStyle1());
    }
    
     private void setAllEventComponent() {
        pnaListCustomer.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                showListCustomerByPage(page, listCustomers);
            }
        });
        
        
        //--------
        txt_tenKH.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                listCustomers = customerServiceImpl.searchListCustomer(getDataSearchCustomer());
                showListCustomerByPage(1, listCustomers);
            }
        });
    }
    
    private void addCustomer() {
        if (customerServiceImpl.addCustomer(getDataCustomer("add"))) {
            try {
                if (checkTrungIDKH(txt_ID.getText())) {
                    JOptionPane.showMessageDialog(this, "ID đã có, vui lòng nhập một ID khác!");
                    return;
                }
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                    listCustomers = customerServiceImpl.getAllCustomer();
                    clearFormKh();

            } catch (Exception e) {

                JOptionPane.showMessageDialog(this, "Lỗi thêm");
            }

        }
    }
    
    private void clearFormKh(){
        txt_ID.setText("");
        txt_SDT.setText("");
        txt_diaChi.setText("");
        txt_tenKH.setText("");
    }
    private CustomerSearchRequest getDataSearchCustomer() {
        String id = "";
        String Name = txt_timKH.getText();
        return new CustomerSearchRequest(id,Name);
    }

    private CustomerRequest getDataCustomer(String method) {
        String id = txt_ID.getText();
        if (checkStringEmpty(id)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ID Khách Hàng!");
            return null;
        }

        String full_name = txt_tenKH.getText().trim();
        if (checkStringEmpty(full_name)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Tên khách hàng!");
            return null;
        }

        String address = txt_diaChi.getText().trim();
        if (checkStringEmpty(address)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Địa chỉ!");
            return null;
        }

        String sdt = txt_SDT.getText().trim();
        if (checkStringEmpty(address)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Số điện thoại khách hàng!");
            return null;
        }

        Boolean gender = true;
        Boolean is_bought = true;
        Boolean status = true;
        return new CustomerRequest(id, full_name, address, sdt, gender, is_bought, status);
    }
    
     private void showListCustomerByPage(int page, List<CustomerReponse> list) {
        int litmit = 6;
        int totalPage = (int) Math.ceil(((double) list.size() / litmit));
        tableModelCustomer = (DefaultTableModel) tblKH.getModel();
        tableModelCustomer.setRowCount(0);
        pnaListCustomer.setPagegination(page, totalPage);
        listToTableCustomer.clear();
        for (int i = 0; i < listCustomers.size(); i++) {
            if (i >= (page - 1) * litmit && i <= (litmit * page) - 1) {
                CustomerReponse item = list.get(i);
                listToTableCustomer.add(item);
                tableModelCustomer.addRow(new Object[]{
                    i + 1,
                    item.getId(),
                    item.getFullName(),
                    item.getDiaChi(),
                    item.getPhoneNumber(),
                    item.getGender() ? "Nam" : "Nữ"
                });
            }
        }
    }
    private boolean checkTrungIDKH(String IDKH) {
        for (int i = 0; i < tblKH.getRowCount() - 1; i++) {
            if (tblKH.getValueAt(i, 1).toString().equalsIgnoreCase(IDKH)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkStringEmpty(String value) {
        return value.trim().isEmpty();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        materialTabbed4 = new components.MaterialTabbed();
        jPanel13 = new javax.swing.JPanel();
        txt_timKH = new components.TextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblKH = new components.Table();
        btnChoose = new components.ButtonCustom();
        pnaListCustomer = new components.Pagination();
        jPanel14 = new javax.swing.JPanel();
        txt_ID = new components.TextField();
        txt_tenKH = new components.TextFieldSuggestion();
        jLabel30 = new javax.swing.JLabel();
        txt_SDT = new components.TextFieldSuggestion();
        jLabel31 = new javax.swing.JLabel();
        txt_diaChi = new components.TextFieldSuggestion();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        rdo_Nam = new javax.swing.JRadioButton();
        rdo_Nu = new javax.swing.JRadioButton();
        btnAdd = new components.ButtonCustom();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        materialTabbed4.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(204, 204, 204)));
        materialTabbed4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        txt_timKH.setLabelText("Tên khách hàng....");

        tblKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "ID", "Tên Khách Hàng", "Địa chỉ", "Số điện thoại", "Giới tính"
            }
        ));
        tblKH.setRowHeight(30);
        jScrollPane10.setViewportView(tblKH);

        btnChoose.setText("Chọn");
        btnChoose.setColor1(new java.awt.Color(204, 204, 204));
        btnChoose.setColor2(new java.awt.Color(153, 153, 153));
        btnChoose.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N

        pnaListCustomer.setBackground(new java.awt.Color(204, 204, 204));
        pnaListCustomer.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_timKH, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(pnaListCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(btnChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_timKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(pnaListCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        materialTabbed4.addTab("Danh sách khách hàng", jPanel13);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        txt_ID.setLabelText("ID khách hàng...");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setText("Tên khách hàng:");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setText("Số điện thoại:");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setText("Giới tính:");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setText("Địa chỉ:");

        buttonGroup1.add(rdo_Nam);
        rdo_Nam.setSelected(true);
        rdo_Nam.setText("Nam");

        buttonGroup1.add(rdo_Nu);
        rdo_Nu.setText("Nữ");

        btnAdd.setText("Thêm");
        btnAdd.setColor1(new java.awt.Color(204, 204, 204));
        btnAdd.setColor2(new java.awt.Color(0, 204, 255));
        btnAdd.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(txt_tenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_diaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(txt_SDT, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(rdo_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(rdo_Nu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(68, 68, 68))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(221, 221, 221)
                        .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(267, 267, 267)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_tenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_SDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_diaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdo_Nam)
                            .addComponent(rdo_Nu)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addGap(42, 42, 42)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        materialTabbed4.addTab("Thiết lập thông tin khách hàng", jPanel14);

        add(materialTabbed4, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        addCustomer();
    }//GEN-LAST:event_btnAddActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ViewChooseKhachHang dialog = new ViewChooseKhachHang(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.ButtonCustom btnAdd;
    private components.ButtonCustom btnChoose;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JScrollPane jScrollPane10;
    private components.MaterialTabbed materialTabbed4;
    private components.Pagination pnaListCustomer;
    private javax.swing.JRadioButton rdo_Nam;
    private javax.swing.JRadioButton rdo_Nu;
    private components.Table tblKH;
    private components.TextField txt_ID;
    private components.TextFieldSuggestion txt_SDT;
    private components.TextFieldSuggestion txt_diaChi;
    private components.TextFieldSuggestion txt_tenKH;
    private components.TextField txt_timKH;
    // End of variables declaration//GEN-END:variables
}
