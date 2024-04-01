/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import components.EventPagination;
import components.PaginationItemRenderStyle1;
import java.util.ArrayList;
import java.util.List;
import javax.management.Notification;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import request.CustomerHistoryRequest;
import request.CustomerHistorySearchRequest;
import request.CustomerRequest;
import request.CustomerSearchRequest;
import response.CustomerHistoryReponse;
import response.CustomerReponse;
import service.impl.CustomerServiceImpl;

/**
 *
 * @author LE MINH
 */
public class View extends javax.swing.JFrame {

    /**
     * Creates new form View
     */
    DefaultTableModel model = new DefaultTableModel();
    private List<CustomerReponse> listCustomers = new ArrayList<>();
    private List<CustomerReponse> listToTableCustomer = new ArrayList<>();
    private List<CustomerHistoryReponse> listCustomerHistory = new ArrayList<>();
    private List<CustomerHistoryReponse> listToTableCustomerHistory = new ArrayList<>();
    private CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

    public View() {
        initComponents();
        this.setLocationRelativeTo(null);
        setVisibleAllView();
        ViewBanHang.setVisible(true);
        pagination1.setPaginationItemRender(new PaginationItemRenderStyle1());
        pagination1.setPagegination(1, 10);
        btn_ThemTaiKhoan.setVisible(false);
        listCustomers = customerServiceImpl.getAllCustomer();
        showListCustomerByPage(1, listCustomers);
        setUpAllPagination();
        setAllEventComponent();

    }

    private void setUpAllPagination() {
        pagination1.setPaginationItemRender(new PaginationItemRenderStyle1());
        pnaListCustomer.setPaginationItemRender(new PaginationItemRenderStyle1());
        pnaListCustomerHistory.setPaginationItemRender(new PaginationItemRenderStyle1());
    }

    private void setVisibleAllView() {
        ViewBanHang.setVisible(false);
        ViewNhanVien.setVisible(false);
        ViewDoiMatKhau.setVisible(false);
        ViewHoaDon.setVisible(false);
        ViewKhuyenMai.setVisible(false);
        ViewThongKe.setVisible(false);
        ViewKhachHang.setVisible(false);
        ViewSanPham.setVisible(false);
    }

    //Start : Customer
    //Showlist
    private boolean checkStringEmpty(String value) {
        return value.trim().isEmpty();
    }

    private void showListCustomerByPage(int page, List<CustomerReponse> list) {
        int litmit = 6;
        int totalPage = (int) Math.ceil(((double) list.size() / litmit));
        model = (DefaultTableModel) tbl_Customer.getModel();
        model.setRowCount(0);
        pnaListCustomer.setPagegination(page, totalPage);
        listToTableCustomer.clear();
        for (int i = 0; i < listCustomers.size(); i++) {
            if (i >= (page - 1) * litmit && i <= (litmit * page) - 1) {
                CustomerReponse item = list.get(i);
                listToTableCustomer.add(item);
                model.addRow(new Object[]{
                    i + 1,
                    item.getId(),
                    item.getFullName(),
                    item.getDiaChi(),
                    item.getPhoneNumber(),
                    item.getGender() ? "Nam" : "Nữ",
                    item.getTime_create()
                });
            }
        }
    }
    
    private void showListCustomerHistoryByPage(int page, List<CustomerHistoryReponse> listh) {
        int litmit = 6;
        int totalPage = (int) Math.ceil(((double) listh.size() / litmit));
        model = (DefaultTableModel) tbl_CustomerHistory.getModel();
        model.setRowCount(0);
        pnaListCustomerHistory.setPagegination(page, totalPage);
        listToTableCustomerHistory.clear();
        for (int i = 0; i < listCustomerHistory.size(); i++) {
            if (i >= (page - 1) * litmit && i <= (litmit * page) - 1) {
                CustomerHistoryReponse item = listh.get(i);
                listToTableCustomerHistory.add(item);
                model.addRow(new Object[]{
                    i + 1,
                    item.getMaKh(),
                    item.getMaNv(),
                    item.getMaHd(),
                    item.getSdt(),
                    item.getTime_create(),
                    item.getTongTien(),
                    item.getStatus() ? "Đã thanh toán" : "Chưa Thanh toán"
                });
            }
        }
    }

