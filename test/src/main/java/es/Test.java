package es;

import baseFramework.es.ESDocement;
import baseFramework.es.ESQCondition;
import baseFramework.es.ElasticSearchUtil;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author chao.li
 * @date 2017/7/28
 */
public class Test {

	public static void main(String[] args) throws Exception {


		String json = "{\"title\":\"Nest eggs\",\"body\":\"Making your money work...\",\"tags\":[\"cash\",\"shares\"],\"comments\": [{\"name\":\"John Smith\",\"comment\":\"Great article\",\"age\": 28,\"stars\": 4,\"date\":\"2014-09-01\"},{\"name\":\"Alice White\",\"comment\":\"More like this please\",\"age\": 31,\"stars\": 5,\"date\":\"2014-10-22\"}]}";


		ESDocement esDoc = new ESDocement();
		esDoc.setDoc(json);
		esDoc.setId("1");

//		for (int i = 1; i <= 5 ; i++) {
//			ElasticSearchUtil.indexDoc("evan","evan-user",String.valueOf(i), json,true);
//		}


		ESQCondition esqCondition = new ESQCondition();
		esqCondition.setQueryBuilder(QueryBuilders.nestedQuery("comments", QueryBuilders.matchPhraseQuery("comments.name", "John Smith"), ScoreMode.None).query());
		List<String> list = ElasticSearchUtil.search(new String[]{"evan"}, new String[]{"evan-user"}, esqCondition);
		list.forEach(s -> System.out.println(s));

		System.out.println("finish");


	}


	private static void get(TransportClient client) throws ExecutionException, InterruptedException {
		System.out.println(client.prepareGet("evan", "evan-user", "1").get());
	}

	private static void delete(TransportClient client) {
		DeleteResponse response = client.prepareDelete("evan", "user", "1").get();
		System.out.println(response);
	}

	private static void search(TransportClient client) {
		SearchResponse response = client.prepareSearch("evan")
				.setTypes("evan-user")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.matchAllQuery())                 // Query
//				.setPostFilter(QueryBuilders.rangeQuery("age").from(23).to(25))     // Filter
				.addSort("age", SortOrder.DESC)
				.setFrom(0).setSize(60).setExplain(true)
				.get();
		for (SearchHit hit : response.getHits().getHits()) {
			System.out.println(hit.getSource());
		}
	}


}
