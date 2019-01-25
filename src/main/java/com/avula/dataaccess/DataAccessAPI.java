package com.avula.dataaccess;

import com.avula.models.Customer;

public interface DataAccessAPI {

	public Iterable<Customer> getAllCustomers() throws Exception;
	public String save(String customerObject) throws Exception;
	public void delete(String documentId) throws Exception;
	public Long getAutoIncrementedValue() throws Exception;
	public String getFilteredResultsById(String id) throws Exception;

}
