package com.avula.controllers;

import java.util.Random;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.avula.dataaccess.couchbase.server.CouchbaseServerDataAccessImpl;
import com.avula.models.Customer;



@RestController
public class CustomerController
{
   @Autowired
   CouchbaseServerDataAccessImpl couchbaseServerDataAccessImpl;
   
      @RequestMapping(method=RequestMethod.GET, value="/getAllCustomer")
   public Iterable<Customer> contact() throws Exception {
       return couchbaseServerDataAccessImpl.getAllCustomers();
   }

   @RequestMapping(method=RequestMethod.POST, value="/customers")
   public Customer save(@RequestBody Customer customer)  throws Exception {
	   
	    Random rand = new Random(); 	    
        Integer randomNumber = rand.nextInt(10000); 
        customer.setId(randomNumber);
        
        JSONObject jObject = new JSONObject();

		jObject.put("id",  customer.getId());
		jObject.put("name", customer.getName());
		jObject.put("address", customer.getAddress());
		jObject.put("phone", customer.getPhone());
		jObject.put("email", customer.getEmail());
				
		String customerJsonText = jObject.toString();


	   couchbaseServerDataAccessImpl.save(customerJsonText);
       return customer;
   }

   @RequestMapping(method=RequestMethod.GET, value="/contacts/{id}")
   public String show(@PathVariable String id) throws Exception {
	   return couchbaseServerDataAccessImpl.getFilteredResultsById(id);
   }


   @RequestMapping(method=RequestMethod.DELETE, value="/contacts/{id}")
   public String delete(@PathVariable String id) throws Exception {
	   couchbaseServerDataAccessImpl.delete(id);

       return "";
   }
  
}
