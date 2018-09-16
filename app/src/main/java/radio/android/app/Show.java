package radio.android.app;


import radio.android.app.schedule.ScheduleRecyclerItem;
import radio.android.app.schedule.ScheduleRecyclerViewAdapter;

public class Show extends ScheduleRecyclerItem {

    private String day;

    public Show(String title, String date) {
        super(ScheduleRecyclerViewAdapter.CARD, title, date);
    }

    public String getTitle() {
        return super.getS1();
    }

    public String getTime() {
        return super.getS2();
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
