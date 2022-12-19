package com.example.demo.controller;


import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.util.TextUtils;
import com.example.demo.model.auth;
import com.example.demo.model.push;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.Message;

public class fcmandroid {
	public static void androidSet() throws IOException {
		// TODO Auto-generated method stub
		System.out.println("andand and and ");
		int rowCnt = 0;
		String projectId = "fcm-test-7f358";
		String token = "cpX1aV0_SYiALxwUA1MvF8:APA91bHcGpOQ5Ta_LyjoFWPbgeRYmR3XJ_wfnHiLLnv1N1BYeHLzsjifTYmpbFpFDWxyBQKXDsm48uLbRN1cvah7Fubn_ZeTmfjq3QckcGdF9I7eV9RrIvQam_3ONYaeyVlHj38eiqVl";
		//cpX1aV0_SYiALxwUA1444:APA91bHcGpOQ5Ta_LyjoFWPbgeRYmR3XJ_wfnHiLLnv1N1BYeHLzsjifT123456FpFDWxyBQKXDsm48uLbRN1cvah7Fubn_ZeTmfjq3QckcGdF9I7eV9RrIvQam_3ONYaeyVlHj38eiqVl
		
		String title = "title area~";
		String body = "body area";
		String icon = "";
		String sound = "";
		String tag = "";
		String clickAction = "";

		push.Builder pushBuilder = new push.Builder()
	          .setProjectID(projectId)
	          .setAccessToken(auth.getToken())
	          .setCallback(new push.Callback() {
	              @Override
	              public void onComplete(int resCode, String resMsg ) {
	            	  
	                  System.out.println("onComplete :: resCode=" + resCode + " :: resMsg=" + resMsg);
	            	  //status 값을 기준으로 성공 실패처리 
	            	  //db connection 후 저장로직 추가 
	                  if (resCode != 200) {
	                	  System.out.println("fail fcm push");
	                	  
	                  }else {
	                	  System.out.println("success fcm push");
	                  }
	              }
	          });
		//create thread 
		System.out.println("thread start ");
		System.out.println("thread start ");
		//BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(1000);
		//cdBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();		//동적처리 기능으로 array 보다 안정
		//ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 8, 100, TimeUnit.SECONDS, blockingQueue);
		ExecutorService executor = Executors.newFixedThreadPool(50);
		//Runnable task = new Task();	
		//DB connection 
		
		try {
			//conn = dbcon.getConnection();
			//Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			//resultSet = state.executeQuery(dbCon.selectPushQuery());
			//String token = "";
			System.out.println("push set start");
			Message.Builder msgBuilder = Message.builder().setToken(token);
            AndroidConfig.Builder androidBuilder = AndroidConfig.builder();
            androidBuilder.setPriority(AndroidConfig.Priority.HIGH);
            setAndroidNotidata(androidBuilder, title, body, icon, sound, tag, clickAction);
            //androidBuilder.putAllData(dataMap);
            pushBuilder.setAndroidConfig(androidBuilder.build());
            pushBuilder.setMessage(msgBuilder.build());
            //threadPoolExecutor.submit(pushBuilder.build());
    		//threadPoolExecutor.execute(pushBuilder.build());
    		//threadPoolExecutor.execute(new Task(token));
            executor.submit(pushBuilder.build());
    		System.out.println("push set end");
    		//System.out.println(">>>>>"+ resultSet.getObject("idx"));
    		//System.out.println(threadPoolExecutor.);
            //pushBuilder.build();
		} catch (Exception e ) {
            e.printStackTrace();
        } finally {
        	executor.shutdown();
        }
		System.out.println("rowCnt::>"+ rowCnt);

				
		
		
	}
	

	
	private static AndroidConfig.Builder setAndroidNotidata(AndroidConfig.Builder builder, String title, String body, String icon, String sound, String tag, String clickAction) throws SQLException {
        AndroidNotification.Builder notiBuilder = AndroidNotification.builder();
        boolean isAdded = false;

        if (!TextUtils.isEmpty(title)) {
            notiBuilder.setTitle(title);
            isAdded = true;
        }
        if (!TextUtils.isEmpty(body)) {
            notiBuilder.setBody(body);
            isAdded = true;
        }
        if (!TextUtils.isEmpty(icon)) {
            notiBuilder.setIcon(icon);
            isAdded = true;
        }
        if (!TextUtils.isEmpty(sound)) {
            notiBuilder.setSound(sound);
            isAdded = true;
        }
        if (!TextUtils.isEmpty(tag)) {
            notiBuilder.setTag(tag);
            isAdded = true;
        }
        if (!TextUtils.isEmpty(clickAction)) {
            notiBuilder.setClickAction(clickAction);
            isAdded = true;
        }
        if (isAdded) {
            builder.setNotification(notiBuilder.build());
        }

        return builder;
    }

}
