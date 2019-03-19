package CustomsHZ;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import javax.xml.namespace.QName;
import java.rmi.RemoteException;

public class AxisTest {

    private static String url = "http://xxx";
    private static String method = "getSjPartner";

//    public static void testAxis() throws ServiceException, MalformedURLException, RemoteException {
//        try {
//            Service service = new Service();
//            Call call = (Call) service.createCall();
//            call.setTargetEndpointAddress(url);
//
////            call.setOperationName(method);
//            call.setOperationName(new QName("http://webservice.fwms.com/", method));
//            call.addParameter("SJ_ID", XMLType.XSD_STRING,
//
//                    javax.xml.rpc.ParameterMode.IN);
//
//            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
//
//            call.setUseSOAPAction(true);
//
//            call.setSOAPActionURI("http://webservice.fwms.com/OrderServiceImpl/getSjPartnerRequest");
//
//            String str = "3204993994922383998";
//            String result = (String)call.invoke(new Object[] {str});//"3204993994922383998"
//            System.out.println(result);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public static void axis2Test() throws AxisFault {
        try {
            // 使用RPC方式调用WebService
            RPCServiceClient serviceClient = new RPCServiceClient();
            // 指定调用WebService的URL
            EndpointReference targetEPR = new EndpointReference(url);
            Options options = serviceClient.getOptions();
            // 确定目标服务地址
            options.setTo(targetEPR);
            // 确定调用方法（wsdl 命名空间地址 (wsdl文档中的targetNamespace) 和 方法名称 的组合）
            options.setAction("http://xxx");
            // 指定方法的参数值
            Object[] parameters = new Object[] {"3204993994922383998", ""};

            // 创建服务名称
            QName qname = new QName("http://webservice.fwms.com/", "getSjPartner");

            OMElement element = serviceClient.invokeBlocking(qname, parameters);
            System.out.println(element);

            String result = element.getFirstElement().getText();
            System.out.println(result);

            Class[] returnTypes = new Class[] {String.class};
            Object[] response = serviceClient.invokeBlocking(qname, parameters, returnTypes);
            String r = (String) response[0];
            System.out.println(r);

        } catch (AxisFault e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws RemoteException {
//        testAxis();
        axis2Test();
    }
}
