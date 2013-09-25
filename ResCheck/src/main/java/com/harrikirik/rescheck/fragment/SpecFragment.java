package com.harrikirik.rescheck.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.harrikirik.rescheck.R;
import com.harrikirik.rescheck.util.Util;

public class SpecFragment extends Fragment {

    public static Fragment newInstance() {
        // No args for now
        return new SpecFragment();
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.spec_fragment_layout, null);

		final TextView textData = (TextView) view.findViewById(R.id.text_spec_info);
		StringBuilder dataBuff = new StringBuilder();
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		dataBuff.append("Model: " + android.os.Build.MODEL + "\n");
		dataBuff.append("Device: " + android.os.Build.DEVICE + "\n");
		dataBuff.append("Manufacturer: " + android.os.Build.MANUFACTURER + "\n");
		dataBuff.append("Product: " + android.os.Build.PRODUCT + "\n\n");
		dataBuff.append("Display id: " + display.getDisplayId() + "\n");
		dataBuff.append("Display width: " + display.getWidth() + "\n");
		dataBuff.append("Display height: " + display.getHeight() + "\n");
		String orientation = "ORIENTATION_UNDEFINED";
		switch (getResources().getConfiguration().orientation) {
		case android.content.res.Configuration.ORIENTATION_LANDSCAPE:
			orientation = "ORIENTATION_LANDSCAPE";
			break;
		case android.content.res.Configuration.ORIENTATION_PORTRAIT:
			orientation = "ORIENTATION_PORTRAIT";
			break;
		case android.content.res.Configuration.ORIENTATION_SQUARE:
			orientation = "ORIENTATION_SQUARE";
			break;
		}
		dataBuff.append("Screen orientation: " + orientation + "\n");

		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		dataBuff.append("Screen density: " + metrics.density + "\n");
		dataBuff.append("Screen densityDpi: " + metrics.densityDpi + "\n");
		dataBuff.append("Screen scaledDensity: " + metrics.scaledDensity + "\n");

		final int screenSize = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);

		if (screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
			dataBuff.append("Screen size: NORMAL\n");
		} else if (screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL) {
			dataBuff.append("Screen size: SMALL\n");
		} else if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
			dataBuff.append("Screen size: LARGE\n");
		} else if (screenSize == 4) {
			dataBuff.append("Screen size: XLARGE\n");
		} else if (screenSize == Configuration.SCREENLAYOUT_SIZE_UNDEFINED) {
			dataBuff.append("Screen size: UNDEFINED\n");
		}

		final int screenLength = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_LONG_MASK);

		if (screenLength == Configuration.SCREENLAYOUT_LONG_YES) {
			dataBuff.append("Screen length: LONG\n");
		} else if (screenLength == Configuration.SCREENLAYOUT_LONG_NO) {
			dataBuff.append("Screen length: NOTLONG\n");
		} else if (screenLength == Configuration.SCREENLAYOUT_LONG_UNDEFINED) {
			dataBuff.append("Screen length: UNDEFINED\n");
		}

		dataBuff.append("Device memory class: " + (Util.getMemoryClass(getActivity()) / 1024d / 1024d) + " MB\n");
		dataBuff.append("Device allowed memory: " + (Util.getAllowedMemory() / 1024d / 1024d) + " MB\n");
		textData.setText(dataBuff.toString());

		return view;
	}

}
