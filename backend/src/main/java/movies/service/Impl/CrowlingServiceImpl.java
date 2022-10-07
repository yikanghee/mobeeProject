package movies.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movies.config.constants.ApiUrlConstants;
import movies.domain.CrowlingMovie;
import movies.repository.CrowlingRepository;
import movies.service.CrowlingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrowlingServiceImpl implements CrowlingService {

    private String codeUrl = ApiUrlConstants.MOVIE_URL;
    private String Url = ApiUrlConstants.URL;
    private final CrowlingRepository crowlingRepository;

    public void getCrowling() throws IOException {

        List<String> codeList = getCode();

        codeList.remove(0);

        for (String i : codeList) {

            String a = i.substring(30);

            Document doc = Jsoup.connect(Url + a).get();

            String title = doc.select("#content > div.article > div.mv_info_area > div.mv_info > h3 > a:nth-child(1)").text();
            String description = doc.select("#content > div.article > div.section_group.section_group_frst > div:nth-child(1) > div > div.story_area > p").text();
            String imgUrl = doc.select("#content > div.article > div.mv_info_area > div.poster > a > img").attr("src");

            crowlingRepository.save(CrowlingMovie.builder()
                    .title(title)
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

}
