package com.example.periscopelayout;

import com.example.nfcdemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PeriscopeActivity extends Activity implements OnClickListener{
	private Button mShowBT;
	// ÐÄÐÍÆøÅÝ    
	private PeriscopeLayout periscopeLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_periscope);
		mShowBT = (Button) findViewById(R.id.btn_show);
		periscopeLayout = (PeriscopeLayout) findViewById(R.id.periscope);
		mShowBT.setOnClickListener(this);
		
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_show:
			periscopeLayout.addHeart();
			break;

		default:
			break;
		}
	}
	
}
