package movies.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movies.Utils.NetworkUtil;
import movies.config.constants.ApiUrlConstants;
import movies.domain.CrowlingMovie;
import movies.repository.CrowlingRepository;
import movies.service.CrowlingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrowlingServiceImpl implements CrowlingService {

    @Value("${naver.papago.clientId}")
    private String clientId;

    @Value("${naver.papago.clientSecret}")
    private String clientSecret;

    private String codeUrl = ApiUrlConstants.MOVIE_URL;
    private String Url = ApiUrlConstants.URL;

    private String TRANSLATEURL = ApiUrlConstants.TRANSLATEURL;
    private final CrowlingRepository crowlingRepository;

    public void getCrowling() throws Exception {

        List<String> codeList = getCode();

        codeList.remove(0);

        for (String i : codeList) {

            String a = i.substring(30);

            Document doc = Jsoup.connect(Url + a).get();

            String title = doc.select("#content > div.article > div.mv_info_area > div.mv_info > h3 > a:nth-child(1)").text();
            String description = doc.select("#content > div.article > div.section_group.section_group_frst > div:nth-child(1) > div > div.story_area > p").text();
            String imgUrl = doc.select("#content > div.article > div.mv_info_area > div.poster > a > img").attr("src");
            String engdescription = translate(description);

            crowlingRepository.save(CrowlingMovie.builder()
                    .title(title)
                    .engDescription(engdescription)
                    .description(description)
                    .imgUrl(imgUrl)
                    .build());

        }
    }

    public List<String> getCode() throws IOException {

        try {
            Document doc = Jsoup.connect(codeUrl).get();

            Elements elements = doc.select("#old_content > table > tbody > tr");

            int rank = -1;

            List<String> rList = new ArrayList<>();

            for (Element elem : elements) {

                rank++;

                String code = elem.select("div[class=tit3] a").attr("href");

                rList.add(code);

                if (rank == 10) {
                    break;
                }
            }
            return rList;
        } catch (IOException e) {
            log.info("error = " + e);
        }

        return null;
    }

    public void deleteCrowling() {
        crowlingRepository.deleteAll();
    }


    public String translate(String contents) throws Exception {

        log.info(this.getClass().getName() + "traslate Start!");

        String source = "ko";
        String target = "en";

        String postParams = "source=" + source + "&target=" + target + "&text=" + URLEncoder.encode(contents, "UTF-8");

        log.info("postParams : " + postParams);

        String json = NetworkUtil.post(TRANSLATEURL, this.setNaverInfo(), postParams);

        log.info("json : " + json);

        Map<String, Object> rMap = new ObjectMapper().readValue(json, LinkedHashMap.class);

        // 결과 내용 중 message 정보가져오기
        Map<String, Object> messageMap = (Map<String, Object>) rMap.get("message");

        // message 결과 내용 중 result 정보가져오기
        Map<String, String> resultMap = (Map<String, String>) messageMap.get("result");

        log.info("resultMap : " + resultMap);

        String translatedText = resultMap.get("translatedText");

        return translatedText;
    }



    //== 네이버 API 사용을 위한 접속 정보 설정하기 ==//
    private Map<String, String> setNaverInfo() {
        log.info("### setNaverInfo start");
        HashMap<String, String> requestheader = new HashMap<>();

        requestheader.put("X-Naver-Client-Id", clientId);
        requestheader.put("X-Naver-Client-Secret", clientSecret);

        log.info("### clientId : {}", clientId);
        log.info("### clientSecret : {}", clientSecret);

        log.info("### setNaverInfo end");
        return requestheader;
    }

}
