package CustomsHZ;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomsHzTest {

    public static String connHzService(String path){
        PostMethod postMethod = new PostMethod(path);
        HttpClient httpClient = new HttpClient();

        try {
            URL url = new URL(path);
            // 打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置提交类型
            conn.setRequestMethod("POST");
            // 设置通用的请求属性

            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStream os = conn.getOutputStream();
            os.flush();

            //读取响应
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String lines;

            while((lines = br.readLine()) != null){
                System.out.println(lines);
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
            httpClient.getHttpConnectionManager().closeIdleConnections(0);
        }
        return null;
    }

    /**
     * 北京接口测试
     * @param path
     * @return
     */
    public static String connBjService(String path){
        String line;
        StringBuffer resultStr = new StringBuffer();

        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

//            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.connect();

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

    public static void main(String[] args) throws Exception {
        String order_id = "4636";

        isEmpty();

        String path = "http://127.0.0.1:8080/customs/hz_test";
        System.out.println(connHzService(path));

        String path2 = "http://127.0.0.1:8080/customs/bj_test";
        System.out.println(connBjService(path2));

//        String content = "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";
//        String xml = decodeXml(content);
//        System.out.println(xml);
//        System.out.println(x.equals(y));
//        backStrToRecordAll();
//        createCebXML();
//        receiveXML();
//        System.out.println("timer begin...");

//        while(true){
//            TimerTest(5);
//        }
//        createCebXML();
    }

    public static void TimerTest(int time){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time's up!!!");
            }
        }, time * 1000);
    }

    public static String decodeXml(String content) throws Exception {
        String AES_KEY = "";
        byte[] input_content = Base64.decodeBase64(content.getBytes("utf-8"));
        byte[] aes_key = Base64.decodeBase64(AES_KEY.getBytes("utf-8"));
        String original_content =new String(AESUtil.decrypt(input_content, aes_key),"utf-8");
        return original_content;
    }

    public static String formatXML(String xmlStr) throws DocumentException, IOException {
        Document document = DocumentHelper.parseText(xmlStr);

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        format.setIndent(true);
        format.setIndent("  ");
        format.setNewLineAfterDeclaration(false);
        format.setNewlines(true);

        StringWriter writer = new StringWriter();
        // 格式化输出流
        XMLWriter xmlWriter = new XMLWriter(writer, format);
        // 将document写入到输出流
        xmlWriter.write(document);
        xmlWriter.close();
        return writer.toString();
    }

    public static String backStrToRecordAll() throws DocumentException {
        String result = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "        <mo version=\"1.0.0\">\n" +
                "          <head>\n" +
                "            <businessType>RESULT</businessType>\n" +
                "          </head>\n" +
                "          <body>\n" +
                "            <list>\n" +
                "              <jkfResult>\n" +
                "                <companyCode></companyCode>\n" +
                "                <businessNo>4841</businessNo>\n" +
                "                <businessType>IMPORTORDER</businessType>\n" +
                "                <declareType>1</declareType>\n" +
                "                <chkMark>1</chkMark>\n" +
                "                <noticeDate>2019-03-01</noticeDate>\n" +
                "                <noticeTime>15:41</noticeTime>\n" +
                "                <resultList>\n" +
                "                  <jkfResultDetail>\n" +
                "                    <resultInfo>处理成功</resultInfo>\n" +
                "                  </jkfResultDetail>\n" +
                "                </resultList>\n" +
                "              </jkfResult>\n" +
                "            </list>\n" +
                "          </body>\n" +
                "        </mo>";

        try {
            Document document = DocumentHelper.parseText(result);
            Element root = document.getRootElement();
            getNodes(root);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void getNodes(Element node){
        System.out.println("当前节点名称："+node.getName());//当前节点名称
        System.out.println("当前节点的内容："+node.getTextTrim());//当前节点名称
        List<Attribute> listAttr=node.attributes();//当前节点的所有属性的list
        for(Attribute attr:listAttr){//遍历当前节点的所有属性
            String name=attr.getName();//属性名称
            String value=attr.getValue();//属性的值
            System.out.println("属性名称："+name+"属性值："+value);
        }

        //递归遍历当前节点所有的子节点
        List<Element> listElement=node.elements();//所有一级子节点的list
        for(Element e:listElement){//遍历所有一级子节点
            getNodes(e);//递归
        }
    }

    public static String createCebXML(){
        String orderGoods = null;
        Document doc;
        String uuid = UUID.randomUUID().toString().toUpperCase();

        Element root = DocumentHelper.createElement("ceb:CEB711Message");
        doc = DocumentHelper.createDocument(root);
        doc.setRootElement(root);

        root.addAttribute("guid", uuid);
        root.addAttribute("version", "1.0");
        Namespace namespace = new Namespace("ceb", "http://www.chinaport.gov.cn/ceb");
        root.addNamespace("ceb", "http://www.chinaport.gov.cn/ceb");
        root.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        Element delivery = root.addElement(new QName("Delivery", namespace));
        Element deliveryHead = delivery.addElement(new QName("DeliveryHead", namespace));

        Element guid = deliveryHead.addElement(new QName("guid", namespace));
        guid.setText(uuid);//企业系统生成36位唯一序号 (英文字母大写)
        Element appType = deliveryHead.addElement(new QName("appType", namespace));
        appType.setText("1");//企业报送类型。1-新增 2-变 更 3-删除。默认为1。
        Element appTime = deliveryHead.addElement(new QName("appTime", namespace));
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        appTime.setText(df.format(new Date()));
        Element appStatus = deliveryHead.addElement(new QName("appStatus", namespace));
        appStatus.setText("1"); //业务状态:1-暂存,2-申报,默认为1。填写2 时,Signature节点必须填 写。
        Element customsCode = deliveryHead.addElement(new QName("customsCode", namespace));
        customsCode.setText("");//接受申报的海关关区代码， 参照JGS/T 18《海关关区代 码》
        Element copNo = deliveryHead.addElement(new QName("copNo", namespace));
        copNo.setText("");
        Element preNo = deliveryHead.addElement(new QName("preNo", namespace));
        preNo.setText(""); // 固定值
        Element rkdNo = deliveryHead.addElement(new QName("rkdNo", namespace));
        rkdNo.setText(""); // 固定值
        Element operatorCode = deliveryHead.addElement(new QName("operatorCode", namespace));
        operatorCode.setText(""); // 固定值
        Element operatorName = deliveryHead.addElement(new QName("operatorName", namespace));
        operatorName.setText(""); // 固定值
        Element ieFlag = deliveryHead.addElement(new QName("ieFlag", namespace));
        ieFlag.setText("");
        Element trafMode = deliveryHead.addElement(new QName("trafMode", namespace));
        trafMode.setText("");
        Element trafNo = deliveryHead.addElement(new QName("trafNo", namespace));
        trafNo.setText(""); // 固定值
        Element voyageNo = deliveryHead.addElement(new QName("voyageNo", namespace));
        voyageNo.setText("");
        Element billNo = deliveryHead.addElement(new QName("billNo", namespace));
        billNo.setText("");
        Element logisticsCode = deliveryHead.addElement(new QName("logisticsCode", namespace));
        logisticsCode.setText("");
        Element logisticsName = deliveryHead.addElement(new QName("logisticsName", namespace));
        logisticsName.setText("");
        Element unloadLocation = deliveryHead.addElement(new QName("unloadLocation", namespace));
        unloadLocation.setText("");
        Element note_head = deliveryHead.addElement(new QName("note", namespace));
        note_head.setText("");

        Element DeliveryList = delivery.addElement(new QName("DeliveryList", namespace));
        Element gnum = DeliveryList.addElement(new QName("gnum", namespace));
        gnum.setText("");
        Element logisticsNo = DeliveryList.addElement(new QName("logisticsNo", namespace));
        logisticsNo.setText("");
        Element note_list = DeliveryList.addElement(new QName("note", namespace));
        note_list.setText("");

        //报文传输企业信息
        Element baseTransfer = root.addElement(new QName("BaseTransfer", namespace));
        Element copCode = baseTransfer.addElement(new QName("copCode", namespace));
        copCode.setText("");
        Element copName = baseTransfer.addElement(new QName("copName", namespace));
        copName.setText("");
        Element dxpMode = baseTransfer.addElement(new QName("dxpMode", namespace));
        dxpMode.setText("DXP");
        Element dxpId = baseTransfer.addElement(new QName("dxpId", namespace));
        dxpId.setText("");
        Element noteBT = baseTransfer.addElement(new QName("note", namespace));
        noteBT.setText("");

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        format.setIndent(true);
        format.setIndent("  ");
        format.setNewLineAfterDeclaration(false);
        format.setNewlines(true);

        try{
            XMLWriter xmlWriter = new XMLWriter(format);
            xmlWriter.setEscapeText(false);
            xmlWriter.write(doc);
            String xml = String.valueOf(xmlWriter);
            xmlWriter.close();
            return xml;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean receiveXML(){
        StringBuffer xml = new StringBuffer();
        try{
            String encoding = "UTF-8";
            File file = new File("/Users/shengjiezhai/Desktop/Recive/CEB312Message.xml");
            while(!file.exists()){
                System.out.println("等待回执报文...");
                Thread.sleep(2000);
            }
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);

                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    xml.append(lineTxt);
                }
                System.out.println(xml.toString());
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean isEmpty(){
        File file = new File("/Users/shengjiezhai/Desktop/Recive");

        if(file != null && file.exists() && file.isDirectory()){
            File[] file_list = file.listFiles();
            for(File f : file_list){
                String fname = f.getName();
                if (fname.endsWith("xml")){
                    System.out.println("文件夹不为空");
                    return false;
                }
            }
            System.out.println("文件夹为空");
            return true;
        }else{
            System.out.println("文件夹不存在或者路径错误...");
        }
        return false;
    }
}
