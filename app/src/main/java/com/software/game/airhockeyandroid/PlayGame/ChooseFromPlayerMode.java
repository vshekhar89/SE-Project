package com.software.game.airhockeyandroid.PlayGame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.software.game.airhockeyandroid.R;

/**
 * Created by Abhishek on 11/2/2015.
 */
public class ChooseFromPlayerMode extends AppCompatActivity implements View.OnClickListener{

    Button mSingle;
    Button mMulti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_choose_play_options);
        mSingle = (Button) findViewById(R.id.single_player);
        mMulti = (Button) findViewById(R.id.multi_player);
        mSingle.setOnClickListener(this);
        mMulti.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.single_player:
                Intent intent = new Intent(this,SinglePlayerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.multi_player:
                Toast.makeText(this,R.string.coming_soon,Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
