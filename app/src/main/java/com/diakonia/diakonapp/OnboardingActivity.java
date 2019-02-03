package com.diakonia.diakonapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.diakonia.diakonapp.adapters.SectionsPagerAdapter;


public class OnboardingActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Button mSkipBtn, mFinishBtn;
    private ImageButton mNextBtn;
    private ImageView[] indicators = new ImageView[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        indicators = new ImageView[]{findViewById(R.id.intro_indicator_0),
                findViewById(R.id.intro_indicator_1),
                findViewById(R.id.intro_indicator_2)};

        mSkipBtn = findViewById(R.id.intro_btn_skip);
        mNextBtn = findViewById(R.id.intro_btn_next);
        mFinishBtn = findViewById(R.id.intro_btn_finish);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        Log.d("mequierodormir" , mSectionsPagerAdapter.toString());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.onboarding_pager_id);

        mSectionsPagerAdapter.AddFragment((Fragment)PlaceholderFragment.newInstance(0), "0");
        mSectionsPagerAdapter.AddFragment((Fragment)PlaceholderFragment.newInstance(1), "1");
        mSectionsPagerAdapter.AddFragment((Fragment)PlaceholderFragment.newInstance(2), "2");

        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                updateIndicators(position);

                mNextBtn.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                mFinishBtn.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        mSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishIntro();
            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(getItem(+1), true);
            }
        });

        mFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishIntro();
            }
        });

    }

    public void finishIntro(){
        Intent introIntent = new Intent(OnboardingActivity.this, LogInActivity.class);
        introIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(introIntent);
        //  update 1st time pref
        DiakoUtils.saveSharedSetting(OnboardingActivity.this, "PREF_USER_FIRST_TIME", "false");
    }


    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }

    void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setImageResource(
                    i == position ? R.drawable.ic_indicator_selected_24dp : R.drawable.ic_indicator_unselected_24dp
            );
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private ImageView mainImage;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_onboarding, container, false);

            mainImage = rootView.findViewById(R.id.startPopImg_id);
            int imageId = 0;
            int section = this.getArguments().getInt(ARG_SECTION_NUMBER);

            switch (section){
                case 0:
                    imageId = R.drawable.inicio1;
                    break;
                case 1:
                    imageId = R.drawable.inicio2;
                    break;
                case 2:
                    imageId = R.drawable.inicio3;
                    break;

                    default:
                        break;
            }
            this.mainImage.setImageResource(imageId);
            return rootView;
        }
    }

}
