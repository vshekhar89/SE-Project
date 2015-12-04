package com.software.game.airhockeyandroid.LoginManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.software.game.airhockeyandroid.Entities.Player;
import com.software.game.airhockeyandroid.NetworkManager.CustomJSONRequest;
import com.software.game.airhockeyandroid.NetworkManager.VolleySingleton;
import com.software.game.airhockeyandroid.R;
import com.software.game.airhockeyandroid.Utilities.Constants;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abhishek on 10/16/2015.
 */
public class ManageProfile extends AppCompatActivity implements View.OnClickListener {

    CardView mDeleteProfile;
    CheckBox mCheckUsername;
    CheckBox mCheckPassword;
    EditText mOldUsername;
    EditText mNewUsername;
    EditText mUsername;
    EditText mOldPassword;
    EditText mNewPassword;
    Button mUpdate;
    LinearLayout mUsernameLayout;
    LinearLayout mPasswordLayout;
    private RequestQueue queue = null;
    private boolean usernameChecked = false;
    private boolean passwordChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_manage_profile);

        initialize();
    }

    private void initialize() {
        queue = VolleySingleton.getsInstance().getRequestQueue();
        mDeleteProfile = (CardView) findViewById(R.id.delete_profile_card);
        mCheckUsername = (CheckBox) findViewById(R.id.update_username_checkbox);
        mCheckPassword = (CheckBox) findViewById(R.id.update_password_checkbox);
        mOldUsername = (EditText) findViewById(R.id.update_old_username_edit_text);
        mNewUsername = (EditText) findViewById(R.id.update_new_username_edit_text);
        mUsername = (EditText) findViewById(R.id.enter_username);
        mOldPassword = (EditText) findViewById(R.id.update_old_password_edit_text);
        mNewPassword = (EditText) findViewById(R.id.update_new_password_edit_text);
        mUpdate = (Button) findViewById(R.id.update);
        mUsernameLayout = (LinearLayout) findViewById(R.id.layout_update_username);
        mPasswordLayout = (LinearLayout) findViewById(R.id.layout_update_password);
        mCheckUsername.setOnClickListener(this);
        mCheckPassword.setOnClickListener(this);
        mDeleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ManageProfile.this, R.string.update_both_fields_or_any_one, Toast.LENGTH_SHORT).show();
                Player player= Player.getInstance();
                Map<String,String> map= new HashMap<>();
                map.put("username", player.getUsername());
                deleteProfile(map);
            }
        });
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyInformationEntered();
            }
        });

    }

    private void verifyInformationEntered() {
        Map<String,String> params = new HashMap<>();
        if (usernameChecked && passwordChecked) {
            if ((mOldUsername.getText().toString()).equalsIgnoreCase("") || (mNewUsername.getText().toString()).equalsIgnoreCase("") || (mUsername.getText().toString()).equalsIgnoreCase("") || (mOldPassword.getText().toString()).equalsIgnoreCase("") || (mNewPassword.getText().toString()).equalsIgnoreCase("") )
                Toast.makeText(ManageProfile.this,R.string.enter_all_info,Toast.LENGTH_LONG).show();
            else{
                params.put("oldUsername",mOldUsername.getText().toString());
                params.put("newUsername",mNewUsername.getText().toString());
                params.put("username1",mUsername.getText().toString());
                params.put("oldPassword",mOldPassword.getText().toString());
                params.put("newPassword",mNewPassword.getText().toString());
                updateProfile(params);
            }
        } else if (usernameChecked) {
            if ((mOldUsername.getText().toString()).equalsIgnoreCase("") || (mNewUsername.getText().toString()).equalsIgnoreCase(""))
                Toast.makeText(ManageProfile.this,R.string.enter_all_info,Toast.LENGTH_LONG).show();
            else{
                params.put("oldUsername",mOldUsername.getText().toString());
                params.put("newUsername",mNewUsername.getText().toString());
                updateProfile(params);
            }
        } else if (passwordChecked) {
            if ((mUsername.getText().toString()).equalsIgnoreCase("") || (mOldPassword.getText().toString()).equalsIgnoreCase("") || (mNewPassword.getText().toString()).equalsIgnoreCase("") )
                Toast.makeText(ManageProfile.this,R.string.enter_all_info,Toast.LENGTH_LONG).show();
            else{
                params.put("username",mUsername.getText().toString());
                params.put("oldPassword",mOldPassword.getText().toString());
                params.put("newPassword",mNewPassword.getText().toString());
                updateProfile(params);
            }
        } else
            Toast.makeText(ManageProfile.this, R.string.update_both_fields_or_any_one, Toast.LENGTH_SHORT).show();
    }

    private void deleteProfile(final Map<String,String> params) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.dialog_confirm);
        builder.setMessage(R.string.dialog_message);

        builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                CustomJSONRequest request = new CustomJSONRequest(Request.Method.POST, Constants.DELETE_PROFILE_URL, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ManageProfile.this, R.string.profile_deleted, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ManageProfile.this, User_Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ManageProfile.this, R.string.wrong_info, Toast.LENGTH_SHORT).show();
                    }
                });
                if (queue != null)
                    queue.add(request);

                dialog.dismiss();
            }

        });

        builder.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }

    private void updateProfile(Map<String,String> params) {
        CustomJSONRequest request = new CustomJSONRequest(Request.Method.POST, Constants.UPDATE_PROFILE_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(ManageProfile.this, R.string.profile_updated, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManageProfile.this, User_Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ManageProfile.this, R.string.wrong_info, Toast.LENGTH_SHORT).show();
            }
        });
        if (queue != null)
            queue.add(request);

    }

    @Override
    public void onClick(View v) {
        boolean ifChecked = ((CheckBox) v).isChecked();
        switch (v.getId()) {

            case R.id.update_username_checkbox:
                mOldUsername.setText("");
                mNewUsername.setText("");
                if (ifChecked) {
                    mUsernameLayout.setVisibility(View.VISIBLE);
                    usernameChecked = true;
                } else {
                    mUsernameLayout.setVisibility(View.GONE);
                    usernameChecked = false;
                }
                break;

            case R.id.update_password_checkbox:
                mUsername.setText("");
                mOldPassword.setText("");
                mNewPassword.setText("");
                if (ifChecked) {
                    mPasswordLayout.setVisibility(View.VISIBLE);
                    passwordChecked = true;
                } else {
                    mPasswordLayout.setVisibility(View.GONE);
                    passwordChecked = false;
                }
                break;
        }
    }
}
