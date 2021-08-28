import java.io.File;
public class DbConnector {
    private String fileName = "mini_store_db.txt";

    protected File getFile() {
        return new File(this.fileName);
    }
}
