import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

public class OrderToXML {

    /*
     * 将json数据转换成xml报文
     * @param obj 对象类型json数据
     */
    public static void orderJson2XML(Object obj){
        String orderGoods = null;
        JSONObject jsonObject = JSONObject.fromObject(obj);
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
        Element order = root.addElement(new QName("Order", namespace));
        Element orderHead = order.addElement(new QName("OrderHead", namespace));
        Element guid = orderHead.addElement(new QName("guid", namespace));
        guid.setText(uuid);

        //添加订单信息
        String key, val;
        Iterator it = jsonObject.keys();
        Element appType = orderHead.addElement(new QName("appType", namespace));
        appType.setText("1");
        Element appTime = orderHead.addElement(new QName("appTime", namespace));
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        appTime.setText(df.format(new Date()));
        Element appStatus = orderHead.addElement(new QName("appStatus", namespace));
        appStatus.setText("2"); // 2-申报
        Element orderType = orderHead.addElement(new QName("orderType", namespace));
        orderType.setText("I");
        Element orderNo = orderHead.addElement(new QName("orderNo", namespace));
        Element ebpCode = orderHead.addElement(new QName("ebpCode", namespace));
        ebpCode.setText("4444441112"); // 固定值
        Element ebpName = orderHead.addElement(new QName("ebpName", namespace));
        ebpName.setText("北京xxxxxxxxx公司"); // 固定值
        Element ebcCode = orderHead.addElement(new QName("ebcCode", namespace));
        ebcCode.setText("1111969944"); // 固定值
        Element ebcName = orderHead.addElement(new QName("ebcName", namespace));
        ebcName.setText("北京xxxxxxxxx公司"); // 固定值
        Element goodsValue = orderHead.addElement(new QName("goodsValue", namespace));
        Element freight = orderHead.addElement(new QName("freight", namespace));
        Element discount = orderHead.addElement(new QName("discount", namespace));
        discount.setText("0"); // 固定值
        Element taxTotal = orderHead.addElement(new QName("taxTotal", namespace));
        Element acturalPaid = orderHead.addElement(new QName("acturalPaid", namespace));
        Element currency = orderHead.addElement(new QName("currency", namespace));
        currency.setText("142");
        Element buyerRegNo = orderHead.addElement(new QName("buyerRegNo", namespace));
        Element buyerName = orderHead.addElement(new QName("buyerName", namespace));
        Element buyerTelephone = orderHead.addElement(new QName("buyerTelephone", namespace));
        Element buyerIdType = orderHead.addElement(new QName("buyerIdType", namespace));
        buyerIdType.setText("1");
        Element buyerIdNumber = orderHead.addElement(new QName("buyerIdNumber", namespace));
        Element payCode = orderHead.addElement(new QName("payCode", namespace));
        payCode.setText("4444441112");
        Element payName = orderHead.addElement(new QName("payName", namespace));
        payName.setText("xxxxxxxx有限公司");
        Element payTransactionId = orderHead.addElement(new QName("payTransactionId", namespace));
        Element batchNumbers = orderHead.addElement(new QName("batchNumbers", namespace));
        batchNumbers.setText("");
        Element consignee = orderHead.addElement(new QName("consignee", namespace));
        Element consigneeTelephone = orderHead.addElement(new QName("consigneeTelephone", namespace));
        Element consigneeAddress = orderHead.addElement(new QName("consigneeAddress", namespace));
        Element consigneeDistrict = orderHead.addElement(new QName("consigneeDistrict", namespace));
        Element note = orderHead.addElement(new QName("note", namespace));
        note.setText("");

        if (it.hasNext()) {
            do {
                key = (String) it.next();
                val = jsonObject.getString(key);
                switch (key){
                    case "order_code":
                        orderNo.setText(val);break;
                    case "order_price":
                        goodsValue.setText(val);break;
                    case "express_price":
                        freight.setText(val);break;
                    case "total_tax":
                        taxTotal.setText(val);break;
                    case "pay_money":
                        acturalPaid.setText(val);break;
                    case "customer_id":
                        buyerRegNo.setText(val);break;
                    case "info_realname":
                        buyerName.setText(val);break;
                    case "shipping_mobile":
                        buyerTelephone.setText(val);
                        consigneeTelephone.setText(val);break;
                    case "info_cardid":
                        buyerIdNumber.setText(val);break;
                    case "trade_no":
                        payTransactionId.setText(val);break;
                    case "shipping_person":
                        consignee.setText(val);break;
                    case "shipping_address":
                        consigneeAddress.setText(val);break;
                    case "shipping_county_code":
                        consigneeDistrict.setText(val);break;
                    case "order_goods":
                        orderGoods = val;break;
                }
            } while (it.hasNext());
        }
        //报文传输企业信息
        Element baseTransfer = root.addElement(new QName("BaseTransfer", namespace));
        Element copCode = baseTransfer.addElement(new QName("copCode", namespace));
        copCode.setText("1111111111");
        Element copName = baseTransfer.addElement(new QName("copName", namespace));
        copName.setText("北京xxxxxxxx公司");
        Element dxpMode = baseTransfer.addElement(new QName("dxpMode", namespace));
        dxpMode.setText("DXP");
        Element dxpId = baseTransfer.addElement(new QName("dxpId", namespace));
        dxpId.setText("DXPIDishere0001");
        Element noteBT = baseTransfer.addElement(new QName("note", namespace));
        noteBT.setText("");

        //order_goods，jsonstr格式转换
        int num = 1;// 订单数量递增
        JSONArray jsonArray = JSONArray.fromObject(orderGoods);
        int size = jsonArray.size();
        if (size > 0) {
            for (Object arrayStr : jsonArray) {
                if (arrayStr != null) {
                    Element orderList = order.addElement(new QName("OrderList", namespace));
                    JSONObject jsonOrderList = JSONObject.fromObject(arrayStr);
                    String keyGood, valGood;
                    Iterator itGoods = jsonOrderList.keys();

                    Element gnum = orderList.addElement(new QName("gnum", namespace));
                    gnum.setText(String.valueOf(num));
                    Element itemNo = orderList.addElement(new QName("itemNo", namespace));
                    Element itemName = orderList.addElement(new QName("itemName", namespace));
                    Element gmodel = orderList.addElement(new QName("gmodel", namespace));
                    Element itemDescribe = orderList.addElement(new QName("itemDescribe", namespace));
                    Element barCode = orderList.addElement(new QName("barCode", namespace));
                    Element unit = orderList.addElement(new QName("unit", namespace));
                    Element qty = orderList.addElement(new QName("qty", namespace));
                    Element price = orderList.addElement(new QName("price", namespace));
                    Element totalPrice = orderList.addElement(new QName("totalPrice", namespace));
                    Element goodCurrency = orderList.addElement(new QName("currency", namespace));
                    goodCurrency.setText("142");
                    Element country = orderList.addElement(new QName("country", namespace));
                    Element goodNote = orderList.addElement(new QName("note", namespace));
                    goodNote.setText("");
                    while (itGoods.hasNext()) {
                        keyGood = (String) itGoods.next();
                        valGood = jsonOrderList.getString(keyGood);
                        switch(keyGood){
                            case "goods_info_id":
                                itemNo.setText(valGood);break;
                            case "goods_info_name":
                                itemName.setText(valGood);
                                itemDescribe.setText(valGood);break;
                            case "gmodel":
                                gmodel.setText(valGood);break;
                            case "goods_info_barcode":
                                barCode.setText(valGood);break;
                            case "unit":
                                UnitCodeConversion ucc = new UnitCodeConversion();
                                unit.setText(ucc.text2UnitCode(valGood));break;
                            case"goods_info_num":
                                qty.setText(valGood);break;
                            case "goods_info_price":
                                price.setText(valGood);break;
                            case "goods_info_sum_price":
                                totalPrice.setText(valGood);break;
                            case "country":
                                country.setText(valGood);break;
                        }
                    }
                }
                num++;
            }
        }

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        format.setIndent(true);
        format.setIndent("  ");
        format.setNewLineAfterDeclaration(false);
        format.setNewlines(true);
        File file = new File("CEB311Message.xml");

        try{
            XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(file), format);
            xmlWriter.setEscapeText(false);
            xmlWriter.write(doc);
            xmlWriter.close();
            System.out.println("生成xml成功");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    /*
//     * 生成XML文件
//     */
//    public static void createXMLFile(String xml){
//        OutputFormat format = OutputFormat.createPrettyPrint();
//        // 设置编码格式
//        format.setEncoding("UTF-8");
//        File file = new File("CEB311Message.xml");
//        XMLWriter writer = null;
//        try {
//            writer = new XMLWriter(new FileOutputStream(file), format);
//            // 设置是否转义，默认使用转义字符
//            writer.setEscapeText(false);
//            writer.write(xml);
//            writer.close();
//            System.out.println("生成rss.xml成功");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
}
