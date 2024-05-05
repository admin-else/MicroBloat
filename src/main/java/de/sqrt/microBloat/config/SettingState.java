package de.sqrt.microBloat.config;

public enum SettingState {
	NORMAL(0),
	HIDDEN(1),
	SPECIAL(2);
	
	int state;
	
	SettingState(int state) {
		this.state = state;
	}
}
