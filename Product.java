import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.*;

public class Product extends DbConnector {
    public static ArrayList<String[]> ITEM_CART = new ArrayList<>();

    public void updateProduct() throws IOException {
        ArrayList<String[]> products = this.getProducts();
        PrintWriter printWriter = new PrintWriter(super.getFile());
        for (String[] record : products) {
            for (String[] item : ITEM_CART) {
                if (record[0].equalsIgnoreCase(item[0])) {
                    record[3] = String.valueOf(Integer.parseInt(record[3]) - Integer.parseInt(item[2]));
                    break;
                }
            }
            printWriter.println(String.format("%s,%s,%s,%s", record[0], record[1], record[2], record[3]));
        }
        ITEM_CART.clear();
        printWriter.flush();
        printWriter.close();
    }

    public void removeExistingProduct(String productCode) {
        Iterator<String[]> itemCartIterator = ITEM_CART.iterator();
        while (itemCartIterator.hasNext()) {
            String[] item = itemCartIterator.next();
            if (productCode.equalsIgnoreCase(item[0])) {
                itemCartIterator.remove();
            }
        }
    }

    public ArrayList<String[]> getProducts() throws IOException {
        ArrayList<String[]> rowList = new ArrayList<>();
        Scanner scanner = new Scanner(super.getFile());
        while (scanner.hasNext()) {
            rowList.add(scanner.nextLine().split(","));
        }
        scanner.close();
        return rowList;
    }
}
