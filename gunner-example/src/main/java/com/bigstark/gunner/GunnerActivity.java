package com.bigstark.gunner;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigstark.gunner.library.Bullet;
import com.bigstark.gunner.library.Gunner;

public class GunnerActivity extends Activity {

    private ImageView imageview;
    private ImageChanger imageChanger;


    private TextView textview;
    private TextChanger textChanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gunner);
        imageview = (ImageView) findViewById(R.id.imageview);
        textview = (TextView) findViewById(R.id.textview);

        imageChanger = new ImageChanger();
        textChanger = new TextChanger();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Gunner.shoot(imageChanger, textChanger);
    }


    @Override
    protected void onStop() {
        super.onStop();
        Gunner.cancel(imageChanger, textChanger);
    }


    class ImageChanger {
        @Bullet(sequence = 1, delay = 500)
        private void load1() {
            imageview.setImageResource(R.drawable.android1);
        }

        @Bullet(sequence = 2, delay = 500)
        private void load2() {
            imageview.setImageResource(R.drawable.android2);
        }

        @Bullet(sequence = 3, delay = 500)
        void load3() {
            imageview.setImageResource(R.drawable.android3);
        }

        @Bullet(sequence = 4, delay = 500)
        void load4() {
            imageview.setImageResource(R.drawable.android4);
        }

        @Bullet(sequence = 5, delay = 500)
        void load5() {
            imageview.setImageResource(R.drawable.android5);
        }

        @Bullet(sequence = 6, delay = 500)
        void load6() {
            imageview.setImageResource(R.drawable.android6);
            Gunner.cancel(this);
            Gunner.shoot(this);
        }
    }


    class TextChanger {
        @Bullet(sequence = 1, delay = 500)
        private void load1() {
            textview.setText("android1");
        }

        @Bullet(sequence = 2, delay = 500)
        private void load2() {
            textview.setText("android2");
        }

        @Bullet(sequence = 3, delay = 500)
        void load3() {
            textview.setText("android3");
        }

        @Bullet(sequence = 4, delay = 500)
        void load4() {
            textview.setText("android4");
        }

        @Bullet(sequence = 5, delay = 500)
        void load5() {
            textview.setText("android5");
        }

        @Bullet(sequence = 6, delay = 500)
        void load6() {
            textview.setText("android6");
            Gunner.cancel(this);
            Gunner.shoot(this);
        }
    }

}