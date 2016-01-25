package com.bigstark.gunner;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.bigstark.gunner.library.Bullet;
import com.bigstark.gunner.library.Gunner;

public class GunnerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gunner);
        shoot();
    }

    private void shoot() {
        Gunner.shoot(this);
    }

    @Bullet(sequence = 1, delay = 1000)
    private void load1() {
        Log.v("TAG", "load1");
    }

    @Bullet(sequence = 2, delay = 2000)
    private void load2() {
        Log.v("TAG", "load2");
    }

    @Bullet(sequence = 3, delay = 3000)
    void load3() {
        Log.v("TAG", "load3");
        Gunner.cancel(this);
    }

    @Bullet(sequence = 4)
    void load4() {
        Log.v("TAG", "load4");
    }
}