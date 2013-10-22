package test.java;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.nhncorp.utilsettest.input.InputTestActivity;

public class InputTestCase extends ActivityInstrumentationTestCase2<InputTestActivity> {
	private Solo solo; 
	
	public InputTestCase() {
		super(InputTestActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testFirst() {
		
		try {
			solo.clickOnButton("Show");
			Thread.sleep(500);
			solo.clickOnButton("Hide");
			Thread.sleep(500);
			solo.clickOnButton("Toggle");
			Thread.sleep(500);
			solo.clickOnButton("Toggle");
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
