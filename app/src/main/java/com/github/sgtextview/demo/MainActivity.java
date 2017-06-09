package com.github.sgtextview.demo;

import android.app.Activity;
import android.os.Bundle;
import com.github.sgtextview.R;
import com.github.sgtextview.SGTextView;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		SGTextView tv = (SGTextView) findViewById(R.id.textview);
		tv.setTextSize(100);
		tv.setText("Test");
		
		tv.setStyle("#711304", "#ffe775", "#f1a212", 3, 15);
		tv.setShadowLayer(2, 0, 2, "#000000");

	}
}
