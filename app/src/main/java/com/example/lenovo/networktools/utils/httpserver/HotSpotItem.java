package com.example.lenovo.networktools.utils.httpserver;

public class HotSpotItem {
	
	private String ssid;
	private String bssid;
	
	
	
	public HotSpotItem(String sSID2, String bSSID2) {
		ssid = sSID2;
		bssid = bSSID2;
	}
	
	
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	
	
	public String getBssid() {
		return bssid;
	}
	public void setBssid(String bssid) {
		this.bssid = bssid;
	}
	
	
	public String getTitle() {
		int index = ssid.indexOf("-");
		String title = ssid.substring(index + 1);
		return title;
	}
	
}
