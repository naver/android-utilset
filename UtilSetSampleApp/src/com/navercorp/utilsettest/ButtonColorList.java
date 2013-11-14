package com.navercorp.utilsettest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ButtonColorList {
	@SuppressWarnings({"serial" })
	private static final List<ButtonColor> list = Collections.unmodifiableList(new ArrayList<ButtonColor>() {
		{
			add(new ButtonColor("Indigo", 0xFF481884, 0xFF270059, 0xFFffffff));
			add(new ButtonColor("Steel blue", 0xFF3983b6, 0xFF154b70, 0xFFffffff));
			add(new ButtonColor("Carnation pink", 0xFFffaac9, 0xFF9c546f, 0xFFffffff));
			add(new ButtonColor("Carmine", 0xFFff0038, 0xFF80001c, 0xFFffffff));
			add(new ButtonColor("Sienna", 0xFF8c3611, 0xFF461a09, 0xFFffffff));
			add(new ButtonColor("Lime", 0xFFc1f900, 0xFF7fb900, 0xFF156615));
			add(new ButtonColor("Kelly green", 0xFF49b700, 0xFF255c00, 0xFFffffff));
			add(new ButtonColor("Teal", 0xFF338594, 0xFF004d5b, 0xFFffffff));
			add(new ButtonColor("Peace", 0xFF3794dd, 0xFF0b5794, 0xFFffffff));
			add(new ButtonColor("Azure", 0xFF0085ff, 0xFF004b8f, 0xFFffffff));
		}
	});

	public static List<ButtonColor> getButtonColorList() {
		return list;
	}
	
	public static ButtonColor getButtonColor(int index) {
		return list.get(index);
	}
}