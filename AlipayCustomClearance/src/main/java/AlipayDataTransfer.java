import org.apache.commons.httpclient.methods.PostMethod;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AlipayDataTransfer {
    /*
     * query parameters initialize
     * @service         接口名称
     * @partner         合作者身份ID：以2088开头的16位纯数字组成。
     * @_input_charset  参数编码字符集：商户网站使用的编码格式，如UTF-8、GBK、GB2312等。
     * @sign_type       签名方式：DSA、RSA、MD5三个值可选，必须大写。
     * @sign            签名
     * @out_request_nos 报关请求号：需要查询的商户端报关请求号，支持批量查询，多个值用英文半角逗号分隔，单次最多10个报关请求号
     */
    private static final String service = "alipay.overseas.acquire.customs.query";
    private static String partner;
    private static final String _input_charset = "UTF-8";
    private static final String sign_type = "MD5";
    private static String sign;
    private static String out_request_nos;

    /*
     * 请求参数签名
     * @return
     */
    public static String requestParamSign(){

        return null;
    }

    /*
     * 调用 支付宝报关接口 接口方法
     * @param path 接口http路径
     */
    public static String getAlipayInterface(String path){
        BufferedReader br;
        StringBuffer result = new StringBuffer();
        String line;
        StringBuffer request = new StringBuffer();


        request.append(path+"?");
        request.append("&out_request_nos="+out_request_nos);
        request.append("&sign_type="+sign_type);
        request.append("&sign="+sign);
        request.append("&_input_charset="+_input_charset);
        request.append("&service="+service);
        request.append("&partner="+partner);

//        try {
//            URL url = new URL(path);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
//
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//            conn.connect();
//
////            PrintStream ps = new PrintStream(conn.getOutputStream());
////            ps.print(query);
////            ps.close();
//
//            BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//            while((line = bReader.readLine()) != null) {
//                result.append(line);
//            }
//
//            conn.disconnect();
//            bReader.close();
//
//            return result.toString();
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
        return request.toString();
    }

    public static void main(String[] args){
        String url = "https://mapi.alipay.com/gateway.do";
        String re = getAlipayInterface(url);
        System.out.println(re);
        String ss = "https://mapi.alipay.com/gateway.do?out_request_nos=201506010001%2C201506010002%2C201506010003&sign_type=MD5&sign=b7baf9af3c91b37bef4261849aa76281&_input_charset=UTF-8&service=alipay.overseas.acquire.customs.query&partner=2088101568338364";
        String[] aa = ss.split("[&,?]");
        System.out.println("ss1");
        for (int i=0; i < aa.length; i++){
            System.out.println(aa[i]);
        }
        System.out.println("");
        String ss2 = "_input_charset=utf-8&body=testjsdzbody&notify_url=http://www.test.com/create_direct_pay_by_user-JAVA-UTF-8/notify_url.jsp&out_trade_no=9890879868657&partner=2088000000000000&payment_type=1&return_url=http://www.baidu.com&seller_id=2088000000000000&service=create_direct_pay_by_user&subject=testjsdz&total_fee=0.01svzitn**********pslfal77xlxm0qhc";
        String[] bb = ss2.split("[&,?]");
        System.out.println("ss2: 待签名字符串的示例，key值已被隐藏");
        for (int i=0; i < bb.length; i++){
            System.out.println(bb[i]);
        }
        System.out.println("");
        String ss3 = "http://mapi.alipay.com/gateway.do?body=testjsdzbody&subject=testjsdz&sign_type=MD5&notify_url=http://www.test.com/create_direct_pay_by_user-JAVA-UTF-8/notify_url.jsp&out_trade_no=9890879868657&return_url=http://www.baidu.com&sign=***&_input_charset=utf-8&total_fee=0.01&service=create_direct_pay_by_user&partner=2088000000000000&seller_id=2088000000000000&payment_type=1";
        String[] cc = ss3.split("[&,?]");
        System.out.println("ss3: 最终的请求信息");
        for (int i=0; i < cc.length; i++){
            System.out.println(cc[i]);
        }


    }
}
