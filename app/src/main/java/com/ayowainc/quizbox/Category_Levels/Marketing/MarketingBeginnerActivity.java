package com.ayowainc.quizbox.Category_Levels.Marketing;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ayowainc.quizbox.MenuHomeScreenActivity;
import com.ayowainc.quizbox.R;
import com.ayowainc.quizbox.User.LoginActivity;
import com.ayowainc.quizbox.User.UserProfileActivity;
import com.ayowainc.quizbox.questionsModelClass;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MarketingBeginnerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE = 0.7f;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button navToggler_btn, ShareQue_btn, Next_btn;
    LinearLayout linearLayout, linearLayout1;
    TextView txtQuestions, txtQuestionsIndicator;
    Dialog dialog;
    private int count = 0;
    private int position = 0;
    private List<questionsModelClass> list;
    private int score = 0;
    private InterstitialAd mInterstitialAd;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); ///Eneter into fullscreen mode
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_questions_view);

        //All Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navToggler_btn = findViewById(R.id.action_menu_presenter);
        linearLayout = findViewById(R.id.main_content);
        linearLayout1 = findViewById(R.id.options_layout);
        txtQuestions = findViewById(R.id.question_view);
        txtQuestionsIndicator = findViewById(R.id.no_of_questions_view);
        ShareQue_btn = findViewById(R.id.share_que_btn);
        Next_btn = findViewById(R.id.next_btn);

        final MediaPlayer level_lose = MediaPlayer.create(this, R.raw.level_lose);///Play sound when user loses level
        final MediaPlayer level_won = MediaPlayer.create(this, R.raw.applause_wav);///Play sound when user wins level

        ShareQue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = list.get(position).getQuestions() + "\n" +
                        "A :" + " " + list.get(position).getOptionA() + "\n" +
                        "B :" + " " + list.get(position).getOptionB() + "\n" +
                        "C :" + " " + list.get(position).getOptionC() + "\n" +
                        "D :" + " " + list.get(position).getOptionD();
                String shareSub = "Your subject here";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });

        ////Google ads- interstatial Integration-------------------///////////////
        MobileAds.initialize(this, getString(R.string.admob_id));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.inter_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });

        dialog = new Dialog(this, R.style.AnimateDialog);


        ////////////////////////////////////////////////////////////////////BEGINNER MARKETING QUESTIONS////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////ADD MARKETING BEGINNER QUESTIONS HERE////////////////////////////////////////////////////////////////////////////////////////
        list = new ArrayList<>();
        list.add(new questionsModelClass("What is the stage of PLC", "Introduction", "Growth", "Maturity & Decline", "All options", "All options"));
        list.add(new questionsModelClass("Market control process consists", "Correcting Deviations", "Performance Appraisal", "Formation of Performance standards ", "All options", "All options"));
        list.add(new questionsModelClass("The marketing plan provides both", "The vision and control", "The vision and the post", "The vision and the direction", "The vision and the cost", "The vision and the direction"));
        list.add(new questionsModelClass("\"A marketing policy is a statement of a course of action which will be followed under a given set of circumstances.\" Who said it?", "Daniel Siaw", "William J. Stanton", "Manson and Rath", "None of these", "William J. Stanton"));
        list.add(new questionsModelClass("Marketing research does not normally", "Provide a continuous source of information", "Gather environmental information", "Relate to all aspects of marketing operations", "Describe the current situation", "Provide a continuous source of information"));
        list.add(new questionsModelClass("Which of the following is not factors of the demand variable, according to Philip Kotler?", "Competition Variable", "Customer Variable", "Environment Variable", "All of the above", "Customer Variable"));
        list.add(new questionsModelClass("Which of the following is not controllable variable of marketing management?", "Economic Environment", "Competitive Environment", "Technological Environment", "Science Environment", "Economic Environment"));
        list.add(new questionsModelClass("The marketing concerns requires both fixed capital and", "Share Capital", "Flexible Capital", "Working Capital", "Long-term Capital", "Working Capital"));
        list.add(new questionsModelClass("Promotion mix is the particular combination of promotional tools used by a company to _____ with its audiences.", "Communicate", "Plan", "Help", "Purchasing Decision", "Communicate"));
        list.add(new questionsModelClass("Internet Marketing does not deal with", "Advertising", "Display Advertising", "Interactive Marketing", "E-mail Marketing and Web advertising", "Display Advertising"));


        for (int i = 0; i < 4; i++) {
            linearLayout1.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAns((Button) v);
                }
            });
        }

        txtQuestionsIndicator.setText(position + 1 + "/" + list.size());

        playAnim(txtQuestions, 0, list.get(position).getQuestions());

        Next_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                Next_btn.setEnabled(false);
                Next_btn.setAlpha(0.7f);
                enableOptions(true);
                position++;


                if (position == list.size()) {
                    //Score Activities
                    if (score <= 2) {

                        Button try_again, share;
                        dialog.setContentView(R.layout.activity_fail_20_layout);
                        try_again = dialog.findViewById(R.id.try_again_btn);
                        share = dialog.findViewById(R.id.share_btn);

                        level_lose.start();

                        try_again.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent BG = new Intent(getApplicationContext(), MarketingBeginnerActivity.class); //If User get 20% let him or her play again
                                startActivity(BG);
                            }
                        });

                        ///Share Quiz Box to friends
                        share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = "Hey! I just played Quiz Box and it's WONDERFUL!";
                                String shareSub = "Your subject here";
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                            }
                        });

                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();


                    } else if (score <= 4) {

                        Button try_again, share;
                        dialog.setContentView(R.layout.activity_pass_50_layout);
                        try_again = dialog.findViewById(R.id.try_again_btn);
                        share = dialog.findViewById(R.id.share_btn);

                        level_lose.start();

                        try_again.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent BG = new Intent(getApplicationContext(), MarketingBeginnerActivity.class); ///If User get 50% let him or her play again
                                startActivity(BG);
                            }
                        });

                        ///Share Quiz Box to friends
                        share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = "Hey! I just played Quiz Box and it's WONDERFUL!";
                                String shareSub = "Your subject here";
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                            }
                        });

                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();


                    } else if (score <= 9) {

                        Button promote_btn, share;
                        dialog.setContentView(R.layout.activity_pass_70_layout);
                        promote_btn = dialog.findViewById(R.id.nl_btn);
                        share = dialog.findViewById(R.id.share_btn);

                        level_won.start();

                        promote_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent BG = new Intent(getApplicationContext(), MarketingProfessionalActivity.class); ///If User get 70% let him to next level
                                startActivity(BG);
                            }
                        });


                        ///Share Quiz Box to friends
                        share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = "Hey! I just played Quiz Box and it's WONDERFUL!";
                                String shareSub = "Your subject here";
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                            }
                        });

                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                    } else if (score == 10) {

                        Button promote_btn, share;
                        dialog.setContentView(R.layout.activity_pass_100_layout);
                        promote_btn = dialog.findViewById(R.id.nl_btn);
                        share = dialog.findViewById(R.id.share_btn);
                        level_won.start();

                        promote_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent BG = new Intent(getApplicationContext(), MarketingProfessionalActivity.class); ///If User get 100% promote him or her to next level
                                startActivity(BG);
                            }
                        });

                        ///Share Quiz Box to friends
                        share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = "Hey! I just played Quiz Box and it's WONDERFUL!";
                                String shareSub = "Your subject here";
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                            }
                        });


                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                    }
                    return;
                }

                count = 0;
                playAnim(txtQuestions, 0, list.get(position).getQuestions());

            }
        });

        navigationDrawer();


    }

    ///////////////////////////////////////////////////////////////////ANIMATING SCREEN/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void playAnim(final View view, final int value, final String data) {

        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                if (value == 0 && count < 4) {
                    String option = "";
                    if (count == 0) {
                        option = list.get(position).getOptionA();
                    } else if (count == 1) {
                        option = list.get(position).getOptionB();
                    } else if (count == 2) {
                        option = list.get(position).getOptionC();
                    } else if (count == 3) {
                        option = list.get(position).getOptionD();
                    }
                    playAnim(linearLayout1.getChildAt(count), 0, option);
                    count++;
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onAnimationEnd(Animator animation) {

                if (value == 0) {

                    try {
                        ((TextView) view).setText(data);
                        txtQuestionsIndicator.setText(position + 1 + "/" + list.size());
                    } catch (ClassCastException ex) {
                        ((Button) view).setText(data);
                    }
                    view.setTag(data);

                    playAnim(view, 1, data);

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void checkAns(Button selectedOptions) {
        enableOptions(false);
        Next_btn.setEnabled(true);
        Next_btn.setAlpha(1);
        if (selectedOptions.getText().toString().equals(list.get(position).getCorrectAnswer())) {
            //correct Answer
            score++;
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.ding);
            selectedOptions.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#14E39A")));
            mp.start();
        } else {
            //wrong Answer
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.wrong_buzzer);
            selectedOptions.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF2B55")));
            Button correctOption = linearLayout1.findViewWithTag(list.get(position).getCorrectAnswer());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#14E39A")));

            mp.start();
        }
    }

    private void enableOptions(boolean enable) {
        for (int i = 0; i < 4; i++) {
            linearLayout1.getChildAt(i).setEnabled(enable);
            if (enable) {
                linearLayout1.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2133A0")));
            }
        }
    }

    ///////////////////////////////////////////////////////////////////ALL ABOUT NAVIGATION DRAWER/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void navigationDrawer() {

        //Navigation Drawer

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        navToggler_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();

    }

    ////////////////////////////////////////////////////////////ANIMATE NAV DRAWER////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void animateNavigationDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.cat_heading));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                final float diffScaledOffset = slideOffset*(1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                linearLayout.setScaleX(offsetScale);
                linearLayout.setScaleY(offsetScale);


                final float xOffset = drawerView.getWidth()*slideOffset;
                final float xOffsetDiff = linearLayout.getWidth()*diffScaledOffset/2;
                final float xTranslation = xOffset - xOffsetDiff;
                linearLayout.setTranslationX(xTranslation);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.home) {
            Intent home = new Intent(getApplicationContext(), MenuHomeScreenActivity.class);
            startActivity(home);
            MarketingBeginnerActivity.super.onBackPressed();

        } else if (menuItem.getItemId() == R.id.rate) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=")));
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.thedonuttech.tk")));
            }
        } else if (menuItem.getItemId() == R.id.user_profile) {
            Intent user_profile = new Intent(getApplicationContext(), UserProfileActivity.class);
            startActivity(user_profile);
        } else if (menuItem.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        return true;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

