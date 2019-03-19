import net.sf.json.JSONArray;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

//import org.apache.commons.httpclient.Header;

public class DataTransfer {

    /*
     * 调用"获取所有可清关订单"接口方法
     * @param path 接口http路径
     */
    private static String getOrderInterface(String path){
        BufferedReader br;
        StringBuffer result = new StringBuffer();
        String line;
        PostMethod postMethod = new PostMethod(path);
        HttpClient httpClient = new HttpClient();

        try {
            URL url = new URL(path);
            // open connect between URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // set header
//            Header header = new Header();
//            header.setName("app_id");
//            header.setValue("2018xxxxxxx");
//            postMethod.setRequestHeader(header);

            // general request property setting
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            // send POST request
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            // get URL response
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            // avoid muti-threads block send/receive message
            conn.disconnect();
            br.close();
            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
            httpClient.getHttpConnectionManager().closeIdleConnections(0);
        }
        return null;
    }

    /*
     * 调用"推送订单清关接口"接口方法
     * @param path 接口http路径
     * @param query 请求参数
     */
    private static String getOrderClearanceStatus(String path, String query){
        String line;
        StringBuffer resultStr = new StringBuffer();

        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.connect();

            PrintStream ps = new PrintStream(conn.getOutputStream());
            ps.print(query);
            ps.close();

            BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while((line = bReader.readLine()) != null) {
                resultStr.append(line);
            }

            System.out.println(resultStr.toString());
            conn.disconnect();
            bReader.close();

            return resultStr.toString();

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 调用"推送支付清关接口"接口方法
     * @param path 接口http路径
     * @param query 请求参数
     */
    private static String getPayClearanceStatus(String path, String query){
        String line;
        StringBuffer resultStr = new StringBuffer();

        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.connect();

            PrintStream ps = new PrintStream(conn.getOutputStream());
            ps.print(query);
            ps.close();

            BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while((line = bReader.readLine()) != null) {
                resultStr.append(line);
            }

            System.out.println(resultStr.toString());
            conn.disconnect();
            bReader.close();

            return resultStr.toString();

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 调用"推送物流单清关接口"接口方法
     * @param path 接口http路径
     * @param query 请求参数
     */
    private static String getExpressClearanceStatus(String path, String query){
        String line;
        StringBuffer resultStr = new StringBuffer();

        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.connect();

            PrintStream ps = new PrintStream(conn.getOutputStream());
            ps.print(query);
            ps.close();

            BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while((line = bReader.readLine()) != null) {
                resultStr.append(line);
            }

            System.out.println(resultStr.toString());
            conn.disconnect();
            bReader.close();

            return resultStr.toString();

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 转换Json类型的信息到对象类型
     * @param jsonStr Json格式的字符串
     */
    public static Object json2Obj(String jsonStr){
        JSONArray jsonArray = JSONArray.fromObject(jsonStr);
        try {
            Object arrayStr = jsonArray.get(0);
//            JSONObject jsonObject = JSONObject.fromObject(arrayStr);
//            Iterator it = jsonObject.keys();
//            while(it.hasNext()){
//                String key = (String) it.next();
//                String val = jsonObject.getString(key);
//                System.out.printf("%s : %s\n", key, val);
//            }
            return arrayStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String orderClearanceQuery = null;
    private static String payClearanceQuery = null;
    private static String expressClearanceQuery = null;
    /*
     * 获取海关接口回执报文(xml)中的值
     * @param xml xml格式报文数据
     */
    public static void getQuery(String xml){
        /*下面初始的变量是从海关步骤返回信息后，考虑到的信息处理*/
        //initial query param
        String order_id = null;

        String send_status = null; //order_push_status
        String order_push_message = null;

//        String pay_push_status = null; //pay_status
//        String pay_push_message = null;
//
//        String express_push_status = null; //trade_type
//        String express_push_message = null;

        SAXReader reader = new SAXReader();

        try {
            Document document = DocumentHelper.parseText(xml);
            Element rootElement = document.getRootElement();
            Element ele = rootElement.element("OrderReturn");
            HashMap<String, String> query = new HashMap<String, String>();
            query.put("order_id", ele.elementText("orderNo"));
            query.put("send_status", ele.elementText("returnStatus"));
            query.put("order_push_message", ele.elementText("returnInfo"));
            orderClearanceQuery = CustomConnection.encryptQuery(query);;
//            System.out.println(orderClearanceQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, DocumentException {
        String orderUrl = "servlet url deleted";
        String orderClearanceUrl = "servlet url deleted";
        String payClearanceUrl = "servlet url deleted";
        String expressClearanceUrl = "servlet url deleted";

        String info = getOrderInterface(orderUrl);
        Object objData = json2Obj(info);
        // 订单生成XML报文
        OrderToXML.orderJson2XML(objData);

        // 海关总署传输节点报文
//        CustomConnection.getCustomInterface(path, xmlOrder);
        //test
        String test = "<CEB312Message xmlns=\"http://www.chinaport.gov.cn/ceb\" guid=\"4CDE1CFD-EDED-46B1-946C-B8022E42FC94\" version=\"1.0\">\n" +
                "    <OrderReturn>\n" +
                "        <guid>4CDE1CFD-EDED-46B1-946C-B8022E42FC94</guid>\n" +
                "        <ebpCode>1105910159</ebpCode>\n" +
                "        <ebcCode>1105910159</ebcCode>\n" +
                "        <orderNo>order20160321116420545</orderNo>\n" +
                "        <returnStatus>2</returnStatus>\n" +
                "        <returnTime>20160428182238000</returnTime>\n" +
                "        <returnInfo>新增申报成功[4CDE1CFD-EDED-46B1-946C-B8022E42FC94]</returnInfo>\n" +
                "    </OrderReturn>\n" +
                "</CEB312Message>";
        getQuery(test);

        // 支付生成XML报文
//        String xmlPay = PayToXML.payJson2XML(objData);
//        System.out.println(xmlPay);

        // 物流生成XML报文
//        String xmlExpress = ExpressToXML.expressJson2XML(objData);
//        System.out.println(xmlExpress);

        // "推送订单清关接口"返回状态
        String orderClearanceStatus = getOrderClearanceStatus(orderClearanceUrl, orderClearanceQuery);
        // "推送支付清关接口"返回状态
        String payClearanceStatus = getPayClearanceStatus(payClearanceUrl, payClearanceQuery);
        // "推送物流单清关接口"返回状态
        String expressClearanceStatus = getExpressClearanceStatus(expressClearanceUrl, expressClearanceQuery);
    }
}
