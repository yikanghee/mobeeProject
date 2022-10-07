package movies.service;

import java.io.IOException;
import java.util.List;

public interface CrowlingService {

    void getCrowling() throws Exception;

    List<String> getCode() throws IOException;

    void deleteCrowling();

}
