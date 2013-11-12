package com.navercorp.utilsettest.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.navercorp.utilsettest.R;

public class IntroductionDialogFragment extends DialogFragment {
	private String title;
	private String content;
	private Dialog dialog;
	
	public IntroductionDialogFragment(String title, String content) {
		this.title = title;
		this.content = content;
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
        
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;
        dialog.getWindow().setLayout(width*2/3, height*2/3);

        Button declineButton = (Button) dialog.findViewById(R.id.closeButtonDialog);
        // if decline button is clicked, close the custom dialog
        declineButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dismiss();
            }
        });

        return dialog;
    }
	
	public void dismiss() {
		dialog.dismiss();
	}
}