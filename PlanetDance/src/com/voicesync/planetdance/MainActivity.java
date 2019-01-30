package com.voicesync.planetdance;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;

public class MainActivity extends Activity {
	Spinner sp1, sp2;
	SeekBar sbN;
	PlanetDance pd;
	int nps=0;
	PlanetAdapter plAdapter=new PlanetAdapter();
	String copyright="(c) VoiceSync.org";

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
	}
	private void init() {
		pd=(PlanetDance)findViewById(R.id.planetDance1);
		pd.setOnClickListener(new OnClickListener(){
			@Override public void onClick(View arg0) { 	setPreset(nps++);		}});
		sp1=(Spinner)findViewById(R.id.spin1); sp1.setAdapter(adapterPlanets(pd.getNames()));
		sp2=(Spinner)findViewById(R.id.spin2); sp2.setAdapter(adapterPlanets(pd.getNames()));
		sp1.setOnItemSelectedListener(plAdapter); sp2.setOnItemSelectedListener(plAdapter);
		sbN=(SeekBar)findViewById(R.id.sbN);
		sbN.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			@Override public void onProgressChanged(SeekBar sb, int pos, boolean fromUser) {
				if (fromUser) draw();
			}
			@Override public void onStartTrackingTouch(SeekBar arg0) {}
			@Override public void onStopTrackingTouch(SeekBar arg0) {}}
		);
		sbN.setProgress(8);
		setPreset(0);
	}
	private class PlanetAdapter implements OnItemSelectedListener {
		@Override public void onItemSelected(AdapterView<?> av, View v, int pos, long arg3) {
			draw();
		}
		@Override public void onNothingSelected(AdapterView<?> av) {}
	}
	private void setPreset(int i) {
		pd.setPreset(i);		
		sp1.setSelection(pd.getP1()); sp2.setSelection(pd.getP2()); sbN.setProgress(pd.getNorbit());
	}
	public void draw() {
		pd.setPlanets(sp1.getSelectedItemPosition(), sp2.getSelectedItemPosition(), sbN.getProgress());		
	}
	private ArrayAdapter<String> adapterPlanets(String[]s) { // return String[] adapter
		ArrayAdapter<String> dataAdapterSamp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, 
				new ArrayList<String>(Arrays.asList(s)));
		dataAdapterSamp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return dataAdapterSamp;
	}
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
