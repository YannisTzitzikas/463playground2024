/**
 * 
 */
package ZA_ExternalSystems;

import java.io.IOException;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @author Yannis Tzitzikas (yannistzitzik@gmail.com)
 */
public class ElasticSearchClient {

	public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
        
        SearchRequest searchRequest = new SearchRequest("multiple");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery("age", "30"));
        searchRequest.source(sourceBuilder);

        // execute the search request
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // print search results
        System.out.println("Total hits: " + searchResponse.getHits().getTotalHits().value);
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println("Found document: " + hit.getSourceAsString());
        }

        // Close the client
        client.close();
    }

}
