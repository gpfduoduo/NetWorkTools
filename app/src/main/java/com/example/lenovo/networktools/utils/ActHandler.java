package com.example.lenovo.networktools.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @author 郭攀峰 10129302 2014-11-12创建
 */
public class ActHandler extends Handler{

	public interface HandleMsg {
		public void handleMessage(Message msg);
	}
	
	private HandleMsg handleMsg;
	
	private WeakReference<Activity> actReference;
	
	public ActHandler(Activity act, HandleMsg handleMsg) {
		actReference = new WeakReference<Activity>(act);
		this.handleMsg = handleMsg;
	}
	
	@Override
	public void handleMessage(Message msg) {
		if (actReference.get() == null || actReference.get().isFinishing()) 
			this.removeCallbacksAndMessages(null);
		else {
			this.handleMsg.handleMessage(msg);
		}
	}
	
}
