package com.software.game.airhockeyandroid.PlayGame;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Typeface;
import android.media.SoundPool;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.software.game.airhockeyandroid.Entities.Player;
import com.software.game.airhockeyandroid.Entities.PowerUp;
import com.software.game.airhockeyandroid.GameSettings.Settings;
import com.software.game.airhockeyandroid.LeadershipBoard.Leadership;
import com.software.game.airhockeyandroid.LoginManager.User_Login;
import com.software.game.airhockeyandroid.NetworkManager.CustomJSONRequest;
import com.software.game.airhockeyandroid.NetworkManager.VolleySingleton;
import com.software.game.airhockeyandroid.R;
import com.software.game.airhockeyandroid.Utilities.Constants;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abhishek on 11/2/2015.
 */
public class SinglePlayerActivity extends AppCompatActivity {
    private RequestQueue queue = null;
    static int[] ballBlurColor = new int[]{8355711, -1349546097};
    static int[] ballColor = new int[]{-8421505, -269488145};
    static float ballRad = 0.045f;
    static int colorsNo = 2;
    static int[] iArr = new int[72];
    static int[] dataQuads = iArr;
    static float decelHit = 0.8f;
    static long doubleDelay = 2000;
    static int[] edgeBottomBlurColor = new int[]{16711680, -1342242816};
    static int[] edgeBottomColor = new int[]{Color.YELLOW, -269488145};
    static float edgeLoc = 0.03f;
    static int[] edgeTopBlurColor = new int[]{MotionEventCompat.ACTION_MASK, -1358954241};
    static int[] edgeTopColor = new int[]{Color.GREEN, -269488145};
    static int[] fieldColor = new int[]{-1, -16777216};
    static float goalWidth = 0.39f;
    static float goalWidthPlayer = 0.39f;
    static int[] lineColor = new int[]{-15987980, -8421505};
    static int[] malletBottomBlurColor = new int[]{16711680, -1342242816};
    static int[] malletBottomOuterColor = new int[]{-8454144, -269488145};
    static int[] malletBottomMiddleColor = new int[]{-12648448, -6356992};
    static int[] malletBottomInnerColor = new int[]{-65536, -65536};
    static float malletInnerRad = 0.03f;
    static float malletMiddleRad = 0.06f;
    static float malletOuterRad = 0.09f;
    static float malletInnerRad2 = 0.03f;
    static float malletMiddleRad2 = 0.06f;
    static float malletOuterRad2 = 0.09f;
    static int[] malletTopBlurColor = new int[]{MotionEventCompat.ACTION_MASK, -1358954241};
    static int[] malletTopOuterColor = new int[]{-16777089, -269488145};
    static int[] malletTopMiddleColor = new int[]{-16777153, -16777057};
    static int[] malletTopInnerColor = new int[]{-16776961, -16776961};
    static int[] scoreBlurColor = new int[]{8355711, -1349546097};
    static int[] scoreColor = new int[]{-16777216, -1052689};
    static long startDelay = 1000;
    static float velHitMax = 1.4f;
    static float velMax = 500.0f;
    static float velMaxPlayer = 200.0f;
    static float velMin = 20000.0f;
    int aa;
    int ab;
    int ac;
    int ad;
    int ae;
    int af;
    int backEnded = 0;
    float ballGo2X;
    float ballGo2Y;
    float ballGoX;
    float ballGoY;
    float ballRadScaled;
    float ballStartX;
    float ballStartY;
    long ballTime;
    float ballVel;
    float ballVelX;
    float ballVelY;
    float batBottomCenterX;
    float batBottomCenterY;
    float batBottomGoX;
    float batBottomGoY;
    float batBottomStartX;
    float batBottomStartY;
    float batTopCenterX;
    float batTopCenterY;
    float batTopGoX;
    float batTopGoY;
    float batTopStartX;
    float batTopStartY;
    Bitmap bgndBitmap;
    int bgndLoaded = 0;
    int canvasH;
    int canvasW;
    int coordX;
    int coordY;
    long currentTime;
    int[] dataColors = new int[]{-1, -16777216, -16777216, -1052689};
    long doubleTime;
    Canvas drawCanvas;
    int drawNo;
    Paint drawPaint;
    int drawStart;
    float edgeLocScaled;
    float fa;
    float fb;
    float fc;
    float fd;
    float fe;
    float ff;
    float fg;
    float fh;
    float fi;
    float fj;
    float fk;
    long gameDuration;
    long gameEndTime;
    int gameEnded = 0;
    long trackMalletTime;
    long trackGoalTime;
    RelativeLayout gameLayout;
    float goalOffset;
    float goalWidthScaled;
    float goalWidthScaledPlayer;
    int hitFlag;
    long hitTime;
    float malletInnerRadScaled;
    float malletInnerRadScaled2;
    float malletMiddleRadScaled;
    //new variable
    float malletMiddleRadScaled2;
    float malletOuterRadScaled;
    float malletOuterRadScaled2;
    Paint paint;
    Paint paintBlur;
    Path path;
    int paused = 0;
    long paused2Time;
    long pausedBallTime;
    long pausedGameEndTime;
    long pausedTime;
    float[] relBatSize = new float[]{1.0f, 0.8f, 0.6f};
    float[] relGoalSize = new float[]{1.0f, 0.9f, 1.3f};
    float[] relBallSize = new float[]{1.0f, 0.9f, 0.8f};
    float[] relVel = new float[]{0.6f, 0.8f, 1.0f};
    int scoreBottom;
    int scoreTop;
    int screenH;
    int screenH2;
    int screenW;
    int screenW2;
    int soundGoal;
    int soundGoalAct;
    int soundLoaded = 1;
    int soundMallet;
    int soundMalletAct;
    SoundPool soundPool;
    int soundWall;
    int soundWallAct;
    int useSound = 0;
    float velMaxBottom;
    float velMaxScaled;
    float velMaxTop;
    float velMinScaled;
    float volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(1);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        setContentView(R.layout.layout_play_single_player_game);
        initializeArray();
        volume = Settings.mGameVolume;
        gameDuration = 6000000000000L;
        paint = new Paint();
        paintBlur = new Paint();
        path = new Path();
        LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);
        gameLayout = (RelativeLayout) findViewById(R.id.layoutGame);
        View viewBackground = new Background(this);
        viewBackground.setLayoutParams(new LayoutParams(-1, -1));
        gameLayout.addView(viewBackground);
        View viewGame = new PlayGame(this);
        viewGame.setLayoutParams(new LayoutParams(-1, -1));
        gameLayout.addView(viewGame);
        soundGoalAct = 0;
        soundWallAct = 0;
        soundMalletAct = 0;
        soundPool = new SoundPool(10, 3, 0);
        soundWall = soundPool.load(this, R.raw.snd_wall, 1);
        soundMallet = soundPool.load(this, R.raw.snd_mallet, 1);
        soundGoal = soundPool.load(this, R.raw.snd_goal, 1);
        IntentFilter filter = new IntentFilter("android.intent.action.SCREEN_ON");
        filter.addAction("android.intent.action.SCREEN_OFF");
        registerReceiver(new ScreenReceiver(), filter);
         final Player player = Player.getInstance();
        queue = VolleySingleton.getsInstance().getRequestQueue();
        Button bt = (Button) findViewById(R.id.changeMalletSize);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trackMalletTime = System.currentTimeMillis();
                updateButtonText("malletsize");

                setPlayerMalletSize();
            }
        });

        Button btGoal = (Button) findViewById(R.id.changeGoalSize);
        btGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trackGoalTime = System.currentTimeMillis();
                updateButtonText("goalsize");
                setGoalSize();
            }
        });

        Button btpuck = (Button) findViewById(R.id.multiPuck);
        btpuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trackGoalTime = System.currentTimeMillis();
                updateButtonText("multipuck");
                setGoalSize();
            }
        });


        for(PowerUp power : player.powerUps){
            if(power.getType().equalsIgnoreCase("Mallet Size") && power.getCount()!=0){
                Button mallet= (Button)findViewById(R.id.changeMalletSize);
                mallet.setVisibility(View.VISIBLE);
                mallet.setText("Mallet Size"+"-"+power.getCount());
            }
            if(power.getType().equalsIgnoreCase("Goal Size") && power.getCount()!=0){
                Button goal= (Button)findViewById(R.id.changeGoalSize);
                goal.setVisibility(View.VISIBLE);
                goal.setText("Goal Size" + "-"+power.getCount());
            }
            if(power.getType().equalsIgnoreCase("Puck") && power.getCount()!=0){
                Button puck= (Button)findViewById(R.id.multiPuck);
                puck.setVisibility(View.VISIBLE);
                puck.setText("MultiPuck"+"-"+power.getCount());
            }

        }
    }

    public void updateButtonText(String type) {
        Player player = Player.getInstance();
        Map<String,String> params = new HashMap<>();
        params.put("username",player.getUsername());
        if (type.equalsIgnoreCase("malletsize")) {
            for (PowerUp power : player.powerUps) {
                if (power.getType().equalsIgnoreCase("Mallet Size")) {
                    power.setCount(power.getCount() - 1);
                    Button mallet= (Button)findViewById(R.id.changeMalletSize);
                    mallet.setText("Mallet Size" + "-"+power.getCount());
                    params.put("count", String.valueOf(power.getCount()));
                    params.put("type","Mallet Size");
                    CustomJSONRequest request = new CustomJSONRequest(Request.Method.POST, Constants.UPDATE_POWER_UP_URL, params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    if (queue != null)
                        queue.add(request);

                    if(power.getCount()<1){
                        mallet.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
        if (type.equalsIgnoreCase("goalsize")) {
            for (PowerUp power : player.powerUps) {
                if (power.getType().equalsIgnoreCase("Goal Size")) {
                    power.setCount(power.getCount() - 1);
                    Button goal= (Button)findViewById(R.id.changeGoalSize);
                    goal.setText("Goal Size" +"-"+ power.getCount());
                    params.put("count",String.valueOf(power.getCount()));
                    params.put("type","Goal Size");
                    CustomJSONRequest request = new CustomJSONRequest(Request.Method.POST, Constants.UPDATE_POWER_UP_URL, params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    if (queue != null)
                        queue.add(request);

                    if(power.getCount()<1){
                        goal.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
        if (type.equalsIgnoreCase("multipuck")) {
            for (PowerUp power : player.powerUps) {
                if (power.getType().equalsIgnoreCase("Multi Puck")) {
                    power.setCount(power.getCount() - 1);
                    Button puck= (Button)findViewById(R.id.multiPuck);
                    puck.setText("MultiPuck" + "-"+power.getCount());
                    params.put("count", String.valueOf(power.getCount()));
                    params.put("type","Multi Puck");
                    CustomJSONRequest request = new CustomJSONRequest(Request.Method.POST, Constants.UPDATE_POWER_UP_URL, params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    if (queue != null)
                        queue.add(request);

                    if(power.getCount()<1){
                        puck.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
    }
    public void setPlayerMalletSize() {
        if (bgndLoaded != 1) {
            malletMiddleRadScaled = (malletMiddleRad * ((float) screenW)) * relBatSize[0];
            malletOuterRadScaled = (malletOuterRad * ((float) screenW)) * relBatSize[0];
            malletInnerRadScaled = (malletInnerRad * ((float) screenW)) * relBatSize[0];
        } else {
            if (System.currentTimeMillis() - trackMalletTime > 30000) {
                malletMiddleRad = 0.06f;
                malletOuterRad = 0.09f;
                malletInnerRad = 0.04f;
            } else {
                malletMiddleRad = 0.09f;
                malletOuterRad = 0.13f;
                malletInnerRad = 0.06f;
            }
            malletMiddleRadScaled = (malletMiddleRad * ((float) screenW)) * relBatSize[0];
            malletOuterRadScaled = (malletOuterRad * ((float) screenW)) * relBatSize[0];
            malletInnerRadScaled = (malletInnerRad * ((float) screenW)) * relBatSize[0];
        }
    }

    public void setGoalSize() {
        if (bgndLoaded != 1) {
            goalWidthPlayer = 0.39f;
            goalWidthScaledPlayer = (goalWidthPlayer * ((float) screenW)) * relGoalSize[0];
        } else {
            if (System.currentTimeMillis() - trackGoalTime > 30000) {
                goalWidthPlayer = 0.39f;
                goalWidthScaledPlayer = (goalWidthPlayer * ((float) screenW)) * relGoalSize[0];
            } else {
                goalWidthPlayer = 0.5f;
                goalWidthScaledPlayer = (goalWidthPlayer * ((float) screenW)) * relGoalSize[0];
            }
        }
    }

    public void initializeArray() {
        iArr[0] = 390;
        iArr[1] = 390;
        iArr[2] = 610;
        iArr[3] = 390;
        iArr[4] = 610;
        iArr[5] = 610;
        iArr[6] = 390;
        iArr[7] = 610;
        iArr[8] = 1;
        iArr[9] = 1;
        iArr[12] = 400;
        iArr[13] = 400;
        iArr[14] = 490;
        iArr[15] = 400;
        iArr[16] = 490;
        iArr[17] = 600;
        iArr[18] = 400;
        iArr[19] = 600;
        iArr[20] = 1;
        iArr[21] = 1;
        iArr[22] = 1;
        iArr[24] = 600;
        iArr[25] = 400;
        iArr[26] = 510;
        iArr[27] = 400;
        iArr[28] = 510;
        iArr[29] = 600;
        iArr[30] = 600;
        iArr[31] = 600;
        iArr[32] = 1;
        iArr[33] = 1;
        iArr[34] = 1;
        iArr[36] = 390;
        iArr[37] = 390;
        iArr[38] = 390;
        iArr[39] = 610;
        iArr[40] = 610;
        iArr[41] = 610;
        iArr[42] = 610;
        iArr[43] = 390;
        iArr[44] = 1;
        iArr[45] = 1;
        iArr[48] = 400;
        iArr[49] = 400;
        iArr[50] = 400;
        iArr[51] = 490;
        iArr[52] = 600;
        iArr[53] = 490;
        iArr[54] = 600;
        iArr[55] = 400;
        iArr[56] = 1;
        iArr[57] = 1;
        iArr[58] = 1;
        iArr[60] = 400;
        iArr[61] = 600;
        iArr[62] = 400;
        iArr[63] = 510;
        iArr[64] = 600;
        iArr[65] = 510;
        iArr[66] = 600;
        iArr[67] = 600;
        iArr[68] = 1;
        iArr[69] = 1;
        iArr[70] = 1;
    }

    class Background extends View {


        public Background(Context context) {
            super(context);
        }

        public void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            screenW = w;
            screenH = h;
            edgeLocScaled = edgeLoc * ((float) screenW);
            goalWidthScaled = (goalWidth * ((float) screenW)) * relGoalSize[0];
            setGoalSize();
            goalWidthScaledPlayer = (goalWidthPlayer * ((float) screenW)) * relGoalSize[0];
            ballRadScaled = (ballRad * ((float) screenW)) * relBallSize[0];
            //Mallet size of computer
            malletMiddleRadScaled2 = (malletMiddleRad2 * ((float) screenW)) * relBatSize[0];
            malletOuterRadScaled2 = (malletOuterRad2 * ((float) screenW)) * relBatSize[0];
            malletInnerRadScaled2 = (malletInnerRad2 * ((float) screenW)) * relBatSize[0];
            setPlayerMalletSize();
            velMaxScaled = (((float) screenW) / velMax) * relVel[0];
            float f = ((float) screenW) / velMaxPlayer;
            velMaxTop = f;
            velMaxBottom = f;
            velMinScaled = ((float) screenW) / velMin;
            float f2 = (float) screenW2;
            batTopCenterX = f2;
            batTopStartX = f2;
            batTopGoX = f2;
            f2 = (float) screenW2;
            batBottomCenterX = f2;
            batBottomStartX = f2;
            batBottomGoX = f2;
            f2 = (goalWidthScaled / 2.0f) + edgeLocScaled;
            batTopCenterY = f2;
            batTopStartY = f2;
            batTopGoY = f2;
            f2 = (((float) screenH) - (goalWidthScaled / 2.0f)) + edgeLocScaled;
            batBottomCenterY = f2;
            batBottomStartY = f2;
            batBottomGoY = f2;
            screenH2 = screenH / 2;
            screenW2 = screenW / 2;
            velMaxTop = velMaxScaled;
            paintBlur.setMaskFilter(new BlurMaskFilter(malletOuterRadScaled - malletMiddleRadScaled, Blur.NORMAL));
        }

        private void drawedges() {

            path.reset();
            coordX = 0;
            coordY = ac;
            path.moveTo((float) coordX, (float) coordY);
            coordX = aa;
            coordY = ac;
            path.lineTo((float) coordX, (float) coordY);
            coordX = aa;
            coordY = ac - ab;
            if (coordY < 0) {
                coordY = ac + ab;
            }
            path.lineTo((float) coordX, (float) coordY);
            coordX = 0;
            coordY = ac - ab;
            if (coordY < 0) {
                coordY = ac + ab;
            }
            path.lineTo((float) coordX, (float) coordY);
            coordX = 0;
            coordY = ac;
            path.lineTo((float) coordX, (float) coordY);
            coordX = screenW;
            coordY = ac;
            path.moveTo((float) coordX, (float) coordY);
            coordX = screenW - aa;
            coordY = ac;
            path.lineTo((float) coordX, (float) coordY);
            coordX = screenW - aa;
            coordY = ac - ab;
            if (coordY < 0) {
                coordY = ac + ab;
            }
            path.lineTo((float) coordX, (float) coordY);
            coordX = screenW;
            coordY = ac - ab;

            if (coordY < 0) {
                coordY = ac + ab;
            }
            path.lineTo((float) coordX, (float) coordY);
            coordX = screenW;
            coordY = ac;
            path.lineTo((float) coordX, (float) coordY);
            coordX = 0;
            coordY = ac;
            path.moveTo((float) coordX, (float) coordY);
            coordX = ab;
            coordY = ac;
            path.lineTo((float) coordX, (float) coordY);
            coordX = ab;
            coordY = screenH2;
            if (coordY < 0) {
                coordY = ac + ab;
            }
            path.lineTo((float) coordX, (float) coordY);
            coordX = 0;
            coordY = screenH2;
            if (coordY < 0) {
                coordY = ac + ab;
            }
            path.lineTo((float) coordX, (float) coordY);
            coordX = 0;
            coordY = ac;
            path.lineTo((float) coordX, (float) coordY);
            coordX = screenW;
            coordY = ac;
            path.moveTo((float) coordX, (float) coordY);
            coordX = screenW - ab;
            coordY = ac;
            path.lineTo((float) coordX, (float) coordY);
            coordX = screenW - ab;
            coordY = screenH2;
            if (coordY < 0) {
                coordY = ac + ab;
            }
            path.lineTo((float) coordX, (float) coordY);
            coordX = screenW;
            coordY = screenH2;
            if (coordY < 0) {
                coordY = ac + ab;
            }
            path.lineTo((float) coordX, (float) coordY);
            coordX = screenW;
            coordY = ac;
            path.lineTo((float) coordX, (float) coordY);
        }
    }

    class PlayGame extends View {
        public PlayGame(Context context) {
            super(context);
        }

        public void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            long currentTimeMillis = System.currentTimeMillis();
            ballTime = currentTimeMillis;
            currentTime = currentTimeMillis;
            gameEndTime = gameDuration + currentTime;
            scoreTop = 0;
            scoreBottom = 0;
            paused = 0;
            float f = (float) screenW2;
            batTopStartX = f;
            batTopCenterX = f;
            batTopGoX = f;
            f = (goalWidthScaled / 2.0f) + edgeLocScaled;
            batTopStartY = f;
            batTopCenterY = f;
            batTopGoY = f;
            f = (float) screenW2;
            batBottomStartX = f;
            batBottomCenterX = f;
            batBottomGoX = f;
            f = (((float) screenH) - (goalWidthScaled / 2.0f)) - edgeLocScaled;
            batBottomStartY = f;
            batBottomCenterY = f;
            batBottomGoY = f;
        }

        public synchronized boolean onTouchEvent(MotionEvent ev) {
            super.onTouchEvent(ev);
            int eventaction = ev.getAction();
            if (eventaction != 0 || gameEnded <= 0) {
                aa = ev.getPointerCount();
                ab = 0;
                while (ab < aa) {
                    coordX = (int) ev.getX(ab);
                    coordY = (int) ev.getY(ab);
                    if (((float) coordY) >= ((((float) screenH2) - malletOuterRadScaled) - ballRadScaled) + 1.0f || gameEnded >= 0) {
                        if (((float) coordY) <= ((((float) screenH2) + malletOuterRadScaled) + ballRadScaled) - 1.0f || gameEnded >= 0) {
                            if (eventaction == 0 && ((float) coordY) < ((float) screenH2) + ((malletOuterRadScaled + ballRadScaled) / 2.0f) && ((float) coordY) > ((float) screenH2) - ((malletOuterRadScaled + ballRadScaled) / 2.0f)) {
                                if (paused == 0) {
                                    pausedTime = currentTime;
                                    pausedBallTime = ballTime;
                                    pausedGameEndTime = gameEndTime;
                                    paused = 1;
                                } else if (paused == 1) {
                                    long currentTimeMillis = System.currentTimeMillis();
                                    currentTime = currentTimeMillis;
                                    paused2Time = currentTimeMillis;
                                    ballTime = (pausedBallTime - pausedTime) + currentTime;
                                    gameEndTime = (pausedGameEndTime - pausedTime) + currentTime;
                                    paused = 2;
                                    invalidate();
                                }
                            }
                        } else if (paused == 0) {
                            if(scoreBottom-scoreTop >= 3){
                                batBottomGoX = (float) coordX+100;
                                batBottomGoY = (float) coordY+100;
                            }else{
                                batBottomGoX = (float) coordX;
                                batBottomGoY = (float) coordY;
                            }

                        }
                    } else if (paused == 0) {
                        if(scoreTop-scoreBottom >= 3 ){
                            batTopGoX = (float) coordX+100;
                            batTopGoY = (float) coordY+100;
                        }
                        else{
                            batTopGoX = (float) coordX;
                            batTopGoY = (float) coordY;
                        }
                    }
                    ab++;
                }
            } else {
                backEnded = 1;
                finish();
            }
            return true;
        }

        public void setBoard(Canvas canvas) {
            Background back = new Background(SinglePlayerActivity.this);
            bgndLoaded = 1;
            coordX = screenW;
            coordY = screenH;
            bgndBitmap = Bitmap.createBitmap(coordX, coordY, Config.RGB_565);
            Canvas bgndCanvas = new Canvas(bgndBitmap);
            bgndCanvas.drawColor(fieldColor[0]);
            paint.setAntiAlias(true);
            paint.setColor(lineColor[0]);
            paint.setStyle(Style.STROKE);
            paint.setStrokeWidth((float) (screenH / 115));
            paintBlur.setAntiAlias(true);
            paintBlur.setStyle(Style.FILL);
            coordX = 0;
            coordY = screenH2;
            aa = coordX;
            ab = coordY;
            coordX = screenW;
            coordY = screenH2;
            bgndCanvas.drawLine((float) aa, (float) ab, (float) coordX, (float) coordY, paint);
            coordX = screenW2;
            coordY = screenH2;
            bgndCanvas.drawCircle((float) coordX, (float) coordY, goalWidthScaled / 2.0f, paint);
            coordX = screenW2;
            coordY = screenH - ((int) edgeLocScaled);
            bgndCanvas.drawCircle((float) coordX, (float) coordY, goalWidthScaled / 2.0f, paint);
            coordX = screenW2;
            coordY = (int) edgeLocScaled;
            bgndCanvas.drawCircle((float) coordX, (float) coordY, goalWidthScaledPlayer / 2.0f, paint);
            paint.setAntiAlias(true);
            paint.setColor(-lineColor[0]);
            paint.setStyle(Style.FILL);
            path.reset();
            int i = (screenW - ((int) goalWidthScaled)) / 2;
            coordX = i;
            aa = i;
            ab = (int) edgeLocScaled;
            coordX = aa;
            coordY = 0;
            path.moveTo((float) coordX, (float) coordY);
            coordX = screenW - aa;
            coordY = 0;
            path.lineTo((float) coordX, (float) coordY);
            coordX = screenW - aa;
            coordY = ab;
            path.lineTo((float) coordX, (float) coordY);
            coordX = aa;
            coordY = ab;
            path.lineTo((float) coordX, (float) coordY);
            coordX = aa;
            coordY = 0;
            path.lineTo((float) coordX, (float) coordY);
            coordX = aa;
            coordY = screenH;
            path.moveTo((float) coordX, (float) coordY);
            coordX = screenW - aa;
            coordY = screenH;
            path.lineTo((float) coordX, (float) coordY);
            coordX = screenW - aa;
            coordY = screenH - ab;
            path.lineTo((float) coordX, (float) coordY);
            coordX = aa;
            coordY = screenH - ab;
            path.lineTo((float) coordX, (float) coordY);
            coordX = aa;
            coordY = screenH;
            path.lineTo((float) coordX, (float) coordY);
            bgndCanvas.drawPath(path, paint);
            paint.setAntiAlias(true);
            paint.setStyle(Style.FILL);
            paintBlur.setColor(edgeTopBlurColor[0]);
            ab = (((int) edgeLocScaled) * 4) / 2;
            aa = (screenW - ((int) (goalWidthScaled - edgeLocScaled))) / 2;
            ac = 0;
            back.drawedges();
            bgndCanvas.drawPath(path, paintBlur);
            paint.setColor(edgeTopColor[0]);
            ab = (int) edgeLocScaled;
            aa = (screenW - ((int) goalWidthScaledPlayer)) / 2;
            ac = 0;
            back.drawedges();
            bgndCanvas.drawPath(path, paint);
            paintBlur.setColor(edgeBottomBlurColor[0]);
            ab = (((int) edgeLocScaled) * 4) / 2;
            aa = (screenW - ((int) (goalWidthScaled - edgeLocScaled))) / 2;
            ac = screenH;
            back.drawedges();
            bgndCanvas.drawPath(path, paintBlur);
            paint.setColor(edgeBottomColor[0]);
            ab = (int) edgeLocScaled;
            aa = (screenW - ((int) goalWidthScaled)) / 2;
            ac = screenH;
            back.drawedges();
            bgndCanvas.drawPath(path, paint);
            canvas.drawBitmap(bgndBitmap, 0.0f, 0.0f, null);
        }

        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            setBoard(canvas);
            if (soundMalletAct > 0) {
                soundMalletAct--;
            }
            if (soundWallAct > 0) {
                soundWallAct--;
            }
            if (soundGoalAct > 0) {
                soundGoalAct--;
            }
            drawCanvas = canvas;
            canvasW = screenW;
            canvasH = screenH;
            if (paused > 0) {
                ballTime = (pausedBallTime - pausedTime) + currentTime;
                gameEndTime = (pausedGameEndTime - pausedTime) + currentTime;
                if (paused == 1) {
                    paused2Time = System.currentTimeMillis();
                }
                if (paused == 2 && System.currentTimeMillis() > paused2Time + startDelay) {
                    paused = 0;
                    currentTime = System.currentTimeMillis();
                    ballTime = (pausedBallTime - pausedTime) + currentTime;
                    gameEndTime = (pausedGameEndTime - pausedTime) + currentTime;
                }
            } else {
                currentTime = System.currentTimeMillis();
                if (currentTime == ballTime) {
                    ballTime--;
                }
                if (ballGoY <= 0.0f) {
                    ballGoY = 1.0f;
                }
                if (ballGoY >= ((float) screenH)) {
                    ballGoY = (float) (screenH - 1);
                }
                if (currentTime > hitTime + doubleDelay) {
                    hitFlag = 0;
                }
                if (ballGoY < ((float) screenH2) && hitFlag == 2) {
                    hitFlag = 0;
                }
                if (ballGoY > ((float) screenH2) && hitFlag == 1) {
                    hitFlag = 0;
                }
                if (((ballVelY > 0.0f || hitFlag != 0) && currentTime <= doubleTime + doubleDelay) || ballGoY > ((float) screenH2) || gameEnded >= 0) {
                    batTopGoX = (float) screenW2;
                    batTopGoY = (goalWidthScaled / 2.0f) + edgeLocScaled;
                } else {
                    batTopGoX = goalOffset + (((ballGoX - goalOffset) * (((float) screenH) - batTopCenterY)) / (((float) screenH) - ballGoY));
                    if (ballGoY <= ((((float) screenH2) - malletOuterRadScaled) - ballRadScaled) + 1.0f) {
                        if (ballVelY <= 0.0f) {
                            doubleTime = currentTime;
                        }
                        batTopGoY = ballGoY;
                    }
                }
                fa = (batTopGoX - batTopStartX) / ((float) (currentTime - ballTime));
                fb = (batTopGoY - batTopStartY) / ((float) (currentTime - ballTime));
                fc = (float) (Math.sqrt((fb * fb) + (fa * fa)) + 1.0E-6f);
                if (fc <= velMaxTop) {
                    batTopCenterX = batTopGoX;
                    batTopCenterY = batTopGoY;
                } else {
                    batTopCenterX = batTopStartX + (((batTopGoX - batTopStartX) * velMaxTop) / fc);
                    batTopCenterY = batTopStartY + (((batTopGoY - batTopStartY) * velMaxTop) / fc);
                }
                fa = (batBottomGoX - batBottomStartX) / ((float) (currentTime - ballTime));
                fb = (batBottomGoY - batBottomStartY) / ((float) (currentTime - ballTime));
                fc = (float) (Math.sqrt((fb * fb) + (fa * fa)) + 1.0E-6f);
                if (fc <= velMaxBottom) {
                    batBottomCenterX = batBottomGoX;
                    batBottomCenterY = batBottomGoY;
                } else {
                    batBottomCenterX = batBottomStartX + (((batBottomGoX - batBottomStartX) * velMaxBottom) / fc);
                    batBottomCenterY = batBottomStartY + (((batBottomGoY - batBottomStartY) * velMaxBottom) / fc);
                }
                if (batBottomCenterY + malletOuterRadScaled > ((float) screenH) - edgeLocScaled) {
                    batBottomCenterY = (((float) screenH) - edgeLocScaled) - malletOuterRadScaled;
                }
                if (batBottomCenterX + malletOuterRadScaled > ((float) screenW) - edgeLocScaled) {
                    batBottomCenterX = (((float) screenW) - edgeLocScaled) - malletOuterRadScaled;
                }
                if (batBottomCenterX - malletOuterRadScaled < edgeLocScaled) {
                    batBottomCenterX = edgeLocScaled + malletOuterRadScaled;
                }
                if (batTopCenterY - malletOuterRadScaled < edgeLocScaled) {
                    batTopCenterY = edgeLocScaled + malletOuterRadScaled;
                }
                if (batTopCenterX + malletOuterRadScaled > ((float) screenW) - edgeLocScaled) {
                    batTopCenterX = (((float) screenW) - edgeLocScaled) - malletOuterRadScaled;
                }
                if (batTopCenterX - malletOuterRadScaled < edgeLocScaled) {
                    batTopCenterX = edgeLocScaled + malletOuterRadScaled;
                }
            }
            setPlayerMalletSize();
            setGoalSize();
            coordX = (int) batBottomCenterX;
            coordY = (int) batBottomCenterY;
            paint.setAntiAlias(true);
            paint.setStyle(Style.FILL);
            paintBlur.setColor(malletBottomBlurColor[0]);
            canvas.drawCircle((float) coordX, (float) coordY, (malletOuterRadScaled * 5.0f) / 4.0f, paintBlur);
            paint.setColor(malletBottomOuterColor[0]);
            canvas.drawCircle((float) coordX, (float) coordY, malletOuterRadScaled, paint);
            paint.setColor(malletBottomMiddleColor[0]);
            canvas.drawCircle((float) coordX, (float) coordY, malletMiddleRadScaled, paint);
            paint.setColor(malletBottomInnerColor[0]);
            canvas.drawCircle((float) coordX, (float) coordY, malletInnerRadScaled, paint);
            coordX = (int) batTopCenterX;
            coordY = (int) batTopCenterY;
            paint.setAntiAlias(true);
            paintBlur.setColor(malletTopBlurColor[0]);
            canvas.drawCircle((float) coordX, (float) coordY, (malletOuterRadScaled2 * 5.0f) / 4.0f, paintBlur);
            paint.setColor(malletTopOuterColor[0]);
            canvas.drawCircle((float) coordX, (float) coordY, malletOuterRadScaled2, paint);
            paint.setColor(malletTopMiddleColor[0]);
            canvas.drawCircle((float) coordX, (float) coordY, malletMiddleRadScaled2, paint);
            paint.setColor(malletTopInnerColor[0]);
            canvas.drawCircle((float) coordX, (float) coordY, malletInnerRadScaled2, paint);
            if (paused == 0) {
                if (gameEnded > 0) {
                    ballGoX = (float) (-screenW);
                } else if (gameEnded == 0) {
                    ballGoX = (float) (-screenW);
                    float f = (float) screenW2;
                    batTopStartX = f;
                    batTopCenterX = f;
                    batTopGoX = f;
                    f = (goalWidthScaled / 1.0f) + edgeLocScaled;
                    batTopStartY = f;
                    batTopCenterY = f;
                    batTopGoY = f;
                    f = (float) screenW2;
                    batBottomStartX = f;
                    batBottomCenterX = f;
                    batBottomGoX = f;
                    f = (((float) screenH) - (goalWidthScaled / 2.0f)) - edgeLocScaled;
                    batBottomStartY = f;
                    batBottomCenterY = f;
                    batBottomGoY = f;
                    if (currentTime > ballTime + startDelay) {
                        if (currentTime <= gameEndTime) {
                            gameEnded = -1;
                            long j = currentTime;
                            ballTime = j;
                            doubleTime = j;
                            hitFlag = 0;
                            if (Math.random() < 0.5d) {
                                ballGoY = ((float) screenH2) + (goalWidthScaled / 2.0f);
                            } else {
                                ballGoY = ((float) screenH2) - (goalWidthScaled / 2.0f);
                            }
                            ballGoX = (float) screenW2;
                            ballStartX = ballGoX;
                            ballStartY = ballGoY;
                            ballVelX = 0.0f;
                            ballVelY = 0.0f;
                            goalOffset = (((float) screenW) + ((goalWidthScaled * 2.0f) * ((float) (Math.random() - 0.5d)))) / 2.0f;
                            coordX = (int) ballGoX;
                            coordY = (int) ballGoY;
                            paint.setAntiAlias(true);
                            paint.setColor(ballColor[0]);
                            canvas.drawCircle((float) coordX, (float) coordY, ballRadScaled, paint);
                        } else {
                            if (scoreTop == scoreBottom) {
                                gameEnded = 3;
                            } else if (scoreTop > scoreBottom) {
                                gameEnded = 1;
                                updateRank(false);
                            } else {
                                gameEnded = 2;
                                updateRank(true);
                            }
                        }
                    }
                } else {
                    ballGoX = (((float) (currentTime - ballTime)) * ballVelX) + ballStartX;
                    ballGoY = (((float) (currentTime - ballTime)) * ballVelY) + ballStartY;
                    if (currentTime == ballTime) {
                        ballTime--;
                    }
                    fa = ((((float) screenW) - goalWidthScaled) - (ballRadScaled / 2.0f)) / 2.0f;
                    fb = ((float) screenW) - fa;
                    if (ballGoY + ballRadScaled >= ((float) screenH) - edgeLocScaled) {
                        if (ballGoX <= fa || ballGoX >= fb) {
                            ballGoY = ((((float) screenH) - edgeLocScaled) - ballRadScaled) - 1.0f;
                            ballVelY = (-ballVelY) * decelHit;
                            ballVelX *= decelHit;
                            soundwall();
                        } else if (ballGoY > ((float) screenH)) {
                            gameEnded = 0;
                            ballTime = currentTime;
                            scoreTop++;
                            if (scoreTop > 6 && gameDuration == 6000000000000L) {
                                gameEndTime = 0;
                            }
                            if (scoreTop > 14 && gameDuration == 12000000000000L) {
                                gameEndTime = 0;
                            }
                            soundgoal();
                        } else {
                            if (ballGoX - ballRadScaled <= fa && ballVelX < 0.0f) {
                                ballGoX = fa + ballRadScaled;
                                ballVelX = -ballVelX;
                                soundwall();
                            }
                            if (ballGoX + ballRadScaled >= fb && ballVelX > 0.0f) {
                                ballGoX = fb - ballRadScaled;
                                ballVelX = -ballVelX;
                                soundwall();
                            }
                        }
                    }
                    if (ballGoY - ballRadScaled <= edgeLocScaled) {
                        if (ballGoX <= fa || ballGoX >= fb) {
                            ballGoY = (edgeLocScaled + ballRadScaled) + 1.0f;
                            ballVelY = (-ballVelY) * decelHit;
                            ballVelX *= decelHit;
                            soundwall();
                        } else if (ballGoY < 0.0f) {
                            gameEnded = 0;
                            ballTime = currentTime;
                            scoreBottom++;
                            if (scoreBottom > 6 && gameDuration == 6000000000000L) {
                                gameEndTime = 0;
                            }
                            if (scoreBottom > 14 && gameDuration == 12000000000000L) {
                                gameEndTime = 0;
                            }
                            soundgoal();
                        } else {
                            if (ballGoX - ballRadScaled <= fa && ballVelX < 0.0f) {
                                ballGoX = fa + ballRadScaled;
                                ballVelX = -ballVelX;
                                soundwall();
                            }
                            if (ballGoX + ballRadScaled >= fb && ballVelX > 0.0f) {
                                ballGoX = fb - ballRadScaled;
                                ballVelX = -ballVelX;
                                soundwall();
                            }
                        }
                    }
                    if (ballGoX + ballRadScaled >= ((float) screenW) - edgeLocScaled) {
                        ballGoX = ((((float) screenW) - edgeLocScaled) - ballRadScaled) - 1.0f;
                        ballVelX = (-ballVelX) * decelHit;
                        ballVelY *= decelHit;
                        soundwall();
                        if (ballVelX + ballVelY < velMinScaled) {
                            ballVelX *= 1.2f;
                            ballVelY *= 1.2f;
                        }
                    }
                    if (ballGoX - ballRadScaled <= edgeLocScaled) {
                        ballGoX = (edgeLocScaled + ballRadScaled) + 1.0f;
                        ballVelX = (-ballVelX) * decelHit;
                        ballVelY *= decelHit;
                        soundwall();
                        if (ballVelX + ballVelY < velMinScaled) {
                            ballVelX *= 1.2f;
                            ballVelY *= 1.2f;
                        }
                    }
                    fh = batTopCenterX - batTopStartX;
                    fi = batTopCenterY - batTopStartY;
                    if (Math.abs(fh) > Math.abs(fi)) {
                        aa = ((int) ((Math.abs(fh) * 2.0f) / malletOuterRadScaled)) + 1;
                    } else {
                        aa = ((int) ((Math.abs(fi) * 2.0f) / malletOuterRadScaled)) + 1;
                    }
                    fh /= (float) aa;
                    fi /= (float) aa;
                    fj = (ballGoX - ballStartX) / ((float) aa);
                    fk = (ballGoY - ballStartY) / ((float) aa);
                    ab = 0;
                    do {
                        ab++;
                        fb = (ballStartX - batTopStartX) + (((float) ab) * (fj - fh));
                        fc = (ballStartY - batTopStartY) + (((float) ab) * (fk - fi));
                        fa = (float) (Math.sqrt((fb * fb) + (fc * fc)) + 1.0E-6f);
                        if (ab >= aa) {
                            break;
                        }
                    }
                    while (fa > ballRadScaled + malletOuterRadScaled);
                    if (fa <= ballRadScaled + malletOuterRadScaled) {
                        soundmallet();
                        ballGo2X = batTopCenterX + ((fb * ((ballRadScaled + 1.0f) + malletOuterRadScaled)) / fa);
                        ballGo2Y = batTopCenterY + ((fc * ((ballRadScaled + 1.0f) + malletOuterRadScaled)) / fa);
                        ballVel = (float) Math.sqrt((ballVelX * ballVelX) + (ballVelY * ballVelY));
                        fd = (float) Math.atan2((double) fb, (double) fc);
                        fe = ((float) Math.atan2((double) ballVelX, (double) ballVelY)) - 3.1415927f;
                        ff = (fd * 2.0f) - fe;
                        fg = ff - fd;
                        if (fg > 3.1415927f) {
                            fg -= 6.2831855f;
                        }
                        if (fg < -3.1415927f) {
                            fg += 6.2831855f;
                        }
                        if (fg > 1.57f) {
                            ff = fd + 1.57f;
                        }
                        if (fg < -1.57f) {
                            ff = fd - 1.57f;
                        }
                        fh = (batTopCenterX - batTopStartX) / ((float) (currentTime - ballTime));
                        fi = (batTopCenterY - batTopStartY) / ((float) (currentTime - ballTime));
                        fj = (float) Math.atan2((double) fh, (double) fi);
                        fg = fj - fd;
                        if (fg > 3.1415927f) {
                            fg -= 6.2831855f;
                        }
                        if (fg < -3.1415927f) {
                            fg += 6.2831855f;
                        }
                        if (ballVel > velMaxScaled * velHitMax) {
                            ballVel = velMaxScaled * velHitMax;
                        }
                        fk = (float) Math.abs(Math.cos(fg));
                        ballVelX = (float) ((((Math.sin(ff) * ballVel) * decelHit) + (fh * fk)) - ((fi * fk) * Math.sin(fg)));
                        ballVelY = (float) ((((Math.cos(ff) * ballVel) * decelHit) + (fi * fk)) + ((fh * fk) * Math.sin(fg)));
                        ballGoX = ballGo2X;
                        ballGoY = ballGo2Y;
                        hitFlag = 1;
                        hitTime = currentTime;
                    }
                    fh = batBottomCenterX - batBottomStartX;
                    fi = batBottomCenterY - batBottomStartY;
                    if (Math.abs(fh) > Math.abs(fi)) {
                        aa = ((int) ((Math.abs(fh) * 2.0f) / malletOuterRadScaled)) + 1;
                    } else {
                        aa = ((int) ((Math.abs(fi) * 2.0f) / malletOuterRadScaled)) + 1;
                    }
                    fh /= (float) aa;
                    fi /= (float) aa;
                    fj = (ballGoX - ballStartX) / ((float) aa);
                    fk = (ballGoY - ballStartY) / ((float) aa);
                    ab = 0;
                    do {
                        ab++;
                        fb = (ballStartX - batBottomStartX) + (((float) ab) * (fj - fh));
                        fc = (ballStartY - batBottomStartY) + (((float) ab) * (fk - fi));
                        fa = (float) (Math.sqrt((fb * fb) + (fc * fc)) + 1.0E-6f);
                        if (ab >= aa) {
                            break;
                        }
                    }
                    while (fa > ballRadScaled + malletOuterRadScaled);
                    if (fa <= ballRadScaled + malletOuterRadScaled) {
                        soundmallet();
                        ballGo2X = batBottomCenterX + ((fb * ((ballRadScaled + 1.0f) + malletOuterRadScaled)) / fa);
                        ballGo2Y = batBottomCenterY + ((fc * ((ballRadScaled + 1.0f) + malletOuterRadScaled)) / fa);
                        ballVel = (float) Math.sqrt((ballVelX * ballVelX) + (ballVelY * ballVelY));
                        fd = (float) Math.atan2((double) fb, (double) fc);
                        fe = ((float) Math.atan2((double) ballVelX, (double) ballVelY)) - 3.1415927f;
                        ff = (fd * 2.0f) - fe;
                        fg = ff - fd;
                        if (fg > 3.1415927f) {
                            fg -= 6.2831855f;
                        }
                        if (fg < -3.1415927f) {
                            fg += 6.2831855f;
                        }
                        if (fg > 1.57f) {
                            ff = fd + 1.57f;
                        }
                        if (fg < -1.57f) {
                            ff = fd - 1.57f;
                        }
                        fh = (batBottomCenterX - batBottomStartX) / ((float) (currentTime - ballTime));
                        fi = (batBottomCenterY - batBottomStartY) / ((float) (currentTime - ballTime));
                        fj = (float) Math.atan2((double) fh, (double) fi);
                        fg = fj - fd;
                        if (fg > 3.1415927f) {
                            fg -= 6.2831855f;
                        }
                        if (fg < -3.1415927f) {
                            fg += 6.2831855f;
                        }
                        if (ballVel > velMaxScaled * velHitMax) {
                            ballVel = velMaxScaled * velHitMax;
                        }
                        fk = (float) Math.abs(Math.cos(fg));
                        ballVelX = (float) ((((Math.sin(ff) * ballVel) * decelHit) + (fh * fk)) - ((fi * fk) * Math.sin(fg)));
                        ballVelY = (float) ((((Math.cos(ff) * ballVel) * decelHit) + (fi * fk)) + ((fh * fk) * Math.sin(fg)));
                        ballGoX = ballGo2X;
                        ballGoY = ballGo2Y;
                        hitFlag = 2;
                        hitTime = currentTime;
                    }
                    ballTime = currentTime;
                }
                ballStartX = ballGoX;
                ballStartY = ballGoY;
                batTopStartX = batTopCenterX;
                batBottomStartX = batBottomCenterX;
                batTopStartY = batTopCenterY;
                batBottomStartY = batBottomCenterY;
            }
            coordX = (int) ballGoX;
            coordY = (int) ballGoY;
            paint.setAntiAlias(true);
            paintBlur.setColor(ballBlurColor[0]);
            canvas.drawCircle((float) coordX, (float) coordY, (ballRadScaled * 7.0f) / 4.0f, paintBlur);
            canvas.drawCircle((float) coordX - 100, (float) coordY - 20, (ballRadScaled * 7.0f) / 4.0f, paintBlur);
            paint.setColor(ballColor[0]);
            canvas.drawCircle((float) coordX, (float) coordY, ballRadScaled, paint);
            canvas.drawCircle((float) coordX - 20, (float) coordY, ballRadScaled - 20, paint);
            paint.setStyle(Style.FILL);
            paint.setAntiAlias(true);
            paint.setTextSize((float) (screenW / 10));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setTextAlign(Align.LEFT);
            paint.setColor(scoreColor[0]);
            paintBlur.setColor(scoreBlurColor[0]);
            paint.setTextAlign(Align.RIGHT);
            canvas.drawText(new StringBuilder(String.valueOf(Integer.toString(scoreTop))).append("  ").toString(), (float) screenW, (float) (screenH / 4), paint);
            canvas.drawText(new StringBuilder(String.valueOf(Integer.toString(scoreBottom))).append("  ").toString(), (float) screenW, (float) ((screenH * 3) / 4), paint);

            //end the game if the game end time less than the current time
            if (currentTime > gameEndTime) {
                if (gameEnded == -1) {
                    gameEnded = 0;
                }

                paint.setColor(dataColors[(0 * colorsNo) + 0]);
                paint.setTextAlign(Align.CENTER);
                coordX = screenW / 2;
                coordY = screenH / 2;
                canvas.drawText(getResources().getString(R.string.game_over), (float) (coordX - 3), (float) (coordY - 3), paint);
                canvas.drawText(getResources().getString(R.string.game_over), (float) (coordX - 3), (float) (coordY + 3), paint);
                canvas.drawText(getResources().getString(R.string.game_over), (float) (coordX + 3), (float) (coordY - 3), paint);
                canvas.drawText(getResources().getString(R.string.game_over), (float) (coordX + 3), (float) (coordY + 3), paint);
                paint.setColor(dataColors[(0 * colorsNo) + 1]);
                canvas.drawText(getResources().getString(R.string.game_over), (float) coordX, (float) coordY, paint);
            }
            if (paused == 1) {
                drawStart = 0;
                drawNo = 3;
                drawquads();
            }
            if (gameEnded <= 0 && paused != 1) {
                invalidate();
            }

        }

        private void drawquads() {
            aa = 0;
            while (aa < drawNo) {
                if (dataQuads[drawStart + 11] == 0) {
                    drawPaint = paint;
                } else {
                    drawPaint = paintBlur;
                }
                ae = dataQuads[drawStart + 8];
                if (ae == 0) {
                    drawPaint.setStyle(Style.STROKE);
                } else {
                    drawPaint.setStyle(Style.FILL);
                }
                drawPaint.setStrokeWidth((float) ((canvasW * dataQuads[drawStart + 9]) / 1000));
                drawPaint.setColor(dataColors[(0 * colorsNo) + dataQuads[drawStart + 10]]);

                ab = (dataQuads[drawStart + 6] * canvasW) / 1000;
                ac = (dataQuads[drawStart + 7] * canvasH) / 1000;
                path.reset();
                path.moveTo((float) ab, (float) ac);
                af = 0;
                while (af < 4) {
                    int[] iArr;
                    int i;
                    iArr = dataQuads;
                    i = drawStart;
                    drawStart = i + 1;
                    ad = (iArr[i] * canvasW) / 1000;
                    iArr = dataQuads;
                    i = drawStart;
                    drawStart = i + 1;
                    ae = (iArr[i] * canvasH) / 1000;
                    path.lineTo((float) ad, (float) ae);
                    af++;
                }
                drawCanvas.drawPath(path, drawPaint);
                drawStart += 4;
                aa++;
            }
        }

        private void soundwall() {
            if (soundLoaded > 0 && useSound == 0) {
                if (soundWallAct == 0) {
                    soundPool.play(soundWall, volume, volume, 1, 0, 1.0f);
                }
                soundWallAct = 2;
            }
        }

        private void soundmallet() {
            if (soundLoaded > 0 && useSound == 0) {
                if (soundMalletAct == 0) {
                    soundPool.play(soundMallet, volume, volume, 1, 0, 1.0f);
                }
                soundMalletAct = 2;
            }
        }

        private void soundgoal() {
            if (soundLoaded > 0 && useSound == 0) {
                if (soundGoalAct == 0) {
                    soundPool.play(soundGoal, volume, volume, 1, 0, 1.0f);
                }
                soundGoalAct = 2;
                soundPool.play(soundGoal, volume, volume, 1, 0, 1.0f);
            }
        }
    }

    public class ScreenReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                if (paused == 0) {
                    pausedTime = currentTime;
                    pausedBallTime = ballTime;
                    pausedGameEndTime = gameEndTime;
                    paused = 1;
                }
            } else if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
                long currentTimeMillis = System.currentTimeMillis();
                currentTime = currentTimeMillis;
                paused2Time = currentTimeMillis;
                ballTime = (pausedBallTime - pausedTime) + currentTime;
                gameEndTime = (pausedGameEndTime - pausedTime) + currentTime;
            }
        }
    }


    public void onDestroy() {
        super.onDestroy();
        if (bgndBitmap != null) {
            bgndBitmap.recycle();
        }
        if (soundPool != null) {
            soundPool.release();
        }
    }

    public void onPause() {
        super.onPause();
        if (paused == 0) {
            pausedTime = currentTime;
            pausedBallTime = ballTime;
            pausedGameEndTime = gameEndTime;
            paused = 1;
        }
        finishGame();
    }

    public void onResume() {
        super.onResume();
        long currentTimeMillis = System.currentTimeMillis();
        currentTime = currentTimeMillis;
        paused2Time = currentTimeMillis;
        ballTime = (pausedBallTime - pausedTime) + currentTime;
        gameEndTime = (pausedGameEndTime - pausedTime) + currentTime;
        ((RelativeLayout)findViewById(R.id.layoutGame)).invalidate();
    }

    public void finishGame(){
        Intent intent = new Intent(this, Leadership.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    public void updateRank(boolean isWon){
        Player player = Player.getInstance();
        HashMap<String,String> params = new HashMap<String,String>();
        if(isWon){
            player.setGames_won(player.getGames_won()+1);
            player.setPoints(player.getPoints()+50);
        }else{
            player.setGames_lost(player.getGames_lost()+1);
        }
        int score = (player.getGames_won()*100)/(player.getGames_lost()+player.getGames_won());
        player.setRank(score);
        params.put("games_lost",String.valueOf(player.getGames_lost()));
        params.put("games_won",String.valueOf(player.getGames_won()));
        params.put("score",String.valueOf(score));
        params.put("coins",String.valueOf(player.getPoints()));
        params.put("username",player.getUsername());
        CustomJSONRequest request = new CustomJSONRequest(Request.Method.POST, Constants.UPDATE_SCORE, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        if (queue != null)
            queue.add(request);

    }
}
