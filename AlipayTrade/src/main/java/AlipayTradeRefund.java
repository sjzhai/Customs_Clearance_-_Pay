import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;


public class AlipayTradeRefund {

    /*
     * 支付宝订单退款接口
     * return 返回的退款信息
     */
    public static String doRefund(){
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfigs.SERVER_URL,
                AlipayConfigs.APP_ID, AlipayConfigs.APP_PRIVATE_KEY, AlipayConfigs.FORMAT,
                AlipayConfigs.CHARSET_GBK, AlipayConfigs.ALIPAY_PUBLIC_KEY, AlipayConfigs.SIGN_TYPE);

        AlipayTradeRefundRequest alipayTradeRefundRequest = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutRequestNo(OrderInfo.out_trade_no);
        model.setTradeNo(OrderInfo.trade_no);
        model.setRefundAmount(OrderInfo.refund_amount);
        model.setRefundCurrency(OrderInfo.refund_currency);
        model.setRefundReason(OrderInfo.refund_reason);
//        model.setOutRequestNo("HZ01RF001");
//        model.setOperatorId("OP001");
//        model.setTerminalId("NJ_T_001");
////        model.setGoodsDetail();
//        model.setOrgPid("2088101117952222");
        alipayTradeRefundRequest.setBizModel(model);

        AlipayTradeRefundResponse alipayTradeRefundResponse = null;

        //表单字符串
        String form = "";

        try {
            alipayTradeRefundResponse = alipayClient.execute(alipayTradeRefundRequest);
            if(alipayTradeRefundResponse.isSuccess()){
                form = alipayTradeRefundResponse.getBody();
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
        form = doRefund();
        System.out.println(form);
    }
}
