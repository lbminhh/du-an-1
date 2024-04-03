/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package views;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import request.CustomerRequest;
import response.CustomerResponse;
import response.VoucherResponse;
import service.impl.CustomerServiceImpl;
import service.impl.VoucherServiceImpl;
import util.MoneyConverter;

/**
 *
 * @author LE MINH
 */
public class ViewSendEmail extends javax.swing.JDialog {

    private CustomerResponse customer = new CustomerResponse();
    private VoucherServiceImpl voucherServiceImpl = new VoucherServiceImpl();
    private CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
    private VoucherResponse itemVoucher = new VoucherResponse();

//    , CustomerResponse customerResponse
    /**
     * Creates new form ViewSendEmail
     */
    public ViewSendEmail(java.awt.Frame parent, boolean modal, String idCustomer) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        customer = customerServiceImpl.getCustomerById(idCustomer);
        System.out.println(customer);
        txtHoTen.setText(customer.getFullname());
        txtToEmail.setText(customer.getEmail());
        showDataVoucher();
        cbbVoucher.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String value = String.valueOf(cbbVoucher.getSelectedItem());
                VoucherResponse voucherResponse = new VoucherResponse();
                for (VoucherResponse item : voucherServiceImpl.getAllVoucherNoCustomer()) {
                    if (value.contains(item.getId())) {
                        voucherResponse = item;
                    }
                }
                setContent(voucherResponse);
            }
        });
    }

    private void showDataVoucher() {
        cbbVoucher.removeAllItems();
        cbbVoucher.addItem("");
        for (VoucherResponse item : voucherServiceImpl.getAllVoucherNoCustomer()) {
            if (item.getType().equalsIgnoreCase("Phần trăm")) {
                cbbVoucher.addItem(item.getId() + ":     " + item.getValue().setScale(0) + "%" + "     (" + item.getType() + ")");
            } else {
                cbbVoucher.addItem(item.getId() + ":     " + MoneyConverter.parse(item.getValue()) + "     (" + item.getType() + ")");
            }
        }

    }
    
    private CustomerRequest getDataCustomer(CustomerResponse customerResponse) {
        Long type = null;
        if (customerResponse.getTypeCustomer().equalsIgnoreCase("Khách lẻ")) {
            type = Long.valueOf(2);
        } else {
            type = Long.valueOf(1);
        }
        return new CustomerRequest(customerResponse.getId(), customerResponse.getFullname(),
                customerResponse.getPhoneNumber(), type, txtToEmail.getText());
    }

    private boolean validateEmail() {
        String email = txtToEmail.getText().trim();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập email");
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        if (!email.matches(emailRegex)) {
            JOptionPane.showMessageDialog(this, "Email không đúng định dạng!");
            return false;
        }
        return true;
    }

    private void setContent(VoucherResponse voucherResponse) {
        itemVoucher = voucherResponse;
        String messageVoucher = null;
        if (voucherResponse.getType().equalsIgnoreCase("Tiền")) {
            messageVoucher = MoneyConverter.parse(voucherResponse.getValue());
        } else {
            messageVoucher = String.valueOf(voucherResponse.getValue().setScale(0)) + "%";
        }
        String firstContent = "Xin chào " + customer.getFullname() + "!\n\n";
        String middleContent = "Chúc mừng quý khách đã mua đồ tại Sneaker Store. Chúng tôi tặng bạn voucher có giá trị " + messageVoucher + " với mã là " + voucherResponse.getId()
                + " áp dụng mọi hoá đơn mua tại cửa hàng, "
                + "áp dụng từ ngày " + voucherResponse.getTimeStart() + " đến ngày " + voucherResponse.getTimeEnd()
                + ". Sneaker Store xin cảm ơn quý khách. \n\n";
        String lastContent = "Have a great day!";
        txtContent.setText(firstContent + middleContent + lastContent);
    }

    private void sendEmail() {
        String ToEmail = txtToEmail.getText();
        String FromEmail = "lbaminh021124@gmail.com";//studyviral2@gmail.com
        String FromEmailPassword = "rcos drcn yuds xwlp";//You email Password from you want to send email
        String Subjects = "Sneaker Store cảm ơn!";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FromEmail, FromEmailPassword);
            }

        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(txtToEmail.getText())
            );
            message.setSubject(Subjects);
            message.setText(txtContent.getText());
            Transport.send(message);
            if (customerServiceImpl.resetNumberOfPurchase(customer.getId()) && voucherServiceImpl.getCustomerToVoucher(customer.getId(), itemVoucher.getId()) && customerServiceImpl.updateCustomer(getDataCustomer(customer))) {
                JOptionPane.showMessageDialog(this, "Đã gửi thành công!");
            }
            this.dispose();
        } catch (Exception ex) {
            System.out.println("" + ex);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtToEmail = new components.TextFieldSuggestion();
        jLabel3 = new javax.swing.JLabel();
        cbbVoucher = new components.Combobox();
        buttonCustom9 = new components.ButtonCustom();
        jLabel4 = new javax.swing.JLabel();
        textAreaScroll2 = new components.TextAreaScroll();
        txtContent = new components.TextArea();
        jLabel5 = new javax.swing.JLabel();
        txtHoTen = new components.TextFieldSuggestion();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("#Send email");

        jLabel2.setText("Email:");

        jLabel3.setText("Voucher:");

        cbbVoucher.setLabeText("");

        buttonCustom9.setText("Gửi");
        buttonCustom9.setColor1(new java.awt.Color(0, 255, 51));
        buttonCustom9.setColor2(new java.awt.Color(51, 255, 51));
        buttonCustom9.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        buttonCustom9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom9ActionPerformed(evt);
            }
        });

        jLabel4.setText("Nội dung:");

        textAreaScroll2.setLabelText("");

        txtContent.setColumns(20);
        txtContent.setRows(5);
        textAreaScroll2.setViewportView(txtContent);

        jLabel5.setText("Họ tên:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addComponent(textAreaScroll2, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbVoucher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtToEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHoTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(buttonCustom9, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtToEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbbVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textAreaScroll2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(buttonCustom9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCustom9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom9ActionPerformed
        // TODO add your handling code here:
        if (validateEmail()) {
            sendEmail();
        }
    }//GEN-LAST:event_buttonCustom9ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[], String idCustomer) {
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
            java.util.logging.Logger.getLogger(ViewSendEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewSendEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewSendEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewSendEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ViewSendEmail dialog = new ViewSendEmail(new javax.swing.JFrame(), true, idCustomer);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.ButtonCustom buttonCustom9;
    private components.Combobox cbbVoucher;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private components.TextAreaScroll textAreaScroll2;
    private components.TextArea txtContent;
    private components.TextFieldSuggestion txtHoTen;
    private components.TextFieldSuggestion txtToEmail;
    // End of variables declaration//GEN-END:variables
}
