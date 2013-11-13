package com.navercorp.utilsettest.dialog;

import org.junit.Assert;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.navercorp.utilsettest.R;

public class IntroductionDialogFragment extends DialogFragment {
	private static final int DEFAULT_TIMER_UPDATE_INTERVAL = 10;
	private String title;
	private String content;
	private int time;
	private Dialog dialog;
	private CountDownTimer timer;
	private ProgressBar progressBar;
	
	public IntroductionDialogFragment(String title, String content, int time) {
		this.title = title;
		this.content = content;
		this.time = time;
	}

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		Activity activity = getActivity();
        
		dialog = new Dialog(activity, android.R.style.Theme_Panel);
        
        dialog.setContentView(R.layout.dialog_introduction);
        
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        
        TextView titleTextView = (TextView) dialog.findViewById(R.id.titleForDescriptionDialog);
        TextView contentTextView = (TextView) dialog.findViewById(R.id.contentForDescriptionDialog);
        
        titleTextView.setText(title);
        contentTextView.setText(content);
        
        progressBar = (ProgressBar) dialog.findViewById(R.id.progressBarForDescriptionDialog);
        progressBar.setMax(time);
        
        this.timer = new CountDownTimer(time, DEFAULT_TIMER_UPDATE_INTERVAL) {
			@Override
			public void onTick(long millisUntilFinished) {
				
				// Not sure if this is called when millisUntilFinished is zero.
				// This is to avoid so called "divide by zero" error, just in case. 
				if (millisUntilFinished == 0)
					return;
			
				progressBar.setProgress(time - (int) millisUntilFinished);
			}
			
			@Override
			public void onFinish() {
				dismiss();
			}
		};
		
		timer.start();
        
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;
//        dialog.getWindow().setLayout(width*2/3, height*2/3);
        
        dialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        

        Button declineButton = (Button) dialog.findViewById(R.id.closeButtonDialog);
        // if decline button is clicked, close the custom dialog
        declineButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
            	timer.cancel();
                dismiss();
            }
        });

        return dialog;
    }
	
	public boolean isShowing() {
		return dialog.isShowing();
	}
	
	public void dismiss() {
		dialog.dismiss();
	}
}