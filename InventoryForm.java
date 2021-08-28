import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.*;

public class InventoryForm extends JFrame {
    private JButton btnClose;
    private JScrollPane jScrollPane1;
    private JTable tblInventory;

    public InventoryForm() {
        initComponents();
        populateTable();
    }

    private void initComponents() {
        jScrollPane1 = new JScrollPane();
        tblInventory = new JTable();
        btnClose = new JButton("Close");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inventory Form");

        tblInventory.setModel(
                new DefaultTableModel(new Object[][] {}, new String[] { "Product Code", "Description", "Stock" }) {
                    Class[] types = new Class[] { String.class, String.class, String.class };
                    boolean[] canEdit = new boolean[] { false, false, false };

                    public Class getColumnClass(int columnIndex) {
                        return types[columnIndex];
                    }

                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                });

        jScrollPane1.setViewportView(tblInventory);
        if (tblInventory.getColumnModel().getColumnCount() > 0) {
            tblInventory.getColumnModel().getColumn(0).setResizable(false);
            tblInventory.getColumnModel().getColumn(1).setResizable(false);
            tblInventory.getColumnModel().getColumn(2).setResizable(false);
        }

        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(15, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClose, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE).addContainerGap()));

        pack();
        setLocationRelativeTo(null);
    }

    // This method storing values from inventory list and populate it to the table
    private void populateTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) tblInventory.getModel();
            Product product = new Product();
            ArrayList<String[]> products = product.getProducts();

            for (String[] row : products) {
                model.addRow(new String[] { row[0], row[1], row[3] });
            }

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    protected void btnCloseMouseClicked(MouseEvent evt) {
        this.dispose();
        new MenuForm().setVisible(true);
    }
}
