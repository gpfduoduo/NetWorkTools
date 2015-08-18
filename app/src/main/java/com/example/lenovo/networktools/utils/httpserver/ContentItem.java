package com.example.lenovo.networktools.utils.httpserver;


@SuppressWarnings("rawtypes")
public class ContentItem implements Comparable {

	private String contentID;
	private String contentName;
	private String contentSize;
	private long   contentSizeLong;
	private String contentTime;
	private String contentPath;
	private String contentURL;
	private String contentType;
	private String sendedSize;
	private String receivedSize;
	private String downloadprogress = "0";


	public ContentItem() {}


	public ContentItem(String id, String musicName, String musicSize, String musicTime,
			String musicPath, String musicURL) {
		super();
		this.contentID = id;
		this.contentName = musicName;
		this.contentSize = musicSize;
		this.contentTime = musicTime;
		this.contentPath = musicPath;
		this.contentURL = musicURL;
	}


	public ContentItem(String id, String title, String pictureSize, String filePath,
			String pictureurl) {
		this.contentID = id;
		this.contentName = title;
		this.contentSize = pictureSize;
		this.contentPath = filePath;
		this.contentURL = pictureurl;
	}


	public String getContentID() {
		return contentID;
	}
	public void setContentID(String contentID) {
		this.contentID = contentID;
	}


	public String getContentName() {
		return contentName;
	}


	public void setContentName(String contentName) {
		this.contentName = contentName;
	}


	public String getContentSize() {
		return contentSize;
	}


	public void setContentSize(String contentSize) {
		this.contentSize = contentSize;
	}


	public String getContentTime() {
		return contentTime;
	}


	public void setContentTime(String contentTime) {
		this.contentTime = contentTime;
	}


	public String getContentPath() {
		return contentPath;
	}


	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}


	public String getContentURL() {
		return contentURL;
	}


	public void setContentURL(String contentURL) {
		this.contentURL = contentURL;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContentType() {
		return contentType;
	}


	public String getSendedSize() {
		return sendedSize;
	}
	public void setSendedSize(String sendedSize) {
		this.sendedSize = sendedSize;
	}


	public String getReceivedSize() {
		return receivedSize;
	}
	public void setReceivedSize(String receivedSize) {
		this.receivedSize = receivedSize;
	}


	public String getDownloadprogress() {
		return downloadprogress;
	}
	public void setDownloadprogress(String downloadprogress) {
		this.downloadprogress = downloadprogress;
	}


	public long getContentSizeLong() {
		return contentSizeLong;
	}
	public void setContentSizeLong(long contentSizeLong) {
		this.contentSizeLong = contentSizeLong;
	}

	@Override
	public int compareTo(Object another) {
		ContentItem contentItem  = (ContentItem)another;
		String name = contentItem.getContentName();
		return this.contentName.compareToIgnoreCase(name);
	}

}
