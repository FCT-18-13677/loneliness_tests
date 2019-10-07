package es.uji.giant.DialogFlowTests.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ElasticSearchConfiguration extends AbstractFactoryBean {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticSearchConfiguration.class);

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodes;

    private RestHighLevelClient restHighLevelClient;
    private TransportClient client;

    @PostConstruct
    public void setupTransportClient() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", clusterName)
                .build();

        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(clusterNodes.split(":")[0]), Integer.parseInt(clusterNodes.split(":")[1])));
    }

    @Override
    public void destroy() throws IOException {
        if (restHighLevelClient != null)
            restHighLevelClient.close();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }


    @Override
    public Class<RestHighLevelClient> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    protected RestHighLevelClient createInstance() throws Exception {
        return buildClient();
    }

    private RestHighLevelClient buildClient() {
        restHighLevelClient = new RestHighLevelClient(RestClient.builder(
                new HttpHost(clusterNodes.split(":")[0], 9200)
        ));
        return restHighLevelClient;
    }

    public TransportClient getClient() {
        return client;
    }
}
