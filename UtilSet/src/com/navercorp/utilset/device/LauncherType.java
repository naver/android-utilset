package com.navercorp.utilset.device;

public enum LauncherType {
	/** Samsung Default Home */
	SAMSUNG_SMALL("com.sec.android.app.twlauncher.LauncherApplication", 140),
	
	/** Samsung Large Text */
	SMASUNG_LARGE("com.sec.android.app.seniorlauncher.LauncherApplication", 140),
	
	/** Galaxy S3/S4 */
	SMASUNG_GALAXY3_4("com.sec.android.app.launchercom.android.launcher2.LauncherApplication", 200),
	
	/** ICS Default Launcher */
	ICS_DEFAULT("com.android.launcher2.LauncherApplication", 150),
	
	/** Gingerbread Default Launcher */
	GINGERBREAD_DEFAULT("com.android.launcher2.LauncherApplication", 125),
	
	/** GO Launcher */
	GO("com.jiubang.ggheart.launcher.GOLauncherApp", 115),
	
	/** Go Launcher */
	GO_EX("com.gau.go.launcherex", 115),
	
	/** Nova Launcher */
	NOVA("com.teslacoilsw.launcher.NovaApplication", 105),
	
	/** Apex Launcher */
	APEX("com.anddoes.launcher.LauncherApplication", 105),
	
	/** LG Default Launcher */
	LG_DEFUALT("com.lge.launcher.LauncherApplication", 95),
	
	/** If Launcher is not defined */
	ANDROID("android", 130);
	
	String packageName;
	float paddingDp;

	private LauncherType(String packageName, float paddingDp) {
		this.packageName = packageName;
		this.paddingDp = paddingDp;
	}
	
	public float getPaddingDp() {
		return paddingDp;
	}
}