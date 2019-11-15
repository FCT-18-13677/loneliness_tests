package es.uji.giant.DialogFlowTests.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import es.uji.giant.DialogFlowTests.config.ElasticSearchConfiguration;
import es.uji.giant.DialogFlowTests.model.Questionnarie;
import es.uji.giant.DialogFlowTests.utils.Constants;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class QuestionnarieDao {
    private static final Logger LOG = LoggerFactory.getLogger(QuestionnarieDao.class);
    private static final String INDEX = Constants.ELASTICSEARCH_TEST_INDEX;
    private static final String TYPE = Constants.ELASTICSEARCH_TEST_TYPE;

    private RestHighLevelClient restHighLevelClient;
    private ObjectMapper objectMapper;

    // Inyección más abajo -> setElasticsearchConfigration
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

    public List<Questionnarie> getAllQuestionnaries() {
        Gson gson = new Gson();
        List<Questionnarie> questionnaries = new ArrayList<>();

        try {
            SearchResponse searchResponse = elasticSearchConfiguration.getClient()
                    .prepareSearch(INDEX)
                    .setTypes(TYPE)
                    .setScroll(new TimeValue(60000))
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setFrom(0).setSize(10000).setExplain(true)
                    .get();

            do {
                for (SearchHit hit : searchResponse.getHits()) {
                    String sourceAsString = hit.getSourceAsString();
                    if (sourceAsString != null) {
                        Questionnarie questionnarie = gson.fromJson(sourceAsString, Questionnarie.class);
                        questionnaries.add(questionnarie);
                    }
                }

                searchResponse = elasticSearchConfiguration.getClient().prepareSearchScroll(searchResponse.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
            } while(searchResponse.getHits().getHits().length != 0);


            return questionnaries;
        } catch (IndexNotFoundException e) {
            return null;
        }
    }

    @Autowired
    public void setElasticSearchConfiguration(ElasticSearchConfiguration configuration) {
        this.elasticSearchConfiguration = configuration;
    }

}
