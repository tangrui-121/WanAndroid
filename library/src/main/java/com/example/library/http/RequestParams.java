package com.example.library.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.library.utils.FormatLog;
import com.example.library.utils.LogUtils;
import com.example.library.utils.StringUtils;
import com.yanzhenjie.nohttp.Binary;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.IBasicRequest;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;

import java.io.File;
import java.util.List;

/**
 * 请求参数封装，普通请求用这个类
 */

public class RequestParams<T> extends RestRequest<JSONObject> {

    private boolean isToken;
    public boolean isCanChange = true;
    private static String UPFILE = "upload_file";

    public RequestParams(String url, RequestMethod requestMethod, boolean isToken) {
        super(url, requestMethod);
        this.isToken = isToken;
    }

    @Override
    public void onPreExecute() {
        //TODO 加密操作
//        try {
//            StringBuilder sb = new StringBuilder();
//            if (isToken) {
//                sb.append(App._mContext.getAccountConfig().getAccessToken());//token登录成功后保存到变量里，用时直接引用
//                LogUtils.d("token is  = " + App._mContext.getAccountConfig().getAccessToken());
//            }
//            sb.append(",");
//            sb.append(Utils.formatNow(null));
//            sb.append(",");
//            sb.append(Utils.randomLong());
//            String extend = CipherUtils.encrypt(sb.toString(), "LMQx0%ttF@GmXeJKXUlQkwf!");
//            add("extend", extend);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        add("origin", BasicConstants.ORIGIN + SystemTool.getAppVersionName(App.INSTANCE));
//        add("deviceid", SystemTool.getPhoneIMEI(App.INSTANCE) + "," + SystemTool.getModel() + ","
//                + SystemTool.getSystemVersion() + "," + SystemTool.getPhoneInfos(App.INSTANCE));
//        add("sig", getSig());
    }

    @Override
    public JSONObject parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        String result = StringRequest.parseResponseString(responseHeaders, responseBody);
//        if (parseJSON(result)) {
        printLog(result);
        return JSON.parseObject(result);
//        }
//        result = CipherUtils.decrypt(result, "LMQx0%ttF@GmXeJKXUlQkwf!");
//        printLog(result);
//        return JSON.parseObject(result);
    }

    public void setCanChange(boolean canChange) {
        isCanChange = canChange;
    }

    @Override
    public IBasicRequest add(String key, int value) {
        return super.set(key, Integer.toString(value));
    }

    @Override
    public IBasicRequest add(String key, long value) {
        return super.set(key, Long.toString(value));
    }

    @Override
    public IBasicRequest add(String key, boolean value) {
        return super.set(key, String.valueOf(value));
    }

    @Override
    public IBasicRequest add(String key, char value) {
        return super.set(key, String.valueOf(value));
    }

    @Override
    public IBasicRequest add(String key, double value) {
        return super.set(key, Double.toString(value));
    }

    @Override
    public IBasicRequest add(String key, float value) {
        return super.set(key, Float.toString(value));
    }

    @Override
    public IBasicRequest add(String key, short value) {
        return super.set(key, Integer.toString(value));
    }

    @Override
    public IBasicRequest add(String key, byte value) {
        return super.set(key, Integer.toString(value));
    }

    @Override
    public IBasicRequest add(String key, List<Binary> binaries) {
        if (StringUtils.isEmpty(key) || !UPFILE.equals(key)) {
            key = UPFILE;
        }
        return super.set(key, binaries);
    }

    @Override
    public IBasicRequest add(String key, File file) {
        if (StringUtils.isEmpty(key) || !UPFILE.equals(key)) {
            key = UPFILE;
        }
        return super.set(key, file);
    }

    @Override
    public IBasicRequest add(String key, Binary binary) {
        if (StringUtils.isEmpty(key) || !UPFILE.equals(key)) {
            key = UPFILE;
        }
        return super.set(key, binary);
    }

    @Override
    public IBasicRequest add(String key, String value) {
        return super.set(key, value);
    }

//    private String getSig() {
//        MultiValueMap mParamKeyValues = getParamKeyValues();
//        Set<String> keys = mParamKeyValues.keySet();
//        HashMap<String, Object> maps = new HashMap<String, Object>();
//        for (String key : keys) {
//            if (UPFILE.equals(key)) {//过滤掉上传文件
//                continue;
//            }
//            List<Object> values = mParamKeyValues.getValues(key);
//            for (Object value : values) {
//                maps.put(key, value);
//            }
//        }
//        return C.getSig(maps);
//    }

    private boolean parseJSON(String data) {//简单判断可以结束符号是否是{}，主要用来判读是否解密字符串，懒得加参数，这样简单判断下也可以解决
        if (data != null && data.startsWith("{") && data.endsWith("}")) {
            Logger.i(">>>>>>> 直接返回参数 <<<<<<<<<<<");
            return true;
        }
        Logger.i(">>>>>>> 解密返回参数 <<<<<<<<<<<");
        return false;
    }

    private void printLog(String result) {
        List<Object> keys = getParamKeyValues().getValues("commandtype");
        if (keys != null && keys.size() > 0) {
            LogUtils.d("commandtype = " + keys.get(0) + " response: " + FormatLog.fomatJson(result));
        } else {
            LogUtils.all_e("RequestParams", "response: " + FormatLog.fomatJson(result));
        }
    }
}
