package squirrel.pp.ua.arrive.view;

import android.app.Activity;

public interface MapView {
    Activity getActivity();

    void switchCheckedTraceTB(boolean enable);

    void massageSetDestination();
}
