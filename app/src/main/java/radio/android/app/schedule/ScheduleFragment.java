package radio.android.app.schedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import radio.android.app.Constants;
import radio.android.app.R;
import radio.android.app.Show;

public class ScheduleFragment extends Fragment {

    public static final String TAG = "ScheduleFragment";

    private ScheduleRecyclerViewAdapter mAdapter;
    private ArrayList<ScheduleRecyclerItem> mContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate the fragment's view
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        // initialize the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.rv_schedule);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //if (mContent.get(position).getViewType() == ScheduleRecyclerViewAdapter.CARD)
                //    return 1;
                //else
                    return 3;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ScheduleRecyclerViewAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();

        getContent();
        mAdapter.addContent(mContent);
    }

    public void getContent() {
        mContent = new ArrayList<>();
        Schedule schedule = new Schedule(getActivity());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:m a");
        // E - day name in week
        // a - am/pm marker
        // h - hour in am/pm (1-12)
        // m - minute in hour

        // get today's date

        String[] DAYS_OF_WEEK = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        Date today = new Date();
        String todayDayOfWeek = dayFormat.format(today);

        mContent.add(new ScheduleRecyclerItem(ScheduleRecyclerViewAdapter.SECTION_HEADER, "Full Schedule", ""));
        int index = Arrays.asList(DAYS_OF_WEEK).indexOf(todayDayOfWeek);

        if ( index == 0)  {
            for (int i = index; i < 7; i++) {
                mContent.add(new ScheduleRecyclerItem(ScheduleRecyclerViewAdapter.DAY_SCHEDULE, Constants.DAYS_OF_WEEK[i], ""));

            String mDay = Constants.DAYS_OF_WEEK[i];

                ArrayList<Show> showsToday1 = schedule.getShowByDay(mDay);
                for (int j = 0; j < showsToday1.size(); j++) {
                    Show show = showsToday1.get(j);
                    mContent.add(show);
                }
            }
        } else {
            for (int i = index; i < 7; i++) {
                mContent.add(new ScheduleRecyclerItem(ScheduleRecyclerViewAdapter.DAY_SCHEDULE, Constants.DAYS_OF_WEEK[i], ""));

            String mDay = Constants.DAYS_OF_WEEK[i];

                ArrayList<Show> showsToday1 = schedule.getShowByDay(mDay);
                for (int j = 0; j < showsToday1.size(); j++) {
                    Show show = showsToday1.get(j);
                    mContent.add(show);
                }
            }
            for (int k = 0; k < index; k++) {
                mContent.add(new ScheduleRecyclerItem(ScheduleRecyclerViewAdapter.DAY_SCHEDULE, Constants.DAYS_OF_WEEK[k], ""));

            String mDay = Constants.DAYS_OF_WEEK[k];

                ArrayList<Show> showsToday2 = schedule.getShowByDay(mDay);
                for (int j = 0; j < showsToday2.size(); j++) {
                    Show show = showsToday2.get(j);
                    mContent.add(show);
                }
            }
        }
        schedule.close();
    }
}
