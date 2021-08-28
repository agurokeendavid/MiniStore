import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;

public class MenuForm extends JFrame {
    private JButton btnBuy;
    private JButton btnCheckOut;
    private JButton btnExit;
    private JButton btnInventory;
    private JScrollPane jScrollPane1;
    private JTable tblRecord;

    public MenuForm() {
        initComponents();
        populateTable();
    }

    private void initComponents() {
        jScrollPane1 = new JScrollPane();
        tblRecord = new JTable();
        btnBuy = new JButton("Buy");
        btnCheckOut = new JButton("Check-out");
        btnInventory = new JButton("Inventory");
        btnExit = new JButton("Exit");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MINI STORE - KEEN DAVID AGURO");

        tblRecord.setModel(
                new DefaultTableModel(new Object[][] {}, new String[] { "Product Code", "Description", "Price" }) {
                    Class[] types = new Class[] { String.class, String.class, String.class };
                    boolean[] canEdit = new boolean[] { false, false, false };

                    public Class getColumnClass(int columnIndex) {
                        return types[columnIndex];
                    }

                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                });

        jScrollPane1.setViewportView(tblRecord);
        if (tblRecord.getColumnModel().getColumnCount() > 0) {
            tblRecord.getColumnModel().getColumn(0).setResizable(false);
            tblRecord.getColumnModel().getColumn(1).setResizable(false);
            tblRecord.getColumnModel().getColumn(2).setResizable(false);
        }

        btnBuy.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                btnBuyMouseClicked(evt);
            }
        });

        btnCheckOut.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                btnCheckOutMouseClicked(evt);
            }
        });

        btnInventory.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                btnInventoryMouseClicked(evt);
            }
        });

        btnExit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                btnExitMouseClicked(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addComponent(jScrollPane1)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(GroupLayout.Alignment.TRAILING,
                        layout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnBuy).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCheckOut).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnInventory).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExit).addGap(92, 92, 92)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup().addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(btnExit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnInventory, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCheckOut, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuy))
                .addContainerGap()));
        pack();
        setLocationRelativeTo(null);
    }

    private void populateTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) tblRecord.getModel();
            Product product = new Product();
            ArrayList<String[]> products = product.getProducts();
            for (String[] row : products) {
                model.addRow(row);
            }
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(null, "File not found");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    protected void btnExitMouseClicked(MouseEvent evt) {
        this.dispose();
    }

    protected void btnInventoryMouseClicked(MouseEvent evt) {
        this.dispose();
        new InventoryForm().setVisible(true);
    }

    protected void btnCheckOutMouseClicked(MouseEvent evt) {
        this.dispose();
        new CheckOutForm().setVisible(true);
    }

    protected void btnBuyMouseClicked(MouseEvent evt) {
        try {
            String inputProductCode = JOptionPane.showInputDialog(null, "Enter product code:");
            int numberOfItems, quantity;
            String productCode, description;
            double price, totalItemPrice;
            boolean isProductCodeExist = false;
            Product product = new Product();
            ArrayList<String[]> products = product.getProducts();

            for (String[] row : products) {
                productCode = row[0];
                description = row[1];
                price = Double.parseDouble(row[2]);
                quantity = Integer.parseInt(row[3]);
                if (productCode.equalsIgnoreCase(inputProductCode)) {
                    numberOfItems = Integer.parseInt(JOptionPane.showInputDialog(null, "Number of item to be bought:"));
                    
                    if (numberOfItems <= 0)
                        throw new Exception("Number of items should be greater than 0.");
                    
                    if (numberOfItems > quantity)
                        throw new Exception("Number of items should not be exceed to the current quantity.");

                    product.removeExistingProduct(inputProductCode);
                    totalItemPrice = price * numberOfItems;
                    Product.ITEM_CART.add(new String[] { productCode, description, String.valueOf(numberOfItems),
                            String.valueOf(totalItemPrice) });
                    isProductCodeExist = true;
                    JOptionPane.showMessageDialog(null, "Product successfully added to cart!");
                    break;
                }
            }

            if (!isProductCodeExist) {
                throw new Exception("Product Code Not Found!");
            }

        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "Invalid input");
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(null, "File not found!");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }
}
