package app.andrey_voroshkov.chorus_laptimer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.SeekBar;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;


/**
 * Created by meszfer
 */

public class BandScannerFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private View mRootView;
    private Context mContext;


    protected BarChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;

    public BandScannerFragment() {

    }
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static BandScannerFragment newInstance(int sectionNumber) {
        BandScannerFragment fragment = new BandScannerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.band_scanner, container, false);
        mRootView = rootView;
        mContext = getContext();

        LineChart chart = (LineChart) rootView.findViewById(R.id.chart);



        DeviceState[] dataObjects =  new DeviceState[10];


        ArrayList<Entry> entries = new ArrayList<Entry>();
        int i = 1;
        int e = 10;
        for (DeviceState data : dataObjects) {

            // turn your data into Entry objects
           // entries.add(new Entry(data.band, data.currentRSSI));
            entries.add(new Entry(i, e));
            i++;e+=10;
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        //dataSet.setColor(...);
        //dataSet.setValueTextColor(...); // styling, ...

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh


        return rootView;
    }


}
