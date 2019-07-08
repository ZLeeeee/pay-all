package com.chaotu.pay.common.utils;

import com.google.gson.Gson;

import java.net.URLEncoder;
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

}
