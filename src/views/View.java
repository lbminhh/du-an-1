/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.raven.chart.ModelChart;
import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;
import components.EventPagination;
import components.Notification;
import components.PaginationItemRenderStyle1;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import request.BillDetailRequest;
import request.BillDetailUpdateRequest;
import request.BillsRequest;
import request.BillsSearchRequest;
import request.CustomerRequest;
import request.CustomerUpdateRequest;
import request.ProductsRequest;
import request.ProductsSearchRequest;
import response.BrandResponse;
import response.CategoryResponse;
import response.ColorResponse;
import response.MaterialResponse;
import request.ProductDetailRequest;
import request.ProductDetailSearchRequest;
import response.BillDetailResponse;
import response.BillsResponse;
import response.CustomerResponse;
import response.ProductCardResponse;
import response.ProductDetailResponse;
import response.ProductsResponse;
import response.SizeResponse;
import response.SoleShoesResponse;
import response.VoucherResponse;
import service.impl.BillDetailServiceImpl;
import service.impl.BillsServiceImpl;
import service.impl.BrandServiceImpl;
import service.impl.CategoryServiceImpl;
import service.impl.ColorServiceImpl;
import service.impl.CustomerServiceImpl;
import service.impl.MaterialServiceImpl;
import service.impl.ProductDetailServiceImpl;
import service.impl.ProductsServiceImpl;
import service.impl.SizeServiceImpl;
import service.impl.SoleServiceImpl;
import service.impl.VoucherServiceImpl;
import util.GetListMonth;
import util.MoneyConverter;
import util.ZXingHelper;

/**
 *
 * @author LE MINH
 */
public class View extends javax.swing.JFrame implements Runnable, ThreadFactory {

    private Executor executor = Executors.newSingleThreadExecutor(this);
    private WebcamPanel webcamPanel = null;
    private Webcam webcam = null;
    private DefaultTableModel tableModel = new DefaultTableModel();
    private List<ProductsResponse> listProducts = new ArrayList<>();
    private List<CustomerResponse> listCustomers = new ArrayList<>();
    private List<BillsResponse> listCustomerBills = new ArrayList<>();
    private List<BillsResponse> listBills = new ArrayList<>();
    private List<BillDetailResponse> listBillDetails = new ArrayList<>();
    private List<BillDetailResponse> listToTableBillDetails = new ArrayList<>();
    private List<BillsResponse> listToTableBills = new ArrayList<>();
    private List<ProductsResponse> listToTableProducts = new ArrayList<>();
    private List<CustomerResponse> listToTableCustomer = new ArrayList<>();
    private List<BillsResponse> listToTableCustomerBills = new ArrayList<>();
    private List<ProductDetailResponse> listProductDetail = new ArrayList<>();
    private List<ProductDetailResponse> listToTableProductDetail = new ArrayList<>();
    private List<ProductDetailResponse> listToTableProductDetailShop = new ArrayList<>();
    private List<ProductCardResponse> listProductCard = new ArrayList<>();
    private List<BillsResponse> listBillsToday = new ArrayList<>();
    private final ProductsServiceImpl productsServiceImpl = new ProductsServiceImpl();
    private final CategoryServiceImpl categoryServiceImpl = new CategoryServiceImpl();
    private final BrandServiceImpl brandServiceImpl = new BrandServiceImpl();
    private final ProductDetailServiceImpl productDetailServiceImpl = new ProductDetailServiceImpl();
    private final ColorServiceImpl colorServiceImpl = new ColorServiceImpl();
    private final SoleServiceImpl soleServiceImpl = new SoleServiceImpl();
    private final MaterialServiceImpl materialServiceImpl = new MaterialServiceImpl();
    private final SizeServiceImpl sizeServiceImpl = new SizeServiceImpl();
    private final BillsServiceImpl billsServiceImpl = new BillsServiceImpl();
    private final CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
    private final BillDetailServiceImpl billDetailServiceImpl = new BillDetailServiceImpl();
    private final VoucherServiceImpl voucherServiceImpl = new VoucherServiceImpl();
    private Integer indexCard = null;
    private Integer indexProductDetail = null;
    private final String idEmployee = "NV01";

    /**
     * Creates new form View
     */
    public View() {
        initComponents();
        this.setLocationRelativeTo(null);
        setVisibleAllView();
        ViewBanHang.setVisible(true);
        pnaListProductShop.setPagegination(1, 10);
        listProducts = productsServiceImpl.getAllProducts();
        listProductDetail = productDetailServiceImpl.getAllProductDetail(null);
        listCustomers = customerServiceImpl.getListCustomer();
        listBills = billsServiceImpl.getAllBills();
        showListBillsByPage(1, listBills);
        showListCustomerByPage(1, listCustomers);
        showListProductDetailByPage(1, listProductDetail);
        showListProductDetailByPageShop(1, listProductDetail);
        showListProductsByPage(1, listProducts);
        setUpAllPagination();
        setDataAllCombobox();
        txtMaSanPham.setText("###");
        setAllEventComponent();
        initWebcamCapture();
        listBillsToday = billsServiceImpl.getAllBillsToday();
        showAllBillsToday(listBillsToday);
        txtTimeEndHD.setText("");
        txtTimeStartHD.setText("");
        setDataChart();
    }

    private void setUpAllPagination() {
        pnaListProductShop.setPaginationItemRender(new PaginationItemRenderStyle1());
        pnaListProducts.setPaginationItemRender(new PaginationItemRenderStyle1());
        pnaListProductDetail.setPaginationItemRender(new PaginationItemRenderStyle1());
        pnaListProductShop.setPaginationItemRender(new PaginationItemRenderStyle1());
        pnaListCustomer.setPaginationItemRender(new PaginationItemRenderStyle1());
        pnaListCustomerBills.setPaginationItemRender(new PaginationItemRenderStyle1());
        pnaListBillsHD.setPaginationItemRender(new PaginationItemRenderStyle1());
    }

