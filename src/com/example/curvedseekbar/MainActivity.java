package com.example.curvedseekbar;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	CurvedSeekbar cs;
	SeekBar sb1, sb2;
	TextView value;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cs = (CurvedSeekbar) findViewById(R.id.curvedSeekbar1);
		sb1 = (SeekBar) findViewById(R.id.seekBar1);
		sb2 = (SeekBar) findViewById(R.id.seekBar2);
		value = (TextView) findViewById(R.id.textView3);
		sb1.setOnSeekBarChangeListener(sb1_change);
		sb2.setOnSeekBarChangeListener(sb2_change);
		cs.setOnSeekBarChangeListener(cs_change);
	}

	private OnSeekBarChangeListener sb1_change = new OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			if (fromUser) {
				cs.setProcess(progress);
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
		
	};
	
	private OnSeekBarChangeListener sb2_change = new OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			if (fromUser) {
				cs.setSecondaryProcess(progress);
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
		
	};

	private OnSeekBarChangeListener cs_change = new OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			if (fromUser) {
				value.setText("Value: " + progress);
				sb1.setProgress(progress);
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
		
	};
}
