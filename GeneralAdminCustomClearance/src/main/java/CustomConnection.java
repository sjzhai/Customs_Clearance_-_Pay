
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;


public class CustomConnection {

    /*
     * query->md5加密
     * @param query字符串类型的参数
     */
    public static String encryptQuery(HashMap query){
        final String APP_ID = "xxxxxxxxxxx";
        final String APP_KEY = "11111a111aaaa11a1a11111111a1a111a";
        StringBuffer encode = new StringBuffer();
        String queryCode = "";
        Iterator it = query.keySet().iterator();
        while(it.hasNext()){
            Object key = it.next();
            Object val = query.get(key);
            queryCode += key.toString()+'='+val.toString()+'|';
        }
        encode.append(APP_ID).append(queryCode, 0, queryCode.length()-1).append(APP_KEY);

        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytesQuery = encode.toString().getBytes();
            md5.update(bytesQuery);
            byte[] resultQuery = md5.digest();
            return byteArrayToHex(resultQuery);
        } catch (NoSuchAlgorithmException e){
            return null;
        }
    }

    /*
     * byte -> Hex
     * @ byteArray 二进制参数array
     * return 16进制密文md5
     */
    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];

        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }

        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    /*
     * 连接海关接口并提交xml报文
     * @param path 海关接口
     * @param query 传参的参数
     * @return 返回订单回执报文xml
     */
    public static String getCustomInterface(String path, String query){
        String line;
        StringBuffer stringBuffer = new StringBuffer();

        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

//            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setDoOutput(true);
            conn.connect();

            PrintStream ps = new PrintStream(conn.getOutputStream());
            ps.print(query);
            ps.close();

            Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            if (query != null && !"".equals(query)) {
                writer.write(query);
            }
            writer.flush();
            writer.close();

            InputStream in;
            System.out.println(conn.getResponseCode());
            if (conn.getResponseCode() >= 400) {
                in = conn.getErrorStream();
            } else {
                in = conn.getInputStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String tmp;
            while ((tmp = bufferedReader.readLine()) != null) {
                response.append(tmp);
            }
            if (in != null) {
                in.close();
            }
            bufferedReader.close();
            conn.disconnect();
            return response.toString();

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
