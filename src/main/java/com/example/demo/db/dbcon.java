package com.example.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;



public class dbcon {
	private static final dbcon mInstance = new dbcon();
	
	private dbcon() {
    }
	
	public static dbcon getInstance() {
        return dbcon.mInstance;
    }
    /**
     * SQL 서버 연결 함수.
     *
     * @return {@link Connection} 객체.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Fcm_dev";
        return DriverManager.getConnection(url, "adm_test", "1111"); // id, pw 
    }
	
	
	public String selectPushQuery() {
		return"select * From Fcm_dev.dbo.TBL_APP_FCM_TOKEN where Idx =9887  ;";
	}
	
	 public int getRowCount(ResultSet rs) throws SQLException {
	        int rowCount = 0;
	        if (rs.last()) {
	            rowCount = rs.getRow();
	            rs.beforeFirst();
	        }
	        return rowCount;
	    }
	 
	 public static final class TABLE_MESSAGE {
	        /**
	         * Table 이름
	         */
	        private static final String TABLE_NAME = "TBL_APP_FCM_MESSAGE";

	        /**
	         * FCM 발송 처리 상태 코드 : 발송 전
	         */
	        public static final int STATUS_NONE = 0;
	        /**
	         * FCM 발송 처리 상태 코드 : 발송을 위해 서버에서 데이터를 조회함.
	         */
	        public static final int STATUS_PUSHING = 1;
	        /**
	         * FCM 발송 처리 상태 코드 : 발송 처리 완료.
	         */
	        public static final int STATUS_PUSHED = 2;

	        /**
	         * 해당 Table의 Column 정보
	         */
	        public enum COLUMN {
	            ID("Idx", "msgId"),
	            /**
	             * FCM 토큰 데이터 ID
	             */
	            TOKEN_ID("TockenID"),
	            /**
	             * Push 주제 값
	             */
	            TOPIC("Topic"),
	            /**
	             * 공통 플랫폼용 알림 타이틀
	             */
	            TITLE("NotiTitle"),
	            /**
	             * 공통 플랫폼용 알림 메시지
	             */
	            BODY("NotiBody"),
	            /**
	             * Push 발송 상태 값
	             */
	            STATUS("MStatus"),
	            /**
	             * FCM 서버에 발송 요청 후 결과 코드.
	             * Http Response Code
	             */
	            RES_CODE("ResCode"),
	            /**
	             * FCM 서버에 발송 요청 후 결과 메시지.
	             * Http Response Message
	             */
	            RES_MSG("ResMsg"),
	            /**
	             * 발송부서
	             */
	            SEND_DEPART("SendDepart"),
	            /**
	             * 데이터 등록 시간
	             */
	            REGDATE("RegDate"),
	            /**
	             * 발송 일자
	             */
	            SEND_DT("SendDT");

	            public String name;
	            public String asName;


	            COLUMN(String name) {
	                this.name = name;
	                this.asName = this.name;
	            }

	            COLUMN(String name, String asN) {
	                this.name = name;
	                this.asName = asN;
	            }

	            @Override
	            public String toString() {
	                return this.name;
	            }
	        }
	    } // end of cla
}
