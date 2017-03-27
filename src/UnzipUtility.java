import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class UnzipUtility {

    // 1. Download the zip file from the NSE website.
    // 2. Unzip the zip file downloaded

    public static List<String> downloadAndUnzip(String url, String zipFilePath, String destDirectory) throws IOException{

        URL destURL = new URL(url);
        URLConnection urlConnection = destURL.openConnection();

        ReadableByteChannel zipByteChannel = Channels.newChannel(urlConnection.getInputStream());
        FileOutputStream fos = new FileOutputStream(zipFilePath);
        fos.getChannel().transferFrom(zipByteChannel,0,Long.MAX_VALUE);

        return unzip(zipFilePath,destDirectory);

    }

    public static List<String> unzip(String zipFilePath, String destDirectory) throws IOException{

        List<String> unzippedFilesList = new ArrayList<>();

        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry zipEntry = zipIn.getNextEntry();

        while (zipEntry != null){

            String filePath = destDirectory+File.separator+zipEntry.getName();

            if (!zipEntry.isDirectory()){
                unzippedFilesList.add(extractFile(zipIn, filePath));
            } else{
                File dir = new File(filePath);
                dir.mkdir();
            }

            zipEntry = zipIn.getNextEntry();
        }

        System.out.println("Unzipping Done!");
        return  unzippedFilesList;
    }

    public static String extractFile(ZipInputStream zipIn, String filePath) throws IOException{

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));

        byte bytes[] = new byte[4096];
        int read = 0;
        read = zipIn.read(bytes);

        while (read != -1){

            bos.write(bytes,0,read);
            read = zipIn.read(bytes);
        }

        bos.close();

        System.out.println("Extracted a file");
        return filePath;
    }
}
