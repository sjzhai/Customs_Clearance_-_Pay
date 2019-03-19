public class OrderInfo {

    // 商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复
    public static String out_trade_no = "20150320010101007";
    public static String out_request_no = out_trade_no;
    // 支付场景
    // 条码支付，取值：bar_code
    public static String scene_bar = "bar_code";
    // 支付场景
    // 声波支付，取值：wave_code
    public static String scene_wave = "wave_code";
    // 支付宝交易号
    public static String trade_no = "2019012922001408781012112548";
    // 支付授权码，25~30开头的长度为16~24位的数字，实际字符串长度以开发者获取的付款码长度为准
    public static String auth_code = "28763443825664394";
    // 销售产品码 FACE_TO_FACE_PAYMENT (可选)
    public static String product_code = "FAST_INSTANT_TRADE_PAY";
    // 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
    public static String total_amount = "0.05";
    // 订单标题
    public static String subject = "保健品（测试）";
    // 订单描述
    public static String body = "保健品（测试）";
    // 银行间联模式下有用，其它场景请不要使用；
    // 双联通过该参数指定需要查询的交易所属收单机构的pid;
    public static String org_pid = "2088031383495176";
    // 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
    public static String refund_amount = "0.01";
    // 订单退款币种信息 USD
    public static String refund_currency = "CNY";
    // 退款的原因说明
    public static String refund_reason = "正常退款";
}