    private void setVisibleAllView() {
        ViewBanHang.setVisible(false);
        ViewNhanVien.setVisible(false);
        ViewDoiMatKhau.setVisible(false);
        ViewHoaDon.setVisible(false);
        ViewVoucher.setVisible(false);
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
        cbbTimHang2.removeAllItems();
        cbbTimHang2.addItem("");
        cbbTimHangShop.removeAllItems();
        cbbTimHangShop.addItem("");
        for (BrandResponse item : brandServiceImpl.getAllBrand()) {
            if (item.getStatus()) {
                cbbHang.addItem(item.getBrandName());
            }
            cbbTimHang.addItem(item.getBrandName());
            cbbTimHang2.addItem(item.getBrandName());
            cbbTimHangShop.addItem(item.getBrandName());
        }

        //Category
        cbbTheLoai.removeAllItems();
        cbbTheLoai.addItem("");
        cbbTimTheLoai.removeAllItems();
        cbbTimTheLoai.addItem("");
        for (CategoryResponse item : categoryServiceImpl.getAllCategory()) {
            if (item.getStatus()) {
                cbbTheLoai.addItem(item.getCategoryName());
            }
            cbbTimTheLoai.addItem(item.getCategoryName());
        }

        //Color
        cbbMauSac.removeAllItems();
        cbbMauSac.addItem("");
        cbbTimMauSac.removeAllItems();
        cbbTimMauSac.addItem("");
        cbbTimMauSacShop.removeAllItems();
        cbbTimMauSacShop.addItem("");
        for (ColorResponse item : colorServiceImpl.getAllColor()) {
            if (item.getStatus()) {
                cbbMauSac.addItem(item.getColorName());
            }
            cbbTimMauSac.addItem(item.getColorName());
            cbbTimMauSacShop.addItem(item.getColorName());
        }

        //Material
        cbbChatLieu.removeAllItems();
        cbbChatLieu.addItem("");
        cbbTimChatLieu.removeAllItems();
        cbbTimChatLieu.addItem("");
        cbbTimChatLieuShop.removeAllItems();
        cbbTimChatLieuShop.addItem("");
        for (MaterialResponse item : materialServiceImpl.getAllMaterial()) {
            if (item.getStatus()) {
                cbbChatLieu.addItem(item.getMaterialName());
            }
            cbbTimChatLieu.addItem(item.getMaterialName());
            cbbTimChatLieuShop.addItem(item.getMaterialName());
        }

        //Size
        cbbSize.removeAllItems();
        cbbSize.addItem("");
        cbbTimSize.removeAllItems();
        cbbTimSize.addItem("");
        cbbTimSizeShop.removeAllItems();
        cbbTimSizeShop.addItem("");
        for (SizeResponse item : sizeServiceImpl.getAllSize()) {
            if (item.getStatus()) {
                cbbSize.addItem(item.getSizeName());
            }
            cbbTimSize.addItem(item.getSizeName());
            cbbTimSizeShop.addItem(item.getSizeName());
        }

        //Sole
        cbbDeGiay.removeAllItems();
        cbbDeGiay.addItem("");
        cbbTimDeGiay.removeAllItems();
        cbbTimDeGiay.addItem("");
        cbbTimDeGiayShop.removeAllItems();
        cbbTimDeGiayShop.addItem("");
        for (SoleShoesResponse item : soleServiceImpl.getAllSole()) {
            if (item.getStatus() == true) {
                cbbDeGiay.addItem(item.getSoleName());
            }
            cbbTimDeGiay.addItem(item.getSoleName());
            cbbTimDeGiayShop.addItem(item.getSoleName());
        }

        //Mã sản phẩm
        cbbMaSanPham.removeAllItems();
        cbbMaSanPham.addItem("");
        for (ProductsResponse item : listProducts) {
            cbbMaSanPham.addItem(item.getId());
        }

        //Hình thức thanh toán
        cbbHinhThucThanhToan.removeAllItems();
        cbbHinhThucThanhToan.addItem("");
        cbbHinhThucThanhToan.addItem("Tiền mặt");
        cbbHinhThucThanhToan.addItem("Chuyển khoản");
        cbbHinhThucThanhToan.addItem("Kết hợp");

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
        cbbTimMauSac.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                listProductDetail = productDetailServiceImpl.searchListProductDetail(getDataSearchProductDetail());
                showListProductDetailByPage(1, listProductDetail);
            }
        });
        cbbTimMauSacShop.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                listProductDetail = productDetailServiceImpl.searchListProductDetail(getDataSearchProductDetailShop());
                showListProductDetailByPageShop(1, listProductDetail);
            }
        });
        cbbTimHang2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                listProductDetail = productDetailServiceImpl.searchListProductDetail(getDataSearchProductDetail());
                showListProductDetailByPage(1, listProductDetail);
            }
        });
        cbbTimHangShop.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                listProductDetail = productDetailServiceImpl.searchListProductDetail(getDataSearchProductDetailShop());
                showListProductDetailByPageShop(1, listProductDetail);
            }
        });
        cbbTimDeGiay.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                listProductDetail = productDetailServiceImpl.searchListProductDetail(getDataSearchProductDetail());
                showListProductDetailByPage(1, listProductDetail);
            }
        });
        cbbTimDeGiayShop.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                listProductDetail = productDetailServiceImpl.searchListProductDetail(getDataSearchProductDetailShop());
                showListProductDetailByPageShop(1, listProductDetail);
            }
        });
        cbbTimChatLieu.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                listProductDetail = productDetailServiceImpl.searchListProductDetail(getDataSearchProductDetail());
                showListProductDetailByPage(1, listProductDetail);
            }
        });
        cbbTimChatLieuShop.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                listProductDetail = productDetailServiceImpl.searchListProductDetail(getDataSearchProductDetailShop());
                showListProductDetailByPageShop(1, listProductDetail);
            }
        });
        cbbTimSize.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                listProductDetail = productDetailServiceImpl.searchListProductDetail(getDataSearchProductDetail());
                showListProductDetailByPage(1, listProductDetail);
            }
        });
        cbbHinhThucSearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                listBills = billsServiceImpl.getAllBillsSearch(getDataSearchBills());
                showListBillsByPage(1, listBills);
            }
        });
        cbbTrangThaiSearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                listBills = billsServiceImpl.getAllBillsSearch(getDataSearchBills());
                showListBillsByPage(1, listBills);
            }
        });
        cbbTimSizeShop.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                listProductDetail = productDetailServiceImpl.searchListProductDetail(getDataSearchProductDetailShop());
                showListProductDetailByPageShop(1, listProductDetail);
            }
        });
        cbbTimTrangThai2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                listProductDetail = productDetailServiceImpl.searchListProductDetail(getDataSearchProductDetail());
                showListProductDetailByPage(1, listProductDetail);
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
        txtTimeStartHD.getDocument().addDocumentListener(new DocumentListener() {
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
                listBills = billsServiceImpl.getAllBillsSearch(getDataSearchBills());
                showListBillsByPage(1, listBills);
            }
        });
        txtTimeEndHD.getDocument().addDocumentListener(new DocumentListener() {
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
                listBills = billsServiceImpl.getAllBillsSearch(getDataSearchBills());
                showListBillsByPage(1, listBills);
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
        tblListProductShop.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    // your valueChanged overridden method 

                    if (lbMaHoaDon.getText().equalsIgnoreCase("###")) {
                        JOptionPane.showMessageDialog(View.getJframe(), "Vui lòng chọn hoá đơn!");
                        return;
                    }
                    String quantity = JOptionPane.showInputDialog("Mời nhập số lượng:");
                    if (quantity != null) {
                        try {
                            ProductDetailResponse productDetailResponse = listToTableProductDetailShop.get(row);
                            getItemToCard(productDetailResponse, Integer.parseInt(quantity));
                        } catch (NumberFormatException e) {
                            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Số lượng sai dữ liệu!");
                            notification.showNotification();
                        }
                    }
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
        pnaListProductShop.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                showListProductDetailByPageShop(page, listProductDetail);
            }
        });
        pnaListCustomer.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                listCustomers = customerServiceImpl.getListCustomer();
                showListCustomerByPage(page, listCustomers);
            }
        });
        pnaListCustomerBills.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                listCustomerBills = billsServiceImpl.getCustomerBill(txtMaKH.getText());
                showListCustomerBillsByPage(page, listCustomerBills);
            }
        });
        pnaListBillsHD.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                showListBillsByPage(page, listBills);
            }
        });
        pnaListBillDetail.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                showListBillDetailByPage(page, listBillDetails);
            }
        });
        dateTimeEndHD.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                if (action.getAction() == SelectedAction.DAY_SELECTED) {
                    dateTimeEndHD.hidePopup();
                }
            }
        });
        dateTimeStartHD.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                if (action.getAction() == SelectedAction.DAY_SELECTED) {
                    dateTimeStartHD.hidePopup();
                }
            }
        });
    }

    private Long getIdColor(String color) {
        Long result = null;
        for (ColorResponse item : colorServiceImpl.getAllColor()) {
            if (color.equalsIgnoreCase(item.getColorName())) {
                result = item.getId();
                break;
            }
        }
        return result;
    }

    private Long getIdSize(String size) {
        Long result = null;
        for (SizeResponse item : sizeServiceImpl.getAllSize()) {
            if (size.equalsIgnoreCase(item.getSizeName())) {
                result = item.getId();
                break;
            }
        }
        return result;
    }

    private Long getIdSole(String sole) {
        Long result = null;
        for (SoleShoesResponse item : soleServiceImpl.getAllSole()) {
            if (sole.equalsIgnoreCase(item.getSoleName())) {
                result = item.getId();
                break;
            }
        }
        return result;
    }

    private Long getIdMaterial(String material) {
        Long result = null;
        for (MaterialResponse item : materialServiceImpl.getAllMaterial()) {
            if (material.equalsIgnoreCase(item.getMaterialName())) {
                result = item.getId();
                break;
            }
        }
        return result;
    }

    //START: update view
    public void updateComboboxHang(String method) {
        int lastIndex = 0;
        cbbHang.removeAllItems();
        cbbHang.addItem("");
        cbbTimHang.removeAllItems();
        cbbTimHang.addItem("");
        cbbTimHang2.removeAllItems();
        cbbTimHang2.addItem("");
        for (BrandResponse item : brandServiceImpl.getAllBrand()) {
            if (item.getStatus()) {
                cbbHang.addItem(item.getBrandName());
                lastIndex++;
            }
            cbbTimHang.addItem(item.getBrandName());
            cbbTimHang2.addItem(item.getBrandName());
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
                cbbTheLoai.addItem(item.getCategoryName());
                lastIndex++;
            }
            cbbTimTheLoai.addItem(item.getCategoryName());
        }
        if (method.equalsIgnoreCase("add")) {
            cbbTheLoai.setSelectedIndex(lastIndex);
        }
    }

    public void updateComboboxMauSac(String method) {
        int lastIndex = 0;
        cbbMauSac.removeAllItems();
        cbbMauSac.addItem("");
        cbbTimMauSac.removeAllItems();
        cbbTimMauSac.addItem("");
        for (ColorResponse item : colorServiceImpl.getAllColor()) {
            if (item.getStatus()) {
                cbbMauSac.addItem(item.getColorName());
                lastIndex++;
            }
            cbbTimMauSac.addItem(item.getColorName());
        }
        if (method.equalsIgnoreCase("add")) {
            cbbMauSac.setSelectedIndex(lastIndex);
        }
    }

    public void updateComboboxSize(String method) {
        int lastIndex = 0;
        cbbSize.removeAllItems();
        cbbSize.addItem("");
        cbbTimSize.removeAllItems();
        cbbTimSize.addItem("");
        for (SizeResponse item : sizeServiceImpl.getAllSize()) {
            if (item.getStatus()) {
                cbbSize.addItem(item.getSizeName());
                lastIndex++;
            }
            cbbTimSize.addItem(item.getSizeName());
        }
        if (method.equalsIgnoreCase("add")) {
            cbbSize.setSelectedIndex(lastIndex);
        }
    }

    public void updateComboboxMaterial(String method) {
        int lastIndex = 0;
        cbbChatLieu.removeAllItems();
        cbbChatLieu.addItem("");
        cbbTimChatLieu.removeAllItems();
        cbbTimChatLieu.addItem("");
        for (MaterialResponse item : materialServiceImpl.getAllMaterial()) {
            if (item.getStatus()) {
                cbbChatLieu.addItem(item.getMaterialName());
                lastIndex++;
            }
            cbbTimChatLieu.addItem(item.getMaterialName());
        }
        if (method.equalsIgnoreCase("add")) {
            cbbChatLieu.setSelectedIndex(lastIndex);
        }
    }

    public void updateComboboxSole(String method) {
        int lastIndex = 0;
        cbbDeGiay.removeAllItems();
        cbbDeGiay.addItem("");
        cbbTimDeGiay.removeAllItems();
        cbbTimDeGiay.addItem("");
        for (SoleShoesResponse item : soleServiceImpl.getAllSole()) {
            if (item.getStatus()) {
                cbbDeGiay.addItem(item.getSoleName());
                lastIndex++;
            }
            cbbTimDeGiay.addItem(item.getSoleName());
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
        clearFormProductDetail();
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

    //START: Product detail
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

    private ProductDetailRequest getDataUpdateProductDetail(int index) {
        ProductDetailResponse productDetailResponse = listToTableProductDetail.get(index);
        Long id = productDetailResponse.getId();
        String idProduct = String.valueOf(cbbMaSanPham.getSelectedItem());
        if (idProduct.trim().isEmpty()) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng chọn mã sản phẩm!");
            notification.showNotification();
            return null;
        }
        if (checkStringEmpty(txtGiaBanSanPham.getText())) {
            String mes = "";
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
        Long idColor = getIdColor(colorName);
        //Lấy id size
        String sizeName = String.valueOf(cbbSize.getSelectedItem());
        if (checkStringEmpty(sizeName)) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng chọn kích cỡ!");
            notification.showNotification();
            return null;
        }
        Long idSize = getIdSize(sizeName);
        //Lấy id sole
        String soleName = String.valueOf(cbbDeGiay.getSelectedItem());
        if (checkStringEmpty(soleName)) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng chọn đế giày!");
            notification.showNotification();
            return null;
        }
        Long idSole = getIdSole(soleName);
        //Lấy id material
        String materialName = String.valueOf(cbbChatLieu.getSelectedItem());
        if (checkStringEmpty(materialName)) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng chọn chất liệu!");
            notification.showNotification();
            return null;
        }
        Long idMaterial = getIdMaterial(materialName);
        Boolean status = true;
        return new ProductDetailRequest(id, idProduct, BigDecimal.valueOf(price), quantiy, idColor, idSize, idMaterial, idSole, status);

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
            String mes = "";
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
        Long idColor = getIdColor(colorName);
        //Lấy id size
        String sizeName = String.valueOf(cbbSize.getSelectedItem());
        if (checkStringEmpty(sizeName)) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng chọn kích cỡ!");
            notification.showNotification();
            return null;
        }
        Long idSize = getIdSize(sizeName);
        //Lấy id sole
        String soleName = String.valueOf(cbbDeGiay.getSelectedItem());
        if (checkStringEmpty(soleName)) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng chọn đế giày!");
            notification.showNotification();
            return null;
        }
        Long idSole = getIdSole(soleName);
        //Lấy id material
        String materialName = String.valueOf(cbbChatLieu.getSelectedItem());
        if (checkStringEmpty(materialName)) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Vui lòng chọn chất liệu!");
            notification.showNotification();
            return null;
        }
        Long idMaterial = getIdMaterial(materialName);
        Boolean status = true;
        return new ProductDetailRequest(id, idProduct, BigDecimal.valueOf(price), quantiy, idColor, idSize, idMaterial, idSole, status);
    }

    private void showDetailProductDetail(int index) throws WriterException, IOException {
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
        byte[] qrCode = ZXingHelper.generateBarcode(String.valueOf(productDetailResponse.getId()), 200, 50);
        lbQRCode.setIcon(new ImageIcon(qrCode));
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
        if (indexProductDetail == null) {
            JOptionPane.showMessageDialog(this, "Vui lọng chọn sản phẩm");
            return;
        }
            System.out.println(indexProductDetail);
        if (productDetailServiceImpl.updateProductDetail(getDataUpdateProductDetail(indexProductDetail))) {
            Notification notification = new Notification(View.getJframe(), Notification.Type.SUCCESS, Notification.Location.TOP_RIGHT, "Sửa thành công!");
            notification.showNotification();
            String idProduct = String.valueOf(cbbMaSanPham.getSelectedItem());
            listProductDetail = productDetailServiceImpl.getAllProductDetail(idProduct);
            showListProductDetailByPage(1, listProductDetail);
            showListProductDetailByPageShop(1, productDetailServiceImpl.getAllProductDetail(null));
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
        lbQRCode.setIcon(null);
    }

    private ProductDetailSearchRequest getDataSearchProductDetail() {
        String idProduct = String.valueOf(cbbMaSanPham.getSelectedItem());
        if (idProduct.isEmpty()) {
            idProduct = null;
        }
        String colorName = String.valueOf(cbbTimMauSac.getSelectedItem());
        if (colorName.isEmpty()) {
            colorName = null;
        }
        String sizeName = String.valueOf(cbbTimSize.getSelectedItem());
        if (sizeName.isEmpty()) {
            sizeName = null;
        }
        String materialName = String.valueOf(cbbTimChatLieu.getSelectedItem());
        if (materialName.isEmpty()) {
            materialName = null;
        }
        String soleName = String.valueOf(cbbTimDeGiay.getSelectedItem());
        if (soleName.isEmpty()) {
            soleName = null;
        }
        String brandName = String.valueOf(cbbTimHang2.getSelectedItem());
        if (brandName.isEmpty()) {
            brandName = null;
        }
        String statusName = String.valueOf(cbbTimTrangThai2.getSelectedItem());
        Boolean status = null;
        if (statusName.equalsIgnoreCase("Ngừng bán")) {
            status = false;
        }
        if (statusName.equalsIgnoreCase("Đang bán")) {
            status = true;
        }
        return new ProductDetailSearchRequest(colorName, sizeName, materialName, soleName, idProduct, status, brandName);
    }

    private void exportProductDetail() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Danh sach san pham");
            XSSFRow row = null;
            Cell cell = null;

            row = sheet.createRow(3);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Tên sản phâm");

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Hãng");

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Giá bán");

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Màu sắc");

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Kích cỡ");

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Đế giày");

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue("Chất liệu");

            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue("Số lượng");

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue("Trạng thái");

            List<ProductDetailResponse> list = new ArrayList<>();
            if (rdoExportExcel.isSelected()) {
                list = productDetailServiceImpl.getAllProductDetail(null);
            } else {
                String id = String.valueOf(cbbMaSanPham.getSelectedItem());
                list = productDetailServiceImpl.getAllProductDetail(id);
            }
            System.out.println(list.size());
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    ProductDetailResponse item = list.get(i);
                    row = sheet.createRow(4 + i);

                    cell = row.createCell(0, CellType.NUMERIC);
                    cell.setCellValue(i + 1);

                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellValue(item.getProductName());

                    cell = row.createCell(2, CellType.STRING);
                    cell.setCellValue(item.getBrandName());

                    cell = row.createCell(3, CellType.NUMERIC);
                    cell.setCellValue(Double.parseDouble(String.valueOf(item.getPrice())));

                    cell = row.createCell(4, CellType.STRING);
                    cell.setCellValue(item.getColor());

                    cell = row.createCell(5, CellType.STRING);
                    cell.setCellValue(item.getSize());

                    cell = row.createCell(6, CellType.STRING);
                    cell.setCellValue(item.getSoleShoes());

                    cell = row.createCell(7, CellType.STRING);
                    cell.setCellValue(item.getMaterial());

                    cell = row.createCell(8, CellType.NUMERIC);
                    cell.setCellValue(item.getQuantity());

                    if (item.getStatus()) {
                        cell = row.createCell(9, CellType.STRING);
                        cell.setCellValue("Đang bán");
                    } else {
                        cell = row.createCell(9, CellType.STRING);
                        cell.setCellValue("Ngừng bán");
                    }
                    System.out.println("Xuất thành công " + item.getProductName());
                }
                String fileName = "export_" + LocalDate.now() + ".xlsx";
                File file = new File("C:\\Users\\LE MINH\\Documents\\du-an-1\\" + fileName);
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    workbook.write(out);
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(this, "Xuất file Excel thành công ");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

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
    //START: Webcam capture
    private void initWebcamCapture() {
        Dimension size = WebcamResolution.QVGA.getSize();
        try {
            webcam = Webcam.getWebcams().get(0);
        } catch (IndexOutOfBoundsException e) {
            // Không tìm thấy webcam, xử lý lỗi
            System.out.println("Không tìm thấy webcam!");
        }
        webcam.setViewSize(size);
//        webcam.open();

        webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setFPSDisplayed(true);
        webcamPanel.setPreferredSize(size);

        lbWebcam.add(webcamPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 230));
        lbWebcam.getParent().revalidate();

        executor.execute(this);
    }
    //END: Webcam capture

    //START: Bán hàng
    //START: Hoá đơn chi tiết
    private void showListProductDetailByPageShop(int page, List<ProductDetailResponse> list) {
        int litmit = 3;
        int totalPage = (int) Math.ceil(((double) list.size() / litmit));
        tableModel = (DefaultTableModel) tblListProductShop.getModel();
        tableModel.setRowCount(0);
        pnaListProductShop.setPagegination(page, totalPage);
        listToTableProductDetailShop.clear();
        for (int i = 0; i < list.size(); i++) {
            if (i >= (page - 1) * litmit && i <= (litmit * page) - 1) {
                ProductDetailResponse item = list.get(i);
                if (item.getStatus() == true) {
                    listToTableProductDetailShop.add(item);
                    tableModel.addRow(new Object[]{
                        i + 1,
                        item.getProductName(),
                        item.getBrandName(),
                        item.getColor(),
                        item.getSize(),
                        item.getMaterial(),
                        item.getSoleShoes(),
                        MoneyConverter.parse(item.getPrice()),
                        item.getQuantity()
                    });

                }
            }
        }
    }

    private ProductDetailSearchRequest getDataSearchProductDetailShop() {
        String idProduct = null;
        String colorName = String.valueOf(cbbTimMauSac.getSelectedItem());
        if (colorName.isEmpty()) {
            colorName = null;
        }
        String sizeName = String.valueOf(cbbTimSizeShop.getSelectedItem());
        if (sizeName.isEmpty()) {
            sizeName = null;
        }
        String materialName = String.valueOf(cbbTimChatLieuShop.getSelectedItem());
        if (materialName.isEmpty()) {
            materialName = null;
        }
        String soleName = String.valueOf(cbbTimDeGiayShop.getSelectedItem());
        if (soleName.isEmpty()) {
            soleName = null;
        }
        String brandName = String.valueOf(cbbTimHangShop.getSelectedItem());
        if (brandName.isEmpty()) {
            brandName = null;
        }
        Boolean status = true;
        return new ProductDetailSearchRequest(colorName, sizeName, materialName, soleName, idProduct, status, brandName);
    }

    private BillDetailRequest getDataBillDetail(ProductDetailResponse productDetailResponse, int quantity) {
        String idBill = lbMaHoaDon.getText();
        if (idBill.equalsIgnoreCase("###")) {
            return null;
        }
        Long idProductDetail = productDetailResponse.getId();
        BigDecimal totalMoney = productDetailResponse.getPrice().multiply(BigDecimal.valueOf((double) quantity));
        Boolean status = true;
        return new BillDetailRequest(null, idBill, idProductDetail, quantity, totalMoney, status);
    }

    private BillDetailUpdateRequest getDataUpdateBillDetail(ProductDetailResponse productDetailResponse, int quantity, String method) {
        ProductCardResponse productCardResponse = new ProductCardResponse();
        for (ProductCardResponse item : listProductCard) {
            if (Objects.equals(productDetailResponse.getId(), item.getIdProductDetail())) {
                productCardResponse = item;
                break;
            }
        }
        String idBill = lbMaHoaDon.getText();
        Integer quantityUpdate = null;
        BigDecimal totalMoneyUpdate = null;
        Long idProductDetail = productDetailResponse.getId();
        if (method.equalsIgnoreCase("addQuantity")) {
            quantityUpdate = quantity + productCardResponse.getQuantity();
            BigDecimal plusTotalMoney = productDetailResponse.getPrice().multiply(BigDecimal.valueOf((double) quantity));
            totalMoneyUpdate = productCardResponse.getTotalMoney().add(plusTotalMoney);
        }
        if (method.equalsIgnoreCase("updateQuantity")) {
            quantityUpdate = quantity;
            totalMoneyUpdate = productDetailResponse.getPrice().multiply(BigDecimal.valueOf((double) quantity));
        }
        return new BillDetailUpdateRequest(quantityUpdate, totalMoneyUpdate, idProductDetail, idBill);
    }
    //END: Hoá đơn chi tiết

    //START: Giỏ hàng
    private void showListProductDetailCard(List<ProductCardResponse> list) {
        tableModel = (DefaultTableModel) tblListProductCard.getModel();
        tableModel.setRowCount(0);
        for (ProductCardResponse item : list) {
            tableModel.addRow(new Object[]{
                item.getProductName(),
                item.getColorName(),
                item.getSizeName(),
                item.getMaterialName(),
                item.getSoleName(),
                item.getQuantity(),
                MoneyConverter.parse(item.getTotalMoney())
            });
        }
    }

    private boolean isExistInCard(ProductDetailResponse item) {
        if (billDetailServiceImpl.checkCardItemInDetailBill(item.getId(), lbMaHoaDon.getText())) {
            return true;
        }
        return false;
    }

    private int getQuantityProductCard(Long id) {
        int result = 0;
        for (ProductCardResponse item : listProductCard) {
            if (Objects.equals(id, item.getIdProductDetail())) {
                result = item.getQuantity();
            }
        }
        return result;
    }

    private boolean checkQuantity(Long idProductDetail, int quantity, String method, String idBills) {
        int totalQuanity = 0;
        List<ProductCardResponse> listTest = new ArrayList<>();
        for (BillsResponse billsResponse : billsServiceImpl.getAllBillsToday()) {
            for (ProductCardResponse item : billDetailServiceImpl.getListProductCard(billsResponse.getId())) {
                if (idProductDetail == item.getIdProductDetail()) {
                    if (method.equalsIgnoreCase("update") && idBills.equalsIgnoreCase(billsResponse.getId())) {
                        item.setQuantity(quantity);
                    }
                    totalQuanity += item.getQuantity();
                }
            }
        }
        if (method.equalsIgnoreCase("add")) {
            totalQuanity += quantity;
        }
        if (totalQuanity > productDetailServiceImpl.getQuantityByProductDetail(idProductDetail)) {
            return false;
        }
        return true;
    }

    private void getItemToCard(ProductDetailResponse productDetailResponse, int quantity) {
        String idBill = lbMaHoaDon.getText();
        if (!checkQuantity(productDetailResponse.getId(), quantity, "add", idBill)) {
            JOptionPane.showMessageDialog(this, "Lỗi. Sản phẩm trong kho không đủ!");
            return;
        }
        if (isExistInCard(productDetailResponse)) {
            //Nếu product đã tồn tại trong card
            int totalQuantity = quantity + getQuantityProductCard(productDetailResponse.getId());
            if (billDetailServiceImpl.updateBillDetail(getDataUpdateBillDetail(productDetailResponse, quantity, "addQuantity"))) {
                listProductCard = billDetailServiceImpl.getListProductCard(idBill);
                showAllProductCardByBill(listProductCard);
                caculatorBills(idBill);
                Notification notification = new Notification(View.getJframe(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Đã thêm vào giỏ hàng!");
                notification.showNotification();
            }
        } else {

            //Nếu product chưa có trong card thì thêm mới vào
            if (billDetailServiceImpl.addBillDetail(getDataBillDetail(productDetailResponse, quantity))) {
                System.out.println("vào đây");
                listProductCard = billDetailServiceImpl.getListProductCard(idBill);
                showAllProductCardByBill(listProductCard);
                caculatorBills(idBill);
                Notification notification = new Notification(View.getJframe(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Đã thêm vào giỏ hàng!");
                notification.showNotification();
            }
        }
    }

    private void changeQuantityProductCard(int index, int quantity) {
        String idBill = lbMaHoaDon.getText();
        ProductCardResponse productCardResponse = listProductCard.get(index);
        ProductDetailResponse productDetailResponse = productDetailServiceImpl.getProductDetailById(productCardResponse.getIdProductDetail());
        // Kiểm tra xem số lượng có bị nhiều hơn sản phầm tồn trong kho hay k
        if (!checkQuantity(productDetailResponse.getId(), quantity, "update", idBill)) {
            JOptionPane.showMessageDialog(this, "Lỗi. Sản phẩm trong kho không đủ!");
            return;
        }
        if (billDetailServiceImpl.updateBillDetail(getDataUpdateBillDetail(productDetailResponse, quantity, "updateQuantity"))) {
            listProductCard = billDetailServiceImpl.getListProductCard(idBill);
            showAllProductCardByBill(listProductCard);
            caculatorBills(idBill);
            Notification notification = new Notification(View.getJframe(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Đã sửa số lượng!");
            notification.showNotification();
        }
    }

    private void scanBarcodeProductCard(String result) {
        if (lbMaHoaDon.getText().equalsIgnoreCase("###")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoá đơn!");
            return;
        }
        String value = JOptionPane.showInputDialog("Nhập số lượng:");
        if (value != null) {
            int quantity = Integer.parseInt(value);
            ProductDetailResponse productDetailResponse = productDetailServiceImpl.getProductDetailById(Long.valueOf(result));
            getItemToCard(productDetailResponse, quantity);
            webcam.close();
        }
    }

    private void deleteProductCard(int index) {
        ProductCardResponse productCardResponse = listProductCard.get(index);
        String idBill = lbMaHoaDon.getText();
        if (idBill.equalsIgnoreCase("###")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoá đơn!");
            return;
        }
        int option = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá không?");
        if (option == 0) {
            if (billDetailServiceImpl.deleteBillDetail(productCardResponse.getIdProductDetail(), idBill)) {
                listProductCard = billDetailServiceImpl.getListProductCard(idBill);
                showAllProductCardByBill(listProductCard);
                caculatorBills(idBill);
                Notification notification = new Notification(View.getJframe(), Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Đã xoá khỏi giỏ hàng!");
                notification.showNotification();
            }
        }
    }

    //END: Giỏ hàng
    //START: Hoá đơn
    private BigDecimal getTotalMoneyCard() {
        BigDecimal result = BigDecimal.ZERO;
        for (ProductCardResponse item : billDetailServiceImpl.getListProductCard(lbMaHoaDon.getText())) {
            result = result.add(item.getTotalMoney());
        }
        return result;
    }

    private BillsRequest getDataBill(String method) {
        String id = lbMaHoaDon.getText();
        String idCustomer = lbMaKhachHang.getText();
        Boolean status = true;
        if (method.contains("add")) {
            status = false;
        }
        String phoneNumber = txtSDTKhachHang.getText();
        String convertMoney = lbTongTien.getText().substring(0, lbTongTien.getText().length() - 2).replace(",", "");
        BigDecimal totalMoney = BigDecimal.ZERO;
        String idVoucher = null;
        String convertMoney2 = lbTienDuocGiam.getText().substring(0, lbTienDuocGiam.getText().length() - 2).replace(",", "");
        BigDecimal reduceMoney = BigDecimal.ZERO;

        String payment = String.valueOf(cbbHinhThucThanhToan.getSelectedItem());
        Long idPayment = null;
        if (payment.contains("Chuyển khoản")) {
            idPayment = Long.valueOf(1);
        }
        if (payment.contains("Tiền mặt")) {
            idPayment = Long.valueOf(2);
        }
        if (payment.contains("Kết hợp")) {
            idPayment = Long.valueOf(3);
        }
        if (method.contains("update")) {
            totalMoney = new BigDecimal(convertMoney.trim());
            reduceMoney = new BigDecimal(convertMoney2.trim());

        }
        return new BillsRequest(id, idCustomer, status, phoneNumber, totalMoney, idVoucher, idPayment, idEmployee, reduceMoney);
    }

    private void showAllProductCardByBill(List<ProductCardResponse> list) {
        tableModel = (DefaultTableModel) tblListProductCard.getModel();
        tableModel.setRowCount(0);
        for (ProductCardResponse productCardResponse : list) {
            tableModel.addRow(new Object[]{
                productCardResponse.getProductName(),
                productCardResponse.getColorName(),
                productCardResponse.getSizeName(),
                productCardResponse.getMaterialName(),
                productCardResponse.getSoleName(),
                productCardResponse.getQuantity(),
                MoneyConverter.parse(productCardResponse.getTotalMoney())
            });
        }
    }

    private void showAllBillsToday(List<BillsResponse> list) {
        tableModel = (DefaultTableModel) tblListHoaDonHomNay.getModel();
        tableModel.setRowCount(0);
        for (BillsResponse billsResponse : list) {
            tableModel.addRow(new Object[]{
                billsResponse.getId(),
                billsResponse.getIdEmployee(),
                String.valueOf(billsResponse.getTimeCreate()).substring(0, 16),
                billsResponse.getStatus() ? "Đã thanh toán" : "Chưa thanh toán"
            });
        }
    }

    private void addBills() {
        if (billsServiceImpl.getAllBillsToday().size() == 10) {
            JOptionPane.showMessageDialog(this, "Chỉ xử lý được 10 hoá đơn đang chờ!");
            return;
        }
        int option = JOptionPane.showConfirmDialog(this, "Bạn muốn thêm hoá đơn?");
        if (option == 0) {
            lbMaKhachHang.setText(customerServiceImpl.getIdCustomer());
            if (customerServiceImpl.addCustomer(getDataCustomer()) && billsServiceImpl.addBill(getDataBill("add"))) {
                BillsResponse billsResponse = billsServiceImpl.getBills();
                lbMaHoaDon.setText(billsResponse.getId());
                lbTongTien.setText("0 đ");
                lbTamTinh.setText("0 đ");
                lbTienDuocGiam.setText("0 đ");
                listBillsToday = billsServiceImpl.getAllBillsToday();
                showAllBillsToday(listBillsToday);
                Notification notification = new Notification(View.getJframe(), Notification.Type.SUCCESS, Notification.Location.TOP_RIGHT, "Đã thêm hoá đơn!");
                notification.showNotification();
            }
        }
    }

    private void clearFormShop() {
        lbMaHoaDon.setText("###");
        lbMaKhachHang.setText("###");
        lbTamTinh.setText("###");
        lbTienDuocGiam.setText("###");
        lbTongTien.setText("###");
        txtTenKhachHang.setText("");
        txtSDTKhachHang.setText("");
        cbbHinhThucThanhToan.setSelectedIndex(0);
        txtVoucher.setText("");
    }

    private void setQuantityProductDetail(List<ProductCardResponse> list) {
        for (ProductCardResponse productCardResponse : list) {
            productDetailServiceImpl.setMinusQuantityProductDetail(productCardResponse.getIdProductDetail(), productCardResponse.getQuantity());
        }
    }

    private void completeBills() {
        if (lbMaHoaDon.getText().equalsIgnoreCase("###")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoá đơn!");
            return;
        }
        if (listProductCard.size() == 0) {
            JOptionPane.showMessageDialog(this, "Hoá đơn chưa có sản phẩm!");
            return;
        }
        String typePayment = String.valueOf(cbbHinhThucThanhToan.getSelectedItem());
        if (typePayment.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phương thức thanh toán!");
            return;
        }
        int option = JOptionPane.showConfirmDialog(this, "Bạn muốn hoàn thành hoá đơn này chứ?");
        if (option == 0) {
            String idCustomer = lbMaKhachHang.getText();
            BillsRequest billsRequest = billsServiceImpl.getBillsRequestById(lbMaHoaDon.getText());
            if (typePayment.equalsIgnoreCase("Tiền mặt")) {
                billsRequest.setIdPayment(Long.valueOf(2));
            } else {
                billsRequest.setIdPayment(Long.valueOf(1));
            }
            billsRequest.setStatus(true);
            if (billsServiceImpl.updateBill(billsRequest) && customerServiceImpl.updateCustomer(getDataCustomer())) {
                if (billsRequest.getIdVoucher() != null) {
                    if (billsRequest.getReduceMoney().compareTo(new BigDecimal(0)) > 0) {
                        //Nếu hoá đơn đã đc giảm thì set voucher là false
                        System.out.println(voucherServiceImpl.setDisableVoucher(billsRequest.getIdVoucher()));
                    } else {
                        // Nếu hoá đơn chưa đc giảm thì set voucher là false
                        if (billsServiceImpl.setNullVoucherInBills(billsRequest.getId())) {
                            JOptionPane.showMessageDialog(this, "Voucher " + billsRequest.getIdVoucher() + " sẽ đc áp dụng cho lần sau.");
                        }
                    }
                }
                System.out.println(billsRequest);
                if (customerServiceImpl.getNumberOfPurchase(idCustomer) >= 5) {
                    new ViewSendEmail(this, true, idCustomer).setVisible(true);
                }
                try {
                    exportBills(billsServiceImpl.getBillsResponseById(billsRequest.getId()));
                } catch (IOException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
                setQuantityProductDetail(listProductCard);
                listProductDetail = productDetailServiceImpl.getAllProductDetail(null);
                showListProductDetailByPage(1, listProductDetail);
                showListProductDetailByPageShop(1, listProductDetail);
                listBillsToday = billsServiceImpl.getAllBillsToday();
                showAllBillsToday(listBillsToday);
                listProductCard.clear();
                showAllProductCardByBill(listProductCard);
                clearFormShop();
                Notification notification = new Notification(View.getJframe(), Notification.Type.SUCCESS, Notification.Location.TOP_RIGHT, "Đã hoàn thành!");
                notification.showNotification();

            } else {
                System.out.println("ko okk");
            }
        }
    }

    private CustomerRequest getDataCustomer() {
        String id = lbMaKhachHang.getText();
        CustomerResponse customerResponse = customerServiceImpl.getCustomerById(id);
        String fullname = txtTenKhachHang.getText();
        String phoneNumber = txtSDTKhachHang.getText();
        Long type = Long.valueOf(1); //Khách quen
        if (fullname.isEmpty() && phoneNumber.isEmpty()) {
            type = Long.valueOf(2); //Khách lẻ
        }
        String email = "";
        if (customerResponse != null) {
            email = customerResponse.getEmail();
        }
        return new CustomerRequest(id, fullname, phoneNumber, type, email);
    }

    private void showDetailBills(int index) {
        BillsResponse billsResponse = listBillsToday.get(index);
        listProductCard = billDetailServiceImpl.getListProductCard(billsResponse.getId());
        lbMaHoaDon.setText(billsResponse.getId());
        lbMaKhachHang.setText(billsResponse.getIdCustomer());
        txtTenKhachHang.setText(billsResponse.getCustomerName());
        txtSDTKhachHang.setText(billsResponse.getPhoneNumber() != null ? billsResponse.getPhoneNumber() : "");
        lbTamTinh.setText(MoneyConverter.parse(getTotalMoneyCard()));
        lbTienDuocGiam.setText(MoneyConverter.parse(billsResponse.getReduceMoney()));
        lbTongTien.setText(MoneyConverter.parse(billsResponse.getTotalMoney()));
        showAllProductCardByBill(listProductCard);
        VoucherResponse voucher = voucherServiceImpl.getVoucherById(billsResponse.getIdVoucher());
        if (voucher != null) {
            if (voucher.getType().equalsIgnoreCase("Tiền")) {
                txtVoucher.setText("Giảm " + MoneyConverter.parse(voucher.getValue()) + " với hoá đơn đạt " + MoneyConverter.parse(voucher.getValueCondition()));
            } else {
                txtVoucher.setText("Giảm " + voucher.getValue().setScale(0) + "%" + " với hoá đơn đạt " + MoneyConverter.parse(voucher.getValueCondition()));
            }
        } else {
            txtVoucher.setText("");
        }
    }

    public void getDataCustomerSelected(CustomerResponse customerResponse) {
        lbMaKhachHang.setText(customerResponse.getId());
        txtTenKhachHang.setText(customerResponse.getFullname());
        txtSDTKhachHang.setText(customerResponse.getPhoneNumber());
        String idBill = lbMaHoaDon.getText();
        String idCustomer = lbMaKhachHang.getText();
        String phone = txtSDTKhachHang.getText();
        if (billsServiceImpl.updateBillCustomer(idBill, idCustomer, phone)) {
            customerServiceImpl.deleteCustomer();
            listBillsToday = billsServiceImpl.getAllBillsToday();
            showAllBillsToday(listBillsToday);
        }
    }

    private void exportBills(BillsResponse billsResponse) throws IOException {
        int x = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hoá đơn này chứ?");

        if (x != 0) {
            return;
        }

        String path = "";
        JFileChooser j = new JFileChooser();

        int option = j.showSaveDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getPath();
        }

        Document doc = new Document();

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path + billsResponse.getId() + ".pdf"));

            doc.open();
            List<BillDetailResponse> listBillDetail = billDetailServiceImpl.getBillDetailByBills(billsResponse.getId());

            File fontFile = new File("C:\\FPT Polytechnic\\JAVA 3\\Project\\PRO1041-SD18404-NHOM3\\src\\font\\vuArial.ttf");
            BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(bf, 15);

            Paragraph text = new Paragraph("HOÁ ĐƠN BÁN HÀNG", font);
            text.setAlignment(Element.ALIGN_CENTER);
//            text.setFont(fontBold);
            doc.add(text);

            Paragraph text1 = new Paragraph("Ngày: " + billsResponse.getTimeCreate().toString().substring(0, 16));
            doc.add(text1);

            Paragraph text2 = new Paragraph("Thu ngân: " + billsResponse.getIdEmployee());
            doc.add(text2);

            Paragraph text3 = new Paragraph("Khách hàng: " + billsResponse.getCustomerName());
            text3.setSpacingAfter(30f);
            doc.add(text3);

            PdfPTable tbl = new PdfPTable(3);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Tên sản phẩm", font));
            cell1.setBorderColor(BaseColor.BLUE);
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Số lượng", font));
            cell1.setBorderColor(BaseColor.BLUE);
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell3 = new PdfPCell(new Paragraph("Thành tiền", font));
            cell1.setBorderColor(BaseColor.BLUE);
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

            tbl.addCell(cell1);
            tbl.addCell(cell2);
            tbl.addCell(cell3);

            BigDecimal totalMoneyBillDetail = BigDecimal.ZERO;
            for (int i = 0; i < listBillDetail.size(); i++) {
                BillDetailResponse item = listBillDetail.get(i);
                tbl.addCell(new Paragraph(item.getProductName(), font));
                tbl.addCell(new Paragraph(String.valueOf(item.getQuantity()), font));
                tbl.addCell(new Paragraph(MoneyConverter.parse(item.getTotalMoney()), font));
                totalMoneyBillDetail = totalMoneyBillDetail.add(item.getTotalMoney());
            }

            doc.add(tbl);

            Paragraph text4 = new Paragraph("Tiền hàng: " + MoneyConverter.parse(totalMoneyBillDetail), font);
            text4.setSpacingBefore(30f);
            doc.add(text4);

            Paragraph text5 = new Paragraph("Được giảm: " + MoneyConverter.parse(billsResponse.getReduceMoney()), font);
            doc.add(text5);

            Paragraph text6 = new Paragraph("Tổng: " + MoneyConverter.parse(billsResponse.getTotalMoney()), font);
            doc.add(text6);

            Paragraph text7 = new Paragraph("Xin cảm ơn quý khách!", font);
            text7.setAlignment(Element.ALIGN_CENTER);
            doc.add(text7);

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        doc.close();
    }

    //END: Hoá đơn
    //START: Tính tiền hoá đơn
    private void caculatorBills(String idBill) {
        BillsRequest billsRequest = billsServiceImpl.getBillsRequestById(idBill);
        VoucherResponse voucher = voucherServiceImpl.getVoucherById(billsRequest.getIdVoucher());
        if (voucher != null) {
            //Hoá đơn đã có voucher
            if (voucher.getType().equalsIgnoreCase("Phần trăm")) {
                //Nếu voucher giảm theo phần trăm
                System.out.println("Voucher phần trăm");
                BigDecimal reduceMoney = getTotalMoneyCard().divide(new BigDecimal(100)).multiply(voucher.getValue());

                if (getTotalMoneyCard().compareTo(voucher.getValueCondition()) < 0) {
                    // Nếu tồng tiền < điều kiện của voucher
                    billsRequest.setTotalMoney(getTotalMoneyCard());
                    billsRequest.setReduceMoney(new BigDecimal(0));
                } else {
                    // Nếu tổng tiền hợp lệ
                    if (getTotalMoneyCard().compareTo(reduceMoney) < 0) {
                        //Nếu tổng tiền < tiền đc giảm
                        billsRequest.setTotalMoney(new BigDecimal(0));
                        billsRequest.setReduceMoney(reduceMoney);
                    } else {
                        // Nếu tổng tiền > tiền đc giảm
                        billsRequest.setTotalMoney(getTotalMoneyCard().subtract(reduceMoney));
                        billsRequest.setReduceMoney(reduceMoney);
                    }
                }
                billsRequest.setIdPayment(Long.valueOf(1));
            } else {
                //Nếu voucher giảm theo tiền
                System.out.println("VOucher tiền");
                BigDecimal reduceMoney = voucher.getValue();

                if (getTotalMoneyCard().compareTo(voucher.getValueCondition()) < 0) {
                    // Nếu tồng tiền < điều kiện của voucher
                    billsRequest.setTotalMoney(getTotalMoneyCard());
                    billsRequest.setReduceMoney(new BigDecimal(0));
                } else {
                    // Nếu tổng tiền hợp lệ
                    if (getTotalMoneyCard().compareTo(reduceMoney) < 0) {
                        //Nếu tổng tiền < tiền đc giảm
                        billsRequest.setTotalMoney(new BigDecimal(0));
                        billsRequest.setReduceMoney(reduceMoney);
                    } else {
                        // Nếu tổng tiền > tiền đc giảm
                        billsRequest.setTotalMoney(getTotalMoneyCard().subtract(reduceMoney));
                        billsRequest.setReduceMoney(reduceMoney);
                    }
                }
                billsRequest.setIdPayment(Long.valueOf(1));
            }
        } else {
            //Hoá đơn không có voucher
            System.out.println("k có voucher");
            billsRequest.setReduceMoney(new BigDecimal(0));
            billsRequest.setTotalMoney(getTotalMoneyCard());
            billsRequest.setIdPayment(Long.valueOf(1));
        }
        if (billsServiceImpl.updateBill(billsRequest)) {
            lbTamTinh.setText(MoneyConverter.parse(getTotalMoneyCard()));
            lbTienDuocGiam.setText(MoneyConverter.parse(billsRequest.getReduceMoney()));
            lbTongTien.setText(MoneyConverter.parse(billsRequest.getTotalMoney()));
        }
    }
    //END: Tính tiền hoá đơn

    //Start: voucher
    private void getVoucher() {
        String idBill = lbMaHoaDon.getText();
        String idCustomer = lbMaKhachHang.getText();
        if (idBill.equalsIgnoreCase("###")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoá đơn");
            return;
        }
        BillsRequest billsRequest = getDataBill("update");
        String value = JOptionPane.showInputDialog(this, "Áp dụng voucher cho " + idBill + ". Nhập mã voucher:");
        VoucherResponse voucher = voucherServiceImpl.getVoucherById(value);

        //Nếu voucher không hợp lệ
        if (voucher == null || voucher.getIdCustomer().isBlank()) {
            JOptionPane.showMessageDialog(this, "Voucher không tồn tại!");
            return;
        }

        //Nếu voucher đã đc sử dụng
        if (!voucher.getStatus() || billsServiceImpl.isExistVoucherInBills(value)) {
            JOptionPane.showMessageDialog(this, "Voucher đã đc sử dụng!");
            return;
        }

        //Kiểm tra voucher đã hết hạn chưa
        Date today = new Date();
        if (today.compareTo(voucher.getTimeStart()) < 0) {
            JOptionPane.showMessageDialog(this, "Voucher chưa đến ngày sử dụng!");
            return;
        }

        if (today.compareTo(voucher.getTimeEnd()) > 0) {
            JOptionPane.showMessageDialog(this, "Voucher đã hết hạn!");
            return;
        }

        if (!voucher.getIdCustomer().equals(idCustomer)) {
            int option = JOptionPane.showConfirmDialog(this, idCustomer + " không sở hữu voucher này. Bạn vẫn muốn áp dụng chứ?");
            if (option == 0) {
                billsRequest.setIdVoucher(voucher.getId());
                billsRequest.setStatus(false);
                billsRequest.setIdPayment(Long.valueOf(1));
                billsRequest.setIdVoucher(value);
                if (billsServiceImpl.updateBill(billsRequest)) {
                    JOptionPane.showMessageDialog(this, "Đã áp dụng vào " + billsRequest.getId());
                    caculatorBills(billsRequest.getId());
                    listBillsToday = billsServiceImpl.getAllBillsToday();
                    if (voucher != null) {
                        if (voucher.getType().equalsIgnoreCase("Tiền")) {
                            txtVoucher.setText("Giảm " + MoneyConverter.parse(voucher.getValue()) + " với hoá đơn đạt " + MoneyConverter.parse(voucher.getValueCondition()));
                        } else {
                            txtVoucher.setText("Giảm " + voucher.getValue().setScale(0) + "%" + " với hoá đơn đạt " + MoneyConverter.parse(voucher.getValueCondition()));
                        }
                    } else {
                        txtVoucher.setText("");
                    }
                }
            }
        } else {
            billsRequest.setIdVoucher(voucher.getId());
            billsRequest.setStatus(false);
            billsRequest.setIdPayment(Long.valueOf(1));
            billsRequest.setIdVoucher(value);
            if (billsServiceImpl.updateBill(billsRequest)) {
                JOptionPane.showMessageDialog(this, "Đã áp dụng vào " + billsRequest.getId());
                caculatorBills(billsRequest.getId());
                listBillsToday = billsServiceImpl.getAllBillsToday();
                if (voucher != null) {
                    if (voucher.getType().equalsIgnoreCase("Tiền")) {
                        txtVoucher.setText("Giảm " + MoneyConverter.parse(voucher.getValue()) + " với hoá đơn đạt " + MoneyConverter.parse(voucher.getValueCondition()));
                    } else {
                        txtVoucher.setText("Giảm " + voucher.getValue().setScale(0) + "%" + " với hoá đơn đạt " + MoneyConverter.parse(voucher.getValueCondition()));
                    }
                } else {
                    txtVoucher.setText("");
                }
            }
        }

    }
    //END: voucher

    //END: Bán hàng
    //START: View Khách Hàng
    private void showListCustomerByPage(int page, List<CustomerResponse> list) {
        int litmit = 6;
        int totalPage = (int) Math.ceil(((double) list.size() / litmit));
        tableModel = (DefaultTableModel) tblListCustomer.getModel();
        tableModel.setRowCount(0);
        pnaListCustomer.setPagegination(page, totalPage);
        listToTableCustomer.clear();
        for (int i = 0; i < list.size(); i++) {
            if (i >= (page - 1) * litmit && i <= (litmit * page) - 1) {
                CustomerResponse item = list.get(i);
                listToTableCustomer.add(item);
                tableModel.addRow(new Object[]{
                    i + 1,
                    item.getId(),
                    item.getFullname(),
                    item.getPhoneNumber(),
                    item.getEmail(),
                    item.getAddress(),
                    item.getGender() ? "Nam" : "Nữ"
                });
            }
        }
    }

    private CustomerUpdateRequest getDataCustomerKH() {
        String id = txtMaKH.getText();
        String fullname = txtHoTenKH.getText();
        String address = txtDiaChiKH.getText();
        String email = txtEmailKH.getText();
        String phoneNumber = txtSoDienThoaiKH.getText();
        Boolean gender = true;
        if (rdoNuKH.isSelected()) {
            gender = false;
        }
        Long idTypeCustomer = Long.valueOf(1);
        if (String.valueOf(cbbLoaiKH.getSelectedItem()).equalsIgnoreCase("Khách lẻ")) {
            idTypeCustomer = Long.valueOf(2);
        }
        return new CustomerUpdateRequest(id, fullname, phoneNumber, idTypeCustomer, email, gender, address);
    }

    private void updateCustomer() {
        if (customerServiceImpl.updateCustomer2(getDataCustomerKH())) {
            listCustomers = customerServiceImpl.getListCustomer();
            showListCustomerByPage(1, listCustomers);
            Notification notification = new Notification(View.getJframe(), Notification.Type.SUCCESS, Notification.Location.TOP_RIGHT, "Đã sửa thành công!");
            notification.showNotification();
        }
    }

    private void addCustomer() {
        if (txtMaKH.getText().trim().isEmpty()) {
            txtMaKH.setText(customerServiceImpl.getIdCustomer());
            Notification notification = new Notification(View.getJframe(), Notification.Type.WARNING, Notification.Location.TOP_RIGHT, "Mời nhập thông tin!");
            notification.showNotification();
            return;
        }
        if (customerServiceImpl.addCustomer2(getDataCustomerKH())) {
            listCustomers = customerServiceImpl.getListCustomer();
            showListCustomerByPage(1, listCustomers);
            Notification notification = new Notification(View.getJframe(), Notification.Type.SUCCESS, Notification.Location.TOP_RIGHT, "Đã thêm thành công!");
            notification.showNotification();
            clearFormKH();
        }
    }

    private void clearFormKH() {
        txtMaKH.setText("");
        txtHoTenKH.setText("");
        txtDiaChiKH.setText("");
        txtEmailKH.setText("");
        txtSoDienThoaiKH.setText("");
        buttonGroup1.clearSelection();
        cbbLoaiKH.setSelectedIndex(0);
    }

    private void showDetailCustomer(int index) {
        CustomerResponse customerResponse = listToTableCustomer.get(index);
        txtMaKH.setText(customerResponse.getId());
        txtEmailKH.setText(customerResponse.getEmail() == null ? "" : customerResponse.getEmail());
        txtDiaChiKH.setText(customerResponse.getAddress() == null ? "" : customerResponse.getAddress());
        txtHoTenKH.setText(customerResponse.getFullname());
        txtSoDienThoaiKH.setText(customerResponse.getPhoneNumber() == null ? "" : customerResponse.getPhoneNumber());
        cbbLoaiKH.setSelectedItem(customerResponse.getTypeCustomer());
        listCustomerBills = billsServiceImpl.getCustomerBill(customerResponse.getId());
        showListCustomerBillsByPage(1, listCustomerBills);
        tabListCustomer.setSelectedIndex(1);

    }

    private void showListCustomerBillsByPage(int page, List<BillsResponse> list) {
        int litmit = 6;
        BigDecimal totalMoney = BigDecimal.ZERO;
        BigDecimal totalReductMoney = BigDecimal.ZERO;
        int totalPage = (int) Math.ceil(((double) list.size() / litmit));
        tableModel = (DefaultTableModel) tblListCustomBills.getModel();
        tableModel.setRowCount(0);
        pnaListCustomerBills.setPagegination(page, totalPage);
        listToTableCustomerBills.clear();
        for (int i = 0; i < list.size(); i++) {
            BillsResponse item = list.get(i);
            if (item.getStatus()) {
                totalMoney = totalMoney.add(item.getTotalMoney());
                totalReductMoney = totalReductMoney.add(item.getReduceMoney());
            }
            if (i >= (page - 1) * litmit && i <= (litmit * page) - 1) {
                VoucherResponse voucher = voucherServiceImpl.getVoucherById(item.getIdVoucher());
                listToTableCustomerBills.add(item);
                tableModel.addRow(new Object[]{
                    i + 1,
                    item.getId(),
                    item.getCustomerName(),
                    item.getTimeCreate().toString().substring(0, 16),
                    MoneyConverter.parse(item.getTotalMoney()),
                    MoneyConverter.parse(item.getReduceMoney()),
                    voucher == null ? "Không có" : getValueVoucher(voucher),
                    item.getStatus() ? "Đã hoàn thành" : "Chưa hoàn thành"
                });
            }
        }
        lbMoneyPurchaseCustomer.setText(MoneyConverter.parse(totalMoney));
        lbMoneyReduceCustomer.setText(MoneyConverter.parse(totalReductMoney));
    }

    private String getValueVoucher(VoucherResponse voucherResponse) {
        if (voucherResponse.getType().equalsIgnoreCase("Tiền")) {
            return "Giảm " + MoneyConverter.parse(voucherResponse.getValue());
        } else {
            return "Giảm " + voucherResponse.getValue().setScale(0) + "%";
        }
    }

    //END: View Khách Hàng
    //START: View Hoá Đơn
    private void showListBillsByPage(int page, List<BillsResponse> list) {
        int litmit = 6;
        int totalPage = (int) Math.ceil(((double) list.size() / litmit));
        tableModel = (DefaultTableModel) tblListBillHD.getModel();
        tableModel.setRowCount(0);
        pnaListBillsHD.setPagegination(page, totalPage);
        listToTableBills.clear();
        for (int i = 0; i < list.size(); i++) {
            if (i >= (page - 1) * litmit && i <= (litmit * page) - 1) {
                BillsResponse item = list.get(i);
                VoucherResponse voucher = voucherServiceImpl.getVoucherById(item.getIdVoucher());
                listToTableBills.add(item);
                tableModel.addRow(new Object[]{
                    i + 1,
                    item.getId(),
                    item.getCustomerName(),
                    item.getTimeCreate().toString().substring(0, 16),
                    MoneyConverter.parse(item.getTotalMoney()),
                    MoneyConverter.parse(item.getReduceMoney()),
                    voucher == null ? "Không có" : getValueVoucher(voucher),
                    item.getStatus() ? "Đã hoàn thành" : "Chưa hoàn thành"
                });
            }
        }
    }

    private void getItemBillSelected(int index) {
        BillsResponse item = listToTableBills.get(index);
        listBillDetails = billDetailServiceImpl.getBillDetailByBills(item.getId());
        lbIdBills.setText(item.getId());
        showListBillDetailByPage(1, listBillDetails);
    }

    private void showListBillDetailByPage(int page, List<BillDetailResponse> list) {
        int litmit = 6;
        int totalPage = (int) Math.ceil(((double) list.size() / litmit));
        tableModel = (DefaultTableModel) tblListBillDetail.getModel();
        tableModel.setRowCount(0);
        pnaListBillDetail.setPagegination(page, totalPage);
        listToTableBillDetails.clear();
        for (int i = 0; i < list.size(); i++) {
            if (i >= (page - 1) * litmit && i <= (litmit * page) - 1) {
                BillDetailResponse item = list.get(i);
                listToTableBillDetails.add(item);
                tableModel.addRow(new Object[]{
                    i + 1,
                    item.getIdBill(),
                    item.getProductName(),
                    item.getQuantity(),
                    MoneyConverter.parse(item.getTotalMoney()),
                    item.getStatus() ? "Đã hoàn thành" : "Chưa hoàn thành"
                });
            }
        }
    }

    private BillsSearchRequest getDataSearchBills() {
        String valueSearch = txtValueSearchHD.getText();
        if (valueSearch.trim().isEmpty()) {
            valueSearch = null;
        }
        String valueStatus = String.valueOf(cbbTrangThaiSearch.getSelectedItem());
        Boolean statusSearch = null;
        if (valueStatus.equalsIgnoreCase("Hoàn thành")) {
            statusSearch = true;
        }
        if (valueStatus.equalsIgnoreCase("Chưa hoàn thành")) {
            statusSearch = false;
        }
        Integer paymentSearch = null;
        String paymentValue = String.valueOf(cbbHinhThucSearch.getSelectedItem());
        if (paymentValue.equalsIgnoreCase("Chuyển khoản")) {
            paymentSearch = 1;
        }
        if (paymentValue.equalsIgnoreCase("Tiền mặt")) {
            paymentSearch = 2;
        }
        String timeStartSearch = txtTimeStartHD.getText();
        if (timeStartSearch.trim().isEmpty()) {
            timeStartSearch = null;
        }
        String timeEndSearch = txtTimeEndHD.getText();
        if (timeEndSearch.trim().isEmpty()) {
            timeEndSearch = null;
        }
        return new BillsSearchRequest(valueSearch, statusSearch, paymentSearch, timeStartSearch, timeEndSearch);
    }

    //END: View Hoá Đơn
    //START: View Thống kê
    private void setDataChart() {
        chart.addLegend("", new Color(250, 250, 250));
        chart.addLegend("Tổng tiền", new Color(189, 135, 245));
    }

    private void getDataChart() {
        chart.clear();
        for (Map.Entry<Integer, Integer> en : GetListMonth.getListMonth().entrySet()) {
            int key = en.getKey();
            int value = en.getValue();
            chart.addData(new ModelChart("Tháng " + key + "-" + value,
                    new double[]{
                        0,
                        billsServiceImpl.getRevenueByMonth(key, value),
                        billsServiceImpl.getRevenueByMonth(key, value),
                        0
                    }));
        }
        chart.start();
    }

    //END: View Thống kê
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        dateTimeStartHD = new com.raven.datechooser.DateChooser();
        dateTimeEndHD = new com.raven.datechooser.DateChooser();
        dateTimeStartTK = new com.raven.datechooser.DateChooser();
        dateTimeEndTK = new com.raven.datechooser.DateChooser();
        jPanel1 = new javax.swing.JPanel();
        btnShowViewBanHang = new javax.swing.JLabel();
        btnShowViewSanPham = new javax.swing.JLabel();
        btnShowViewNhanVien = new javax.swing.JLabel();
        btnShowViewKhachHang = new javax.swing.JLabel();
        btnShowViewThongKe = new javax.swing.JLabel();
        btnShowViewDoiMatKhau = new javax.swing.JLabel();
        btnShowViewHoaDon = new javax.swing.JLabel();
        btnShowViewDoiMatKhau1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        ViewBanHang = new javax.swing.JPanel();
        tabbedPaneCustom1 = new components.TabbedPaneCustom();
        jPanel4 = new javax.swing.JPanel();
        buttonCustom1 = new components.ButtonCustom();
        buttonCustom3 = new components.ButtonCustom();
        buttonCustom4 = new components.ButtonCustom();
        buttonCustom5 = new components.ButtonCustom();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblListHoaDonHomNay = new components.Table();
        panelRound1 = new components.PanelRound();
        buttonCustom8 = new components.ButtonCustom();
        txtSDTKhachHang = new components.TextField();
        txtTenKhachHang = new components.TextField();
        cbbHinhThucThanhToan = new components.Combobox();
        buttonCustom9 = new components.ButtonCustom();
        jLabel18 = new javax.swing.JLabel();
        lbMaHoaDon = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lbMaKhachHang = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lbTamTinh = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lbTienDuocGiam = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lbTongTien = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtVoucher = new components.TextFieldSuggestion();
        tabbedPaneCustom2 = new components.TabbedPaneCustom();
        jPanel6 = new javax.swing.JPanel();
        buttonCustom6 = new components.ButtonCustom();
        buttonCustom7 = new components.ButtonCustom();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblListProductCard = new components.Table();
        buttonCustom10 = new components.ButtonCustom();
        tabbedPaneCustom3 = new components.TabbedPaneCustom();
        jPanel7 = new javax.swing.JPanel();
        pnaListProductShop = new components.Pagination();
        cbbTimMauSacShop = new components.Combobox();
        cbbTimSizeShop = new components.Combobox();
        cbbTimChatLieuShop = new components.Combobox();
        cbbTimHangShop = new components.Combobox();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblListProductShop = new components.Table();
        cbbTimDeGiayShop = new components.Combobox();
        lbWebcam = new javax.swing.JPanel();
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
        cbbTimMauSac = new components.Combobox();
        cbbTimSize = new components.Combobox();
        cbbTimDeGiay = new components.Combobox();
        cbbTimChatLieu = new components.Combobox();
        cbbTimHang2 = new components.Combobox();
        cbbTimTrangThai2 = new components.Combobox();
        buttonCustom17 = new components.ButtonCustom();
        rdoExportExcel = new components.JCheckBoxCustom();
        lbQRCode = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ViewNhanVien = new javax.swing.JPanel();
        ViewThongKe = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        chart = new com.raven.chart.Chart();
        panelRound2 = new components.PanelRound();
        jLabel41 = new javax.swing.JLabel();
        lbTongTienHomNayTK = new javax.swing.JLabel();
        panelRound4 = new components.PanelRound();
        jLabel42 = new javax.swing.JLabel();
        lbTongTien7DayTk = new javax.swing.JLabel();
        panelRound5 = new components.PanelRound();
        jLabel43 = new javax.swing.JLabel();
        lbTongTienMonthTK = new javax.swing.JLabel();
        panelRound6 = new components.PanelRound();
        jLabel44 = new javax.swing.JLabel();
        lbTongTienYearTK = new javax.swing.JLabel();
        panelRound7 = new components.PanelRound();
        jLabel45 = new javax.swing.JLabel();
        lbTongTienTuyChon = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        txtTimeStartTK = new components.TextFieldSuggestion();
        jLabel47 = new javax.swing.JLabel();
        txtTimeEndTK = new components.TextFieldSuggestion();
        buttonCustom19 = new components.ButtonCustom();
        ViewKhachHang = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtHoTenKH = new components.TextField();
        txtMaKH = new components.TextFieldSuggestion();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        rdoNamKH = new components.RadioButtonCustom();
        rdoNuKH = new components.RadioButtonCustom();
        jLabel28 = new javax.swing.JLabel();
        txtSoDienThoaiKH = new components.TextField();
        jLabel29 = new javax.swing.JLabel();
        txtDiaChiKH = new components.TextField();
        jLabel30 = new javax.swing.JLabel();
        txtEmailKH = new components.TextField();
        buttonCustom11 = new components.ButtonCustom();
        jLabel31 = new javax.swing.JLabel();
        btnSuaSanPham1 = new components.ButtonCustom();
        btnXoaSanPham1 = new components.ButtonCustom();
        buttonCustom12 = new components.ButtonCustom();
        cbbLoaiKH = new components.Combobox();
        tabListCustomer = new components.MaterialTabbed();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblListCustomer = new components.Table();
        pnaListCustomer = new components.Pagination();
        textField5 = new components.TextField();
        combobox1 = new components.Combobox();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblListCustomBills = new components.Table();
        pnaListCustomerBills = new components.Pagination();
        jLabel32 = new javax.swing.JLabel();
        lbMoneyPurchaseCustomer = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        lbMoneyReduceCustomer = new javax.swing.JLabel();
        ViewDoiMatKhau = new javax.swing.JPanel();
        ViewVoucher = new javax.swing.JPanel();
        ViewHoaDon = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        txtValueSearchHD = new components.TextField();
        jLabel36 = new javax.swing.JLabel();
        cbbTrangThaiSearch = new components.Combobox();
        jLabel37 = new javax.swing.JLabel();
        cbbHinhThucSearch = new components.Combobox();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblListBillHD = new components.Table();
        jLabel38 = new javax.swing.JLabel();
        txtTimeStartHD = new components.TextFieldSuggestion();
        jLabel39 = new javax.swing.JLabel();
        txtTimeEndHD = new components.TextFieldSuggestion();
        pnaListBillsHD = new components.Pagination();
        buttonCustom2 = new components.ButtonCustom();
        buttonCustom13 = new components.ButtonCustom();
        buttonCustom18 = new components.ButtonCustom();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblListBillDetail = new components.Table();
        pnaListBillDetail = new components.Pagination();
        lbIdBills = new javax.swing.JLabel();

        dateTimeStartHD.setForeground(new java.awt.Color(51, 102, 255));
        dateTimeStartHD.setDateFormat("yyyy-MM-dd");
        dateTimeStartHD.setTextRefernce(txtTimeStartHD);

        dateTimeEndHD.setForeground(new java.awt.Color(0, 102, 255));
        dateTimeEndHD.setDateFormat("yyyy-MM-dd");
        dateTimeEndHD.setTextRefernce(txtTimeEndHD);

        dateTimeStartTK.setDateFormat("yyyy-MM-dd");
        dateTimeStartTK.setTextRefernce(txtTimeStartTK);

        dateTimeEndTK.setDateFormat("yyyy-MM-dd");
        dateTimeEndTK.setTextRefernce(txtTimeEndTK);

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
        btnShowViewDoiMatKhau.setText("Voucher");
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

        btnShowViewDoiMatKhau1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShowViewDoiMatKhau1.setForeground(new java.awt.Color(255, 255, 255));
        btnShowViewDoiMatKhau1.setText("Đăng xuất");
        btnShowViewDoiMatKhau1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowViewDoiMatKhau1MouseClicked(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnShowViewDoiMatKhau1)
                    .addComponent(btnShowViewDoiMatKhau)
                    .addComponent(btnShowViewHoaDon)
                    .addComponent(btnShowViewThongKe)
                    .addComponent(btnShowViewNhanVien)
                    .addComponent(btnShowViewKhachHang)
                    .addComponent(btnShowViewSanPham)
                    .addComponent(btnShowViewBanHang))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnShowViewBanHang)
                .addGap(39, 39, 39)
                .addComponent(btnShowViewSanPham)
                .addGap(42, 42, 42)
                .addComponent(btnShowViewKhachHang)
                .addGap(47, 47, 47)
                .addComponent(btnShowViewNhanVien)
                .addGap(50, 50, 50)
                .addComponent(btnShowViewThongKe)
                .addGap(52, 52, 52)
                .addComponent(btnShowViewHoaDon)
                .addGap(46, 46, 46)
                .addComponent(btnShowViewDoiMatKhau)
                .addGap(52, 52, 52)
                .addComponent(btnShowViewDoiMatKhau1)
                .addContainerGap(91, Short.MAX_VALUE))
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
        buttonCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom1ActionPerformed(evt);
            }
        });

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

        buttonCustom5.setText("Áp dụng voucher");
        buttonCustom5.setColor1(new java.awt.Color(0, 153, 255));
        buttonCustom5.setColor2(new java.awt.Color(0, 102, 255));
        buttonCustom5.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        buttonCustom5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom5ActionPerformed(evt);
            }
        });

        tblListHoaDonHomNay.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã hoá đơn", "Mã NV", "Thời gian", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListHoaDonHomNay.setRowHeight(30);
        tblListHoaDonHomNay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListHoaDonHomNayMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblListHoaDonHomNay);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonCustom4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, Short.MAX_VALUE)
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
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonCustom3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonCustom4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonCustom5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(140, 140, 140))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addContainerGap(62, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(1, 1, 1)))
        );

        tabbedPaneCustom1.addTab("Hoá đơn đang chờ", jPanel4);

        panelRound1.setBackground(new java.awt.Color(255, 255, 255));
        panelRound1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đơn hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP));

        buttonCustom8.setText("Chọn");
        buttonCustom8.setColor1(new java.awt.Color(255, 51, 102));
        buttonCustom8.setColor2(new java.awt.Color(255, 0, 51));
        buttonCustom8.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        buttonCustom8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom8ActionPerformed(evt);
            }
        });

        txtSDTKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSDTKhachHang.setLabelText("Số điện thoại:");

        txtTenKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTenKhachHang.setLabelText("Họ và tên:");

        cbbHinhThucThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cbbHinhThucThanhToan.setLabeText("Hình thức thanh toán");
        cbbHinhThucThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbHinhThucThanhToanActionPerformed(evt);
            }
        });

        buttonCustom9.setText("Thanh toán");
        buttonCustom9.setColor1(new java.awt.Color(0, 255, 51));
        buttonCustom9.setColor2(new java.awt.Color(51, 255, 51));
        buttonCustom9.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        buttonCustom9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom9ActionPerformed(evt);
            }
        });

        jLabel18.setText("Mã hoá đơn:");

        lbMaHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbMaHoaDon.setText("###");

        jLabel20.setText("Mã KH:");

        lbMaKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbMaKhachHang.setText("###");

        jLabel22.setText("Tạm tính:");

        lbTamTinh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTamTinh.setText("###");

        jLabel24.setText("Số tiền được giảm:");

        lbTienDuocGiam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTienDuocGiam.setText("###");

        jLabel26.setText("Tổng tiền:");

        lbTongTien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTongTien.setText("###");

        jLabel23.setText("Voucher:");

        txtVoucher.setEditable(false);

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonCustom9, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(30, 30, 30)
                        .addComponent(lbTienDuocGiam)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelRound1Layout.createSequentialGroup()
                                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtSDTKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                                            .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel18)
                                                .addComponent(jLabel20))
                                            .addGap(34, 34, 34)
                                            .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(panelRound1Layout.createSequentialGroup()
                                                    .addComponent(lbMaHoaDon)
                                                    .addGap(101, 101, 101))
                                                .addGroup(panelRound1Layout.createSequentialGroup()
                                                    .addComponent(lbMaKhachHang)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(buttonCustom8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(12, 12, 12)))))
                                    .addGroup(panelRound1Layout.createSequentialGroup()
                                        .addComponent(jLabel26)
                                        .addGap(63, 63, 63)
                                        .addComponent(lbTongTien))
                                    .addGroup(panelRound1Layout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addGap(55, 55, 55)
                                        .addComponent(lbTamTinh))
                                    .addComponent(cbbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel23))
                                .addGap(0, 18, Short.MAX_VALUE))
                            .addComponent(txtVoucher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lbMaHoaDon))
                .addGap(18, 18, 18)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(lbMaKhachHang)
                    .addComponent(buttonCustom8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(txtSDTKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(lbTamTinh))
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(lbTongTien))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(buttonCustom9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbTienDuocGiam)
                            .addComponent(jLabel24))
                        .addGap(357, 357, 357))))
        );

        tabbedPaneCustom2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tabbedPaneCustom2.setForeground(new java.awt.Color(255, 255, 255));
        tabbedPaneCustom2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        buttonCustom6.setText("Xoá");
        buttonCustom6.setColor1(new java.awt.Color(255, 51, 102));
        buttonCustom6.setColor2(new java.awt.Color(255, 0, 51));
        buttonCustom6.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        buttonCustom6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom6ActionPerformed(evt);
            }
        });

        buttonCustom7.setText("Sửa số lượng");
        buttonCustom7.setColor1(new java.awt.Color(0, 153, 255));
        buttonCustom7.setColor2(new java.awt.Color(0, 102, 255));
        buttonCustom7.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        buttonCustom7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom7ActionPerformed(evt);
            }
        });

        tblListProductCard.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Tên SP", "Màu", "Kích cỡ", "Chất liệu", "Đế giày", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListProductCard.setRowHeight(30);
        tblListProductCard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListProductCardMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblListProductCard);
        if (tblListProductCard.getColumnModel().getColumnCount() > 0) {
            tblListProductCard.getColumnModel().getColumn(0).setResizable(false);
        }

        buttonCustom10.setText("Quét Barcode");
        buttonCustom10.setColor1(new java.awt.Color(0, 153, 255));
        buttonCustom10.setColor2(new java.awt.Color(0, 102, 255));
        buttonCustom10.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        buttonCustom10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonCustom10, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonCustom7, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonCustom6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCustom6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCustom7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCustom10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        tabbedPaneCustom2.addTab("Giỏ hàng", jPanel6);

        tabbedPaneCustom3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tabbedPaneCustom3.setForeground(new java.awt.Color(255, 255, 255));
        tabbedPaneCustom3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        pnaListProductShop.setBackground(new java.awt.Color(204, 204, 204));
        pnaListProductShop.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        cbbTimMauSacShop.setLabeText("Màu sắc");
        cbbTimMauSacShop.setLineColor(new java.awt.Color(255, 0, 0));

        cbbTimSizeShop.setLabeText("Kích cỡ");
        cbbTimSizeShop.setLineColor(new java.awt.Color(255, 0, 0));

        cbbTimChatLieuShop.setLabeText("Chất liệu");
        cbbTimChatLieuShop.setLineColor(new java.awt.Color(255, 0, 0));
        cbbTimChatLieuShop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTimChatLieuShopActionPerformed(evt);
            }
        });

        cbbTimHangShop.setLabeText("Hãng");
        cbbTimHangShop.setLineColor(new java.awt.Color(255, 0, 0));

        tblListProductShop.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Tên SP", "Hãng", "Màu", "Kích cỡ", "Chất liệu", "Đế giày", "Giá", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListProductShop.setRowHeight(35);
        tblListProductShop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListProductShopMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblListProductShop);

        cbbTimDeGiayShop.setLabeText("Đế giày");
        cbbTimDeGiayShop.setLineColor(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(cbbTimHangShop, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbTimSizeShop, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbTimChatLieuShop, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbTimMauSacShop, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbTimDeGiayShop, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnaListProductShop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane6)
                    .addContainerGap()))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbTimMauSacShop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbTimSizeShop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbTimHangShop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbTimChatLieuShop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbTimDeGiayShop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(173, 173, 173)
                .addComponent(pnaListProductShop, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                    .addContainerGap(72, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(56, 56, 56)))
        );

        tabbedPaneCustom3.addTab("Sản phẩm", jPanel7);

        lbWebcam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout ViewBanHangLayout = new javax.swing.GroupLayout(ViewBanHang);
        ViewBanHang.setLayout(ViewBanHangLayout);
        ViewBanHangLayout.setHorizontalGroup(
            ViewBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewBanHangLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(ViewBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tabbedPaneCustom2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabbedPaneCustom3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ViewBanHangLayout.createSequentialGroup()
                        .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbWebcam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(41, 41, 41)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        ViewBanHangLayout.setVerticalGroup(
            ViewBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewBanHangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ViewBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(ViewBanHangLayout.createSequentialGroup()
                        .addGroup(ViewBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbWebcam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(18, 18, 18)
                            .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6))
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addGap(18, 18, 18)
                            .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbChatLieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(btnShowViewMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnShowViewSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnShowViewColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnShowViewSole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShowViewColor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(6, 6, 6)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnShowViewSize, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnShowViewSole, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnShowViewMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                false, false, false, false, false, false, false, false, false, false, false
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

        cbbTimMauSac.setLabeText("Màu sắc");

        cbbTimSize.setLabeText("Kích cỡ");

        cbbTimDeGiay.setLabeText("Đế giày");

        cbbTimChatLieu.setLabeText("Chất liệu");

        cbbTimHang2.setLabeText("Hãng");

        cbbTimTrangThai2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Đang bán", "Ngừng bán" }));
        cbbTimTrangThai2.setLabeText("Trạng thái");

        buttonCustom17.setText("Export Excel");
        buttonCustom17.setColor1(new java.awt.Color(0, 153, 255));
        buttonCustom17.setColor2(new java.awt.Color(0, 102, 255));
        buttonCustom17.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        buttonCustom17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom17ActionPerformed(evt);
            }
        });

        rdoExportExcel.setBackground(new java.awt.Color(153, 255, 255));
        rdoExportExcel.setText("Tất cả");

        jLabel7.setText("QR code:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(buttonCustom14, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(buttonCustom16, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6)
                            .addComponent(jLabel13)
                            .addComponent(jLabel16)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(29, 29, 29)
                                .addComponent(lbTenSanPham))
                            .addComponent(txtGiaBanSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbMaSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(56, 56, 56)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbQRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))))
                .addContainerGap(97, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 28, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnaListProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 1151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(233, 233, 233)
                                .addComponent(buttonCustom15, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(166, 166, 166))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(cbbTimHang2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbTimMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbbTimSize, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addComponent(cbbTimDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbTimChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbTimTrangThai2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(buttonCustom17, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdoExportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(lbQRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCustom14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCustom16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCustom15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCustom17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoExportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel15))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbTimHang2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbbTimMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbbTimSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbbTimDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbbTimChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbbTimTrangThai2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnaListProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(306, 306, 306))
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

        ViewThongKe.setBackground(new java.awt.Color(255, 255, 255));

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel40.setText("#Thống kê");

        panelRound2.setBackground(new java.awt.Color(204, 204, 255));
        panelRound2.setRoundBottomLeft(20);
        panelRound2.setRoundBottomRight(20);
        panelRound2.setRoundTopLeft(20);
        panelRound2.setRoundTopRight(20);

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Hôm nay");

        lbTongTienHomNayTK.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbTongTienHomNayTK.setText("0 đ");

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTongTienHomNayTK)
                    .addComponent(jLabel41))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41)
                .addGap(37, 37, 37)
                .addComponent(lbTongTienHomNayTK)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelRound4.setBackground(new java.awt.Color(204, 204, 255));
        panelRound4.setRoundBottomLeft(20);
        panelRound4.setRoundBottomRight(20);
        panelRound4.setRoundTopLeft(20);
        panelRound4.setRoundTopRight(20);

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("7 ngày gần đây");

        lbTongTien7DayTk.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbTongTien7DayTk.setText("0 đ");

        javax.swing.GroupLayout panelRound4Layout = new javax.swing.GroupLayout(panelRound4);
        panelRound4.setLayout(panelRound4Layout);
        panelRound4Layout.setHorizontalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound4Layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42)
                    .addGroup(panelRound4Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lbTongTien7DayTk)))
                .addGap(68, 68, 68))
        );
        panelRound4Layout.setVerticalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addGap(37, 37, 37)
                .addComponent(lbTongTien7DayTk)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelRound5.setBackground(new java.awt.Color(204, 204, 255));
        panelRound5.setRoundBottomLeft(20);
        panelRound5.setRoundBottomRight(20);
        panelRound5.setRoundTopLeft(20);
        panelRound5.setRoundTopRight(20);

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Tháng");

        lbTongTienMonthTK.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbTongTienMonthTK.setText("0 đ");

        javax.swing.GroupLayout panelRound5Layout = new javax.swing.GroupLayout(panelRound5);
        panelRound5.setLayout(panelRound5Layout);
        panelRound5Layout.setHorizontalGroup(
            panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound5Layout.createSequentialGroup()
                .addGroup(panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound5Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jLabel43))
                    .addGroup(panelRound5Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(lbTongTienMonthTK)))
                .addContainerGap(115, Short.MAX_VALUE))
        );
        panelRound5Layout.setVerticalGroup(
            panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(lbTongTienMonthTK)
                .addGap(46, 46, 46))
        );

        panelRound6.setBackground(new java.awt.Color(204, 204, 255));
        panelRound6.setRoundBottomLeft(20);
        panelRound6.setRoundBottomRight(20);
        panelRound6.setRoundTopLeft(20);
        panelRound6.setRoundTopRight(20);

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Năm");

        lbTongTienYearTK.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbTongTienYearTK.setText("0 đ");

        javax.swing.GroupLayout panelRound6Layout = new javax.swing.GroupLayout(panelRound6);
        panelRound6.setLayout(panelRound6Layout);
        panelRound6Layout.setHorizontalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound6Layout.createSequentialGroup()
                .addContainerGap(114, Short.MAX_VALUE)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
            .addGroup(panelRound6Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(lbTongTienYearTK)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRound6Layout.setVerticalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(lbTongTienYearTK)
                .addGap(46, 46, 46))
        );

        panelRound7.setBackground(new java.awt.Color(204, 204, 255));
        panelRound7.setRoundBottomLeft(20);
        panelRound7.setRoundBottomRight(20);
        panelRound7.setRoundTopLeft(20);
        panelRound7.setRoundTopRight(20);

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Tuỳ chọn");

        lbTongTienTuyChon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbTongTienTuyChon.setText("0 đ");

        javax.swing.GroupLayout panelRound7Layout = new javax.swing.GroupLayout(panelRound7);
        panelRound7.setLayout(panelRound7Layout);
        panelRound7Layout.setHorizontalGroup(
            panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound7Layout.createSequentialGroup()
                .addContainerGap(129, Short.MAX_VALUE)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112))
            .addGroup(panelRound7Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(lbTongTienTuyChon)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRound7Layout.setVerticalGroup(
            panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel45)
                .addGap(29, 29, 29)
                .addComponent(lbTongTienTuyChon)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jLabel46.setText("Từ:");

        jLabel47.setText("Đến");

        buttonCustom19.setText("Lọc");
        buttonCustom19.setColor2(new java.awt.Color(51, 51, 255));
        buttonCustom19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom19ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ViewThongKeLayout = new javax.swing.GroupLayout(ViewThongKe);
        ViewThongKe.setLayout(ViewThongKeLayout);
        ViewThongKeLayout.setHorizontalGroup(
            ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewThongKeLayout.createSequentialGroup()
                .addGroup(ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewThongKeLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel40))
                    .addGroup(ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ViewThongKeLayout.createSequentialGroup()
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(panelRound2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelRound5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(86, 86, 86)
                            .addGroup(ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(panelRound4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelRound6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(74, 74, 74)
                            .addGroup(ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewThongKeLayout.createSequentialGroup()
                                    .addComponent(jLabel47)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtTimeEndTK, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewThongKeLayout.createSequentialGroup()
                                    .addComponent(buttonCustom19, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(114, 114, 114))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewThongKeLayout.createSequentialGroup()
                                    .addComponent(jLabel46)
                                    .addGap(18, 18, 18)
                                    .addGroup(ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(panelRound7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtTimeStartTK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ViewThongKeLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 1158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(66, 66, 66))
        );
        ViewThongKeLayout.setVerticalGroup(
            ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewThongKeLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel40)
                .addGap(18, 18, 18)
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRound2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewThongKeLayout.createSequentialGroup()
                        .addGroup(ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimeStartTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46))
                        .addGap(18, 18, 18)
                        .addGroup(ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimeEndTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47))
                        .addGap(18, 18, 18)
                        .addComponent(buttonCustom19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ViewThongKeLayout.createSequentialGroup()
                        .addGroup(ViewThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelRound5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelRound6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19))))
        );

        jPanel2.add(ViewThongKe);

        ViewKhachHang.setBackground(new java.awt.Color(255, 255, 255));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setText("#Khách hàng");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin"));

        jLabel21.setText("Mã KH: ");

        txtHoTenKH.setLabelText("");

        txtMaKH.setEditable(false);

        jLabel25.setText("Họ và tên:");

        jLabel27.setText("Giới tính:");

        rdoNamKH.setBackground(new java.awt.Color(51, 51, 255));
        buttonGroup1.add(rdoNamKH);
        rdoNamKH.setText("Nam");

        rdoNuKH.setBackground(new java.awt.Color(0, 0, 255));
        buttonGroup1.add(rdoNuKH);
        rdoNuKH.setText("Nữ");

        jLabel28.setText("Số điện thoại:");

        txtSoDienThoaiKH.setLabelText("");

        jLabel29.setText("Địa chỉ:");

        txtDiaChiKH.setLabelText("");

        jLabel30.setText("Email");

        txtEmailKH.setLabelText("");

        buttonCustom11.setText("Thêm");
        buttonCustom11.setColor1(new java.awt.Color(0, 255, 51));
        buttonCustom11.setColor2(new java.awt.Color(51, 255, 51));
        buttonCustom11.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        buttonCustom11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom11ActionPerformed(evt);
            }
        });

        jLabel31.setText("Loại khách:");

        btnSuaSanPham1.setText("Sửa");
        btnSuaSanPham1.setColor1(new java.awt.Color(255, 204, 0));
        btnSuaSanPham1.setColor2(new java.awt.Color(255, 255, 0));
        btnSuaSanPham1.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnSuaSanPham1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSanPham1ActionPerformed(evt);
            }
        });

        btnXoaSanPham1.setText("Xoá");
        btnXoaSanPham1.setColor1(new java.awt.Color(255, 51, 102));
        btnXoaSanPham1.setColor2(new java.awt.Color(255, 0, 51));
        btnXoaSanPham1.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        btnXoaSanPham1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPham1ActionPerformed(evt);
            }
        });

        buttonCustom12.setText("Tặng voucher");
        buttonCustom12.setColor1(new java.awt.Color(0, 153, 255));
        buttonCustom12.setColor2(new java.awt.Color(0, 102, 255));
        buttonCustom12.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 14)); // NOI18N
        buttonCustom12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom12ActionPerformed(evt);
            }
        });

        cbbLoaiKH.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Khách quen", "Khách lẻ" }));
        cbbLoaiKH.setLabeText("");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(181, 181, 181)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel30)
                                        .addComponent(jLabel29))))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(rdoNamKH, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(rdoNuKH, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSoDienThoaiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiaChiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmailKH, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txtHoTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(513, 513, 513))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addComponent(buttonCustom11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(btnSuaSanPham1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(btnXoaSanPham1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addComponent(buttonCustom12, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(98, 98, 98)
                    .addComponent(cbbLoaiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(666, Short.MAX_VALUE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHoTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(txtSoDienThoaiKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDiaChiKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))))
                .addGap(25, 25, 25)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmailKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel27)
                    .addComponent(rdoNamKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoNuKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel31)
                        .addGap(48, 48, 48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonCustom11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaSanPham1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoaSanPham1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonCustom12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24))))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                    .addContainerGap(225, Short.MAX_VALUE)
                    .addComponent(cbbLoaiKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(26, 26, 26)))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblListCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã KH", "Họ tên", "Số điện thại", "Email", "Địa chỉ", "Giới tính", "Số tiền đã chi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListCustomer.setRowHeight(30);
        tblListCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListCustomerMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tblListCustomer);
        if (tblListCustomer.getColumnModel().getColumnCount() > 0) {
            tblListCustomer.getColumnModel().getColumn(1).setResizable(false);
        }

        textField5.setLabelText("");

        combobox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Khách quen", "Khách lẻ" }));
        combobox1.setLabeText("");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(textField5, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(combobox1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnaListCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 1059, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combobox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnaListCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );

        tabListCustomer.addTab("Thông tin", jPanel3);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        tblListCustomBills.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HĐ", "Tên KH", "Thời gian", "Tổng tiền", "Được giảm", "Voucher", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListCustomBills.setRowHeight(30);
        tblListCustomBills.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListCustomBillsMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tblListCustomBills);
        if (tblListCustomBills.getColumnModel().getColumnCount() > 0) {
            tblListCustomBills.getColumnModel().getColumn(1).setResizable(false);
            tblListCustomBills.getColumnModel().getColumn(3).setHeaderValue("Thời gian");
            tblListCustomBills.getColumnModel().getColumn(5).setHeaderValue("Được giảm");
            tblListCustomBills.getColumnModel().getColumn(6).setHeaderValue("Voucher");
        }

        jLabel32.setText("Tiền đã chi:");

        lbMoneyPurchaseCustomer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbMoneyPurchaseCustomer.setText("###");

        jLabel33.setText("Tiền được giảm:");

        lbMoneyReduceCustomer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbMoneyReduceCustomer.setText("###");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 1108, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbMoneyPurchaseCustomer)
                                .addGap(66, 66, 66)
                                .addComponent(jLabel33)
                                .addGap(18, 18, 18)
                                .addComponent(lbMoneyReduceCustomer))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnaListCustomerBills, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(lbMoneyPurchaseCustomer)
                    .addComponent(jLabel33)
                    .addComponent(lbMoneyReduceCustomer))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnaListCustomerBills, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        tabListCustomer.addTab("Lịch sử mua", jPanel11);

        javax.swing.GroupLayout ViewKhachHangLayout = new javax.swing.GroupLayout(ViewKhachHang);
        ViewKhachHang.setLayout(ViewKhachHangLayout);
        ViewKhachHangLayout.setHorizontalGroup(
            ViewKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewKhachHangLayout.createSequentialGroup()
                .addGroup(ViewKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ViewKhachHangLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel19))
                    .addGroup(ViewKhachHangLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(ViewKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tabListCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 1125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        ViewKhachHangLayout.setVerticalGroup(
            ViewKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewKhachHangLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabListCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(35, 35, 35))
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

        ViewVoucher.setBackground(new java.awt.Color(255, 102, 255));

        javax.swing.GroupLayout ViewVoucherLayout = new javax.swing.GroupLayout(ViewVoucher);
        ViewVoucher.setLayout(ViewVoucherLayout);
        ViewVoucherLayout.setHorizontalGroup(
            ViewVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1230, Short.MAX_VALUE)
        );
        ViewVoucherLayout.setVerticalGroup(
            ViewVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1059, Short.MAX_VALUE)
        );

        jPanel2.add(ViewVoucher);

        ViewHoaDon.setBackground(new java.awt.Color(255, 255, 255));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel34.setText("#Hoá đơn");

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Hoá đơn"));

        jLabel35.setText("Tìm kiếm:");

        txtValueSearchHD.setLabelText("Nhập mã HD, tên NV hoặc tên KH");

        jLabel36.setText("Trạng thái:");

        cbbTrangThaiSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Hoàn thành", "Chưa hoàn thành" }));
        cbbTrangThaiSearch.setLabeText("");

        jLabel37.setText("Hình thức:");

        cbbHinhThucSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Tiền mặt", "Chuyển khoản" }));
        cbbHinhThucSearch.setLabeText("");

        tblListBillHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HĐ", "Tên KH", "Thời gian", "Tổng tiền", "Được giảm", "Voucher", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListBillHD.setRowHeight(30);
        tblListBillHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListBillHDMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tblListBillHD);
        if (tblListBillHD.getColumnModel().getColumnCount() > 0) {
            tblListBillHD.getColumnModel().getColumn(1).setResizable(false);
            tblListBillHD.getColumnModel().getColumn(3).setHeaderValue("Thời gian");
            tblListBillHD.getColumnModel().getColumn(5).setHeaderValue("Được giảm");
            tblListBillHD.getColumnModel().getColumn(6).setHeaderValue("Voucher");
        }

        jLabel38.setText("Từ:");

        jLabel39.setText("Đến:");

        buttonCustom2.setText("Xuất hoá đơn");

        buttonCustom13.setText("Lọc");
        buttonCustom13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom13ActionPerformed(evt);
            }
        });

        buttonCustom18.setText("In hoá đơn");
        buttonCustom18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(pnaListBillsHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonCustom18, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(buttonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtValueSearchHD, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonCustom13, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbHinhThucSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbTrangThaiSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(jLabel38)
                                .addGap(18, 18, 18)
                                .addComponent(txtTimeStartHD, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel39)
                                .addGap(18, 18, 18)
                                .addComponent(txtTimeEndHD, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(20, Short.MAX_VALUE))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtValueSearchHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel35)
                        .addComponent(buttonCustom13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbTrangThaiSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38)
                            .addComponent(txtTimeStartHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39)
                            .addComponent(txtTimeEndHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbHinhThucSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel37)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnaListBillsHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonCustom18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Hoá đơn chi tiết"));

        tblListBillDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HD", "Tên sản phẩm", "Số lượng", "Tổng tiền", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListBillDetail.setRowHeight(30);
        tblListBillDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListBillDetailMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tblListBillDetail);
        if (tblListBillDetail.getColumnModel().getColumnCount() > 0) {
            tblListBillDetail.getColumnModel().getColumn(2).setResizable(false);
        }

        lbIdBills.setText("###");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 1158, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(pnaListBillDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(lbIdBills)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(lbIdBills)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnaListBillDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ViewHoaDonLayout = new javax.swing.GroupLayout(ViewHoaDon);
        ViewHoaDon.setLayout(ViewHoaDonLayout);
        ViewHoaDonLayout.setHorizontalGroup(
            ViewHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewHoaDonLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(ViewHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        ViewHoaDonLayout.setVerticalGroup(
            ViewHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewHoaDonLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel34)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        jPanel2.add(ViewHoaDon);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 1230, 800));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnShowViewBanHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewBanHangMouseClicked
        // TODO add your handling code here:
        setVisibleAllView();
        ViewBanHang.setVisible(true);
        showListProductDetailByPageShop(1, productDetailServiceImpl.getAllProductDetail(null));
        showAllBillsToday(billsServiceImpl.getAllBillsToday());
    }//GEN-LAST:event_btnShowViewBanHangMouseClicked

    private void btnShowViewSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewSanPhamMouseClicked
        // TODO add your handling code here:
        setVisibleAllView();
        ViewSanPham.setVisible(true);
        showListProductsByPage(1, productsServiceImpl.getAllProducts());
        showListProductDetailByPage(1, productDetailServiceImpl.getAllProductDetail(null));
    }//GEN-LAST:event_btnShowViewSanPhamMouseClicked

    private void btnShowViewKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewKhachHangMouseClicked
        // TODO add your handling code here:
        setVisibleAllView();
        ViewKhachHang.setVisible(true);
        showListCustomerByPage(1, customerServiceImpl.getListCustomer());
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
        getDataChart();
        lbTongTienHomNayTK.setText(MoneyConverter.parse(billsServiceImpl.getTotalMoneyByToday()));
        lbTongTien7DayTk.setText(MoneyConverter.parse(billsServiceImpl.getTotalMoneyBy7Days()));
        lbTongTienMonthTK.setText(MoneyConverter.parse(billsServiceImpl.getTotalMoneyByMonth()));
        lbTongTienYearTK.setText(MoneyConverter.parse(billsServiceImpl.getTotalMoneyByYear()));
    }//GEN-LAST:event_btnShowViewThongKeMouseClicked

    private void btnShowViewHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewHoaDonMouseClicked
        // TODO add your handling code here:
        setVisibleAllView();
        ViewHoaDon.setVisible(true);
        listBills = billsServiceImpl.getAllBills();
        showListBillsByPage(1, listBills);
    }//GEN-LAST:event_btnShowViewHoaDonMouseClicked

    private void btnShowViewDoiMatKhauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewDoiMatKhauMouseClicked
        // TODO add your handling code here:
        setVisibleAllView();
        ViewDoiMatKhau.setVisible(true);
    }//GEN-LAST:event_btnShowViewDoiMatKhauMouseClicked

    private void btnShowViewDoiMatKhau1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowViewDoiMatKhau1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnShowViewDoiMatKhau1MouseClicked

    private void cbbTimChatLieuShopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTimChatLieuShopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbTimChatLieuShopActionPerformed

    private void cbbHinhThucThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbHinhThucThanhToanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbHinhThucThanhToanActionPerformed

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
        indexProductDetail = tblListSanPhamChiTiet.getSelectedRow();
        try {
            showDetailProductDetail(indexProductDetail);
        } catch (WriterException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private void buttonCustom17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom17ActionPerformed
        // TODO add your handling code here:
        exportProductDetail();
    }//GEN-LAST:event_buttonCustom17ActionPerformed

    private void tblListProductShopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListProductShopMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblListProductShopMouseClicked

    private void buttonCustom10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom10ActionPerformed
        // TODO add your handling code here:
        if (webcam.close()) {
            initWebcamCapture();
        }
    }//GEN-LAST:event_buttonCustom10ActionPerformed

    private void buttonCustom7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom7ActionPerformed
        // TODO add your handling code here:
        if (indexCard == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trong giỏ hàng cần sửa!");
        } else {
            String value = JOptionPane.showInputDialog("Nhập số lượng:");
            if (value.matches("^\\d+$")) {
                int quantity = Integer.parseInt(value);
                changeQuantityProductCard(indexCard, quantity);
            } else {
                JOptionPane.showMessageDialog(this, "Số lượng sai dữ liệu");
            }
        }
    }//GEN-LAST:event_buttonCustom7ActionPerformed

    private void buttonCustom6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom6ActionPerformed
        // TODO add your handling code here:
        if (lbMaHoaDon.getText().equalsIgnoreCase("###")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoá đơn!");
            return;
        }
        if (indexCard == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trong giỏ hàng cần xoá!");
        } else {
            deleteProductCard(indexCard);
        }
    }//GEN-LAST:event_buttonCustom6ActionPerformed

    private void tblListProductCardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListProductCardMouseClicked
        // TODO add your handling code here:
        indexCard = tblListProductCard.getSelectedRow();
    }//GEN-LAST:event_tblListProductCardMouseClicked

    private void buttonCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom1ActionPerformed
        // TODO add your handling code here:
        addBills();
    }//GEN-LAST:event_buttonCustom1ActionPerformed

    private void tblListHoaDonHomNayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListHoaDonHomNayMouseClicked
        // TODO add your handling code here:
        int index = tblListHoaDonHomNay.getSelectedRow();
        showDetailBills(index);
    }//GEN-LAST:event_tblListHoaDonHomNayMouseClicked

    private void buttonCustom8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom8ActionPerformed
        // TODO add your handling code here:
        if (lbMaHoaDon.getText().equalsIgnoreCase("###")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoá đơn");
            return;
        }
        new ViewDataCustomer(this, true).setVisible(true);
    }//GEN-LAST:event_buttonCustom8ActionPerformed

    private void buttonCustom9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom9ActionPerformed
        // TODO add your handling code here:
        if (String.valueOf(cbbHinhThucThanhToan.getSelectedItem()).trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hình thức thanh toán!");
            return;
        }
        completeBills();
    }//GEN-LAST:event_buttonCustom9ActionPerformed

    private void buttonCustom5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom5ActionPerformed
        // TODO add your handling code here:
        getVoucher();
    }//GEN-LAST:event_buttonCustom5ActionPerformed

    private void buttonCustom11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom11ActionPerformed
        // TODO add your handling code here:
        addCustomer();
    }//GEN-LAST:event_buttonCustom11ActionPerformed

    private void btnSuaSanPham1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSanPham1ActionPerformed
        // TODO add your handling code here:
        updateCustomer();
    }//GEN-LAST:event_btnSuaSanPham1ActionPerformed

    private void btnXoaSanPham1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPham1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaSanPham1ActionPerformed

    private void buttonCustom12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom12ActionPerformed
        // TODO add your handling code here:
        if (txtMaKH.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "VUi chọn Khách hàng!");
            return;
        }
        new ViewSendEmail(this, true, txtMaKH.getText()).setVisible(true);
    }//GEN-LAST:event_buttonCustom12ActionPerformed

    private void tblListCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListCustomerMouseClicked
        // TODO add your handling code here:
        int index = tblListCustomer.getSelectedRow();
        showDetailCustomer(index);
    }//GEN-LAST:event_tblListCustomerMouseClicked

    private void tblListCustomBillsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListCustomBillsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblListCustomBillsMouseClicked

    private void tblListBillHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListBillHDMouseClicked
        // TODO add your handling code here:
        int index = tblListBillHD.getSelectedRow();
        getItemBillSelected(index);
    }//GEN-LAST:event_tblListBillHDMouseClicked

    private void tblListBillDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListBillDetailMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblListBillDetailMouseClicked

    private void buttonCustom13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom13ActionPerformed

        listBills = billsServiceImpl.getAllBillsSearch(getDataSearchBills());
        showListBillsByPage(1, listBills);
    }//GEN-LAST:event_buttonCustom13ActionPerformed

    private void buttonCustom18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom18ActionPerformed
        if (lbIdBills.getText().equalsIgnoreCase("###")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoá đơn!");
            return;
        }
        try {
            // TODO add your handling code here:
            exportBills(billsServiceImpl.getBillsResponseById(lbIdBills.getText()));
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonCustom18ActionPerformed

    private void buttonCustom19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom19ActionPerformed
        // TODO add your handling code here:
        String timeStart = txtTimeStartTK.getText();
        String timeEnd = txtTimeEndTK.getText();
        lbTongTienTuyChon.setText(MoneyConverter.parse(billsServiceImpl.getTotalMoneyByDate(timeStart, timeEnd)));
    }//GEN-LAST:event_buttonCustom19ActionPerformed

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
    private javax.swing.JPanel ViewNhanVien;
    private javax.swing.JPanel ViewSanPham;
    private javax.swing.JPanel ViewThongKe;
    private javax.swing.JPanel ViewVoucher;
    private components.ButtonCustom btnLamMoi;
    private javax.swing.JLabel btnShowViewBanHang;
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
    private components.ButtonCustom btnSuaSanPham1;
    private components.ButtonCustom btnThemSanPham;
    private components.ButtonCustom btnXoaSanPham;
    private components.ButtonCustom btnXoaSanPham1;
    private components.ButtonCustom buttonCustom1;
    private components.ButtonCustom buttonCustom10;
    private components.ButtonCustom buttonCustom11;
    private components.ButtonCustom buttonCustom12;
    private components.ButtonCustom buttonCustom13;
    private components.ButtonCustom buttonCustom14;
    private components.ButtonCustom buttonCustom15;
    private components.ButtonCustom buttonCustom16;
    private components.ButtonCustom buttonCustom17;
    private components.ButtonCustom buttonCustom18;
    private components.ButtonCustom buttonCustom19;
    private components.ButtonCustom buttonCustom2;
    private components.ButtonCustom buttonCustom3;
    private components.ButtonCustom buttonCustom4;
    private components.ButtonCustom buttonCustom5;
    private components.ButtonCustom buttonCustom6;
    private components.ButtonCustom buttonCustom7;
    private components.ButtonCustom buttonCustom8;
    private components.ButtonCustom buttonCustom9;
    private javax.swing.ButtonGroup buttonGroup1;
    private components.Combobox cbbChatLieu;
    private components.Combobox cbbDeGiay;
    private components.Combobox cbbHang;
    private components.Combobox cbbHinhThucSearch;
    private components.Combobox cbbHinhThucThanhToan;
    private components.Combobox cbbLoaiKH;
    private components.Combobox cbbMaSanPham;
    private components.Combobox cbbMauSac;
    private components.Combobox cbbSize;
    private components.Combobox cbbTheLoai;
    private components.Combobox cbbTimChatLieu;
    private components.Combobox cbbTimChatLieuShop;
    private components.Combobox cbbTimDeGiay;
    private components.Combobox cbbTimDeGiayShop;
    private components.Combobox cbbTimHang;
    private components.Combobox cbbTimHang2;
    private components.Combobox cbbTimHangShop;
    private components.Combobox cbbTimMauSac;
    private components.Combobox cbbTimMauSacShop;
    private components.Combobox cbbTimSize;
    private components.Combobox cbbTimSizeShop;
    private components.Combobox cbbTimTheLoai;
    private components.Combobox cbbTimTrangThai;
    private components.Combobox cbbTimTrangThai2;
    private components.Combobox cbbTrangThaiSearch;
    private com.raven.chart.Chart chart;
    private components.Combobox combobox1;
    private com.raven.datechooser.DateChooser dateTimeEndHD;
    private com.raven.datechooser.DateChooser dateTimeEndTK;
    private com.raven.datechooser.DateChooser dateTimeStartHD;
    private com.raven.datechooser.DateChooser dateTimeStartTK;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lbIdBills;
    private javax.swing.JLabel lbMaHoaDon;
    private javax.swing.JLabel lbMaKhachHang;
    private javax.swing.JLabel lbMoneyPurchaseCustomer;
    private javax.swing.JLabel lbMoneyReduceCustomer;
    private javax.swing.JLabel lbQRCode;
    private javax.swing.JLabel lbTamTinh;
    private javax.swing.JLabel lbTenSanPham;
    private javax.swing.JLabel lbTienDuocGiam;
    private javax.swing.JLabel lbTongTien;
    private javax.swing.JLabel lbTongTien7DayTk;
    private javax.swing.JLabel lbTongTienHomNayTK;
    private javax.swing.JLabel lbTongTienMonthTK;
    private javax.swing.JLabel lbTongTienTuyChon;
    private javax.swing.JLabel lbTongTienYearTK;
    private javax.swing.JPanel lbWebcam;
    private components.PanelRound panelRound1;
    private components.PanelRound panelRound2;
    private components.PanelRound panelRound4;
    private components.PanelRound panelRound5;
    private components.PanelRound panelRound6;
    private components.PanelRound panelRound7;
    private components.Pagination pnaListBillDetail;
    private components.Pagination pnaListBillsHD;
    private components.Pagination pnaListCustomer;
    private components.Pagination pnaListCustomerBills;
    private components.Pagination pnaListProductDetail;
    private components.Pagination pnaListProductShop;
    private components.Pagination pnaListProducts;
    private components.JCheckBoxCustom rdoExportExcel;
    private components.RadioButtonCustom rdoNamKH;
    private components.RadioButtonCustom rdoNuKH;
    private components.MaterialTabbed tabListCustomer;
    private components.MaterialTabbed tabSanPham;
    private components.TabbedPaneCustom tabbedPaneCustom1;
    private components.TabbedPaneCustom tabbedPaneCustom2;
    private components.TabbedPaneCustom tabbedPaneCustom3;
    private components.Table tblListBillDetail;
    private components.Table tblListBillHD;
    private components.Table tblListCustomBills;
    private components.Table tblListCustomer;
    private components.Table tblListHoaDonHomNay;
    private components.Table tblListProductCard;
    private components.Table tblListProductShop;
    private components.Table tblListSanPham;
    private components.Table tblListSanPhamChiTiet;
    private components.TextAreaScroll textAreaScroll1;
    private components.TextField textField5;
    private components.TextField txtDiaChiKH;
    private components.TextField txtEmailKH;
    private components.TextFieldSuggestion txtGiaBanSanPham;
    private components.TextField txtHoTenKH;
    private components.TextFieldSuggestion txtMaKH;
    private components.TextFieldSuggestion txtMaSanPham;
    private components.TextArea txtMoTa;
    private components.TextField txtSDTKhachHang;
    private components.TextField txtSoDienThoaiKH;
    private components.TextFieldSuggestion txtSoLuong;
    private components.TextField txtTenKhachHang;
    private components.TextFieldSuggestion txtTenSanPham;
    private components.TextField txtTimSanPham;
    private components.TextFieldSuggestion txtTimeEndHD;
    private components.TextFieldSuggestion txtTimeEndTK;
    private components.TextFieldSuggestion txtTimeStartHD;
    private components.TextFieldSuggestion txtTimeStartTK;
    private components.TextField txtValueSearchHD;
    private components.TextFieldSuggestion txtVoucher;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
            }
            if (image != null) {
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (Exception e) {
                }
                if (result != null) {
                    scanBarcodeProductCard(result.getText());
                }
            }
        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My thread");
        t.setDaemon(true);
        return t;
    }
}
