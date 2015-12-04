package com.software.game.airhockeyandroid.LeadershipBoard;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.software.game.airhockeyandroid.Entities.Player;
import com.software.game.airhockeyandroid.NetworkManager.CustomJSONRequest;
import com.software.game.airhockeyandroid.NetworkManager.VolleySingleton;
import com.software.game.airhockeyandroid.R;
import com.software.game.airhockeyandroid.Utilities.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import com.software.game.airhockeyandroid.R;

/**
 * Created by neela on 10/31/2015.
 */
public class Leadership extends AppCompatActivity {

    private RequestQueue mQueue;
    TableLayout mTable;
    TextView mUserName;
    TextView mCoins;
    TextView mRank;
    TextView mGamesWon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_leadership_board);
        mQueue = VolleySingleton.getsInstance().getRequestQueue();
        mTable = (TableLayout) findViewById(R.id.leader_table);
        mTable.removeAllViewsInLayout();
        initialize();
        CustomJSONRequest request = new CustomJSONRequest(Request.Method.GET, Constants.LEADERSHIP_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                showTable(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);
    }

    private void showTable(JSONObject response) {
        try {
            int length;
            if (response.getInt("success") == 1) {
                JSONArray jArray = response.getJSONArray("players");
                if (jArray.length() < 10)
                    length = jArray.length();
                else
                    length = 10;
                int flag = 1;
                for (int i = -1; i <= length - 1; i++) {
                    TableRow tr = new TableRow(Leadership.this);
                    if (flag == 1) {
                        TextView columnUserName = new TextView(Leadership.this);
                        columnUserName.setPadding(20, 20, 20, 20);
                        columnUserName.setText("UserName");
                        columnUserName.setTextColor(Color.BLACK);
                        columnUserName.setTextSize(20);
                        columnUserName.setGravity(Gravity.CENTER);
                        columnUserName.setTypeface(Typeface.DEFAULT_BOLD);
                        tr.addView(columnUserName);

                        TextView columnCoins = new TextView(Leadership.this);
                        columnCoins.setPadding(20, 20, 20, 20);
                        columnCoins.setTextSize(20);
                        columnCoins.setText("Coins");
                        columnCoins.setTextColor(Color.BLACK);
                        columnCoins.setGravity(Gravity.CENTER);
                        columnCoins.setTypeface(Typeface.DEFAULT_BOLD);
                        tr.addView(columnCoins);

                        TextView columnGamesWon = new TextView(Leadership.this);
                        columnGamesWon.setPadding(20, 20, 20, 20);
                        columnGamesWon.setText("Games Won");
                        columnGamesWon.setTextColor(Color.BLACK);
                        columnGamesWon.setTextSize(20);
                        columnGamesWon.setGravity(Gravity.CENTER);
                        columnGamesWon.setTypeface(Typeface.DEFAULT_BOLD);
                        tr.addView(columnGamesWon);

                        mTable.addView(tr);

                        final View vline = new View(Leadership.this);
                        vline.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 2));
                        vline.setBackgroundColor(Color.BLACK);
                        mTable.addView(vline);
                        flag = 0;
                    } else {

                        JSONObject json_data = jArray.getJSONObject(i);

                        TextView username = new TextView(Leadership.this);
                        String stime = String.valueOf(json_data.getString("username"));
                        username.setPadding(20, 20, 20, 20);
                        username.setText(stime);
                        username.setTextColor(Color.RED);
                        username.setTextSize(20);
                        username.setGravity(Gravity.CENTER);
                        username.setTypeface(Typeface.DEFAULT_BOLD);
                        tr.addView(username);

                        TextView usercoins = new TextView(Leadership.this);
                        String stime1 = String.valueOf(json_data.getInt("coins"));
                        usercoins.setPadding(20, 20, 20, 20);
                        usercoins.setText(stime1);
                        usercoins.setTextColor(Color.RED);
                        usercoins.setTextSize(20);
                        usercoins.setGravity(Gravity.CENTER);
                        usercoins.setTypeface(Typeface.DEFAULT_BOLD);
                        tr.addView(usercoins);

                        TextView usergameswon = new TextView(Leadership.this);
                        String stime3 = String.valueOf(json_data.getInt("games_won"));
                        usergameswon.setPadding(20, 20, 20, 20);
                        usergameswon.setText(stime3);
                        usergameswon.setTextColor(Color.RED);
                        usergameswon.setTextSize(20);
                        usergameswon.setGravity(Gravity.CENTER);
                        usergameswon.setTypeface(Typeface.DEFAULT_BOLD);
                        tr.addView(usergameswon);

                        mTable.addView(tr);

                        final View vline1 = new View(Leadership.this);
                        vline1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                        vline1.setBackgroundColor(Color.BLACK);
                        mTable.addView(vline1);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        mUserName = (TextView) findViewById(R.id.user_nam_text);
        mCoins = (TextView) findViewById(R.id.leader_points_user);
        mGamesWon = (TextView) findViewById(R.id.leader_won_user);

        mUserName.setText(Player.getInstance().getUsername());
        mCoins.setText(Integer.toString(Player.getInstance().getPoints()));
        mGamesWon.setText(Integer.toString(Player.getInstance().getGames_won()));
    }
}