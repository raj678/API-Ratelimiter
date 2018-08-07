package com.rt.db;

//Definition of license types, each client will be associated with single  license type
public enum LicenseType {
	LOW(10), MEDIUM(20), HIGH(50);
	int limitsPerSecond;

	LicenseType(int limit) {
		this.limitsPerSecond = limit;
	}

	public int limitsPerSecond() {
		return limitsPerSecond;
	}
}
