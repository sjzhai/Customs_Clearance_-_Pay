
import java.util.*;

public class UnitCodeConversion {
    /*
     * 中文转换计量单位代码
     * @param text 计量单位（中文）
     */
    public static String text2UnitCode(String text){

        List<String> symbol = Arrays.asList("台", "座", "辆", "艘", "架", "套", "个", "只", "头", "张", "件", "支", "枝", "根", "条", "把", "块", "卷", "副", "片",
                "组", "份", "幅", "双", "对", "棵", "株", "井", "米", "盘", "平方米", "立方木", "筒", "千克", "克", "盆", "万个", "具", "百副",
                "百支", "百把", "百个", "百片", "刀", "疋", "公担", "扇", "百枝", "千只", "千块", "千盒", "千枝", "千个", "亿支", "亿个", "万套", "千张", "万张", "千伏安",
                "千瓦", "千瓦时", "千升", "英尺", "吨", "长吨", "短吨", "司马旦", "司马斤", "斤", "磅", "担", "英担", "短担", "两", "市担", "盎司", "克拉", "市尺", "码",
                "英寸", "寸", "升", "毫升", "英加仑", "美加仑", "立方英尺", "立方尺", "平方码", "平方英尺", "平方尺", "英制马力", "公制马力", "令", "箱", "批", "罐", "桶", "扎", "包",
                "箩", "打", "筐", "罗", "匹", "册", "本", "发", "枚", "捆", "袋", "粒", "盒", "合", "瓶", "千支", "万双", "万粒", "千粒", "千米", "千英尺", "百万贝可", "部");
        final int LENGTH = 147;
        HashMap<String, String> unitCode = new HashMap<String, String>();
        String val = null;
        int n = 1;
        for(String key: symbol) {
            val = String.valueOf(n);
            if(val.length() == 1){
                val = "00"+val;
            }else if(val.length() == 2){
                val = "0"+val;
            }
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
        text = text.trim();
        String v = unitCode.get(text);
//        Iterator<String> it = unitCode.keySet().iterator();
//        while(it.hasNext()){
//            String key = it.next();
//            String v = unitCode.get(key);
//            System.out.printf("%s : %s\n", key, v);
//        }
        return v;
    }
}
