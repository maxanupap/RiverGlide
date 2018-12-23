package com.max.hello;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap; import java.util.Map;



public class DistanRequest extends StringRequest {
    //private static final String LOGIN_REQUEST_URL = "ที่อย่ไูฟล์ PHP";
    private static final String LOGIN_REQUEST_URL = "http://manocamera.com/getdistance.php";
    private Map<String, String> params;

    public DistanRequest(String idstopboat,String line,String type, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL+"?id="+idstopboat+"&line="+line+"&type="+type, listener, null);
        params = new HashMap<>();
        params.put("id", idstopboat);
        params.put("line",line);
        params.put("type", type);

    }

    @Override     public Map<String, String> getParams() {
        return params;
        }
}
