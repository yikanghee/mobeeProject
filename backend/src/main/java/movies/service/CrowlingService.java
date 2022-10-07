package movies.service;

import java.io.IOException;
import java.util.List;

public interface CrowlingService {

    void getCrowling() throws IOException;

    List<String> getCode() throws IOException;

    void deleteCrowling();

}
