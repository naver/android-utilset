package com.nhncorp.utilset.common;
public enum LauncherType {
	SAMSUNG_SMALL("com.sec.android.app.twlauncher.LauncherApplication", 140), //	삼성 기본홈
	SMASUNG_LARGE("com.sec.android.app.seniorlauncher.LauncherApplication", 140), // 삼성 큰글씨
	SMASUNG_GALAXY3("com.sec.android.app.launchercom.android.launcher2.LauncherApplication", 200), // 갤럭시 3 기본 런처
	ICS_DEFAULT("com.android.launcher2.LauncherApplication", 150), // ICS기본 런처
	GINGERBREAD_DEFAULT("com.android.launcher2.LauncherApplication", 125), //진저브레드 기본 런처 로직으로 os버전으로 분기한다 클래스명이 동일하다
	GO("com.jiubang.ggheart.launcher.GOLauncherApp", 115), // GO런처
	GO_EX("com.gau.go.launcherex", 115), // GO런처
	NOVA("com.teslacoilsw.launcher.NovaApplication", 105), // 노바 런처
	APEX("com.anddoes.launcher.LauncherApplication", 105), // 아펙스 런처
	LG_DEFUALT("com.lge.launcher.LauncherApplication", 95), // LG 기본런처
	ANDROID("android", 130); //기본런처가 설정되지 않음
	
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