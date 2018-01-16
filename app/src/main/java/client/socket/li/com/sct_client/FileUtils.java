package client.socket.li.com.sct_client;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by TangWei on 2017/2/9.
 * FileUtils
 */

public class FileUtils {

    public static final String PING_PATH = App.SOCKET_PATH + "/pingTestLog.txt";
    public static final String SOCKET_PATH = App.SOCKET_PATH + "/socketTestLog.txt";


    /**
     * 读文件
     *
     * @param file 文件路径
     * @return 读取文件内容结果
     */
    public static String readFile(File file) {
        try {
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\r\n");// windows系统换行为\r\n，Linux为\n
            }
            return sb.delete(sb.length() - 2, sb.length()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读文件
     *
     * @param file 文件路径
     * @return 读取文件内容结果
     */
    public static boolean isExsitFile(File file) {
        return file.exists();
    }

    /**
     * 写文件
     *
     * @param file     文件路径
     * @param content  写入内容
     * @param isAppend 是否追加
     */
    public static void writeFile(String file, String content, boolean isAppend) {
        BufferedWriter bw = null;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, isAppend);
            bw = new BufferedWriter(fileWriter);
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(bw);
            CloseUtils.closeIO(fileWriter);
        }
    }

}
