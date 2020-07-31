package tdc.edu.vn.qlsv.GiaoDien;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tdc.edu.vn.qlsv.R;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class ActivitySplash extends AppCompatActivity {

    boolean running =true;
    private  static int SPLASH_TIMER =1000;

    Animation topAnim,bottomAim;
    TextView ThietBi;
    ImageView imageView;
    AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setController();
        setEvent();
    }
    private void setController()
    {

        ThietBi = findViewById(R.id.thietbi);
        imageView = (ImageView)findViewById(R.id.anh);



    }

    private void setEvent(){

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);




        ThietBi.setAnimation(bottomAim);
        imageView.setAnimation(topAnim);


        //imageView.setBackgroundResource(R.drawable.doimau);
        //anim=(AnimationDrawable)imageView.getBackground();
        //anim.start();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActivitySplash.this, DangNhap.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIMER);
    }

}
