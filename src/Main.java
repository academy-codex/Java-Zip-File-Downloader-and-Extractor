import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

    String url = "http://www.colorado.edu/conflict/peace/download/peace.zip";
    String zipFilePath = "C:\\Users\\siddh\\Downloads\\peace.zip";
    String destDir = "C:\\Users\\siddh\\Desktop\\Extracted";

    UnzipUtility.downloadAndUnzip(url,zipFilePath,destDir);
    }
}
