package com.chaotu.pay.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

public class RequestUtil {
    //private static Logger log = Logger.getLogger(RequestUtil.class);
    private static RequestUtil instance;

    private RequestUtil() {
    }

    public static RequestUtil getInstance() {
        if (instance == null) {
            instance = new RequestUtil();
        }
        return instance;
    }
    /**
     * XML格式字符串转换为Map
     *
     * @param xml XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String xml) {
        try {
            Map<String, String> data = new HashMap<>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            stream.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Author: zhangchu@iyungu.com
     * @Description:根据json获取json对应的实体
     * @Params:
     *   @param: json
     *   @param: classT
     * @Return: T
     * @Throws:
     * @Date: Created in 17:48 2018/2/5
     */
    @SuppressWarnings("unchecked")
    public static <T> T getObjectTFromJson(String json, Class<T> classT) throws Exception {

        ///String json = decryption(code);
        Object obj = null;
        if (null != json && !"".equals(json.trim())) {
            Gson gson = new Gson();
            obj = gson.fromJson(json, classT);
        }
        if (null != obj) {
            return (T) obj;
        }
        return null;
    }

    public static String createPostParamStr(Map<Object, Object> payParam) {
        StringBuilder sb = new StringBuilder();
        try {
            for (Map.Entry entry : payParam.entrySet()) {
                sb.append(URLEncoder.encode(entry.getKey().toString(), "UTF-8")).append("=").append(URLEncoder.encode(entry.getValue().toString(), "UTF-8")).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }catch (Exception e){

        }
        return sb.toString();
    }
    public static String createPostParamStr2(Map<String, Object> payParam) {
        StringBuilder sb = new StringBuilder();
        try {
            for (Map.Entry entry : payParam.entrySet()) {
                sb.append(URLEncoder.encode(entry.getKey().toString(), "UTF-8")).append("=").append(URLEncoder.encode(entry.getValue().toString(), "UTF-8")).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }catch (Exception e){

        }
        return sb.toString();
    }
    public static CloseableHttpClient getHttpsClient() {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
        // 指定信任密钥存储对象和连接套接字工厂
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            // 信任任何链接
            TrustStrategy anyTrustStrategy = new TrustStrategy() {

                @Override
                public boolean isTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws java.security.cert.CertificateException {
                    // TODO Auto-generated method stub
                    return true;
                }
            };
            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy).build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        // 设置连接管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
        // 构建客户端
        return HttpClientBuilder.create().setConnectionManager(connManager).build();
    }
    /**
     * @Author: zhangchu@iyungu.com
     * @Description:根据request将参数整理到MAP对象里
     * @Params:
     *   @param: request
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Throws:
     * @Date: Created in 11:39 2018/5/31
     */
    /*public static Map<String,String> getParamsByRequest(HttpServletRequest request)
    {
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        //log.debug("接收到的报文****************：" + requestParams.keySet().iterator());
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        String jsonParams=JsonUtils.getJsonStrFromObj(params);
        //log.debug("接收到的报文转换后的数据**********：" + jsonParams);
        return params;
    }*/

    public static JSONObject getRequestJsonObject(HttpServletRequest request) throws IOException {
        String json = getRequestJsonString(request);

        return JSONObject.parseObject(json);
    }
    /**
     * 获取 request 中 json 字符串的内容
     *
     * @param request
     * @return : <code>byte[]</code>
     * @throws IOException
     */
    public static String getRequestJsonString(HttpServletRequest request)
            throws IOException {
        String submitMehtod = request.getMethod();
        // GET
        if (submitMehtod.equals("GET")) {
            return new String(request.getQueryString().getBytes("iso-8859-1"),"utf-8").replaceAll("%22", "\"");
            // POST
        } else {
            return getRequestPostStr(request);
        }
    }
    public static String getBody(HttpServletRequest request)
    {
        try {
            StringBuffer sb = new StringBuffer("");
            String line = null;
            BufferedReader reader;
            reader = request.getReader();

            while ((line = reader.readLine()) != null)
                sb.append(line.trim());

            return sb.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 描述:获取 post 请求的 byte[] 数组
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**
     * 描述:获取 post 请求内容
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }


}
