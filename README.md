Android-SGTextView
==================

同时带字体描边 渐变 阴影的TextView - both have stroker , Gradient and shadow TextView

for example:

    SGTextView tv = (SGTextView) findViewById(R.id.textview);
		tv.setTextSize(100);
		tv.setText("Test");
		
		tv.setStyle("#711304", "#ffe775", "#f1a212", 3, 15);
		tv.setShadowLayer(2, 0, 2, "#000000");
