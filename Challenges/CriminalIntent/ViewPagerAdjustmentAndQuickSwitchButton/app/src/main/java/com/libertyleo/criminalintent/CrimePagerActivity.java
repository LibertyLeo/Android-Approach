package com.libertyleo.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {
    private static final String EXTRA_CRIME_ID = "com.libertyleo.criminalIntent.crime_id";
    private List<Crime> mCrimes;

    private ViewPager mViewPager;
    private Button mFirstButton;
    private Button mLastButton;


    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // Since each time we scroll the pager, update the button status
                updateButtonStatueByPagerIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mFirstButton = (Button) findViewById(R.id.jump_first_button);
        mFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Switch to the first crime, and set the first button disabled.
                updateButtonStatueByPagerIndex(0);
                mViewPager.setCurrentItem(0);
            }
        });

        mLastButton = (Button) findViewById(R.id.jump_lat_button);
        mLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Switch to the last crime, and set the last button disabled.
                updateButtonStatueByPagerIndex(mCrimes.size() - 1);
                mViewPager.setCurrentItem(mCrimes.size() - 1);
            }
        });

        // Help to locate the correct crime index
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                // Each locating action should also update the button status.
                updateButtonStatueByPagerIndex(i);
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public void updateButtonStatueByPagerIndex(int index) {
        mFirstButton.setEnabled(index == 0 ? false : true);
        mLastButton.setEnabled(index == (mCrimes.size() - 1) ? false : true);
    }
}
