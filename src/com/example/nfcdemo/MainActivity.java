package com.example.nfcdemo;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private TextView mTV;
	private RelativeLayout mRL;
	private Button mShowBT, mHideBT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTV = (TextView) findViewById(R.id.tv_info);
		mRL = (RelativeLayout) findViewById(R.id.rl);
		mShowBT = (Button) findViewById(R.id.btn_show);
		mHideBT = (Button) findViewById(R.id.btn_hide);
		mShowBT.setOnClickListener(this);
		mHideBT.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	int hideHeight;

	public void hideView() {
		hideHeight = mRL.getHeight();
		ObjectAnimator hideAnim = ObjectAnimator.ofFloat(mRL, "translationY",
				0, -hideHeight);
		hideAnim.setDuration(1000);
		hideAnim.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				mShowBT.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});
		hideAnim.start();

	}

	public void showView() {
		ObjectAnimator showAnim = ObjectAnimator.ofFloat(mRL, "translationY",
				-hideHeight, 0);
		showAnim.setDuration(1000);
		showAnim.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				mShowBT.setVisibility(View.GONE);

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				mHideBT.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});
		showAnim.start();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_hide:
			hideView();
			break;
		case R.id.btn_show:
			showView();
			break;

		default:
			break;
		}

	}

}
