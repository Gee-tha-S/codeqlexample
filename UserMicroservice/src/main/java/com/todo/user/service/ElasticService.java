package com.todo.user.service;

import java.util.Map;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class ElasticService {
	private final String INDEX = "userinfo";
	private final String TYPE = "users";

	private RestHighLevelClient restHighLevelClient;

	private ObjectMapper objectMapper;

	public ElasticService(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
		this.objectMapper = objectMapper;
		this.restHighLevelClient = restHighLevelClient;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public void insertUser(Object object) {
		Map dataMap = objectMapper.convertValue(object, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE).source(dataMap);
		try {
			IndexResponse response = restHighLevelClient.index(indexRequest);
		} catch (ElasticsearchException e) {
			e.getDetailedMessage();
		} catch (java.io.IOException ex) {
			ex.getLocalizedMessage();
		}
	}

	/*public Optional<User> findById(String id)
			throws IOException, ParseException, net.minidev.json.parser.ParseException {

		GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
		GetResponse response = restHighLevelClient.get(getRequest);
		String sourceAsMap = response.getSourceAsString();
		System.out.println(sourceAsMap);
		ObjectMapper objectMapper = new ObjectMapper();
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		json = (JSONObject) parser.parse(sourceAsMap);
		System.out.println("in jsonobject");
		User user = objectMapper.readValue(json.toJSONString(), User.class);
		Optional<User> a = Optional.of(user);
		return a;
	}*/

	
}
