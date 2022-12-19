package com.example.demo.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.Message;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class push implements Runnable{
	 private String requestURL;
	    private String accessToken;
	    private String requestMessage;
	    private Callback callback;

	    public interface Callback {
	        void onComplete(int resCode, String resMsg);
	    }
	    
	    
	    private push(push.Builder builder) {
	        accessToken = builder.accessToken;
	        JsonObject body = new JsonObject();
	        body.add("message", new Gson().toJsonTree(builder.message, Message.class));
	        body.getAsJsonObject("message").add("android", new Gson().toJsonTree(builder.androidConfig));
	        requestMessage = body.toString();
	        callback = builder.callback;
	        requestURL = "https://fcm.googleapis.com/v1/projects/" + builder.projectID +"/messages:send";

	    }
	    @Override
	    public void run() {
	        HttpURLConnection connection = null;
	        int resCode = 0;
	        StringBuffer resBody = new StringBuffer();
	        try {
	            URL url = new URL(requestURL);
	            connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("POST");
	            connection.setDoInput(true);
	            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
	            connection.setRequestProperty("Content-Type", "application/json;");
	            connection.setDoOutput(true);
	            OutputStream os = connection.getOutputStream();
	            os.write(requestMessage.getBytes());
	            os.flush();
	            os.close();
	            resCode = connection.getResponseCode();
	            if(resCode == 200) {
				    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				    String line;
				    while ((line = br.readLine()) != null) {
				        resBody.append(line);
				    }
				    br.close();
	            }
	            
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	        } finally {
	            if (connection != null) {
	                connection.disconnect();
	            }
	        }
	        if (callback != null) {
	            callback.onComplete(resCode, resBody.toString());
	        }else {
	        }
	    }

	    /**
	     * FCM 발송 처리를 위한 데이터 설정 Builder Class.
	     */
	    public static class Builder {
	        private String accessToken;
	        private Message message;
	        private AndroidConfig androidConfig;
	        private push.Callback callback;
	        private String projectID;


	        public Builder() {
	        }

	        public push.Builder setAccessToken(String accessToken) {
	            this.accessToken = accessToken;
	            return this;
	        }

	        public push.Builder setMessage(Message message) {
	            this.message = message;
	            return this;
	        }

	        public push.Builder setAndroidConfig(AndroidConfig androidConfig) {
	            this.androidConfig = androidConfig;
	            return this;
	        }

	        public push.Builder setCallback(push.Callback callback) {
	            this.callback = callback;
	            return this;
	        }

	        public push.Builder setProjectID(String id) {
	            this.projectID = id;
	            return this;
	        }

	        public push build() {
	            return new push(this);
	        }

	    } // end of class Builder
}