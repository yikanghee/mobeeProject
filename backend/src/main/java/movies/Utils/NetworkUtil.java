package movies.Utils;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Slf4j
public class NetworkUtil {

    public static String get(String apiUrl) {
        log.info("### get 단일 파라미터 시작");
        log.info("### apiUrl : {}", apiUrl);
        log.info("### get 단일 파라미터 종료");
        return get(apiUrl, null);
    }

    public static String get(String apiUrl, @Nullable Map<String, String> requestHeaders) {

        log.info("### get 시작");

        log.info("### apiUrl : {}", apiUrl);

        HttpURLConnection con = connect(apiUrl);
        log.info("### con : {}", con);

        try {
            con.setRequestMethod("GET");

            // 전송할 헤더 값이 존재하면, 헤더 값 추가하기
            if (requestHeaders != null) {
                for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                    con.setRequestProperty(header.getKey(), header.getValue());
                }
            }

            // API 호출 후 결과 받기
            int responseCode = con.getResponseCode();

            // API 호출이 성공하면
            if (responseCode == HttpURLConnection.HTTP_OK) {
                log.info("### api 호출 성공");
                return readBody(con.getInputStream());
            } else { // 에러 발생
                log.info("### api 호출 실패");
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            log.info("Exception 발생");
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);

            return (HttpURLConnection) url.openConnection();

        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body) {

        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    public static String post(String apiUrl, @Nullable Map<String, String> requestHeaders, String postParams) {

        HttpURLConnection con = connect(apiUrl);

        try {
            con.setRequestMethod("POST");

            // 전송할 헤더 값이 존재하면, 헤더 값 추가
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            // POST 방식으로 전송할 때 전송할 파라미터 정보 넣기 (GET 방식은 필요 없다.)
            con.setDoOutput(true);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postParams.getBytes());
                wr.flush();
            }

            // API 호출 후 결과 받기
            int responseCode = con.getResponseCode();

            // API 호출이 성공하면..
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream()); //성공 결과 값을 문자열로 변환하기
            } else { // 에러 발생
                return readBody(con.getErrorStream()); //== 실패 결과 값을 문자열로 변환하기 ==//
            }

        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }
}
