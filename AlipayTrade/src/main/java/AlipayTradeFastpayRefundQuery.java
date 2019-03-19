import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;

public class AlipayTradeFastpayRefundQuery {

    /*
     * 支付宝订单退款的查询接口
     * return 返回的退款订单信息
     */
    public static String getQuery(){

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfigs.SERVER_URL,
                AlipayConfigs.APP_ID, AlipayConfigs.APP_PRIVATE_KEY, AlipayConfigs.FORMAT,
                AlipayConfigs.CHARSET_UTF, AlipayConfigs.ALIPAY_PUBLIC_KEY, AlipayConfigs.SIGN_TYPE);

        //初始化API对应的request
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();

        ////业务参数集合模块
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        model.setTradeNo(OrderInfo.trade_no);
        model.setOutTradeNo(OrderInfo.out_trade_no);
        model.setOutRequestNo(OrderInfo.out_request_no);

        request.setBizModel(model);

        AlipayTradeFastpayRefundQueryResponse response = null;

        //表单字符串
        String form = "";
        try {
            response = alipayClient.execute(request);
            if(response.isSuccess()){
                System.out.println("调用成功");
                form = response.getBody();
            }else{
                System.out.println("调用失败");
            }
            return form;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args){
        String form = "";
        form = getQuery();
        System.out.println(form);
    }
}
