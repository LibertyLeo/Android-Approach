package com.libertyleo.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Leo_Lei on 8/17/17.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
