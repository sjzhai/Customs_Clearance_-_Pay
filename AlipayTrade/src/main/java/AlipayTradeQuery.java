import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.OrderRefundInfo;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;

public class AlipayTradeQuery {

    /*
     * 支付宝支付订单的查询接口
     * return 返回的订单查询信息
     */
    public static String getQuery(){

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfigs.SERVER_URL,
                AlipayConfigs.APP_ID, AlipayConfigs.APP_PRIVATE_KEY, AlipayConfigs.FORMAT,
                AlipayConfigs.CHARSET_UTF, AlipayConfigs.ALIPAY_PUBLIC_KEY, AlipayConfigs.SIGN_TYPE);

        //初始化API对应的request
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();

        ////业务参数集合模块
        AlipayTradeQueryModel alipayTradeQueryModel = new AlipayTradeQueryModel();
        alipayTradeQueryModel.setOutTradeNo(OrderInfo.out_trade_no);
        alipayTradeQueryModel.setTradeNo(OrderInfo.trade_no);
        alipayTradeQueryModel.setOrgPid(OrderInfo.org_pid);

        AlipayTradeQueryResponse response = null;
        request.setBizModel(alipayTradeQueryModel);
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
