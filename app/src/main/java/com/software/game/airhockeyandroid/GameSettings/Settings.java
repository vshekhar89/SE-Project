package com.software.game.airhockeyandroid.GameSettings;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.software.game.airhockeyandroid.R;

/**
 * Created by neela on 10/14/2015.
 */
public class Settings extends AppCompatActivity implements View.OnClickListener {
    ImageButton mVolumeControl;
    ImageButton mVibrationControl;
    private AudioManager manager;
    private int mVolumeMode;
    public static float mGameVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_settings);
        initialize();
    }

    private void initialize() {
        manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mVolumeMode = manager.getRingerMode();
        mVolumeControl = (ImageButton) findViewById(R.id.volume_on_off);
        mVibrationControl = (ImageButton) findViewById(R.id.vibration_on_off);
        setInitialVolume();
        mVolumeControl.setOnClickListener(this);
        mVibrationControl.setOnClickListener(this);
    }

    public void setVolumeControl(){
        if (mVolumeMode == AudioManager.RINGER_MODE_NORMAL) {
            mGameVolume= 0.99f;
        } else if (mVolumeMode == AudioManager.RINGER_MODE_SILENT) {
            mGameVolume = 0.0f;
        } else if (mVolumeMode == AudioManager.RINGER_MODE_VIBRATE) {
            mGameVolume = 0.0f;
        }
    }
    private void setInitialVolume() {
        if (mVolumeMode == AudioManager.RINGER_MODE_NORMAL) {
            mVolumeControl.setImageResource(R.mipmap.ic_volume_on);
        } else if (mVolumeMode == AudioManager.RINGER_MODE_SILENT) {
            mVolumeControl.setImageResource(R.mipmap.ic_volume_off);
        } else if (mVolumeMode == AudioManager.RINGER_MODE_VIBRATE) {
            mVolumeControl.setImageResource(R.mipmap.ic_volume_off);
        }
    }

    private void changeVolume() {
        if (mVolumeMode == AudioManager.RINGER_MODE_NORMAL) {
            manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            mVolumeMode = AudioManager.RINGER_MODE_SILENT;
            mVolumeControl.setImageResource(R.mipmap.ic_volume_off);
            mGameVolume = 0.0f;
        } else if (mVolumeMode == AudioManager.RINGER_MODE_SILENT) {
            manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            mVolumeMode = AudioManager.RINGER_MODE_NORMAL;
            mVolumeControl.setImageResource(R.mipmap.ic_volume_on);
            mGameVolume = 0.99f;
        } else if (mVolumeMode == AudioManager.RINGER_MODE_VIBRATE) {
            manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            mVolumeMode = AudioManager.RINGER_MODE_NORMAL;
            mVolumeControl.setImageResource(R.mipmap.ic_volume_on);
            mGameVolume = 0.99f;
        }
    }

    private void handleVibration() {
        if (mVolumeMode == AudioManager.RINGER_MODE_NORMAL) {
            manager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            mVolumeMode = AudioManager.RINGER_MODE_VIBRATE;
            mVolumeControl.setImageResource(R.mipmap.ic_volume_off);
            mGameVolume = 0.0f;
        } else if (mVolumeMode == AudioManager.RINGER_MODE_VIBRATE) {
            manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            mVolumeMode = AudioManager.RINGER_MODE_NORMAL;
            mVolumeControl.setImageResource(R.mipmap.ic_volume_on);
            mGameVolume = 0.99f;
        } else if (mVolumeMode == AudioManager.RINGER_MODE_SILENT) {
            manager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            mVolumeMode = AudioManager.RINGER_MODE_VIBRATE;
            mVolumeControl.setImageResource(R.mipmap.ic_volume_off);
            mGameVolume = 0.0f;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.volume_on_off)
            changeVolume();
        if (id == R.id.vibration_on_off) {
            handleVibration();
        }
    }
}
