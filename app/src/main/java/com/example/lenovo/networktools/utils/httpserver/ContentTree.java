package com.example.lenovo.networktools.utils.httpserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class ContentTree implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final ContentTree instance = new ContentTree();

	private List<ContentItem> sharedResource = new ArrayList<ContentItem>();//sender

	private List<ContentItem> transList; //used to trans
	
	private List<ContentItem> listReceive = new ArrayList<ContentItem>();//receiver
	
	public ContentTree() {};
	
	public static ContentTree getInstance() {
		return instance;
	}

	public List<ContentItem> getSharedResource() {
		return sharedResource;
	}
	
	public void addShareResource(ContentItem item) {
		boolean exist = false;
		for(int i = 0; i < sharedResource.size(); i++) {
			if (item != null && sharedResource.get(i) != null && 
					sharedResource.get(i).getContentID() != null) {
				if(sharedResource.get(i).getContentID().equals(item.getContentID())) {
					exist = true;
				}
			}
		}
		if(!exist) {
			sharedResource.add(item);
		}	
	}
	
	public void removeDevice(ContentItem item) {
		Iterator<ContentItem> iter = sharedResource.iterator();
		while (iter.hasNext()) {
			if (iter.next().getContentID().equals(item.getContentID())) {
				iter.remove();
			}
		}
	}
	
	public void clear() {
		sharedResource.clear();
	}

	/***/
	public int hasRes(String itemId) {
		for (int i = 0; i < sharedResource.size(); i++) {
			if (sharedResource.get(i).getContentID().equals(itemId)) {
				return i;
			}
		}
		return -1;
	}

	
	public List<ContentItem> getTransList() {
		return transList;
	}
	public void setTransList(List<ContentItem> transList) {
		this.transList = transList;
	}

	
	public List<ContentItem> getListReceive() {
		return listReceive;
	}
	public void setListReceive(List<ContentItem> listtReceive) {
		this.listReceive = listtReceive;
	}

	public void addReceivedItem(ContentItem item) {
		boolean exist = false;
		for(int i = 0; i < listReceive.size(); i++) {
			if (item != null && listReceive.get(i) != null) {
				if(listReceive.get(i).getContentURL().equals(item.getContentURL())) {
					exist = true;
				}
			}
		}
		if(!exist) {
			listReceive.add(item);
		}	
	}
	
	
}
