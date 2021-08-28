import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class CheckOutForm extends JFrame {
    private JButton btnClose;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JScrollPane jScrollPane1;
    private JLabel lblItemsPurchased;
    private JLabel lblTotalBill;
    private JTable tblItemsBought;

    public CheckOutForm() {
        initComponents();
        populateComponents();
    }

    private void initComponents() {
        jLabel1 = new JLabel("Items Bought:");
        jScrollPane1 = new JScrollPane();
        tblItemsBought = new JTable();
        jLabel2 = new JLabel("No of items purchased:");
        lblItemsPurchased = new JLabel("0");
        jLabel3 = new JLabel("Total Bill:");
        lblTotalBill = new JLabel("0");
        jLabel4 = new JLabel("Thank you for shopping!");
        btnClose = new JButton("Close");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Check-out Form");

        tblItemsBought.setModel(new DefaultTableModel(new Object[][] {

        }, new String[] { "Description", "Quantity", "Price" }) {
            Class[] types = new Class[] { String.class, String.class, String.class };
            boolean[] canEdit = new boolean[] { false, false, false };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblItemsBought);
        if (tblItemsBought.getColumnModel().getColumnCount() > 0) {
            tblItemsBought.getColumnModel().getColumn(0).setResizable(false);
            tblItemsBought.getColumnModel().getColumn(1).setResizable(false);
            tblItemsBought.getColumnModel().getColumn(2).setResizable(false);
        }

        btnClose.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup().addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 86,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel4, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
                                                148, Short.MAX_VALUE)
                                        .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(
                                                jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
                                        .createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(lblItemsPurchased, GroupLayout.PREFERRED_SIZE, 47,
                                                        GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lblTotalBill, GroupLayout.Alignment.TRAILING,
                                                        GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE).addComponent(btnClose)))))
                .addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup().addContainerGap()
                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblItemsPurchased, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTotalBill, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();
        setLocationRelativeTo(null);

    }

    private void calculateItemPurchasedAndTotalBill() {
        int itemsPurchased = 0;
        double totalBill = 0;
        for (int i = 0; i < tblItemsBought.getRowCount(); i++) {
            itemsPurchased += Integer.parseInt(tblItemsBought.getValueAt(i, 1).toString());
            totalBill += Double.parseDouble(tblItemsBought.getValueAt(i, 2).toString());
        }
        lblItemsPurchased.setText(String.valueOf(itemsPurchased));
        lblTotalBill.setText(String.valueOf(totalBill));

    }

    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) tblItemsBought.getModel();
        ArrayList<String[]> products = Product.ITEM_CART;
        for (String[] row : products) {
            model.addRow(new String[] { row[1], row[2], row[3] });
        }
    }

    private void populateComponents() {
        try {
            Product product = new Product();
            populateTable();
            calculateItemPurchasedAndTotalBill();
            product.updateProduct();
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(null, "File not found");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    protected void btnCloseMouseClicked(MouseEvent evt) {
        this.dispose();
        new MenuForm().setVisible(true);
    }
}
