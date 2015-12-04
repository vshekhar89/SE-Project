package com.software.game.airhockeyandroid.LoginManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.software.game.airhockeyandroid.NetworkManager.CustomJSONRequest;
import com.software.game.airhockeyandroid.NetworkManager.VolleySingleton;
import com.software.game.airhockeyandroid.NetworkRequest.JSONManager;
import com.software.game.airhockeyandroid.R;
import com.software.game.airhockeyandroid.Utilities.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abhishek on 10/14/2015.
 */
public class CreateProfile extends AppCompatActivity implements View.OnClickListener {

    EditText mNewUsername;
    EditText mNewPassword;
    Button mCreate;
    private RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_profile);
        initialize();
    }

    private void initialize() {
        mNewUsername = (EditText) findViewById(R.id.new_username);
        mNewPassword = (EditText) findViewById(R.id.new_password);
        mCreate = (Button) findViewById(R.id.confirm_new_credentials);
        mCreate.setOnClickListener(this);
        queue = VolleySingleton.getsInstance().getRequestQueue();
    }

    private void createProfile(String name, String password) {
        Map<String, String> params = new HashMap<>();
        if (name.equalsIgnoreCase("") || password.equalsIgnoreCase(""))
            Toast.makeText(CreateProfile.this, R.string.incomplete_credentials, Toast.LENGTH_SHORT).show();
        else {
            params.put("username", name);
            params.put("password", password);
            CustomJSONRequest request = new CustomJSONRequest(Request.Method.POST, Constants.CREATE_PROFILE_URL, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.getInt("success") == 1){
                            Toast.makeText(CreateProfile.this, R.string.profile_created, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateProfile.this,User_Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(CreateProfile.this, R.string.profile_already_exist, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CreateProfile.this, R.string.wrong_info, Toast.LENGTH_SHORT).show();
                }
            });
            if (queue != null)
                queue.add(request);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String name = mNewUsername.getText().toString();
        String password = mNewPassword.getText().toString();
        if (id == R.id.confirm_new_credentials) {
            createProfile(name, password);
        }
    }
}
