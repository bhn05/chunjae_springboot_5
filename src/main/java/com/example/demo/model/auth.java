package com.example.demo.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

public class auth {
	
	/**
     * Google Firebase 인증 함수.
     *
     * @param auth Google Firebase 인증키.
     * @return 인증 토큰
     * @throws IOException
     */
    public static String getToken() throws IOException {
        //InputStream inputStream = new ByteArrayInputStream(auth.getBytes());

        GoogleCredential googleCredential = GoogleCredential.fromStream(new FileInputStream("C:/resource/serviceAccountKey.json")).createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase",
                "https://www.googleapis.com/auth/cloud-platform",
                "https://www.googleapis.com/auth/firebase.readonly"));
        googleCredential.refreshToken();
        return googleCredential.getAccessToken();
    }

    /**
     * 테스트 함수
     * @return
     */
    private static String getPath() {
        // TODO TEST
        return "D:/IntelliJ-Project/FCM-Send-Server/web/test-fcm-auth-key.json";
    }

}
