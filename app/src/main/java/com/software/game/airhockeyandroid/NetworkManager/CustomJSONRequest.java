package com.software.game.airhockeyandroid.NetworkManager;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Abhishek on 10/13/2015.
 */
public class CustomJSONRequest extends Request<JSONObject> {

    private Response.Listener<JSONObject> mListener;
    private String mDebug = CustomJSONRequest.class.getSimpleName();
    private Map<String, String> params;

    //Constructor to use the Get method
    public CustomJSONRequest(int method, String url, Response.Listener<JSONObject> responseListener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.mListener = responseListener;
    }

    public CustomJSONRequest(int method, String url, Map<String, String> params, Response.Listener<JSONObject> responseListener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.mListener = responseListener;
        this.params = params;
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            JSONObject obj = new JSONObject(jsonString);
            return Response.success(obj, HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }
}
