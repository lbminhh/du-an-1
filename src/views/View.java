/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import components.EventPagination;
import components.Notification;
import components.PaginationItemRenderStyle1;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import request.ProductsRequest;
import request.ProductsSearchRequest;
import response.BrandResponse;
import response.CategoryResponse;
import response.ColorResponse;
import response.MaterialResponse;
import request.ProductDetailRequest;
import response.ProductDetailResponse;
import response.ProductsResponse;
import response.SizeResponse;
import response.SoleShoesResponse;
import service.impl.BrandServiceImpl;
import service.impl.CategoryServiceImpl;
import service.impl.ColorServiceImpl;
import service.impl.MaterialServiceImpl;
import service.impl.ProductDetailServiceImpl;
import service.impl.ProductsServiceImpl;
import service.impl.SizeServiceImpl;
import service.impl.SoleServiceImpl;
import util.MoneyConverter;

/**
 *
 * @author LE MINH
 */
public class View extends javax.swing.JFrame {

    private DefaultTableModel tableModel = new DefaultTableModel();
    private List<ProductsResponse> listProducts = new ArrayList<>();
    private List<ProductsResponse> listToTableProducts = new ArrayList<>();
    private List<ProductDetailResponse> listProductDetail = new ArrayList<>();
    private List<ProductDetailResponse> listToTableProductDetail = new ArrayList<>();
    private ProductsServiceImpl productsServiceImpl = new ProductsServiceImpl();
    private CategoryServiceImpl categoryServiceImpl = new CategoryServiceImpl();
    private BrandServiceImpl brandServiceImpl = new BrandServiceImpl();
    private ProductDetailServiceImpl productDetailServiceImpl = new ProductDetailServiceImpl();
    private ColorServiceImpl colorServiceImpl = new ColorServiceImpl();
    private SoleServiceImpl soleServiceImpl = new SoleServiceImpl();
    private MaterialServiceImpl materialServiceImpl = new MaterialServiceImpl();
    private SizeServiceImpl sizeServiceImpl = new SizeServiceImpl();

    /**
     * Creates new form View
     */
    public View() {
        initComponents();
        this.setLocationRelativeTo(null);
        setVisibleAllView();
        ViewBanHang.setVisible(true);
        pagination1.setPagegination(1, 10);
        listProducts = productsServiceImpl.getAllProducts();
        listProductDetail = productDetailServiceImpl.getAllProductDetail(null);
        showListProductDetailByPage(1, listProductDetail);
        showListProductsByPage(1, listProducts);
        setUpAllPagination();
        setDataAllCombobox();
        txtMaSanPham.setText("###");
        setAllEventComponent();
    }

