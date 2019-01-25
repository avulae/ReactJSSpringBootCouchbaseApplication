package com.avula.dataaccess.couchbase.server;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.avula.dataaccess.AbstractDataAccessAPI;
import com.avula.dataaccess.factory.DataAccessFactory.SupporedDatabase;
import com.avula.models.Customer;

@Service
public class CouchbaseServerDataAccessImpl extends AbstractDataAccessAPI {

	@Autowired
	private Bucket couchbaseServerBucket;

	static final String COUCHBASE_SERVER_BUCKET_NAME = "avulaCouchDB";

	@Override
	public String save(String object) throws Exception {
		JSONObject jsonObject = new JSONObject(object);
		String documentId = jsonObject.getString("id");
		JsonDocument doc = JsonDocument.create(documentId, JsonObject.fromJson(object));
		couchbaseServerBucket.upsert(doc);
		// fetching inserted document
		return documentId;
	}

	@Override
	public SupporedDatabase type() {
		return SupporedDatabase.COUCHBASESERVER;
	}

	@Override
	public void delete(String documentId) throws Exception {
		couchbaseServerBucket.remove(documentId);
	}

	@Override
	public Long getAutoIncrementedValue() throws Exception {
		couchbaseServerBucket.counter("idGenerator", 0, 0);
		return couchbaseServerBucket.counter("idGenerator", 1).content();
	}

	@Override
	public String getFilteredResultsById(String id) throws Exception {
		StringBuilder query = new StringBuilder("SELECT * FROM");
		query.append(" ").append(COUCHBASE_SERVER_BUCKET_NAME).append(" WHERE id = '").append(id).append("'");
		N1qlQueryResult queryResult = couchbaseServerBucket.query(N1qlQuery.simple(query.toString()));

		for (N1qlQueryRow queryRow : queryResult) {
			JsonObject jsonObject = (JsonObject) queryRow.value().get(COUCHBASE_SERVER_BUCKET_NAME);
			return jsonObject.toString();
		}
		return " ";
	}

	@Override
	public Iterable<Customer> getAllCustomers() throws Exception {
		return null;
	}

}
