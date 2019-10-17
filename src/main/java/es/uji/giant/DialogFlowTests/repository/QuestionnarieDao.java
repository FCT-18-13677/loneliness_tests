package es.uji.giant.DialogFlowTests.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.uji.giant.DialogFlowTests.config.ElasticSearchConfiguration;
import es.uji.giant.DialogFlowTests.model.Questionnarie;
import es.uji.giant.DialogFlowTests.utils.Constants;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;

@Repository
public class QuestionnarieDao {
    private static final Logger LOG = LoggerFactory.getLogger(QuestionnarieDao.class);
    private static final String INDEX = Constants.ELASTICSEARCH_TEST_INDEX;
    private static final String TYPE = Constants.ELASTICSEARCH_TEST_TYPE;

    private RestHighLevelClient restHighLevelClient;
    private ObjectMapper objectMapper;


    //@Autowired
    private ElasticSearchConfiguration elasticSearchConfiguration;

    public QuestionnarieDao(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
        this.objectMapper = objectMapper;
        this.restHighLevelClient = restHighLevelClient;
    }

    public boolean insertQuestionnarie(String id, Questionnarie questionnarie) {
        Map datamap = objectMapper.convertValue(questionnarie, Map.class);
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, id).source(datamap);

        try {
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            LOG.info("Response -> " + response);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
