package com.avula.dataaccess.factory;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.avula.dataaccess.DataAccessAPI;



/**
 * The DataAccessFactory is used for data access of Couch Database
 *
 */
@Component
public class DataAccessFactory
{

   static Map<String, DataAccessAPI> supportedDatabase = new HashMap<>();

   public enum SupporedDatabase
   {
      COUCHBASESERVER
   }

   /**
    * @param type
    * @param dataAccessAPI
    * @return void
    */
   public void register(SupporedDatabase type, DataAccessAPI dataAccessAPI)
   {
      supportedDatabase.put(type.name(), dataAccessAPI);
   }

   /**
    * @return DataAccessAPI
    */
   public DataAccessAPI getPlaformDatabase()
   {
      String database = "COUCHBASESERVER";

      if(database.equalsIgnoreCase(SupporedDatabase.COUCHBASESERVER.name()))
      {
         return supportedDatabase.get(SupporedDatabase.COUCHBASESERVER.name());
      }
      return null;
   }
}
