package CustomsHZ;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Base64;

public class TestSecretXML {
    private static String AES_SECRETE_KEY = "AESAESAES==";
    // 商户私钥
    public static String APP_PRIVATE_KEY = "";
    // 商户公钥
    public static String APP_PUBLIC_KEY = "";
        
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
    public static void main(String[] args) throws Exception {
        String xmlString = "";

        xmlString += "<mo version=\"1.0.0\">";
        xmlString += "    <head>";
        xmlString += "        <businessType>IMPORTORDER</businessType>";
        xmlString += "    </head>";
        xmlString += "    <body>";
        xmlString += "        <orderInfoList>";
        xmlString += "	          <orderInfo>";
        xmlString += "		          <jkfSign>";
        xmlString += "			          <companyCode></companyCode>";//VARCHAR2(20) 是 发送方备案编号
        xmlString += "			          <businessNo></businessNo>";//VARCHAR2(100) 是 业务编号
        xmlString += "			          <businessType>IMPORTORDER</businessType>";//VARCHAR2(30) 是 业务类型
        xmlString += "			          <declareType>1</declareType>";//CHAR(1) 是 企业报送类型。1-新增 2-变更 3-删除。默认为1
        xmlString += "                    <cebFlag>01</cebFlag>";//填写或01表示杭州版报文， 02 表示企业自行生成总署报文， 03委托电子口岸生成总署报文
        xmlString += "			          <note></note>"; //NVARCHAR2(256) 备注
        xmlString += "		          </jkfSign>";
        xmlString += "		          <jkfOrderImportHead>";
        xmlString += "			          <eCommerceCode></eCommerceCode>";//VARCHAR2(60) 是 电商企业编号
        xmlString += "			          <eCommerceName></eCommerceName>";//VARCHAR2(200) 是 电商企业名称
        xmlString += "			          <ieFlag>I</ieFlag>";//CHAR(1) 是 进出口标志，I:进口 E:出口
        xmlString += "			          <payType>01</payType>";//VARCHAR2(60) 是 支付类型 01:银行卡支付 02:余额支付 03:其他
        xmlString += "			          <payCompanyCode></payCompanyCode>";//VARCHAR2(50) 是 支付公司编码
        xmlString += "			          <payNumber></payNumber>";//VARCHAR2(60) 是 支付单号
        xmlString += "			          <orderTotalAmount>108.0</orderTotalAmount>";//NUMBER(12,4) 是 订单总金额 货款+订单税款+运费+保费
        xmlString += "			          <orderNo></orderNo>";//VARCHAR2(60) 是 订单编号
        xmlString += "			          <orderTaxAmount>0.0</orderTaxAmount>";//NUMBER(12,4) 是 订单税款，按缴税新政计算填写
        xmlString += "			          <orderGoodsAmount>108.0</orderGoodsAmount>";//NUMBER(12,4) 是 订单货款
        xmlString += "			          <feeAmount></feeAmount>";//NUMBER(12,4) 否 运费，免邮填写0
        xmlString += "                    <insureAmount>0</insureAmount>";//NUMBER(12,4) 是 保费 商家向用户征收的保价费用，无保费可填写0
        xmlString += "			          <companyName></companyName>";//VARCHAR2(200) 是 企业备案名称
        xmlString += "			          <companyCode></companyCode>";//VARCHAR(20) 是 企业备案编码
        xmlString += "			          <tradeTime>2018-12-13 16:07:06</tradeTime>";//VARCHAR2(25) 是 成交时间 格式：YYYY-MM-DD hh:mm:ss
        xmlString += "		              <currCode>142</currCode>";//VARCHAR2(3) 是 成交币制 见参数表
        xmlString += "                    <totalAmount>108.0</totalAmount>";//NUMBER(14,4) 是 成交总价，与订单货款一致
        xmlString += "			          <consigneeEmail></consigneeEmail>";//VARCHAR2(60) 否 收件人邮箱
        xmlString += "			          <consigneeTel></consigneeTel>";//VARCHAR2(60) 是 收件人联系方式
        xmlString += "			          <consignee></consignee>";//NVARCHAR2(60) 是 收件人姓名
        xmlString += "			          <consigneeAddress></consigneeAddress>";//VARCHAR2(255) 是 收件人地址
        xmlString += "			          <totalCount>2</totalCount>";//NUMBER 是 总件数
        xmlString += "			          <postMode></postMode>";//VARCHAR2(20) 否 发货方式（物流方式）
        xmlString += "			          <senderCountry></senderCountry>";//VARCHAR2(3) 是 发件人国别
        xmlString += "			          <senderName></senderName>";//VARCHAR2(200) 是 发件人名称
        xmlString += "			          <purchaserId></purchaserId>";//VARCHAR2(100) 是 购买人在电商平台的注册ID
        xmlString += "			          <logisCompanyName>EMS</logisCompanyName>";//VARCHAR2(200) 是 物流企业备案名称
        xmlString += "			          <logisCompanyCode></logisCompanyCode>";//VARCHAR2(200) 是 物流企业备案编号
        xmlString += "			          <zipCode></zipCode>";//VARCHAR2(20) 否 邮编
        xmlString += "			          <note></note>";//VARCHAR2(255) 否 备注信息
        xmlString += "			          <wayBills></wayBills>";//VARCHAR2(255) 否 运单号列表,单号之间分号隔开
        xmlString += "                    <rate>1</rate>";//VARCHAR2(10) 否 人民币统一填写1
        xmlString += "                    <discount>0</discount>";//NUMBER(12,4) 是 非现金抵扣金额，使用积分、虚拟货币、代金券等非现金支付金额，无则填写"0"

        xmlString += "                    <batchNumbers></batchNumbers>";//VARCHAR(100) 否 商品批次号
        xmlString += "                    <consigneeDitrict></consigneeDitrict>";//VARCHAR(6) 否 收货地址行政区划代码
        xmlString += "                    <userProcotol>本人承诺所购买商品系个人合理自用，现委托商家代理申报、代缴税款等通关事宜，本人保证遵守《海关法》和国家相关法律法规，保证所提供的身份信息和收货信息真实完整，无侵犯他人权益的行为，以上委托关系系如实填写，本人愿意接受海关、检验检疫机构及其他监管部门的监管，并承担相应法律责任。</userProcotol>";
        xmlString += "                </jkfOrderImportHead>";
        xmlString += "		          <jkfOrderDetailList>";
//        for (Record g : order_goods) {
        xmlString += "			          <jkfOrderDetail>";
        xmlString += "			  	          <goodsOrder></goodsOrder>";//INTEGER 是 商品序号
        xmlString += "				          <goodsName> </goodsName>";//NVARCHAR2(255) 是 物品名称
        xmlString += "				          <goodsModel></goodsModel>";//NVARCHAR2(255) 否 商品规格、型号
        xmlString += "				          <codeTs></codeTs>";//VARCHAR2(10) 是 商品HS编码
        xmlString += "				          <grossWeight></grossWeight>";//NUMBER(15,4) 否 商品毛重
        xmlString += "				          <unitPrice>108.0</unitPrice>";//NUMBER(15,4) 是 成交单价，各商品成交单价*成交数量总和应等于表头的货款、成交总价
        xmlString += "				          <goodsUnit>盒</goodsUnit>";//VARCHAR2(3) 是 成交计量单位， 见参数表
        xmlString += "				          <goodsCount>1</goodsCount>";//NUMBER(15,4) 是 成交数量（申报数量）
        xmlString += "				          <originCountry>116</originCountry>";//VARCHAR2(5) 是 产销国，见参数表
        xmlString += "                        <barCode></barCode>";//VARCHAR2(50) 否 条形码
        xmlString += "				          <currency>142</currency>";//VARCHAR(3) 是 限制人民币，填写"142"
        xmlString += "				          <note></note>";//VARCHAR2(1000) 否 备注
        xmlString += "			          </jkfOrderDetail>";
//        }
        xmlString += "		          </jkfOrderDetailList>";
        xmlString += "		          <jkfGoodsPurchaser>";
        xmlString += "			          <id></id>";//VARCHAR2(100) 是 购买人ID
        xmlString += "			          <name></name>";//NVARCHAR2(100) 是 购买人名称
        xmlString += "			          <email></email>";//VARCHAR2(140) 否 注册邮箱（购买人邮箱）
        xmlString += "			          <telNumber></telNumber>";//VARCHAR2(30) 是 联系电话
        xmlString += "			          <address></address>";//NVARCHAR2(100) 否 地址
        xmlString += "		  	          <paperType>01</paperType>";//VARCHAR2(20) 是 证件号码，01:身份证（试点期间只能是身份证）02:护照 03:其他
        xmlString += "		  	          <paperNumber></paperNumber>";//VARCHAR2(100) 是 证件号码
        xmlString += "		          </jkfGoodsPurchaser>";
        xmlString += "            </orderInfo>";
        xmlString += "        </orderInfoList>";
        xmlString += "    </body>";
        xmlString += "</mo>";

        String xml = formatXML(xmlString);
//        //AES加密
        byte[] b_xml = xml.getBytes();
        byte[] b_aes = AES_SECRETE_KEY.getBytes();
        byte[] encrypt_secretXML = AESUtil.encrypt(b_xml, b_aes);
        //Base64编码（encodeBase64String）
        String encoded = Base64.getEncoder().encodeToString(encrypt_secretXML);
        System.out.println(encoded);

        //RSA加签
        //初始化密钥对儿
//        Map<String, Object> mapKey = RSAUtil.initKey();
//        byte[] publicKey = RSAUtil.getPublicKey(mapKey);
//        byte[] privateKey = RSAUtil.getPrivateKey(mapKey);
//
//        String encoded = Base64.getEncoder().encodeToString(publicKey);
//        String encoded2 = Base64.getEncoder().encodeToString(privateKey);
//        System.out.println("public " + encoded);
//        System.out.println("private " + encoded2);

        //解密和验签

    }
}
