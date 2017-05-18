package app.andrey_voroshkov.chorus_laptimer;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import static app.andrey_voroshkov.chorus_laptimer.AppState.chbList;


/**
 * Created by meszfer
 */

public class BandScannerFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private View mRootView;
    private Context mContext;
    private BarDataSet dataSet;
    private BarData barData;

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
        List<BarEntry> entries = new ArrayList<BarEntry>();

        for (ChannelBandFreq data : chbList) {
            // entries.add(new Entry(data.band, data.currentRSSI));
            entries.add(new BarEntry((float) data.getFreq(), (float) 0));
        }

        dataSet = new BarDataSet(entries, "RSSI");
        barData = new BarData(dataSet);
        chart.setData(barData);
        chart.invalidate(); // refresh
        Log.d("Laptimer","I am here");
        AppState.getInstance().addListener(new IDataListener() {
            @Override
            public void onDataChange(DataAction dataItemName) {
                switch (dataItemName) {
                    case NDevices:
                    case DeviceBand:
                    case DeviceChannel:
                    case DeviceRSSI:
                        updateCurrentRSSI();
                        break;
                }
            }
        });
        return rootView;
    }

    public void updateCurrentRSSI() {
        BarChart chart = (BarChart) mRootView.findViewById(R.id.chart);
        int position = 0;
        final String deviceId = String.format("%X", position);

        //lekérem mi érkezett
        String bschannel = AppState.getInstance().getChannelText(position);
        int bsband = Integer.parseInt(AppState.getInstance().getBandText(position));
        int bsindex = getChannelBandIndex(bschannel, bsband);
/*
        IBarDataSet barDataSet = chart.getData().getDataSetByIndex(0);
        BarEntry bsbe = barDataSet.getEntryForIndex(bsindex);

        bsbe.setY(AppState.getInstance().getCurrentRssi(0));
        barDataSet.addEntry(bsbe);

        chart.notifyDataSetChanged();
        chart.invalidate();

        //utolsó esetén csatornát is váltani kell
        if (bsband == 7){
            AppState.getInstance().sendBtCommand("R" + deviceId + "C");
        }
        AppState.getInstance().sendBtCommand("R" + deviceId + "B");
*/
        Log.d("Laptimer","I am here");



        BarEntry be = dataSet.getEntryForIndex(bsindex);
        be.setY(100);

        //be.setY(AppState.getInstance().getCurrentRssi(0));
        dataSet.removeEntry(bsindex);
        barData.addEntry(be, bsindex);
        barData.notifyDataChanged(); // NOTIFIES THE DATA OBJECT
        chart.setData(barData);
        chart.notifyDataSetChanged(); // let the chart know it's data changed
        chart.invalidate(); // refresh

        //utolsó esetén csatornát is váltani kell
        if (bsband == 7){
            AppState.getInstance().sendBtCommand("R" + deviceId + "C");
        }
        AppState.getInstance().sendBtCommand("R" + deviceId + "B");
    }

    public int getChannelBandIndex(String ch, int band){
        int index = 0;
        for ( ChannelBandFreq i : chbList ) {
            if (i.getChannel().equals(ch) && i.getBand() == band){
                return index;
            }
            index++;
        }
        return -1;
    }
}
