package gr.forth.ics.isl.hy463.elasticsearchsample;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

/**
 * @author Yannis Marketakis
 */
public class ElasticSearchClient {
    
    public static void main(String[] args) {
        String esHost = "http://localhost:9200";
        String indexName = "sample_index";
        String jsonFilePath = "resources/elastic/sample.json";

        RestClient restClient = RestClient.builder(HttpHost.create(esHost)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new co.elastic.clients.json.jackson.JacksonJsonpMapper());
        ElasticsearchClient esClient = new ElasticsearchClient(transport);

        try {
            // Index a few documents
            String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
            IndexRequest<JsonData> request = IndexRequest.of(i -> i
                .index(indexName)
                .document(JsonData.fromJson(jsonContent))
            );
            IndexResponse response = esClient.index(request);
            System.out.println("Document indexed with ID: " + response.id());
            
            // Search for documents
            SearchRequest searchRequest = new SearchRequest.Builder()
                .index(indexName)
                .query(q -> q
                        .range(r -> r
                                .field("age")
                                .gt(JsonData.of("40"))
                        )
                )
                .size(100)
                .build();


            SearchResponse<JsonNode> searchResponse = esClient.search(searchRequest, JsonNode.class);
            List<Hit<JsonNode>> hits = searchResponse.hits().hits();

            if (hits.isEmpty()) {
                System.out.println("No matching persons found.");
            } else {
                for (Hit<JsonNode> hit : hits) {
                    System.out.println("Found: " + hit.source());
                }
            }
            
            
        } catch (IOException | ElasticsearchException e) {
            e.printStackTrace();
        } finally {
            try {
                transport.close();
                restClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
