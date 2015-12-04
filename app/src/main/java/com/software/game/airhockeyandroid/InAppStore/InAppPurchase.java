package com.software.game.airhockeyandroid.InAppStore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.software.game.airhockeyandroid.Entities.Player;
import com.software.game.airhockeyandroid.Entities.PowerUp;
import com.software.game.airhockeyandroid.NetworkManager.CustomJSONRequest;
import com.software.game.airhockeyandroid.NetworkManager.VolleySingleton;
import com.software.game.airhockeyandroid.R;
import com.software.game.airhockeyandroid.Utilities.Constants;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by neela on 10/21/2015.
 */
public class InAppPurchase extends AppCompatActivity implements View.OnClickListener {

    TextView mBalance;
    TextView mPuckCount;
    TextView mHoleCount;
    Button mPaddleCount;
    int coins = 0;
    String powerType;
    int powerCount;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_in_app_purchase);
        initialize();

    }

    private void initialize() {

        mPaddleCount = (Button) findViewById(R.id.paddle_pop_quantity);
        mPuckCount = (Button)findViewById(R.id.multi_puck_quantity);
        mHoleCount = (Button) findViewById(R.id.black_hole_quantity);

        mPaddleCount.setOnClickListener(this);
        mPuckCount.setOnClickListener(this);
        mHoleCount.setOnClickListener(this);

        mBalance = (TextView) findViewById(R.id.balance_text);
        mBalance.setText(Integer.toString(Player.getInstance().getPoints()));
        mQueue = VolleySingleton.getsInstance().getRequestQueue();
        coins = Player.getInstance().getPoints();
        initialPowerUpCount();
    }

    private void initialPowerUpCount(){
        for (int i = 0; i < 3; i++) {
            String power_type = Player.powerUps.get(i).getType();
            int powerUp_count = Player.powerUps.get(i).getCount();
            setPowerUpsCount(power_type,powerUp_count);
        }
    }

    private void setPowerUpsCount(String power_type, int powerUp_count){
        if(power_type.equalsIgnoreCase("Mallet Size")) {
            String quant = getString(R.string.quant,String.valueOf(powerUp_count));
            mPaddleCount.setText(quant);
        }
        else if(power_type.equalsIgnoreCase("Goal Size")){
            String quant = getString(R.string.quant,String.valueOf(powerUp_count));
            mHoleCount.setText(quant);
        }
        else if(power_type.equalsIgnoreCase("Puck")){
            String quant = getString(R.string.quant,String.valueOf(powerUp_count));
            mPuckCount.setText(quant);
        }
    }

    private void assignPowerUp(String type) {
        int deduct = 0;
        if (coins != 0) {
            for (int i = 0; i < 3; i++) {
                String power_type = Player.powerUps.get(i).getType();
                int powerUp_count = Player.powerUps.get(i).getCount();
                if (power_type.equalsIgnoreCase(type) ){
                    if (powerUp_count < 10) {
                        if (power_type.equalsIgnoreCase("Mallet Size")) {
                            if (coins >= 300)
                                deduct = 300;
                            else {
                                Toast.makeText(InAppPurchase.this, R.string.insufficient_coins, Toast.LENGTH_SHORT).show();
                                break;
                            }
                        } else if (power_type.equalsIgnoreCase("Goal Size")) {
                            if (coins >= 300)
                                deduct = 300;
                            else {
                                Toast.makeText(InAppPurchase.this, R.string.insufficient_coins, Toast.LENGTH_SHORT).show();
                                break;
                            }
                        } else if (power_type.equalsIgnoreCase("Puck")) {
                            if (coins >= 200)
                                deduct = 200;
                            else {
                                Toast.makeText(InAppPurchase.this, R.string.insufficient_coins, Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        Player.powerUps.remove(i);
                        PowerUp power = new PowerUp(powerUp_count + 1, power_type);
                        Player.powerUps.add(i, power);
                        setPowerUpsCount(power_type,powerUp_count+1);
                        coins = coins - deduct;
                        mBalance.setText(Integer.toString(coins));
                        Player.getInstance().setPoints(coins);
                        Map<String, String> params = new HashMap<>();
                        params.put("username", Player.getInstance().getUsername());
                        params.put("coins", String.valueOf(coins));
                        updateDataBase(Constants.UPDATE_COINS_URL, params);
                        powerType = type;
                        powerCount = powerUp_count + 1;
                        break;
                    }
                    else
                        Toast.makeText(InAppPurchase.this, R.string.no_more_than_ten, Toast.LENGTH_SHORT).show();
                }
            }
        } else
            Toast.makeText(InAppPurchase.this, R.string.insufficient_coins, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.paddle_pop_quantity:
                assignPowerUp("Mallet Size");
                break;

            case R.id.black_hole_quantity:
                assignPowerUp("Goal Size");
                break;

            case R.id.multi_puck_quantity:
                assignPowerUp("Puck");
                break;

        }
    }

    private void updateDataBase(String url, Map<String, String> params) {
        CustomJSONRequest req = new CustomJSONRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                updatePowerUpDataBase(Constants.UPDATE_POWER_UP_URL);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(req);
    }

    private void updatePowerUpDataBase(String url) {
        Map<String, String> params = new HashMap<>();
        params.put("username", Player.getInstance().getUsername());
        params.put("count", String.valueOf(powerCount));
        params.put("type", powerType);
        CustomJSONRequest req = new CustomJSONRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(req);
    }
}
