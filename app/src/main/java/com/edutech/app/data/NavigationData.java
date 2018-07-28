package com.edutech.app.data;

import com.edutech.app.R;
import com.edutech.app.prototype.NavigationItemPrototype;

import java.util.ArrayList;

/**
 * Created by vedant on 31/8/17.
 */

public class NavigationData {
    public static ArrayList<NavigationItemPrototype> navigationItemPrototypes = new ArrayList<>();
    public static void loadData(){
        navigationItemPrototypes.clear();
        navigationItemPrototypes.add(new NavigationItemPrototype("Home", R.drawable.home_grey));
        navigationItemPrototypes.add(new NavigationItemPrototype("Quick Demo",R.drawable.quick_demo_icon));
        navigationItemPrototypes.add(new NavigationItemPrototype("Start Course",R.drawable.start_course));
        navigationItemPrototypes.add(new NavigationItemPrototype("Our Vendors",R.drawable.shops));
           navigationItemPrototypes.add(new NavigationItemPrototype("View Online",R.drawable.view_online));

        //   navigationItemPrototypes.add(new NavigationItemPrototype("Profile"));
       // navigationItemPrototypes.add(new NavigationItemPrototype("Help"));
        //navigationItemPrototypes.add(new NavigationItemPrototype("Feedback"));
        //navigationItemPrototypes.add(new NavigationItemPrototype("Terms & Conditions"));
       // navigationItemPrototypes.add(new NavigationItemPrototype("Privacy Policies"));


    }
}
