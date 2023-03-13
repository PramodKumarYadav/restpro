package org.powertester.extensions.report;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.typesafe.config.Config;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestClient;
import org.powertester.config.TestEnvFactory;

@Slf4j
public class PublishResults {
  private static final Config CONFIG = TestEnvFactory.getInstance().getConfig();

  private static final ElasticServerChoices ELASTIC_SERVER =
      CONFIG.getEnum(ElasticServerChoices.class, "ELASTIC_SERVER");
  private static final String ELASTIC_INDEX = getStringConfig("ELASTIC_INDEX");

  private static final ElasticsearchClient ELASTICSEARCH_CLIENT =
      getElasticHighLevelRestAPIClient();

  public static void toElastic(TestRunMetaData testRunMetaData) throws IOException {
    IndexResponse response =
        ELASTICSEARCH_CLIENT.index(i -> i.index(ELASTIC_INDEX).document(testRunMetaData));

    log.info("Indexed with version " + response.version());
  }

  public static ElasticsearchClient getElasticHighLevelRestAPIClient() {
    log.info("creating elastic client");

    RestClient restClient = ElasticLowLevelRestClientFactory.getRestClient(ELASTIC_SERVER);

    // Create the transport with a Jackson mapper
    ElasticsearchTransport transport =
        new RestClientTransport(restClient, new JacksonJsonpMapper());

    // And create the API client
    return new ElasticsearchClient(transport);
  }

  private static String getStringConfig(String configName) {
    return CONFIG.getString(ELASTIC_SERVER.getValue() + "." + configName);
  }
}
