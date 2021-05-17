package ma.ensias.ticket_me.activities.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.LinkedList;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.api.APIClient;
import ma.ensias.ticket_me.api.APIInterface;
import ma.ensias.ticket_me.fragments.ListEventsFragment;
import ma.ensias.ticket_me.model.Event;
import ma.ensias.ticket_me.response.ResponseListEvents;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private  LinkedList<Event> adminEvents,otherEvents;


    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        this.adminEvents = adminEvents;
        this.otherEvents =otherEvents;

    }

    @Override
    public Fragment getItem(int position) {
        //mContext.getSharedPreferences( "LoginForm",Context.MODE_PRIVATE).getInt("ID_SESSION",1);
        Fragment frg = null;

        switch (position) {
            case 0:
                frg = new ListEventsFragment(true);
                break;
            case 1:
                frg = new ListEventsFragment(false);
        }

        return frg;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }

}