    private void setAllEventComponent() {
        pnaListCustomer.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                showListCustomerByPage(page, listCustomers);
            }
        });
        pnaListCustomerHistory.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                showListCustomerHistoryByPage(page, listCustomerHistory);
            }
        });
        
        //------
        txt_TimIDKH.getDocument().addDocumentListener(new DocumentListener() {
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
        
        //--------
        txt_TimTenKH.getDocument().addDocumentListener(new DocumentListener() {
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
        
        //----------
        txt_TimLichSuGiaoDichTheoMaKH.getDocument().addDocumentListener(new DocumentListener() {
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
                listCustomerHistory = customerServiceImpl.searchListCustomerHistory(getDataCustomerHistory());
                showListCustomerHistoryByPage(1, listCustomerHistory);
            }
        });
    }

    private CustomerRequest getDataCustomer(String method) {
        String id = txt_IDKH.getText();
        if (checkStringEmpty(id)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ID Khách Hàng!");
            return null;
        }

        String full_name = txt_tenKH.getText().trim();
        if (checkStringEmpty(full_name)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Tên khách hàng!");
            return null;
        }

        String address = txt_DiaChi.getText().trim();
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
    
    private CustomerSearchRequest getDataSearchCustomer() {
        String ID = txt_TimIDKH.getText();
        String Name = txt_TimTenKH.getText();
        
        return new CustomerSearchRequest(ID, Name);
    }
     private CustomerHistorySearchRequest getDataCustomerHistory() {
        String MaKH = txt_TimLichSuGiaoDichTheoMaKH.getText();
        return new CustomerHistorySearchRequest(MaKH);
    }

    private void addCustomer() {
        if (customerServiceImpl.addCustomer(getDataCustomer("add"))) {
            try {
                if (checkTrungIDKH(txt_IDKH.getText())) {
                    JOptionPane.showMessageDialog(this, "ID đã có, vui lòng nhập một ID khác!");
                    return;
                }
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                    listCustomers = customerServiceImpl.getAllCustomer();
                    showListCustomerByPage(1, listCustomers);
                    clearFormKh();

            } catch (Exception e) {

                JOptionPane.showMessageDialog(this, "Lỗi thêm");
            }

        }
    }
    
    private void updateCustomer() {
        if (customerServiceImpl.updateCustomer(getDataCustomer("update"))) {
            try {
                
                JOptionPane.showMessageDialog(this, "Update thành công!");
                    listCustomers = customerServiceImpl.getAllCustomer();
                    showListCustomerByPage(1, listCustomers);
                    

            } catch (Exception e) {

                JOptionPane.showMessageDialog(this, "Lỗi update");
            }

        }
    }
    
    private void deleteCustomer(){
        String id = txt_IDKH.getText().trim();
        if (id.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Vui lÒng thử lại!");
        } else {
            if (customerServiceImpl.deleteCustomer(id)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                listCustomers = customerServiceImpl.getAllCustomer();
                showListCustomerByPage(1, listCustomers);
            }
        }
    }
    

    private void clearFormKh() {
        txt_IDKH.setText("");
        txt_tenKH.setText("");
        txt_DiaChi.setText("");
        txt_SDT.setText("");
        rdo_Nam.setSelected(true);
    }

    private void ShowCustomer() {
        int index = tbl_Customer.getSelectedRow();
        txt_IDKH.setText(tbl_Customer.getValueAt(index, 1).toString());
        txt_tenKH.setText(tbl_Customer.getValueAt(index, 2).toString());
        txt_DiaChi.setText(tbl_Customer.getValueAt(index, 3).toString());
        txt_SDT.setText(tbl_Customer.getValueAt(index, 4).toString());
        rdo_Nam.setSelected(tbl_Customer.getValueAt(index, 5).toString().equalsIgnoreCase("Nam") ? true : false);
        rdo_Nu.setSelected(!tbl_Customer.getValueAt(index, 5).toString().equalsIgnoreCase("Nam") ? true : false);

    }

    private boolean checkTrungIDKH(String IDKH) {
        for (int i = 0; i < tbl_Customer.getRowCount() - 1; i++) {
            if (tbl_Customer.getValueAt(i, 1).toString().equalsIgnoreCase(IDKH)) {
                return true;
            }
        }
        return false;
    }

    //End : Customer
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        btnShowViewBanHang = new javax.swing.JLabel();
        btnShowViewSanPham = new javax.swing.JLabel();
        btnShowViewNhanVien = new javax.swing.JLabel();
        btnShowViewKhachHang = new javax.swing.JLabel();
        btnShowViewThongKe = new javax.swing.JLabel();
        btnShowViewDoiMatKhau = new javax.swing.JLabel();
        btnShowViewHoaDon = new javax.swing.JLabel();
        btnShowViewBanHang1 = new javax.swing.JLabel();
        btnShowViewDoiMatKhau1 = new javax.swing.JLabel();
        btnShowViewBanHang2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnShowViewBanHang3 = new javax.swing.JLabel();
        btnShowViewBanHang4 = new javax.swing.JLabel();
        btnShowViewBanHang5 = new javax.swing.JLabel();
        btnShowViewBanHang6 = new javax.swing.JLabel();
        btnShowViewBanHang7 = new javax.swing.JLabel();
        btnShowViewBanHang8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        ViewBanHang = new javax.swing.JPanel();
        tabbedPaneCustom1 = new components.TabbedPaneCustom();
        jPanel4 = new javax.swing.JPanel();
        buttonCustom1 = new components.ButtonCustom();
        buttonCustom3 = new components.ButtonCustom();
        buttonCustom4 = new components.ButtonCustom();
        buttonCustom5 = new components.ButtonCustom();
        jScrollPane4 = new javax.swing.JScrollPane();
        table2 = new components.Table();
        panelRound1 = new components.PanelRound();
        textField1 = new components.TextField();
        textField5 = new components.TextField();
        buttonCustom8 = new components.ButtonCustom();
        textField6 = new components.TextField();
        textField7 = new components.TextField();
        textField8 = new components.TextField();
        textField9 = new components.TextField();
        textField10 = new components.TextField();
        combobox5 = new components.Combobox();
        textField11 = new components.TextField();
        textField12 = new components.TextField();
        textField13 = new components.TextField();
        buttonCustom9 = new components.ButtonCustom();
        tabbedPaneCustom2 = new components.TabbedPaneCustom();
        jPanel6 = new javax.swing.JPanel();
        buttonCustom6 = new components.ButtonCustom();
        buttonCustom7 = new components.ButtonCustom();
        jScrollPane2 = new javax.swing.JScrollPane();
        table1 = new components.Table();
        tabbedPaneCustom3 = new components.TabbedPaneCustom();
        jPanel7 = new javax.swing.JPanel();
        pagination1 = new components.Pagination();
        combobox1 = new components.Combobox();
        combobox2 = new components.Combobox();
        combobox3 = new components.Combobox();
        combobox4 = new components.Combobox();
        textField4 = new components.TextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        table3 = new components.Table();
        ViewSanPham = new javax.swing.JPanel();
        materialTabbed1 = new components.MaterialTabbed();
        jPanel5 = new javax.swing.JPanel();
        textFieldSuggestion1 = new components.TextFieldSuggestion();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textFieldSuggestion2 = new components.TextFieldSuggestion();
        jLabel3 = new javax.swing.JLabel();
        textAreaScroll1 = new components.TextAreaScroll();
        textArea2 = new components.TextArea();
        jLabel4 = new javax.swing.JLabel();
        combobox6 = new components.Combobox();
        buttonCustom2 = new components.ButtonCustom();
        buttonCustom13 = new components.ButtonCustom();
        buttonCustom11 = new components.ButtonCustom();
        buttonCustom10 = new components.ButtonCustom();
        jLabel14 = new javax.swing.JLabel();
        combobox13 = new components.Combobox();
        jLabel15 = new javax.swing.JLabel();
        combobox14 = new components.Combobox();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        textFieldSuggestion3 = new components.TextFieldSuggestion();
        jLabel7 = new javax.swing.JLabel();
        textFieldSuggestion4 = new components.TextFieldSuggestion();
        textFieldSuggestion5 = new components.TextFieldSuggestion();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        combobox8 = new components.Combobox();
        jLabel10 = new javax.swing.JLabel();
        combobox9 = new components.Combobox();
        jLabel11 = new javax.swing.JLabel();
        combobox10 = new components.Combobox();
        jLabel12 = new javax.swing.JLabel();
        combobox11 = new components.Combobox();
        btnShowViewSole = new components.ButtonCustom();
        btnShowViewSize = new components.ButtonCustom();
        btnShowViewColor = new components.ButtonCustom();
        btnShowViewMaterial = new components.ButtonCustom();
        jLabel13 = new javax.swing.JLabel();
        buttonCustom12 = new components.ButtonCustom();
        buttonCustom14 = new components.ButtonCustom();
        buttonCustom15 = new components.ButtonCustom();
        jLabel16 = new javax.swing.JLabel();
        textFieldSuggestion6 = new components.TextFieldSuggestion();
        jScrollPane7 = new javax.swing.JScrollPane();
        table6 = new components.Table();
        jLabel8 = new javax.swing.JLabel();
        textField15 = new components.TextField();
        combobox12 = new components.Combobox();
        pagination3 = new components.Pagination();
        ViewNhanVien = new javax.swing.JPanel();
        materialTabbed5 = new components.MaterialTabbed();
        jPanel11 = new javax.swing.JPanel();
        textFieldSuggestion13 = new components.TextFieldSuggestion();
        jLabel18 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        textFieldSuggestion14 = new components.TextFieldSuggestion();
        jLabel34 = new javax.swing.JLabel();
        textAreaScroll5 = new components.TextAreaScroll();
        textArea6 = new components.TextArea();
        btn_ThemTaiKhoan = new components.ButtonCustom();
        buttonCustom29 = new components.ButtonCustom();
        buttonCustom30 = new components.ButtonCustom();
        buttonCustom31 = new components.ButtonCustom();
        textFieldSuggestion23 = new components.TextFieldSuggestion();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        btn_ThemNhanVien = new components.ButtonCustom();
        materialTabbed6 = new components.MaterialTabbed();
        jPanel16 = new javax.swing.JPanel();
        textField21 = new components.TextField();
        jScrollPane13 = new javax.swing.JScrollPane();
        table12 = new components.Table();
        jPanel17 = new javax.swing.JPanel();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        ViewThongKe = new javax.swing.JPanel();
        ViewKhachHang = new javax.swing.JPanel();
        materialTabbed3 = new components.MaterialTabbed();
        jPanel9 = new javax.swing.JPanel();
        txt_IDKH = new components.TextFieldSuggestion();
        jLabel17 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txt_tenKH = new components.TextFieldSuggestion();
        jLabel29 = new javax.swing.JLabel();
        textAreaScroll4 = new components.TextAreaScroll();
        txt_DiaChi = new components.TextArea();
        btnAdd = new components.ButtonCustom();
        btnUpdate = new components.ButtonCustom();
        btnDelete = new components.ButtonCustom();
        btnNew = new components.ButtonCustom();
        txt_SDT = new components.TextFieldSuggestion();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        rdo_Nam = new javax.swing.JRadioButton();
        rdo_Nu = new javax.swing.JRadioButton();
        materialTabbed4 = new components.MaterialTabbed();
        jPanel13 = new javax.swing.JPanel();
        txt_TimIDKH = new components.TextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbl_Customer = new components.Table();
        pnaListCustomer = new components.Pagination();
        txt_TimTenKH = new components.TextField();
        jPanel14 = new javax.swing.JPanel();
        txt_TimLichSuGiaoDichTheoMaKH = new components.TextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        tbl_CustomerHistory = new components.Table();
        pnaListCustomerHistory = new components.Pagination();
        ViewDoiMatKhau = new javax.swing.JPanel();
        ViewKhuyenMai = new javax.swing.JPanel();
        ViewHoaDon = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(30, 144, 255));

        btnShowViewBanHang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewBanHang.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewBanHang.setText("Bán hàng");
        btnShowViewBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewBanHangMouseClicked(evt);
            }
        });

        btnShowViewSanPham.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewSanPham.setText("Sản phẩm");
        btnShowViewSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewSanPhamMouseClicked(evt);
            }
        });

        btnShowViewNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewNhanVien.setText("Nhân viên");
        btnShowViewNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewNhanVienMouseClicked(evt);
            }
        });

        btnShowViewKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewKhachHang.setText("Khách hàng");
        btnShowViewKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewKhachHangMouseClicked(evt);
            }
        });

        btnShowViewThongKe.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewThongKe.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewThongKe.setText("Thống kê");
        btnShowViewThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewThongKeMouseClicked(evt);
            }
        });

        btnShowViewDoiMatKhau.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewDoiMatKhau.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewDoiMatKhau.setText("Đổi mật khẩu");
        btnShowViewDoiMatKhau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewDoiMatKhauMouseClicked(evt);
            }
        });

        btnShowViewHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewHoaDon.setText("Hoá đơn");
        btnShowViewHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewHoaDonMouseClicked(evt);
            }
        });

        btnShowViewBanHang1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewBanHang1.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewBanHang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-staff-30.png"))); // NOI18N
        btnShowViewBanHang1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewBanHang1MouseClicked(evt);
            }
        });

        btnShowViewDoiMatKhau1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewDoiMatKhau1.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewDoiMatKhau1.setText("Đăng xuất");
        btnShowViewDoiMatKhau1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewDoiMatKhau1MouseClicked(evt);
            }
        });

        btnShowViewBanHang2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewBanHang2.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewBanHang2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-bonds-30.png"))); // NOI18N
        btnShowViewBanHang2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewBanHang2MouseClicked(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo.jpg"))); // NOI18N

        btnShowViewBanHang3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewBanHang3.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewBanHang3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-staff-30.png"))); // NOI18N
        btnShowViewBanHang3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewBanHang3MouseClicked(evt);
            }
        });

        btnShowViewBanHang4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewBanHang4.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewBanHang4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-staff-30.png"))); // NOI18N
        btnShowViewBanHang4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewBanHang4MouseClicked(evt);
            }
        });

        btnShowViewBanHang5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewBanHang5.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewBanHang5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-staff-30.png"))); // NOI18N
        btnShowViewBanHang5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewBanHang5MouseClicked(evt);
            }
        });

        btnShowViewBanHang6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewBanHang6.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewBanHang6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-staff-30.png"))); // NOI18N
        btnShowViewBanHang6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewBanHang6MouseClicked(evt);
            }
        });

        btnShowViewBanHang7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewBanHang7.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewBanHang7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-load-38.png"))); // NOI18N
        btnShowViewBanHang7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewBanHang7MouseClicked(evt);
            }
        });

        btnShowViewBanHang8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewBanHang8.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewBanHang8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-staff-30.png"))); // NOI18N
        btnShowViewBanHang8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewBanHang8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnShowViewBanHang1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnShowViewBanHang2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnShowViewBanHang3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnShowViewBanHang4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnShowViewBanHang5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnShowViewBanHang6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnShowViewBanHang7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnShowViewBanHang8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnShowViewNhanVien)
                    .addComponent(btnShowViewThongKe)
                    .addComponent(btnShowViewHoaDon)
                    .addComponent(btnShowViewDoiMatKhau)
                    .addComponent(btnShowViewDoiMatKhau1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnShowViewBanHang)
                        .addComponent(btnShowViewSanPham))
                    .addComponent(btnShowViewKhachHang))
                .addGap(53, 53, 53))
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnShowViewBanHang)
                                            .addComponent(btnShowViewBanHang2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(44, 44, 44)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnShowViewSanPham)
                                            .addComponent(btnShowViewBanHang3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(50, 50, 50)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnShowViewKhachHang)
                                            .addComponent(btnShowViewBanHang1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(55, 55, 55)
                                        .addComponent(btnShowViewNhanVien))
                                    .addComponent(btnShowViewBanHang4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnShowViewThongKe)
                                    .addComponent(btnShowViewBanHang5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(52, 52, 52)
                                .addComponent(btnShowViewHoaDon))
                            .addComponent(btnShowViewBanHang6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addComponent(btnShowViewDoiMatKhau))
                    .addComponent(btnShowViewBanHang7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnShowViewDoiMatKhau1)
                    .addComponent(btnShowViewBanHang8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 270, 800));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new javax.swing.OverlayLayout(jPanel2));

        ViewBanHang.setBackground(new java.awt.Color(255, 255, 255));

        tabbedPaneCustom1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tabbedPaneCustom1.setForeground(new java.awt.Color(255, 255, 255));
        tabbedPaneCustom1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        buttonCustom1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-plus-38 (1).png"))); // NOI18N
        buttonCustom1.setColor1(new java.awt.Color(102, 102, 102));
        buttonCustom1.setColor2(new java.awt.Color(102, 102, 102));

        buttonCustom3.setText("Huỷ hoá đơn");
        buttonCustom3.setColor1(new java.awt.Color(255, 51, 102));
        buttonCustom3.setColor2(new java.awt.Color(255, 0, 51));
        buttonCustom3.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N

        buttonCustom4.setText("Làm mới");
        buttonCustom4.setColor1(new java.awt.Color(0, 153, 255));
        buttonCustom4.setColor2(new java.awt.Color(0, 102, 255));
        buttonCustom4.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N

        buttonCustom5.setText("Chọn voucher");
        buttonCustom5.setColor1(new java.awt.Color(0, 153, 255));
        buttonCustom5.setColor2(new java.awt.Color(0, 102, 255));
        buttonCustom5.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N

        table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table2.setRowHeight(30);
        jScrollPane4.setViewportView(table2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonCustom4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 486, Short.MAX_VALUE)
                .addComponent(buttonCustom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCustom5, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane4)
                    .addContainerGap()))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonCustom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonCustom4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonCustom5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(140, 140, 140))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addContainerGap(59, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(1, 1, 1)))
        );

        tabbedPaneCustom1.addTab("Hoá đơn đang chờ", jPanel4);

        panelRound1.setBackground(new java.awt.Color(255, 255, 255));
        panelRound1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đơn hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP));

        textField1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        textField1.setLabelText("Mã hoá đơn:");

        textField5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        textField5.setLabelText("Mã khách hàng:");

        buttonCustom8.setText("Chọn");
        buttonCustom8.setColor1(new java.awt.Color(255, 51, 102));
        buttonCustom8.setColor2(new java.awt.Color(255, 0, 51));
        buttonCustom8.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        buttonCustom8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom8ActionPerformed(evt);
            }
        });

        textField6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        textField6.setLabelText("Số điện thoại:");

        textField7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        textField7.setLabelText("Họ và tên:");

        textField8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        textField8.setLabelText("Tạm tính:");

        textField9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        textField9.setLabelText("Số tiền được giảm:");

        textField10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        textField10.setLabelText("Tổng tiền:");

        combobox5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        combobox5.setLabeText("Hình thức thanh toán");
        combobox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobox5ActionPerformed(evt);
            }
        });

        textField11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        textField11.setLabelText("Tiền mặt:");

        textField12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        textField12.setLabelText("Chuyển khoản:");

        textField13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        textField13.setLabelText("Tiền trả lại:");

        buttonCustom9.setText("Thanh toán");
        buttonCustom9.setColor1(new java.awt.Color(0, 255, 51));
        buttonCustom9.setColor2(new java.awt.Color(51, 255, 51));
        buttonCustom9.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelRound1Layout.createSequentialGroup()
                                .addComponent(textField5, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonCustom8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(textField6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textField7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textField8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textField9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textField10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combobox5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(textField11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textField12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textField13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(buttonCustom9, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCustom8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(combobox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(buttonCustom9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        tabbedPaneCustom2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tabbedPaneCustom2.setForeground(new java.awt.Color(255, 255, 255));
        tabbedPaneCustom2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        buttonCustom6.setText("Xoá");
        buttonCustom6.setColor1(new java.awt.Color(255, 51, 102));
        buttonCustom6.setColor2(new java.awt.Color(255, 0, 51));
        buttonCustom6.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N

        buttonCustom7.setText("Sửa số lượng");
        buttonCustom7.setColor1(new java.awt.Color(0, 153, 255));
        buttonCustom7.setColor2(new java.awt.Color(0, 102, 255));
        buttonCustom7.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table1.setRowHeight(30);
        jScrollPane2.setViewportView(table1);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 668, Short.MAX_VALUE)
                        .addComponent(buttonCustom7, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonCustom6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCustom6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCustom7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
        );

        tabbedPaneCustom2.addTab("Giỏ hàng", jPanel6);

        tabbedPaneCustom3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tabbedPaneCustom3.setForeground(new java.awt.Color(255, 255, 255));
        tabbedPaneCustom3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        pagination1.setBackground(new java.awt.Color(204, 204, 204));
        pagination1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        combobox1.setLabeText("Màu sắc");
        combobox1.setLineColor(new java.awt.Color(255, 0, 0));
        combobox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobox1ActionPerformed(evt);
            }
        });

        combobox2.setLabeText("Kích cỡ");
        combobox2.setLineColor(new java.awt.Color(255, 0, 0));

        combobox3.setLabeText("Chất liệu");
        combobox3.setLineColor(new java.awt.Color(255, 0, 0));
        combobox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobox3ActionPerformed(evt);
            }
        });

        combobox4.setLabeText("Hãng");
        combobox4.setLineColor(new java.awt.Color(255, 0, 0));

        textField4.setLabelText("Tên sản phẩm");

        table3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table3.setRowHeight(30);
        jScrollPane5.setViewportView(table3);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(combobox4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combobox2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combobox3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(combobox1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                        .addComponent(textField4, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane5)
                    .addContainerGap()))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combobox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combobox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combobox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combobox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(190, 190, 190)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(64, 64, 64)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(75, Short.MAX_VALUE)))
        );

        tabbedPaneCustom3.addTab("Sản phẩm", jPanel7);

        javax.swing.GroupLayout ViewBanHangLayout = new javax.swing.GroupLayout(ViewBanHang);
        ViewBanHang.setLayout(ViewBanHangLayout);
        ViewBanHangLayout.setHorizontalGroup(
            ViewBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewBanHangLayout.createSequentialGroup()
                .addGroup(ViewBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewBanHangLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(ViewBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tabbedPaneCustom2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tabbedPaneCustom3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewBanHangLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 872, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(247, Short.MAX_VALUE))
        );
        ViewBanHangLayout.setVerticalGroup(
            ViewBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewBanHangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ViewBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(ViewBanHangLayout.createSequentialGroup()
                        .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tabbedPaneCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabbedPaneCustom3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2.add(ViewBanHang);

        ViewSanPham.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Mã sản phẩm:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Tên sản phẩm:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Mô tả:");

        textAreaScroll1.setLabelText("Description...");

        textArea2.setColumns(20);
        textArea2.setRows(5);
        textAreaScroll1.setViewportView(textArea2);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Hãng:");

        combobox6.setLabeText("");

        buttonCustom2.setText("Thêm");
        buttonCustom2.setColor1(new java.awt.Color(0, 255, 51));
        buttonCustom2.setColor2(new java.awt.Color(51, 255, 51));
        buttonCustom2.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N

        buttonCustom13.setText("Sửa");
        buttonCustom13.setColor1(new java.awt.Color(255, 204, 0));
        buttonCustom13.setColor2(new java.awt.Color(255, 255, 0));
        buttonCustom13.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N

        buttonCustom11.setText("Xoá");
        buttonCustom11.setColor1(new java.awt.Color(255, 51, 102));
        buttonCustom11.setColor2(new java.awt.Color(255, 0, 51));
        buttonCustom11.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N

        buttonCustom10.setText("Làm mới");
        buttonCustom10.setColor1(new java.awt.Color(0, 153, 255));
        buttonCustom10.setColor2(new java.awt.Color(0, 102, 255));
        buttonCustom10.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Thể loại:");

        combobox13.setLabeText("Xuất xứ");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Xuất xứ:");

        combobox14.setLabeText("Thể loại");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(textFieldSuggestion1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(textFieldSuggestion2, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addComponent(combobox6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(74, 74, 74)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(textAreaScroll1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                                .addComponent(combobox13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel15))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGap(75, 75, 75)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(buttonCustom11, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(72, 72, 72)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(buttonCustom10, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonCustom13, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel14)
                                    .addGap(287, 287, 287)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(combobox14, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)))))
                .addGap(38, 38, 38))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel15))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textFieldSuggestion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combobox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(textFieldSuggestion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(combobox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(textAreaScroll1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combobox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonCustom13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonCustom11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonCustom10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        materialTabbed1.addTab("Sản phẩm", jPanel5);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Tên sản phẩm:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Giá nhập:");

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Thuộc tính"));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Màu sắc:");

        combobox8.setLabeText("Màu sắc");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Kích cỡ:");

        combobox9.setLabeText("Size");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Chất liệu:");

        combobox10.setLabeText("Đế giày");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Đế giày:");

        combobox11.setLabeText("Chất liệu");

        btnShowViewSole.setText("Xem");
        btnShowViewSole.setColor1(new java.awt.Color(0, 0, 255));
        btnShowViewSole.setColor2(new java.awt.Color(0, 102, 255));
        btnShowViewSole.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnShowViewSole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowViewSoleActionPerformed(evt);
            }
        });

        btnShowViewSize.setText("Xem");
        btnShowViewSize.setColor1(new java.awt.Color(0, 0, 255));
        btnShowViewSize.setColor2(new java.awt.Color(0, 102, 255));
        btnShowViewSize.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnShowViewSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowViewSizeActionPerformed(evt);
            }
        });

        btnShowViewColor.setText("Xem");
        btnShowViewColor.setColor1(new java.awt.Color(0, 0, 255));
        btnShowViewColor.setColor2(new java.awt.Color(0, 102, 255));
        btnShowViewColor.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnShowViewColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowViewColorActionPerformed(evt);
            }
        });

        btnShowViewMaterial.setText("Xem");
        btnShowViewMaterial.setColor1(new java.awt.Color(0, 0, 255));
        btnShowViewMaterial.setColor2(new java.awt.Color(0, 102, 255));
        btnShowViewMaterial.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnShowViewMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowViewMaterialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(combobox10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(combobox8, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                            .addComponent(combobox9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(combobox11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnShowViewSole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShowViewSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShowViewColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShowViewMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combobox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(btnShowViewColor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combobox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(btnShowViewSize, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combobox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(btnShowViewSole, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combobox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(btnShowViewMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Giá bán:");

        buttonCustom12.setText("Lưu");
        buttonCustom12.setColor1(new java.awt.Color(0, 255, 51));
        buttonCustom12.setColor2(new java.awt.Color(51, 255, 51));
        buttonCustom12.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N

        buttonCustom14.setText("Thêm");
        buttonCustom14.setColor1(new java.awt.Color(0, 255, 51));
        buttonCustom14.setColor2(new java.awt.Color(51, 255, 51));
        buttonCustom14.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        buttonCustom14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom14ActionPerformed(evt);
            }
        });

        buttonCustom15.setText("Làm mới");
        buttonCustom15.setColor1(new java.awt.Color(0, 153, 255));
        buttonCustom15.setColor2(new java.awt.Color(0, 102, 255));
        buttonCustom15.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Số lượng:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(textFieldSuggestion3, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                    .addComponent(textFieldSuggestion4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldSuggestion5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel16)
                    .addComponent(textFieldSuggestion6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonCustom14, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCustom15, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(316, 316, 316)
                .addComponent(buttonCustom12, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldSuggestion3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldSuggestion4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldSuggestion5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldSuggestion6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118)
                        .addComponent(buttonCustom12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(buttonCustom14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonCustom15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        materialTabbed1.addTab("Chi tiết sản phẩm", jPanel8);

        table6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table6.setRowHeight(30);
        table6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table6MouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(table6);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("#Danh sách sản phẩm");

        textField15.setLabelText("Tìm tên sản phẩm...");

        combobox12.setLabeText("Hãng");
        combobox12.setLineColor(new java.awt.Color(255, 0, 0));

        pagination3.setBackground(new java.awt.Color(204, 204, 204));
        pagination3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout ViewSanPhamLayout = new javax.swing.GroupLayout(ViewSanPham);
        ViewSanPham.setLayout(ViewSanPhamLayout);
        ViewSanPhamLayout.setHorizontalGroup(
            ViewSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewSanPhamLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(ViewSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pagination3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ViewSanPhamLayout.createSequentialGroup()
                        .addComponent(textField15, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(combobox12, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 1188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(materialTabbed1, javax.swing.GroupLayout.PREFERRED_SIZE, 1170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(212, Short.MAX_VALUE))
        );
        ViewSanPhamLayout.setVerticalGroup(
            ViewSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewSanPhamLayout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(materialTabbed1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ViewSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combobox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pagination3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jPanel2.add(ViewSanPham);

        ViewNhanVien.setBackground(new java.awt.Color(255, 255, 255));

        materialTabbed5.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(204, 204, 204)));
        materialTabbed5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setText("Họ tên nhân viên:");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setText("Ngày sinh:");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setText("Địa chỉ :");

        textAreaScroll5.setLabelText("Description...");

        textArea6.setColumns(20);
        textArea6.setRows(5);
        textAreaScroll5.setViewportView(textArea6);

        btn_ThemTaiKhoan.setText("Thêm tài khoản");
        btn_ThemTaiKhoan.setColor1(new java.awt.Color(0, 255, 51));
        btn_ThemTaiKhoan.setColor2(new java.awt.Color(51, 255, 51));
        btn_ThemTaiKhoan.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btn_ThemTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemTaiKhoanActionPerformed(evt);
            }
        });

        buttonCustom29.setText("Sửa");
        buttonCustom29.setColor1(new java.awt.Color(255, 204, 0));
        buttonCustom29.setColor2(new java.awt.Color(255, 255, 0));
        buttonCustom29.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N

        buttonCustom30.setText("Xoá");
        buttonCustom30.setColor1(new java.awt.Color(255, 51, 102));
        buttonCustom30.setColor2(new java.awt.Color(255, 0, 51));
        buttonCustom30.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N

        buttonCustom31.setText("Làm mới");
        buttonCustom31.setColor1(new java.awt.Color(0, 153, 255));
        buttonCustom31.setColor2(new java.awt.Color(0, 102, 255));
        buttonCustom31.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        buttonCustom31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom31ActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setText("Số điện thoại:");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel36.setText("Giới tính:");

        jRadioButton3.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Nam");

        jRadioButton4.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("Nữ");

        btn_ThemNhanVien.setText("Thêm");
        btn_ThemNhanVien.setColor1(new java.awt.Color(0, 255, 51));
        btn_ThemNhanVien.setColor2(new java.awt.Color(51, 255, 51));
        btn_ThemNhanVien.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btn_ThemNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18)
                    .addComponent(textFieldSuggestion13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel33)
                    .addComponent(textFieldSuggestion14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel35)
                    .addComponent(textFieldSuggestion23, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(98, 98, 98)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textAreaScroll5, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addGap(32, 32, 32)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton3)
                            .addComponent(jRadioButton4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                .addComponent(btn_ThemTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonCustom30, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonCustom31, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonCustom29, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btn_ThemNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(89, 89, 89))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(jRadioButton3))
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(textFieldSuggestion13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldSuggestion14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldSuggestion23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textAreaScroll5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_ThemNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_ThemTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonCustom29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonCustom31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonCustom30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        materialTabbed5.addTab("#Thiết lập thông tin nhân viên", jPanel11);

        materialTabbed6.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(204, 204, 204)));
        materialTabbed6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        textField21.setLabelText("Tìm theo tên nhân viên...");

        table12.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table12.setRowHeight(30);
        jScrollPane13.setViewportView(table12);

        jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, null, new java.awt.Color(255, 255, 255)));

        jRadioButton5.setText("Đang làm ");

        jRadioButton6.setText("Tất cả");
        jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton6ActionPerformed(evt);
            }
        });

        jRadioButton7.setText("Đã nghỉ");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jRadioButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton5)
                    .addComponent(jRadioButton6)
                    .addComponent(jRadioButton7))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(textField21, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 1188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textField21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        materialTabbed6.addTab("Thông tin nhân viên", jPanel16);

        javax.swing.GroupLayout ViewNhanVienLayout = new javax.swing.GroupLayout(ViewNhanVien);
        ViewNhanVien.setLayout(ViewNhanVienLayout);
        ViewNhanVienLayout.setHorizontalGroup(
            ViewNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewNhanVienLayout.createSequentialGroup()
                .addContainerGap(204, Short.MAX_VALUE)
                .addGroup(ViewNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(materialTabbed6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(materialTabbed5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7))
        );
        ViewNhanVienLayout.setVerticalGroup(
            ViewNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewNhanVienLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(materialTabbed5, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(materialTabbed6, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        jPanel2.add(ViewNhanVien);

        ViewThongKe.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout ViewThongKeLayout = new javax.swing.GroupLayout(ViewThongKe);
        ViewThongKe.setLayout(ViewThongKeLayout);
        ViewThongKeLayout.setHorizontalGroup(
            ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1420, Short.MAX_VALUE)
        );
        ViewThongKeLayout.setVerticalGroup(
            ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 836, Short.MAX_VALUE)
        );

        jPanel2.add(ViewThongKe);

        ViewKhachHang.setBackground(new java.awt.Color(255, 255, 255));

        materialTabbed3.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(204, 204, 204)));
        materialTabbed3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("Mã khách hàng:");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Tên khách hàng:");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setText("Địa chỉ :");

        textAreaScroll4.setLabelText("Description...");

        txt_DiaChi.setColumns(20);
        txt_DiaChi.setRows(5);
        textAreaScroll4.setViewportView(txt_DiaChi);

        btnAdd.setText("Thêm");
        btnAdd.setColor1(new java.awt.Color(0, 255, 51));
        btnAdd.setColor2(new java.awt.Color(51, 255, 51));
        btnAdd.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Sửa");
        btnUpdate.setColor1(new java.awt.Color(255, 204, 0));
        btnUpdate.setColor2(new java.awt.Color(255, 255, 0));
        btnUpdate.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Xoá");
        btnDelete.setColor1(new java.awt.Color(255, 51, 102));
        btnDelete.setColor2(new java.awt.Color(255, 0, 51));
        btnDelete.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnNew.setText("Làm mới");
        btnNew.setColor1(new java.awt.Color(0, 153, 255));
        btnNew.setColor2(new java.awt.Color(0, 102, 255));
        btnNew.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setText("Số điện thoại:");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setText("Giới tính:");

        rdo_Nam.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdo_Nam);
        rdo_Nam.setText("Nam");

        rdo_Nu.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdo_Nu);
        rdo_Nu.setText("Nữ");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel17)
                        .addComponent(txt_IDKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel28)
                        .addComponent(txt_tenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel30)
                    .addComponent(txt_SDT, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(rdo_Nam)
                        .addGap(78, 78, 78)
                        .addComponent(rdo_Nu))
                    .addComponent(jLabel29)
                    .addComponent(textAreaScroll4, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(319, 319, 319)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(0, 3, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(286, 286, 286))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(152, 152, 152)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(txt_IDKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_tenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_SDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdo_Nam)
                                    .addComponent(rdo_Nu))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textAreaScroll4, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        materialTabbed3.addTab("#Thiết lập thông tin khách hàng", jPanel9);

        materialTabbed4.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(204, 204, 204)));
        materialTabbed4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        txt_TimIDKH.setLabelText("ID khách hàng...");

        tbl_Customer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "ID", "Tên Khách Hàng", "Địa Chỉ", "Số Điện Thoại", "Giới Tính", "Time_create"
            }
        ));
        tbl_Customer.setRowHeight(30);
        tbl_Customer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_CustomerMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tbl_Customer);

        pnaListCustomer.setBackground(new java.awt.Color(204, 204, 204));
        pnaListCustomer.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txt_TimTenKH.setLabelText("Tên khách hàng...");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(txt_TimIDKH, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_TimTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 1188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnaListCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(204, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_TimIDKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_TimTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnaListCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        materialTabbed4.addTab("Thông tin khách hàng", jPanel13);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        txt_TimLichSuGiaoDichTheoMaKH.setLabelText("Mã khách hàng");
        txt_TimLichSuGiaoDichTheoMaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TimLichSuGiaoDichTheoMaKHActionPerformed(evt);
            }
        });

        tbl_CustomerHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã khách hàng", "Mã Nhân Viên", "Mã hóa đơn", "Số điện thoại", "Ngày giao dịch", "Tổng tiền", "Trạng thái"
            }
        ));
        tbl_CustomerHistory.setRowHeight(30);
        jScrollPane11.setViewportView(tbl_CustomerHistory);

        pnaListCustomerHistory.setBackground(new java.awt.Color(204, 204, 204));
        pnaListCustomerHistory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 1188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_TimLichSuGiaoDichTheoMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnaListCustomerHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(204, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_TimLichSuGiaoDichTheoMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnaListCustomerHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        materialTabbed4.addTab("Lịch sử giao dịch", jPanel14);

        javax.swing.GroupLayout ViewKhachHangLayout = new javax.swing.GroupLayout(ViewKhachHang);
        ViewKhachHang.setLayout(ViewKhachHangLayout);
        ViewKhachHangLayout.setHorizontalGroup(
            ViewKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewKhachHangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(ViewKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(materialTabbed4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(materialTabbed3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7))
        );
        ViewKhachHangLayout.setVerticalGroup(
            ViewKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewKhachHangLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(materialTabbed3, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(materialTabbed4, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        jPanel2.add(ViewKhachHang);

        ViewDoiMatKhau.setBackground(new java.awt.Color(102, 255, 0));

        javax.swing.GroupLayout ViewDoiMatKhauLayout = new javax.swing.GroupLayout(ViewDoiMatKhau);
        ViewDoiMatKhau.setLayout(ViewDoiMatKhauLayout);
        ViewDoiMatKhauLayout.setHorizontalGroup(
            ViewDoiMatKhauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1420, Short.MAX_VALUE)
        );
        ViewDoiMatKhauLayout.setVerticalGroup(
            ViewDoiMatKhauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 836, Short.MAX_VALUE)
        );

        jPanel2.add(ViewDoiMatKhau);

        ViewKhuyenMai.setBackground(new java.awt.Color(255, 102, 255));

        javax.swing.GroupLayout ViewKhuyenMaiLayout = new javax.swing.GroupLayout(ViewKhuyenMai);
        ViewKhuyenMai.setLayout(ViewKhuyenMaiLayout);
        ViewKhuyenMaiLayout.setHorizontalGroup(
            ViewKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1420, Short.MAX_VALUE)
        );
        ViewKhuyenMaiLayout.setVerticalGroup(
            ViewKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 836, Short.MAX_VALUE)
        );

        jPanel2.add(ViewKhuyenMai);

        ViewHoaDon.setBackground(new java.awt.Color(204, 102, 0));

        javax.swing.GroupLayout ViewHoaDonLayout = new javax.swing.GroupLayout(ViewHoaDon);
        ViewHoaDon.setLayout(ViewHoaDonLayout);
        ViewHoaDonLayout.setHorizontalGroup(
            ViewHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1420, Short.MAX_VALUE)
        );
        ViewHoaDonLayout.setVerticalGroup(
            ViewHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 836, Short.MAX_VALUE)
        );

        jPanel2.add(ViewHoaDon);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 1230, 800));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnShowViewBanHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewBanHangMouseClicked
        // TODO add your handling code here:
        setVisibleAllView();
        ViewBanHang.setVisible(true);
    }//GEN-LAST:event_btnShowViewBanHangMouseClicked

    private void btnShowViewSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewSanPhamMouseClicked
        // TODO add your handling code here:
        setVisibleAllView();
        ViewSanPham.setVisible(true);
    }//GEN-LAST:event_btnShowViewSanPhamMouseClicked

    private void btnShowViewKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewKhachHangMouseClicked
        // TODO add your handling code here:
        setVisibleAllView();
        ViewKhachHang.setVisible(true);
    }//GEN-LAST:event_btnShowViewKhachHangMouseClicked

    private void btnShowViewNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewNhanVienMouseClicked
        // TODO add your handling code here:
        setVisibleAllView();
        ViewNhanVien.setVisible(true);
    }//GEN-LAST:event_btnShowViewNhanVienMouseClicked

    private void btnShowViewThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewThongKeMouseClicked
        // TODO add your handling code here:
        setVisibleAllView();
        ViewThongKe.setVisible(true);
    }//GEN-LAST:event_btnShowViewThongKeMouseClicked

    private void btnShowViewHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewHoaDonMouseClicked
        // TODO add your handling code here:
        setVisibleAllView();
        ViewHoaDon.setVisible(true);
    }//GEN-LAST:event_btnShowViewHoaDonMouseClicked

    private void btnShowViewDoiMatKhauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewDoiMatKhauMouseClicked
        // TODO add your handling code here:
        setVisibleAllView();
        ViewDoiMatKhau.setVisible(true);
    }//GEN-LAST:event_btnShowViewDoiMatKhauMouseClicked

    private void btnShowViewBanHang1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewBanHang1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnShowViewBanHang1MouseClicked

    private void btnShowViewDoiMatKhau1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewDoiMatKhau1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnShowViewDoiMatKhau1MouseClicked

    private void combobox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combobox3ActionPerformed

    private void combobox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combobox5ActionPerformed

    private void btnShowViewColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowViewColorActionPerformed
        // TODO add your handling code here:
        new ViewChangeColorProduct(this, true).setVisible(true);
    }//GEN-LAST:event_btnShowViewColorActionPerformed

    private void btnShowViewSoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowViewSoleActionPerformed
        // TODO add your handling code here:
        new ViewChangSoleProduct(this, true).setVisible(true);
    }//GEN-LAST:event_btnShowViewSoleActionPerformed

    private void btnShowViewMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowViewMaterialActionPerformed
        // TODO add your handling code here:
        new ViewChangeMaterialProduct(this, true).setVisible(true);
    }//GEN-LAST:event_btnShowViewMaterialActionPerformed

    private void btnShowViewSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowViewSizeActionPerformed
        // TODO add your handling code here:
        new ViewChangeSizeProduct(this, true).setVisible(true);
    }//GEN-LAST:event_btnShowViewSizeActionPerformed

    private void btnShowViewBanHang2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewBanHang2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnShowViewBanHang2MouseClicked

    private void btnShowViewBanHang3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewBanHang3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnShowViewBanHang3MouseClicked

    private void btnShowViewBanHang4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewBanHang4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnShowViewBanHang4MouseClicked

    private void btnShowViewBanHang5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewBanHang5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnShowViewBanHang5MouseClicked

    private void btnShowViewBanHang6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewBanHang6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnShowViewBanHang6MouseClicked

    private void btnShowViewBanHang7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewBanHang7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnShowViewBanHang7MouseClicked

    private void btnShowViewBanHang8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewBanHang8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnShowViewBanHang8MouseClicked

    private void buttonCustom14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom14ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_buttonCustom14ActionPerformed

    private void btn_ThemTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemTaiKhoanActionPerformed
        // TODO add your handling code here:
        new ViewThemTaiKhoanNV(this, true).setVisible(true);


    }//GEN-LAST:event_btn_ThemTaiKhoanActionPerformed

    private void btn_ThemNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemNhanVienActionPerformed
        // TODO add your handling code here:
        btn_ThemTaiKhoan.setVisible(true);
    }//GEN-LAST:event_btn_ThemNhanVienActionPerformed

    private void jRadioButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton6ActionPerformed

    private void buttonCustom8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom8ActionPerformed
        // TODO add your handling code here:
        new ViewChooseKhachHang(this, true).setVisible(true);
    }//GEN-LAST:event_buttonCustom8ActionPerformed

    private void combobox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combobox1ActionPerformed

    private void table6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table6MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_table6MouseClicked

    private void tbl_CustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_CustomerMouseClicked
        // TODO add your handling code here:
        try {
            ShowCustomer();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi Click Form " + ERROR);
        }
        
    }//GEN-LAST:event_tbl_CustomerMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        addCustomer();

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateCustomer();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void buttonCustom31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonCustom31ActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        clearFormKh();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteCustomer();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txt_TimLichSuGiaoDichTheoMaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TimLichSuGiaoDichTheoMaKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TimLichSuGiaoDichTheoMaKHActionPerformed

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
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new View().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ViewBanHang;
    private javax.swing.JPanel ViewDoiMatKhau;
    private javax.swing.JPanel ViewHoaDon;
    private javax.swing.JPanel ViewKhachHang;
    private javax.swing.JPanel ViewKhuyenMai;
    private javax.swing.JPanel ViewNhanVien;
    private javax.swing.JPanel ViewSanPham;
    private javax.swing.JPanel ViewThongKe;
    private components.ButtonCustom btnAdd;
    private components.ButtonCustom btnDelete;
    private components.ButtonCustom btnNew;
    private javax.swing.JLabel btnShowViewBanHang;
    private javax.swing.JLabel btnShowViewBanHang1;
    private javax.swing.JLabel btnShowViewBanHang2;
    private javax.swing.JLabel btnShowViewBanHang3;
    private javax.swing.JLabel btnShowViewBanHang4;
    private javax.swing.JLabel btnShowViewBanHang5;
    private javax.swing.JLabel btnShowViewBanHang6;
    private javax.swing.JLabel btnShowViewBanHang7;
    private javax.swing.JLabel btnShowViewBanHang8;
    private components.ButtonCustom btnShowViewColor;
    private javax.swing.JLabel btnShowViewDoiMatKhau;
    private javax.swing.JLabel btnShowViewDoiMatKhau1;
    private javax.swing.JLabel btnShowViewHoaDon;
    private javax.swing.JLabel btnShowViewKhachHang;
    private components.ButtonCustom btnShowViewMaterial;
    private javax.swing.JLabel btnShowViewNhanVien;
    private javax.swing.JLabel btnShowViewSanPham;
    private components.ButtonCustom btnShowViewSize;
    private components.ButtonCustom btnShowViewSole;
    private javax.swing.JLabel btnShowViewThongKe;
    private components.ButtonCustom btnUpdate;
    private components.ButtonCustom btn_ThemNhanVien;
    private components.ButtonCustom btn_ThemTaiKhoan;
    private components.ButtonCustom buttonCustom1;
    private components.ButtonCustom buttonCustom10;
    private components.ButtonCustom buttonCustom11;
    private components.ButtonCustom buttonCustom12;
    private components.ButtonCustom buttonCustom13;
    private components.ButtonCustom buttonCustom14;
    private components.ButtonCustom buttonCustom15;
    private components.ButtonCustom buttonCustom2;
    private components.ButtonCustom buttonCustom29;
    private components.ButtonCustom buttonCustom3;
    private components.ButtonCustom buttonCustom30;
    private components.ButtonCustom buttonCustom31;
    private components.ButtonCustom buttonCustom4;
    private components.ButtonCustom buttonCustom5;
    private components.ButtonCustom buttonCustom6;
    private components.ButtonCustom buttonCustom7;
    private components.ButtonCustom buttonCustom8;
    private components.ButtonCustom buttonCustom9;
    private javax.swing.ButtonGroup buttonGroup1;
    private components.Combobox combobox1;
    private components.Combobox combobox10;
    private components.Combobox combobox11;
    private components.Combobox combobox12;
    private components.Combobox combobox13;
    private components.Combobox combobox14;
    private components.Combobox combobox2;
    private components.Combobox combobox3;
    private components.Combobox combobox4;
    private components.Combobox combobox5;
    private components.Combobox combobox6;
    private components.Combobox combobox8;
    private components.Combobox combobox9;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private components.MaterialTabbed materialTabbed1;
    private components.MaterialTabbed materialTabbed3;
    private components.MaterialTabbed materialTabbed4;
    private components.MaterialTabbed materialTabbed5;
    private components.MaterialTabbed materialTabbed6;
    private components.Pagination pagination1;
    private components.Pagination pagination3;
    private components.PanelRound panelRound1;
    private components.Pagination pnaListCustomer;
    private components.Pagination pnaListCustomerHistory;
    private javax.swing.JRadioButton rdo_Nam;
    private javax.swing.JRadioButton rdo_Nu;
    private components.TabbedPaneCustom tabbedPaneCustom1;
    private components.TabbedPaneCustom tabbedPaneCustom2;
    private components.TabbedPaneCustom tabbedPaneCustom3;
    private components.Table table1;
    private components.Table table12;
    private components.Table table2;
    private components.Table table3;
    private components.Table table6;
    private components.Table tbl_Customer;
    private components.Table tbl_CustomerHistory;
    private components.TextArea textArea2;
    private components.TextArea textArea6;
    private components.TextAreaScroll textAreaScroll1;
    private components.TextAreaScroll textAreaScroll4;
    private components.TextAreaScroll textAreaScroll5;
    private components.TextField textField1;
    private components.TextField textField10;
    private components.TextField textField11;
    private components.TextField textField12;
    private components.TextField textField13;
    private components.TextField textField15;
    private components.TextField textField21;
    private components.TextField textField4;
    private components.TextField textField5;
    private components.TextField textField6;
    private components.TextField textField7;
    private components.TextField textField8;
    private components.TextField textField9;
    private components.TextFieldSuggestion textFieldSuggestion1;
    private components.TextFieldSuggestion textFieldSuggestion13;
    private components.TextFieldSuggestion textFieldSuggestion14;
    private components.TextFieldSuggestion textFieldSuggestion2;
    private components.TextFieldSuggestion textFieldSuggestion23;
    private components.TextFieldSuggestion textFieldSuggestion3;
    private components.TextFieldSuggestion textFieldSuggestion4;
    private components.TextFieldSuggestion textFieldSuggestion5;
    private components.TextFieldSuggestion textFieldSuggestion6;
    private components.TextArea txt_DiaChi;
    private components.TextFieldSuggestion txt_IDKH;
    private components.TextFieldSuggestion txt_SDT;
    private components.TextField txt_TimIDKH;
    private components.TextField txt_TimLichSuGiaoDichTheoMaKH;
    private components.TextField txt_TimTenKH;
    private components.TextFieldSuggestion txt_tenKH;
    // End of variables declaration//GEN-END:variables

}
