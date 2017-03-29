package com.example.avinash.nittcart;

/**
 * Created by AVINASH on 3/9/2017.
 */

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.gordonwong.materialsheetfab.AnimatedFab;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements AnimatedFab {

    SignupActivity register;
    ImageButton imageButton;
    RelativeLayout loginview, registerview, insidelv, Insiderv;
    Animation alphaAnimation ,alphaAnimation2,
            animShow, animHide , animShow2, animHide2 ;
    float pixelDensity;
    boolean flag = true;
    int x,y;

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;

    //-------------signup------------------//

    @Bind(R.id.input_name)
    EditText _nameText;
    //@Bind(R.id.input_address)
    EditText _addressText;
    @Bind(R.id.reg_input_email)
    EditText reg_emailText;
    //@Bind(R.id.input_mobile)
    EditText _mobileText;
    @Bind(R.id.reg_input_password)
    EditText reg_passwordText;
    //@Bind(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @Bind(R.id.btn_register)
    Button _signupButton;
    @Bind(R.id.link_login)
    TextView _loginLink;
    @Bind(R.id.skiplogin)
    TextView skiplogin;


////////////////////////////////////////

    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialoglogin);


        /*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialoglogin, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        //alertDialog.setCancelable(false);
        //alertDialog.setCanceledOnTouchOutside(false);
*/
        //ButterKnife.bind(this, dialogView);

        ButterKnife.bind(this);

            register = new SignupActivity();
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("inside", "yo");
                register.signup();
            }
        });

        skiplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                //Intent intent = condnew Intent(getApplicationContext(), LoginActivity.class);
                //startActivity(intent);
                //finish();
                flag = true;
                registerview.startAnimation(animHide2);
                registerview.setVisibility(View.GONE);
                loginview.setVisibility(View.VISIBLE);
                loginview.startAnimation(animShow2);
                //imageButton.setBackgroundResource(R.drawable.cart_and_plceorder_back);
                imageButton.setImageResource(R.drawable.ic_user);

                //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });


        ///////////////////////////////////////////
        pixelDensity = getResources().getDisplayMetrics().density;

        /*imageButton = (ImageButton)((Dialog) alertDialog).findViewById(R.id.user_profile_photo);
        loginview = (RelativeLayout)((Dialog) alertDialog).findViewById(R.id.relativeLayout);
        registerview = (RelativeLayout) ((Dialog) alertDialog).findViewById(R.id.relativeLayout2);
        insidelv = (RelativeLayout)((Dialog) alertDialog).findViewById(R.id.inside_rl);
        Insiderv = (RelativeLayout)((Dialog) alertDialog).findViewById(R.id.inside_rl2);
*/
        imageButton = (ImageButton)findViewById(R.id.user_profile_photo);
        loginview = (RelativeLayout)findViewById(R.id.relativeLayout);
        registerview = (RelativeLayout)findViewById(R.id.relativeLayout2);
        insidelv = (RelativeLayout)findViewById(R.id.inside_rl);
        Insiderv = (RelativeLayout)findViewById(R.id.inside_rl2);

        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
        alphaAnimation2= AnimationUtils.loadAnimation(this, R.anim.fadeout2);
        animShow = AnimationUtils.loadAnimation( this, R.anim.showlogin);
        animHide = AnimationUtils.loadAnimation( this, R.anim.transitionlogin);
        animShow2 = AnimationUtils.loadAnimation( this, R.anim.registershow);
        animHide2 = AnimationUtils.loadAnimation(this, R.anim.registerhide);


        SpringSystem springSystem = SpringSystem.create();
        final Spring spring = springSystem.createSpring();
        spring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                // You can observe the updates in the spring
                // state by asking its current value in onSpringUpdate.
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.2f);
                imageButton.setScaleX(scale);
                imageButton.setScaleY(scale);
            }
        });
        spring.setEndValue(0);

        imageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // When pressed start solving the spring to 1.
                        spring.setEndValue(1);
                        launchTwitter();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // When released start solving the spring to 0.
                        spring.setEndValue(0);
                        break;
                }
                return true;
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //spring.setEndValue(1);
                launchTwitter();
                //spring.setEndValue(0);
            }
        });


        ///////////////////////////////////////////////////////////////////////

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                flag=false;
                loginview.startAnimation( animHide );
                loginview.setVisibility(View.GONE);
                registerview.setVisibility(View.VISIBLE);
                registerview.startAnimation(animShow);
                //imageButton.setBackgroundResource(R.drawable.rounded_cancel_button);
                imageButton.setImageResource(R.mipmap.cancel);

            }
        });
    }



    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {

                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
        //super.onBackPressed();
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

    public void launchTwitter() {


        int hypotenuse = (int) Math.hypot(registerview.getWidth(), registerview.getHeight());

        if (flag) {

            hypotenuse = (int) Math.hypot(loginview.getWidth(), loginview.getHeight());
            x = loginview.getRight();
            y = loginview.getTop();
            int dx=loginview.getWidth();
            int dy=loginview.getHeight();

            x -= ((28 * pixelDensity) + (16 * pixelDensity));

            loginview.setVisibility(View.GONE);
            //loginview.startAnimation(alphaAnimation2);

            registerview.setVisibility(View.VISIBLE);
            imageButton.setBackgroundResource(R.drawable.user_profile_image_background);
            imageButton.setImageResource(R.mipmap.cancel);

            Animator anim = ViewAnimationUtils.createCircularReveal(registerview,x/2 , y/2+150, 0, hypotenuse);

            anim.setDuration(800);

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    //loginview.setVisibility(View.GONE);
                    //loginview.startAnimation(alphaAnimation2);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            anim.start();

            flag = false;
        } else {

            x = loginview.getRight();
            y = loginview.getTop();

            imageButton.setBackgroundResource(R.drawable.user_profile_image_background);
            imageButton.setImageResource(R.drawable.ic_newuser);

            Animator anim = ViewAnimationUtils.createCircularReveal(registerview, x/2, y/2+150, hypotenuse, 0);
            anim.setDuration(500);

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    registerview
                            .setVisibility(View.GONE);
                    loginview.setVisibility(View.VISIBLE);
                    loginview.startAnimation(alphaAnimation);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            anim.start();
            flag = true;

        }
    }

    @Override
    public void show() {
       show(0,0);
    }

    @Override
    public void show(float translationX, float translationY) {

    }

    @Override
    public void hide() {

    }
}
