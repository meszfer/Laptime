package app.andrey_voroshkov.chorus_laptimer;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;


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

        BarChart chart = (BarChart) rootView.findViewById(R.id.chart);



        DeviceState[] dataObjects =  new DeviceState[10];


        List<BarEntry> entries = new ArrayList<BarEntry>();
        int i = 1;
        int e = 10;
        for (DeviceState data : dataObjects) {

            // turn your data into Entry objects
            // entries.add(new Entry(data.band, data.currentRSSI));
            entries.add(new BarEntry(i, e));
            i++;e+=10;
        }

        BarDataSet dataSet = new BarDataSet(entries, "Label"); // add entries to dataset
        //dataSet.setColor(...);
        //dataSet.setValueTextColor(...); // styling, ...

        BarData barData = new BarData(dataSet);
        chart.setData(barData);
        chart.invalidate(); // refresh


//Beállítások
//     5658, 5695, 5732, 5769, 5806, 5843, 5880, 5917, // Raceband
//     5865, 5845, 5825, 5805, 5785, 5765, 5745, 5725, // Band A
//     5733, 5752, 5771, 5790, 5809, 5828, 5847, 5866, // Band B
//     5705, 5685, 5665, 5645, 5885, 5905, 5925, 5945, // Band E
//     5740, 5760, 5780, 5800, 5820, 5840, 5860, 5880, // Band F / Airwave
//     5362, 5399, 5436, 5473, 5510, 5547, 5584, 5621 // Band D / 5.3


        //létetés lefelé cs
                AppState.getInstance().sendBtCommand("R" + deviceId + "c");
        //léptetés felfelé cs
                AppState.getInstance().sendBtCommand("R" + deviceId + "C");
        //létetés lefelé b
                AppState.getInstance().sendBtCommand("R" + deviceId + "b");
        //léptetés felfelé b
                AppState.getInstance().sendBtCommand("R" + deviceId + "B");

//visszaadja a beállított értékeket
        AppState.getInstance().getChannelText(position);
        AppState.getInstance().getBandText(position);


//rssi beállítás
 /*
        ProgressBar bar = (ProgressBar) mRootView.findViewById(R.id.rssiBar);
        TextView txt = (TextView) mRootView.findViewById(R.id.txtRssi);
        int curRssi = AppState.getInstance().getCurrentRssi(i);
        int rssiThreshold = AppState.getInstance().getRssiThreshold(i);
        bar.setProgress(AppState.convertRssiToProgress(curRssi));
        int colorId = (curRssi > rssiThreshold) ? R.color.colorAccent : R.color.colorPrimary;
        int color = ContextCompat.getColor(mContext, colorId);
        bar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        txt.setText(Integer.toString(curRssi));*/
// no és ez lesz

        //0 az első vevő
        int position = 0;
        final String deviceId = String.format("%X", position);
        int curRssi = AppState.getInstance().getCurrentRssi(i);
        for (int cs = 0; cs < 5; cs++){
            AppState.getInstance().getCurrentRssi(position);
            for (int b = 0; b < 7; b++){
                AppState.getInstance().sendBtCommand("R" + deviceId + "B");
                AppState.getInstance().getCurrentRssi(position);
            }
            AppState.getInstance().sendBtCommand("R" + deviceId + "C");
        }
        return rootView;
    }


}
