/*
  Copyright (C) 2019 YQH
  All rights reserved

  Name: CustomsBJService
  description :
    Push orders in generated XML file to General Beijing Custom Clearance.
    A sample saved for review in later time.

  created by SJ_Zhai at  02/03/2019 15:27:34
*/
import org.apache.xpath.operations.Bool;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomsBJService {
    // private static final Logger L = Logger.getLogger(CustomsZzService.class);
    public static int ERR_CODE_DATA = 1001;
    public static int ERR_CODE_NETWORK = 1002;
    public static int ERR_CODE_PARSER_BACKSTR = 1003;

    private static String mach_nb = "beijing";

    private static String CustomsCode = ""; //电商企业代码；发送方备案编号
    private static String OrgName = "";//电商企业名称这
    private static String pay_code = "";//支付公司编码（财付通编码）
    private static String pay_name = "";
    private static String DXPID = "";

    /**
      订单推送
      @param order_id order number need to push
      @return null  Just return push status which is whether success or fail
     */
    public static Record pushOrderCreate(String order_id) throws Exception {
        Record order = GlobalLogics.getOrderLogic().getOrderSingleBase(order_id);
        String order_code = order.getString("order_code");
        Record customer_info = GlobalLogics.getCustomerLogic().getCustomerInfo(order.getString("customer_id"));
        Record address = GlobalLogics.getAddressLogic().getCustomerSingleAddress(order.getString("shopping_addr_id"));
        RecordSet order_goods = GlobalLogics.getOrderLogic().getOrderGoodsAll(order_id);
        RecordSet specs = GlobalLogics.getOrderLogic().getOrderGoodsAllSpec(order_goods);//得到商品规格
        float total = order.getFloat0("order_price")+order.getFloat0("total_tax")+order.getFloat0("express_price");

        Document doc;
        String uuid = UUID.randomUUID().toString().toUpperCase();

        Element root = DocumentHelper.createElement("ceb:CEB311Message");
        doc = DocumentHelper.createDocument(root);
        doc.setRootElement(root);

        root.addAttribute("guid", uuid);
        root.addAttribute("version", "1.0");
        Namespace namespace = new Namespace("ceb", "http://www.chinaport.gov.cn/ceb");
        root.addNamespace("ceb", "http://www.chinaport.gov.cn/ceb");
        root.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        Element orderxml = root.addElement(new QName("Order", namespace));
        Element orderHead = orderxml.addElement(new QName("OrderHead", namespace));
        Element guid = orderHead.addElement(new QName("guid", namespace));
        guid.setText(uuid);//是 企业系统生成36位唯一序号 (英文字母大写)

        //添加订单信息
        String key, val;
        Element appType = orderHead.addElement(new QName("appType", namespace));
        appType.setText("1");//是 企业报送类型。1-新增 2-变 更 3-删除。默认为1

        Element appTime = orderHead.addElement(new QName("appTime", namespace));
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        appTime.setText(df.format(new Date()));//是 企业报送时间。格 式:YYYYMMDDhhmmss

        Element appStatus = orderHead.addElement(new QName("appStatus", namespace));
        appStatus.setText("2"); //是 业务状态:1-暂存,2-申报,默认为2

        Element orderType = orderHead.addElement(new QName("orderType", namespace));
        orderType.setText("I");//是 电子订单类型:I进口

        Element orderNo = orderHead.addElement(new QName("orderNo", namespace));
        orderNo.setText(order_code);//是 交易平台的订单编号，同一交 易平台的订单编号应唯一。订 单编号长度不能超过60位

        Element ebpCode = orderHead.addElement(new QName("ebpCode", namespace));
        ebpCode.setText(CustomsCode); //是 电商平台的海关注册登记编号

        Element ebpName = orderHead.addElement(new QName("ebpName", namespace));
        ebpName.setText(OrgName); //是 电商平台的海关注册登记名称

        Element ebcCode = orderHead.addElement(new QName("ebcCode", namespace));
        ebcCode.setText(CustomsCode); //是 电商企业的海关注册登记编号

        Element ebcName = orderHead.addElement(new QName("ebcName", namespace));
        ebcName.setText(OrgName); //是 电商企业的海关注册登记名称

        Element goodsValue = orderHead.addElement(new QName("goodsValue", namespace));
        goodsValue.setText(String.valueOf(order.getFloat0("order_price")));//是 商品实际成交价，含非现金抵扣金额

        Element freight = orderHead.addElement(new QName("freight", namespace));
        if(String.valueOf(order.getFloat0("express_price")).equals(null)){
            freight.setText("0");
        }else{
            freight.setText(String.valueOf(order.getFloat0("express_price")));//是 不包含在商品价格中的运杂 费，无则填写"0"
        }

        Element discount = orderHead.addElement(new QName("discount", namespace));
        discount.setText("0");//是 使用积分、虚拟货币、代金券 等非现金支付金额，无则填写 "0"

        Element taxTotal = orderHead.addElement(new QName("taxTotal", namespace));
        taxTotal.setText(String.valueOf(order.getFloat0("total_tax")));//是 企业预先代扣的税款金额，无 则填写“0”

        Element acturalPaid = orderHead.addElement(new QName("acturalPaid", namespace));
        acturalPaid.setText(String.valueOf(total));//是 商品价格+运杂费+代扣税款- 非现金抵扣金额，与支付凭证 的支付金额一致

        Element currency = orderHead.addElement(new QName("currency", namespace));
        currency.setText("142");//是 限定为人民币，填写“142”

        Element buyerRegNo = orderHead.addElement(new QName("buyerRegNo", namespace));
        buyerRegNo.setText(customer_info.getString("customer_id"));//是 订购人的交易平台注册号

        Element buyerName = orderHead.addElement(new QName("buyerName", namespace));
        buyerName.setText(customer_info.getString("info_realname"));//是 订购人的真实姓名

        Element buyerTelephone = orderHead.addElement(new QName("buyerTelephone", namespace));
        buyerTelephone.setText(customer_info.getString("info_mobile"));//是 海关监管对象的电话，要求实 际联系电话

        Element buyerIdType = orderHead.addElement(new QName("buyerIdType", namespace));
        buyerIdType.setText("1");//是 1-身份证,2-其它。限定为身 份证，填写“1”

        Element buyerIdNumber = orderHead.addElement(new QName("buyerIdNumber", namespace));
        buyerIdNumber.setText(customer_info.getString("info_cardid"));//是 订购人的身份证件号码

        Element payCode = orderHead.addElement(new QName("payCode", namespace));
        payCode.setText(pay_code);//否 支付企业的海关注册登记编号

        Element payName = orderHead.addElement(new QName("payName", namespace));
        payName.setText(pay_name);//否 支付企业在海关注册登记的企业名称

        Element payTransactionId = orderHead.addElement(new QName("payTransactionId", namespace));
        payTransactionId.setText("");//否 支付企业唯一的支付流水号

        Element batchNumbers = orderHead.addElement(new QName("batchNumbers", namespace));
        batchNumbers.setText("");//否 商品批次号

        Element consignee = orderHead.addElement(new QName("consignee", namespace));
        consignee.setText(address.getString("address_name"));//是 收货人姓名，必须与电子运单 的收货人姓名一致

        Element consigneeTelephone = orderHead.addElement(new QName("consigneeTelephone", namespace));
        consigneeTelephone.setText(address.getString("address_moblie"));//是 收货人联系电话，必须与电子 运单的收货人电话一致

        Element consigneeAddress = orderHead.addElement(new QName("consigneeAddress", namespace));
        consigneeAddress.setText(address.getString("city_name") + address.getString("province_name") + address.getString("address_detail"));//是 收货地址

        Element consigneeDistrict = orderHead.addElement(new QName("consigneeDistrict", namespace));
        consigneeDistrict.setText(address.getString("address_zip"));//否 参照国家统计局公布的国家 行政区划标准填制

        Element note = orderHead.addElement(new QName("note", namespace));
        note.setText("");

        int num = 1;
        for(Record g : order_goods){
            Element orderList = orderxml.addElement(new QName("OrderList", namespace));
            Element gnum = orderList.addElement(new QName("gnum", namespace));
            gnum.setText(String.valueOf(num));

            Element itemNo = orderList.addElement(new QName("itemNo", namespace));
            itemNo.setText(g.getString("goods_info_id"));//否 电商企业自定义的商品货号 (SKU)

            Element itemName = orderList.addElement(new QName("itemName", namespace));
            itemName.setText(g.getString("goods_info_name"));//是 交易平台销售商品的中文名 称

            Record spec_single = specs.findEq("goods_info_id",g.getString("goods_info_id"));
            Element gmodel = orderList.addElement(new QName("gmodel", namespace));
            gmodel.setText(spec_single.getString("spec_value_remark"));//是 满足海关归类、审价以及监管 的要求为准。包括:品名、牌 名、规格、型号、成份、含量、等级等

            Element itemDescribe = orderList.addElement(new QName("itemDescribe", namespace));
            itemDescribe.setText(g.getString("goods_info_subtitle"));//否 交易平台销售商品的描述信息

            Element barCode = orderList.addElement(new QName("barCode", namespace));
            barCode.setText(g.getString("goods_info_barcode"));//否 国际通用的商品条形码，一般由前缀部分、制造厂商代码、商品代码和校验码组成

            Element unit = orderList.addElement(new QName("unit", namespace));
            unit.setText(text2UnitCode(g.getString("unit")));//是 填写海关标准的参数代码，参照《JGS-20 海关业务代码集》 - 计量单位代码

            Element qty = orderList.addElement(new QName("qty", namespace));
            qty.setText(g.getString("goods_info_num"));//是 商品实际数量

            Element price = orderList.addElement(new QName("price", namespace));
            price.setText(String.valueOf(g.getFloat0("goods_info_price")));//是 商品单价

            Element totalPrice = orderList.addElement(new QName("totalPrice", namespace));
            totalPrice.setText(String.valueOf(g.getFloat0("goods_info_sum_price")));//是 商品总价

            Element goodCurrency = orderList.addElement(new QName("currency", namespace));
            goodCurrency.setText("142");//是 限定为人民币，填写“142”

            Element country = orderList.addElement(new QName("country", namespace));
            country.setText(g.getString("country"));//是 填写海关标准的参数代码，参 照《JGS-20 海关业务代码集》 -国家(地区)代码表

            Element goodNote = orderList.addElement(new QName("note", namespace));
            goodNote.setText("");//否 促销活动，商品单价偏离市场价格的，可以在此说明
            num++;
        }


        //报文传输企业信息
        Element baseTransfer = root.addElement(new QName("BaseTransfer", namespace));
        Element copCode = baseTransfer.addElement(new QName("copCode", namespace));
        copCode.setText(CustomsCode);//是 报文传输的企业代码
        Element copName = baseTransfer.addElement(new QName("copName", namespace));
        copName.setText(OrgName);//是 报文传输的企业名称
        Element dxpMode = baseTransfer.addElement(new QName("dxpMode", namespace));
        dxpMode.setText("DXP");//是 默认为DXP;指中国电子口岸数据交换平台
        Element dxpId = baseTransfer.addElement(new QName("dxpId", namespace));
        dxpId.setText(DXPID);//是 向中国电子口岸数据中心申请数据交换平台 的用户编号
        Element noteBT = baseTransfer.addElement(new QName("note", namespace));
        noteBT.setText("");

//        String xml = "";
//        xml = String.valueOf(doc.asXML());

        Record back = new Record();//储存记录信息
        if(sendXML(doc)){
            if(receiveXML()){

                GlobalLogics.getOrderLogic().updateOrderPushCustomsStatus(order_id,2,"推送成功");
            }else{
                GlobalLogics.getOrderLogic().updateOrderPushCustomsStatus(order_id,3,"推送失败");
            }
        }else{
            System.out.println("报文生成失败...");
        }



        return back;
    }

    /**
     * 把报文生成并保存到本地指定路径
     * @param doc 文档Document格式的xml
     * @return null
     */
    public static Boolean sendXML(Document doc){
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        format.setIndent(true);
        format.setIndent("  ");
        format.setNewLineAfterDeclaration(false);
        format.setNewlines(true);

        String FILE_NAME = "CEB311Message.xml";//要创建的文件名
        String FILE_PATH = "/Users/shengjiezhai/Desktop/Send/";//文件指定存放的路径
        StringBuilder name = new StringBuilder(FILE_NAME);
        String finalname = name.insert(13, "_"+DateUtils.now()).toString();
        File file = new File(FILE_PATH+finalname);

        try{
            file.createNewFile();
            System.out.printf("文件生成成功！已保存在此目录下：%s\n", FILE_PATH);
            System.out.printf("文件名称：%s\n", finalname);

            XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(file), format);
            xmlWriter.setEscapeText(false);
            xmlWriter.write(doc);
            xmlWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取收到的异步回执报文并解析
     * @param doc 文档Document格式的xml
     * @return null
     */
    public static Boolean receiveXML(Document doc){

    }

    /**
     * 中文转换计量单位代码
     * @param text 计量单位（中文）
     * @return String 转换后的计量单位代码(xxx)
     */
    public static String text2UnitCode(String text){
        List<String> symbol = Arrays.asList("台", "座", "辆", "艘", "架", "套", "个", "只", "头", "张", "件", "支", "枝", "根", "条", "把", "块", "卷", "副", "片",
                "组", "份", "幅", "双", "对", "棵", "株", "井", "米", "盘", "平方米", "立方木", "筒", "千克", "克", "盆", "万个", "具", "百副",
                "百支", "百把", "百个", "百片", "刀", "疋", "公担", "扇", "百枝", "千只", "千块", "千盒", "千枝", "千个", "亿支", "亿个", "万套", "千张", "万张", "千伏安",
                "千瓦", "千瓦时", "千升", "英尺", "吨", "长吨", "短吨", "司马旦", "司马斤", "斤", "磅", "担", "英担", "短担", "两", "市担", "盎司", "克拉", "市尺", "码",
                "英寸", "寸", "升", "毫升", "英加仑", "美加仑", "立方英尺", "立方尺", "平方码", "平方英尺", "平方尺", "英制马力", "公制马力", "令", "箱", "批", "罐", "桶", "扎", "包",
                "箩", "打", "筐", "罗", "匹", "册", "本", "发", "枚", "捆", "袋", "粒", "盒", "合", "瓶", "千支", "万双", "万粒", "千粒", "千米", "千英尺", "百万贝可", "部");
        HashMap<String, String> unitCode = new HashMap<String, String>();
        String val = "";
        int n = 1;
        for(String key: symbol) {
            val = String.valueOf(n);
            if(val.length() == 1){ val = "00"+val; }
            else if(val.length() == 2){ val = "0"+val; }
            unitCode.put(key, val);
            if (n == 23){ n += 2; }
            else if (n == 63){ n += 4; }
            else if (n == 67){ n += 3; }
            else if (n == 81){ n += 2; }
            else if (n == 86){ n += 2; }
            else if (n == 89){ n += 6; }
            else if (n == 99){ n += 2; }
            else if (n == 101){ n += 9; }
            else if (n == 112){ n += 3; }
            else if (n == 116){ n += 2; }
            else if (n == 118){ n += 2; }
            else if (n == 136){ n += 3; }
            else if (n == 149){ n += 14; }
            else{ n++; }
        }
        return unitCode.get(text.trim());
    }

}
