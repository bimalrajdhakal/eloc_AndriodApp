package com.nexogen.routefinder.intefaces;

import android.os.Bundle;
import android.support.v4.app.Fragment;


public interface FragmentMessangers {
    void changeFragment(Fragment fragment, Bundle data, String fragmentTag, boolean addToBackStack);

}
