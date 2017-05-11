package app.andrey_voroshkov.chorus_laptimer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by meszfer on 2017.05.11..
 */

public class BandScannerFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private View mRootView;
    private Context mContext;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.race_setup, container, false);
        mRootView = rootView;
        mContext = getContext();
//ide kéne a valami

        return rootView;
    }

}