    private void setUpAllPagination() {
        pagination1.setPaginationItemRender(new PaginationItemRenderStyle1());
        pnaListProducts.setPaginationItemRender(new PaginationItemRenderStyle1());
        pnaListProductDetail.setPaginationItemRender(new PaginationItemRenderStyle1());
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

    private void setDataAllCombobox() {
        //Brand
        cbbHang.removeAllItems();
        cbbHang.addItem("");
        cbbTimHang.removeAllItems();
        cbbTimHang.addItem("");
        for (BrandResponse item : brandServiceImpl.getAllBrand()) {
            if (item.getStatus()) {
                cbbHang.addItem(item.getBrandName());
                cbbTimHang.addItem(item.getBrandName());
            }
        }

        //Category
        cbbTheLoai.removeAllItems();
        cbbTheLoai.addItem("");
        cbbTimTheLoai.removeAllItems();
        cbbTimTheLoai.addItem("");
        for (CategoryResponse item : categoryServiceImpl.getAllCategory()) {
            if (item.getStatus()) {
                cbbTheLoai.addItem(item.getCategoryName());
                cbbTimTheLoai.addItem(item.getCategoryName());
            }
        }

        //Color
        cbbMauSac.removeAllItems();
        cbbMauSac.addItem("");
        for (ColorResponse item : colorServiceImpl.getAllColor()) {
            if (item.getStatus()) {
                cbbMauSac.addItem(item.getColorName());
            }
        }

        //Material
        cbbChatLieu.removeAllItems();
        cbbChatLieu.addItem("");
        for (MaterialResponse item : materialServiceImpl.getAllMaterial()) {
            if (item.getStatus()) {
                cbbChatLieu.addItem(item.getMaterialName());
            }
        }

        //Size
        cbbSize.removeAllItems();
        cbbSize.addItem("");
        for (SizeResponse item : sizeServiceImpl.getAllSize()) {
            if (item.getStatus()) {
                cbbSize.addItem(item.getSizeName());
            }
        }

        //Sole
        cbbDeGiay.removeAllItems();
        cbbDeGiay.addItem("");
        for (SoleShoesResponse item : soleServiceImpl.getAllSole()) {
            if (item.getStatus() == true) {
                cbbDeGiay.addItem(item.getSoleName());
            }
        }

        //Mã sản phẩm
        cbbMaSanPham.removeAllItems();
        cbbMaSanPham.addItem("");
        for (ProductsResponse item : listProducts) {
            cbbMaSanPham.addItem(item.getId());
        }

    }

    private void setAllEventComponent() {
        cbbTimHang.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                listProducts = productsServiceImpl.searchListProducts(getDataSearchProducts());
                showListProductsByPage(1, listProducts);
            }
        });
        cbbTimTheLoai.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                listProducts = productsServiceImpl.searchListProducts(getDataSearchProducts());
                showListProductsByPage(1, listProducts);
            }
        });
        cbbTimTrangThai.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                listProducts = productsServiceImpl.searchListProducts(getDataSearchProducts());
                showListProductsByPage(1, listProducts);
            }
        });
        cbbMaSanPham.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String id = String.valueOf(cbbMaSanPham.getSelectedItem());
                if (id.equalsIgnoreCase("")) {
                    id = null;
                    clearFormProductDetail();
                }
                for (ProductsResponse item : productsServiceImpl.getAllProducts()) {
                    if (id != null) {
                        if (id.equalsIgnoreCase(item.getId())) {
                            lbTenSanPham.setText(item.getProductName());
                            break;
                        }
                    }
                }
                listProductDetail = productDetailServiceImpl.getAllProductDetail(id);
                showListProductDetailByPage(1, listProductDetail);
            }
        });
        txtTimSanPham.getDocument().addDocumentListener(new DocumentListener() {
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
                listProducts = productsServiceImpl.searchListProducts(getDataSearchProducts());
                showListProductsByPage(1, listProducts);
            }
        });
        tblListSanPham.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    // your valueChanged overridden method 
                    setDataWhenDoubleClickProducts();
                }
            }
        });
        pnaListProducts.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                showListProductsByPage(page, listProducts);
            }
        });
        pnaListProductDetail.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                showListProductDetailByPage(page, listProductDetail);
            }
        });
    }

    //START: update view
    public void updateComboboxHang(String method) {
        int lastIndex = 0;
        cbbHang.removeAllItems();
        cbbHang.addItem("");
        cbbTimHang.removeAllItems();
        cbbTimHang.addItem("");
        for (BrandResponse item : brandServiceImpl.getAllBrand()) {
            if (item.getStatus()) {
                cbbHang.addItem(item.getBrandName());
                cbbTimHang.addItem(item.getBrandName());
                lastIndex++;
            }
        }
        if (method.equalsIgnoreCase("add")) {
            cbbHang.setSelectedIndex(lastIndex);
        }
    }

    public void updateComboboxTheLoai(String method) {
        int lastIndex = 0;
        cbbTheLoai.removeAllItems();
        cbbTheLoai.addItem("");
        cbbTimTheLoai.removeAllItems();
        cbbTimTheLoai.addItem("");
        for (CategoryResponse item : categoryServiceImpl.getAllCategory()) {
            if (item.getStatus()) {
                cbbTimTheLoai.addItem(item.getCategoryName());
                cbbTimTheLoai.addItem(item.getCategoryName());
                lastIndex++;
            }
        }
        if (method.equalsIgnoreCase("add")) {
            cbbTheLoai.setSelectedIndex(lastIndex);
        }
    }

    public void updateComboboxMauSac(String method) {
        int lastIndex = 0;
        cbbMauSac.removeAllItems();
        cbbMauSac.addItem("");
        for (ColorResponse item : colorServiceImpl.getAllColor()) {
            if (item.getStatus()) {
                cbbMauSac.addItem(item.getColorName());
                lastIndex++;
            }
        }
        if (method.equalsIgnoreCase("add")) {
            cbbMauSac.setSelectedIndex(lastIndex);
        }
    }

    public void updateComboboxSize(String method) {
        int lastIndex = 0;
        cbbSize.removeAllItems();
        cbbSize.addItem("");
        for (SizeResponse item : sizeServiceImpl.getAllSize()) {
            if (item.getStatus()) {
                cbbSize.addItem(item.getSizeName());
                lastIndex++;
            }
        }
        if (method.equalsIgnoreCase("add")) {
            cbbSize.setSelectedIndex(lastIndex);
        }
    }

    public void updateComboboxMaterial(String method) {
        int lastIndex = 0;
        cbbChatLieu.removeAllItems();
        cbbChatLieu.addItem("");
        for (MaterialResponse item : materialServiceImpl.getAllMaterial()) {
            if (item.getStatus()) {
                cbbChatLieu.addItem(item.getMaterialName());
                lastIndex++;
            }
        }
        if (method.equalsIgnoreCase("add")) {
            cbbChatLieu.setSelectedIndex(lastIndex);
        }
    }

    public void updateComboboxSole(String method) {
        int lastIndex = 0;
        cbbDeGiay.removeAllItems();
        cbbDeGiay.addItem("");
        for (SoleShoesResponse item : soleServiceImpl.getAllSole()) {
            if (item.getStatus()) {
                cbbDeGiay.addItem(item.getSoleName());
                lastIndex++;
            }
        }
        if (method.equalsIgnoreCase("add")) {
            cbbDeGiay.setSelectedIndex(lastIndex);
        }
    }
    //END: update view

    // START: PRODUCTS
    private void showListProductsByPage(int page, List<ProductsResponse> list) {
        int litmit = 6;
        int totalPage = (int) Math.ceil(((double) list.size() / litmit));
        tableModel = (DefaultTableModel) tblListSanPham.getModel();
        tableModel.setRowCount(0);
        pnaListProducts.setPagegination(page, totalPage);
        listToTableProducts.clear();
        for (int i = 0; i < listProducts.size(); i++) {
            if (i >= (page - 1) * litmit && i <= (litmit * page) - 1) {
                ProductsResponse item = list.get(i);
                listToTableProducts.add(item);
                tableModel.addRow(new Object[]{
                    i + 1,
                    item.getId(),
                    item.getProductName(),
                    productDetailServiceImpl.getQuantityByProductsId(item.getId()),
                    item.getCategoryName(),
                    item.getBrandName(),
                    item.getStatus() ? "Đang bán" : "Dừng bán"
                });
            }
        }
    }

    private ProductsRequest getDataProducts(String method) {
        String id = "";
        if (method.equalsIgnoreCase("add")) {
            id = "SP" + productsServiceImpl.getIdProducts();
        } else {
            id = txtMaSanPham.getText();
        }
        String productName = txtTenSanPham.getText().trim();
        if (checkStringEmpty(productName)) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng nhập tên sản phẩm!");
            notification.showNotification();
            return null;
        }
        //Lấy id cate
        String categoryName = String.valueOf(cbbTheLoai.getSelectedItem());
        if (checkStringEmpty(categoryName)) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng chọn loại!");
            notification.showNotification();
            return null;
        }
        Long idCategory = null;
        for (CategoryResponse categoryResponse : categoryServiceImpl.getAllCategory()) {
            if (categoryName.equalsIgnoreCase(categoryResponse.getCategoryName())) {
                idCategory = categoryResponse.getId();
                break;
            }
        }
        //Lấy id brand
        String brandName = String.valueOf(cbbHang.getSelectedItem());
        if (checkStringEmpty(brandName)) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng chọn hãng!");
            notification.showNotification();
            return null;
        }
        Long idBrand = null;
        for (BrandResponse brandResponse : brandServiceImpl.getAllBrand()) {
            if (brandName.equalsIgnoreCase(brandResponse.getBrandName())) {
                idBrand = brandResponse.getId();
                break;
            }
        }
        String description = txtMoTa.getText();
        Boolean status = true;
        return new ProductsRequest(id, productName, description, idCategory, idBrand, null, status);
    }

    private ProductsSearchRequest getDataSearchProducts() {
        String productName = txtTimSanPham.getText();
        String categoryName = String.valueOf(cbbTimTheLoai.getSelectedItem());
        String brandName = String.valueOf(cbbTimHang.getSelectedItem());
        String statusName = String.valueOf(cbbTimTrangThai.getSelectedItem());
        Boolean status = null;
        if (statusName.equalsIgnoreCase("Ngừng bán")) {
            status = false;
        }
        if (statusName.equalsIgnoreCase("Đang bán")) {
            status = true;
        }
        return new ProductsSearchRequest(productName, categoryName, brandName, status);
    }

    private void showDetailProducts(int index) {
        ProductsResponse productsResponse = listToTableProducts.get(index);
        txtMaSanPham.setText(productsResponse.getId());
        txtTenSanPham.setText(productsResponse.getProductName());
        cbbHang.setSelectedItem(productsResponse.getBrandName());
        cbbTheLoai.setSelectedItem(productsResponse.getCategoryName());
        if (productsResponse.getDescription() != null) {
            txtMoTa.setText(productsResponse.getDescription());
        } else {
            txtMoTa.setText("");
        }
    }

    private void addProducts() {
        if (productsServiceImpl.addProducts(getDataProducts("add"))) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.SUCCESS, Notification.Location.TOP_RIGHT, "Thêm thành công!");
            notification.showNotification();
            listProducts = productsServiceImpl.getAllProducts();
            showListProductsByPage(1, listProducts);
            clearForm();
        }
    }

    private void updateProducts() {
        if (productsServiceImpl.updateProducts(getDataProducts("update"))) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.SUCCESS, Notification.Location.TOP_RIGHT, "Sửa thành công!");
            notification.showNotification();
            listProducts = productsServiceImpl.getAllProducts();
            showListProductsByPage(1, listProducts);
        }
    }

    private void clearForm() {
        txtMaSanPham.setText("###");
        txtTenSanPham.setText("");
        cbbHang.setSelectedIndex(0);
        cbbTheLoai.setSelectedIndex(0);
        txtMoTa.setText("");
    }

    private void deleteProducts() {
        String id = txtMaSanPham.getText().trim();
        if (id.equalsIgnoreCase("###")) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng thử lại!");
            notification.showNotification();
        } else {
            if (productsServiceImpl.deleteProducts(id)) {
                Notification notification = new Notification(View.getJframe(), Notification.Type.SUCCESS, Notification.Location.TOP_RIGHT, "Cập nhật thành công!");
                notification.showNotification();
                listProducts = productsServiceImpl.getAllProducts();
                showListProductsByPage(1, listProducts);
            }
        }
    }

    //END: PRODUCTS
    private void setDataWhenDoubleClickProducts() {
        String id = txtMaSanPham.getText();
        cbbMaSanPham.setSelectedItem(id);
        for (ProductsResponse item : listToTableProducts) {
            if (id.equalsIgnoreCase(item.getId())) {
                lbTenSanPham.setText(item.getProductName());
                break;
            }
        }
        listProductDetail = productDetailServiceImpl.getAllProductDetail(id);
        showListProductDetailByPage(1, listProductDetail);
        tabSanPham.setSelectedIndex(1);
    }

    private void showListProductDetailByPage(int page, List<ProductDetailResponse> list) {
        int litmit = 6;
        int totalPage = (int) Math.ceil(((double) list.size() / litmit));
        tableModel = (DefaultTableModel) tblListSanPhamChiTiet.getModel();
        tableModel.setRowCount(0);
        pnaListProductDetail.setPagegination(page, totalPage);
        listToTableProductDetail.clear();
        for (int i = 0; i < listProductDetail.size(); i++) {
            if (i >= (page - 1) * litmit && i <= (litmit * page) - 1) {
                ProductDetailResponse item = list.get(i);
                listToTableProductDetail.add(item);
                tableModel.addRow(new Object[]{
                    i + 1,
                    item.getId(),
                    item.getProductName(),
                    item.getBrandName(),
                    MoneyConverter.parse(item.getPrice()),
                    item.getColor(),
                    item.getSize(),
                    item.getSoleShoes(),
                    item.getMaterial(),
                    item.getQuantity(),
                    item.getStatus() ? "Đang bán" : "Dừng bán"
                });
            }
        }
    }

    private ProductDetailRequest getDataProductDetail() {
        Long id = null;
        for (ProductDetailResponse item : listProductDetail) {
            if (lbTenSanPham.getText().equalsIgnoreCase(item.getProductName())) {
                id = item.getId();
                break;
            }
        }
        String idProduct = String.valueOf(cbbMaSanPham.getSelectedItem());
        if (idProduct.trim().isEmpty()) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng chọn mã sản phẩm!");
            notification.showNotification();
            return null;
        }
        if (checkStringEmpty(txtGiaBanSanPham.getText())) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng nhập giá!");
            notification.showNotification();
            return null;
        }
        if (!checkNumber(txtGiaBanSanPham.getText())) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Giá sai dữ liệu!");
            notification.showNotification();
            return null;
        }
        double price = Double.valueOf(txtGiaBanSanPham.getText().trim());
        if (price <= 1000) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Giá phải lớn hơn 1000!");
            notification.showNotification();
            return null;
        }
        if (checkStringEmpty(txtSoLuong.getText())) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng nhập số lượng!");
            notification.showNotification();
            return null;
        }
        if (!checkNumber(txtSoLuong.getText())) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Số lượng sai dữ liệu!");
            notification.showNotification();
            return null;
        }
        int quantiy = Integer.parseInt(txtSoLuong.getText().trim());
        if (quantiy < 0) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Số lượng là số lớn hơn 0!");
            notification.showNotification();
            return null;
        }
        //Lấy id color
        String colorName = String.valueOf(cbbMauSac.getSelectedItem());
        if (checkStringEmpty(colorName)) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng chọn màu sắc!");
            notification.showNotification();
            return null;
        }
        Long idColor = null;
        for (ColorResponse item : colorServiceImpl.getAllColor()) {
            if (colorName.equalsIgnoreCase(item.getColorName())) {
                idColor = item.getId();
                break;
            }
        }
        //Lấy id size
        String sizeName = String.valueOf(cbbSize.getSelectedItem());
        if (checkStringEmpty(sizeName)) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng chọn kích cỡ!");
            notification.showNotification();
            return null;
        }
        Long idSize = null;
        for (SizeResponse item : sizeServiceImpl.getAllSize()) {
            if (sizeName.equalsIgnoreCase(item.getSizeName())) {
                idSize = item.getId();
                break;
            }
        }
        //Lấy id sole
        String soleName = String.valueOf(cbbDeGiay.getSelectedItem());
        if (checkStringEmpty(soleName)) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng chọn đế giày!");
            notification.showNotification();
            return null;
        }
        Long idSole = null;
        for (SoleShoesResponse item : soleServiceImpl.getAllSole()) {
            if (soleName.equalsIgnoreCase(item.getSoleName())) {
                idSole = item.getId();
                break;
            }
        }
        //Lấy id material
        String materialName = String.valueOf(cbbChatLieu.getSelectedItem());
        if (checkStringEmpty(materialName)) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng chọn chất liệu!");
            notification.showNotification();
            return null;
        }
        Long idMaterial = null;
        for (MaterialResponse item : materialServiceImpl.getAllMaterial()) {
            if (materialName.equalsIgnoreCase(item.getMaterialName())) {
                idMaterial = item.getId();
                break;
            }
        }
        Boolean status = true;
        return new ProductDetailRequest( id,idProduct, BigDecimal.valueOf(price), quantiy, idColor, idSize, idMaterial, idSole, status);
    }

    private void showDetailProductDetail(int index) {
        ProductDetailResponse productDetailResponse = listToTableProductDetail.get(index);
        for (ProductsResponse item : listProducts) {
            if (productDetailResponse.getProductName().equalsIgnoreCase(item.getProductName())) {
                cbbMaSanPham.setSelectedItem(item.getId());
                lbTenSanPham.setText(item.getProductName());
                break;
            }
        }
        txtGiaBanSanPham.setText(String.valueOf(productDetailResponse.getPrice().setScale(0)));
        txtSoLuong.setText(String.valueOf(productDetailResponse.getQuantity()));
        cbbMauSac.setSelectedItem(productDetailResponse.getColor());
        cbbSize.setSelectedItem(productDetailResponse.getSize());
        cbbChatLieu.setSelectedItem(productDetailResponse.getMaterial());
        cbbDeGiay.setSelectedItem(productDetailResponse.getSoleShoes());
    }

    private void addProductDetail() {
        if (productDetailServiceImpl.addProductDetail(getDataProductDetail())) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.SUCCESS, Notification.Location.TOP_RIGHT, "Thêm thành công!");
            notification.showNotification();
            String idProduct = String.valueOf(cbbMaSanPham.getSelectedItem());
            listProductDetail = productDetailServiceImpl.getAllProductDetail(idProduct);
            showListProductDetailByPage(1, listProductDetail);
        }
    }

    private void updateProductDetail() {
        if (productDetailServiceImpl.updateProductDetail(getDataProductDetail())) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.SUCCESS, Notification.Location.TOP_RIGHT, "Sửa thành công!");
            notification.showNotification();
            String idProduct = String.valueOf(cbbMaSanPham.getSelectedItem());
            listProductDetail = productDetailServiceImpl.getAllProductDetail(idProduct);
            showListProductDetailByPage(1, listProductDetail);
        }
    }

    private void clearFormProductDetail() {
        cbbMauSac.setSelectedIndex(0);
        cbbChatLieu.setSelectedIndex(0);
        cbbSize.setSelectedIndex(0);
        cbbDeGiay.setSelectedIndex(0);
        lbTenSanPham.setText("###");
        txtGiaBanSanPham.setText("");
        txtSoLuong.setText("");
    }

    //START: Product detail
    //END: Product detail
    //START: function validate form
    private boolean checkStringEmpty(String value) {
        return value.trim().isEmpty();
    }

    private boolean checkNumber(String value) {
        value = value.trim();
        try {
            double number = Double.valueOf(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //END: function validate form
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        tabSanPham = new components.MaterialTabbed();
        jPanel5 = new javax.swing.JPanel();
        txtMaSanPham = new components.TextFieldSuggestion();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTenSanPham = new components.TextFieldSuggestion();
        jLabel3 = new javax.swing.JLabel();
        textAreaScroll1 = new components.TextAreaScroll();
        txtMoTa = new components.TextArea();
        jLabel4 = new javax.swing.JLabel();
        cbbHang = new components.Combobox();
        btnThemSanPham = new components.ButtonCustom();
        btnSuaSanPham = new components.ButtonCustom();
        btnXoaSanPham = new components.ButtonCustom();
        btnLamMoi = new components.ButtonCustom();
        jLabel14 = new javax.swing.JLabel();
        cbbTheLoai = new components.Combobox();
        jLabel8 = new javax.swing.JLabel();
        txtTimSanPham = new components.TextField();
        cbbTimHang = new components.Combobox();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblListSanPham = new components.Table();
        pnaListProducts = new components.Pagination();
        cbbTimTheLoai = new components.Combobox();
        cbbTimTrangThai = new components.Combobox();
        btnShowViewColor1 = new components.ButtonCustom();
        btnShowViewColor2 = new components.ButtonCustom();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtGiaBanSanPham = new components.TextFieldSuggestion();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cbbMauSac = new components.Combobox();
        jLabel10 = new javax.swing.JLabel();
        cbbSize = new components.Combobox();
        jLabel11 = new javax.swing.JLabel();
        cbbDeGiay = new components.Combobox();
        jLabel12 = new javax.swing.JLabel();
        cbbChatLieu = new components.Combobox();
        btnShowViewSole = new components.ButtonCustom();
        btnShowViewSize = new components.ButtonCustom();
        btnShowViewColor = new components.ButtonCustom();
        btnShowViewMaterial = new components.ButtonCustom();
        jLabel13 = new javax.swing.JLabel();
        buttonCustom14 = new components.ButtonCustom();
        buttonCustom15 = new components.ButtonCustom();
        jLabel16 = new javax.swing.JLabel();
        txtSoLuong = new components.TextFieldSuggestion();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblListSanPhamChiTiet = new components.Table();
        cbbMaSanPham = new components.Combobox();
        pnaListProductDetail = new components.Pagination();
        jLabel17 = new javax.swing.JLabel();
        lbTenSanPham = new javax.swing.JLabel();
        buttonCustom16 = new components.ButtonCustom();
        ViewNhanVien = new javax.swing.JPanel();
        ViewThongKe = new javax.swing.JPanel();
        ViewKhachHang = new javax.swing.JPanel();
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
        buttonCustom3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom3ActionPerformed(evt);
            }
        });

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addContainerGap(306, Short.MAX_VALUE)))
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
                .addContainerGap(55, Short.MAX_VALUE))
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

        txtMaSanPham.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Mã sản phẩm:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Tên sản phẩm:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Mô tả:");

        textAreaScroll1.setLabelText("Description...");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        textAreaScroll1.setViewportView(txtMoTa);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Hãng:");

        cbbHang.setLabeText("");

        btnThemSanPham.setText("Thêm");
        btnThemSanPham.setColor1(new java.awt.Color(0, 255, 51));
        btnThemSanPham.setColor2(new java.awt.Color(51, 255, 51));
        btnThemSanPham.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamActionPerformed(evt);
            }
        });

        btnSuaSanPham.setText("Sửa");
        btnSuaSanPham.setColor1(new java.awt.Color(255, 204, 0));
        btnSuaSanPham.setColor2(new java.awt.Color(255, 255, 0));
        btnSuaSanPham.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnSuaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSanPhamActionPerformed(evt);
            }
        });

        btnXoaSanPham.setText("Xoá");
        btnXoaSanPham.setColor1(new java.awt.Color(255, 51, 102));
        btnXoaSanPham.setColor2(new java.awt.Color(255, 0, 51));
        btnXoaSanPham.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnXoaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPhamActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm mới");
        btnLamMoi.setColor1(new java.awt.Color(0, 153, 255));
        btnLamMoi.setColor2(new java.awt.Color(0, 102, 255));
        btnLamMoi.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Thể loại:");

        cbbTheLoai.setLabeText("");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("#Danh sách sản phẩm");

        txtTimSanPham.setLabelText("Tìm tên sản phẩm...");

        cbbTimHang.setLabeText("Hãng");
        cbbTimHang.setLineColor(new java.awt.Color(255, 0, 0));

        tblListSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "Số lượng tồn", "Danh mục", "Thương hiệu", "Trạng thái"
            }
        ));
        tblListSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListSanPhamMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblListSanPham);

        pnaListProducts.setBackground(new java.awt.Color(204, 204, 204));
        pnaListProducts.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        cbbTimTheLoai.setLabeText("Thể loại");

        cbbTimTrangThai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Đang bán", "Ngừng bán" }));
        cbbTimTrangThai.setLabeText("Trạng thái");

        btnShowViewColor1.setText("Xem");
        btnShowViewColor1.setColor1(new java.awt.Color(0, 0, 255));
        btnShowViewColor1.setColor2(new java.awt.Color(0, 102, 255));
        btnShowViewColor1.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnShowViewColor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowViewColor1ActionPerformed(evt);
            }
        });

        btnShowViewColor2.setText("Xem");
        btnShowViewColor2.setColor1(new java.awt.Color(0, 0, 255));
        btnShowViewColor2.setColor2(new java.awt.Color(0, 102, 255));
        btnShowViewColor2.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnShowViewColor2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowViewColor2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(cbbHang, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnShowViewColor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel3)
                            .addComponent(textAreaScroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(cbbTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnShowViewColor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(60, 60, 60)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnXoaSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSuaSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txtTimSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbbTimHang, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbbTimTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbbTimTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnaListProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 1188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(textAreaScroll1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnShowViewColor1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnShowViewColor2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(71, 71, 71)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(40, 40, 40)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbTimHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbTimTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbTimTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnaListProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(308, Short.MAX_VALUE))
        );

        tabSanPham.addTab("Sản phẩm", jPanel5);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Mã SP:");

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Thuộc tính"));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Màu sắc:");

        cbbMauSac.setLabeText("Màu sắc");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Kích cỡ:");

        cbbSize.setLabeText("Size");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Chất liệu:");

        cbbDeGiay.setLabeText("Đế giày");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Đế giày:");

        cbbChatLieu.setLabeText("Chất liệu");

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
                        .addComponent(cbbDeGiay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbbMauSac, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                            .addComponent(cbbSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbChatLieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                    .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(btnShowViewColor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(btnShowViewSize, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(btnShowViewSole, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(btnShowViewMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Giá bán:");

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
        buttonCustom15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom15ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Số lượng:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("#Chi tiết sản phẩm");

        tblListSanPhamChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã CTSP", "Tên SP", "Hãng", "Giá bán", "Màu sắc", "Kích cỡ", "Đế giày", "Chất liệu", "Số lượng", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListSanPhamChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListSanPhamChiTietMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblListSanPhamChiTiet);

        cbbMaSanPham.setLabeText("");

        pnaListProductDetail.setBackground(new java.awt.Color(204, 204, 204));
        pnaListProductDetail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("Tên sản phẩm:");

        lbTenSanPham.setText("####");

        buttonCustom16.setText("Sửa");
        buttonCustom16.setColor1(new java.awt.Color(255, 255, 0));
        buttonCustom16.setColor2(new java.awt.Color(255, 255, 0));
        buttonCustom16.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        buttonCustom16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel13)
                    .addComponent(jLabel16)
                    .addComponent(cbbMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(29, 29, 29)
                        .addComponent(lbTenSanPham))
                    .addComponent(txtGiaBanSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(292, 292, 292)
                .addComponent(buttonCustom14, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(buttonCustom16, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106)
                .addComponent(buttonCustom15, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 28, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(pnaListProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 1151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonCustom15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonCustom14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonCustom16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(lbTenSanPham))
                                .addGap(19, 19, 19)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtGiaBanSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)))
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnaListProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(297, Short.MAX_VALUE))
        );

        tabSanPham.addTab("Chi tiết sản phẩm", jPanel8);

        javax.swing.GroupLayout ViewSanPhamLayout = new javax.swing.GroupLayout(ViewSanPham);
        ViewSanPham.setLayout(ViewSanPhamLayout);
        ViewSanPhamLayout.setHorizontalGroup(
            ViewSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewSanPhamLayout.createSequentialGroup()
                .addComponent(tabSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 1190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );
        ViewSanPhamLayout.setVerticalGroup(
            ViewSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewSanPhamLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tabSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 1059, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.add(ViewSanPham);

        ViewNhanVien.setBackground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout ViewNhanVienLayout = new javax.swing.GroupLayout(ViewNhanVien);
        ViewNhanVien.setLayout(ViewNhanVienLayout);
        ViewNhanVienLayout.setHorizontalGroup(
            ViewNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1230, Short.MAX_VALUE)
        );
        ViewNhanVienLayout.setVerticalGroup(
            ViewNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1059, Short.MAX_VALUE)
        );

        jPanel2.add(ViewNhanVien);

        ViewThongKe.setBackground(new java.awt.Color(204, 255, 51));

        javax.swing.GroupLayout ViewThongKeLayout = new javax.swing.GroupLayout(ViewThongKe);
        ViewThongKe.setLayout(ViewThongKeLayout);
        ViewThongKeLayout.setHorizontalGroup(
            ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1230, Short.MAX_VALUE)
        );
        ViewThongKeLayout.setVerticalGroup(
            ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1059, Short.MAX_VALUE)
        );

        jPanel2.add(ViewThongKe);

        ViewKhachHang.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout ViewKhachHangLayout = new javax.swing.GroupLayout(ViewKhachHang);
        ViewKhachHang.setLayout(ViewKhachHangLayout);
        ViewKhachHangLayout.setHorizontalGroup(
            ViewKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1230, Short.MAX_VALUE)
        );
        ViewKhachHangLayout.setVerticalGroup(
            ViewKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1059, Short.MAX_VALUE)
        );

        jPanel2.add(ViewKhachHang);

        ViewDoiMatKhau.setBackground(new java.awt.Color(102, 255, 0));

        javax.swing.GroupLayout ViewDoiMatKhauLayout = new javax.swing.GroupLayout(ViewDoiMatKhau);
        ViewDoiMatKhau.setLayout(ViewDoiMatKhauLayout);
        ViewDoiMatKhauLayout.setHorizontalGroup(
            ViewDoiMatKhauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1230, Short.MAX_VALUE)
        );
        ViewDoiMatKhauLayout.setVerticalGroup(
            ViewDoiMatKhauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1059, Short.MAX_VALUE)
        );

        jPanel2.add(ViewDoiMatKhau);

        ViewKhuyenMai.setBackground(new java.awt.Color(255, 102, 255));

        javax.swing.GroupLayout ViewKhuyenMaiLayout = new javax.swing.GroupLayout(ViewKhuyenMai);
        ViewKhuyenMai.setLayout(ViewKhuyenMaiLayout);
        ViewKhuyenMaiLayout.setHorizontalGroup(
            ViewKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1230, Short.MAX_VALUE)
        );
        ViewKhuyenMaiLayout.setVerticalGroup(
            ViewKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1059, Short.MAX_VALUE)
        );

        jPanel2.add(ViewKhuyenMai);

        ViewHoaDon.setBackground(new java.awt.Color(204, 102, 0));

        javax.swing.GroupLayout ViewHoaDonLayout = new javax.swing.GroupLayout(ViewHoaDon);
        ViewHoaDon.setLayout(ViewHoaDonLayout);
        ViewHoaDonLayout.setHorizontalGroup(
            ViewHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1230, Short.MAX_VALUE)
        );
        ViewHoaDonLayout.setVerticalGroup(
            ViewHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1059, Short.MAX_VALUE)
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

    private void btnThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamActionPerformed
        // TODO add your handling code here:
        addProducts();
    }//GEN-LAST:event_btnThemSanPhamActionPerformed

    private void tblListSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListSanPhamMouseClicked
        // TODO add your handling code here:
        int index = tblListSanPham.getSelectedRow();
        showDetailProducts(index);
    }//GEN-LAST:event_tblListSanPhamMouseClicked

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnSuaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSanPhamActionPerformed
        // TODO add your handling code here:
        updateProducts();
    }//GEN-LAST:event_btnSuaSanPhamActionPerformed

    private void btnShowViewColor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowViewColor1ActionPerformed
        // TODO add your handling code here:
        new ViewChangeBrandProducts(frame, true).setVisible(true);
    }//GEN-LAST:event_btnShowViewColor1ActionPerformed

    private void btnShowViewColor2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowViewColor2ActionPerformed
        // TODO add your handling code here:
        new ViewChangeCategoryProducts(frame, true).setVisible(true);
    }//GEN-LAST:event_btnShowViewColor2ActionPerformed

    private void btnXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPhamActionPerformed
        // TODO add your handling code here:
        deleteProducts();
    }//GEN-LAST:event_btnXoaSanPhamActionPerformed

    private void buttonCustom3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonCustom3ActionPerformed

    private void buttonCustom14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom14ActionPerformed
        // TODO add your handling code here:
        addProductDetail();
    }//GEN-LAST:event_buttonCustom14ActionPerformed

    private void tblListSanPhamChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListSanPhamChiTietMouseClicked
        // TODO add your handling code here:
        int index = tblListSanPhamChiTiet.getSelectedRow();
        showDetailProductDetail(index);
    }//GEN-LAST:event_tblListSanPhamChiTietMouseClicked

    private void buttonCustom16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom16ActionPerformed
        // TODO add your handling code here:
        updateProductDetail();
    }//GEN-LAST:event_buttonCustom16ActionPerformed

    private void buttonCustom15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom15ActionPerformed
        // TODO add your handling code here:
        clearFormProductDetail();
        cbbMaSanPham.setSelectedIndex(0);
    }//GEN-LAST:event_buttonCustom15ActionPerformed

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
                frame = new View();
                frame.setVisible(true);
            }
        });
    }

    private static JFrame frame;

    public static JFrame getJframe() {
        return frame;
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
    private components.ButtonCustom btnLamMoi;
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
    private components.ButtonCustom btnShowViewColor1;
    private components.ButtonCustom btnShowViewColor2;
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
    private components.ButtonCustom btnSuaSanPham;
    private components.ButtonCustom btnThemSanPham;
    private components.ButtonCustom btnXoaSanPham;
    private components.ButtonCustom buttonCustom1;
    private components.ButtonCustom buttonCustom14;
    private components.ButtonCustom buttonCustom15;
    private components.ButtonCustom buttonCustom16;
    private components.ButtonCustom buttonCustom3;
    private components.ButtonCustom buttonCustom4;
    private components.ButtonCustom buttonCustom5;
    private components.ButtonCustom buttonCustom6;
    private components.ButtonCustom buttonCustom7;
    private components.ButtonCustom buttonCustom8;
    private components.ButtonCustom buttonCustom9;
    private components.Combobox cbbChatLieu;
    private components.Combobox cbbDeGiay;
    private components.Combobox cbbHang;
    private components.Combobox cbbMaSanPham;
    private components.Combobox cbbMauSac;
    private components.Combobox cbbSize;
    private components.Combobox cbbTheLoai;
    private components.Combobox cbbTimHang;
    private components.Combobox cbbTimTheLoai;
    private components.Combobox cbbTimTrangThai;
    private components.Combobox combobox1;
    private components.Combobox combobox2;
    private components.Combobox combobox3;
    private components.Combobox combobox4;
    private components.Combobox combobox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lbTenSanPham;
    private components.Pagination pagination1;
    private components.PanelRound panelRound1;
    private components.Pagination pnaListProductDetail;
    private components.Pagination pnaListProducts;
    private components.MaterialTabbed tabSanPham;
    private components.TabbedPaneCustom tabbedPaneCustom1;
    private components.TabbedPaneCustom tabbedPaneCustom2;
    private components.TabbedPaneCustom tabbedPaneCustom3;
    private components.Table table1;
    private components.Table table2;
    private components.Table table3;
    private components.Table tblListSanPham;
    private components.Table tblListSanPhamChiTiet;
    private components.TextAreaScroll textAreaScroll1;
    private components.TextField textField1;
    private components.TextField textField10;
    private components.TextField textField11;
    private components.TextField textField12;
    private components.TextField textField13;
    private components.TextField textField4;
    private components.TextField textField5;
    private components.TextField textField6;
    private components.TextField textField7;
    private components.TextField textField8;
    private components.TextField textField9;
    private components.TextFieldSuggestion txtGiaBanSanPham;
    private components.TextFieldSuggestion txtMaSanPham;
    private components.TextArea txtMoTa;
    private components.TextFieldSuggestion txtSoLuong;
    private components.TextFieldSuggestion txtTenSanPham;
    private components.TextField txtTimSanPham;
    // End of variables declaration//GEN-END:variables
}
