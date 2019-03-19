import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;



public class AlipayTradePay {


//    public static void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        paymentQRCode(response);
//
//    }

    /*
     * PC场景下单并支付
     * return 返回的支付页面表单
     */
    public static String paymentQRCode() {

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfigs.SERVER_URL,
                AlipayConfigs.APP_ID, AlipayConfigs.APP_PRIVATE_KEY, AlipayConfigs.FORMAT,
                AlipayConfigs.CHARSET_UTF, AlipayConfigs.ALIPAY_PUBLIC_KEY, AlipayConfigs.SIGN_TYPE);

        //初始化API对应的request
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

        //在公共参数中设置回跳和通知地址
        /*
         * 1. 周末写 ReturnUrl 和 NotifyUrl
         * 2. 把表单放在HTML中连接好 */
        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
        alipayRequest.setNotifyUrl("https://wechat.qqyqh.com/api/zfb/pay_notify"); //在公共参数中设置回跳和通知地址

        //业务参数集合模块
        AlipayTradePayModel model = new AlipayTradePayModel();
        model.setOutTradeNo(OrderInfo.out_trade_no);
        model.setScene(OrderInfo.scene_bar);
        model.setAuthCode(OrderInfo.auth_code);
        model.setProductCode(OrderInfo.product_code);
        model.setTotalAmount(OrderInfo.total_amount);
        model.setSubject(OrderInfo.subject);
        model.setBody(OrderInfo.body);

        AlipayTradePagePayResponse response = null;
        alipayRequest.setBizModel(model);
        //表单字符串
        String form="";

        try {
            //调用SDK生成表单
            response = alipayClient.pageExecute(alipayRequest);
            if (response.isSuccess()){
                form = response.getBody();
                return form;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return null;
    }

//    public static void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        doGet(request, response);
//    }

    public static void main(String[] args) throws IOException {
        String form = "";
        form = paymentQRCode();

        File input = new File("/Users/shengjiezhai/IdeaProjects/CustomClearance/AlipayTrade/src/main/java/index.html");
        Document doc = Jsoup.parse(input, "UTF-8");
        Elements content = doc.getElementsByTag("body");
        content.remove();
        doc.appendElement("body");
        Elements newContent = doc.getElementsByTag("body");
        newContent.append(form);
        FileOutputStream fos = new FileOutputStream("/Users/shengjiezhai/IdeaProjects/CustomClearance/AlipayTrade/src/main/java/index.html");
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        osw.write(doc.html());
        osw.close();




    }
}
