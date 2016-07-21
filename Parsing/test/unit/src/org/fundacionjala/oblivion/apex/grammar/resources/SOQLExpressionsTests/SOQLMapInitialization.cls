public with sharing class soql_Map_initializations {

    {   
        Map<id, AnyObject__c> collection_query = new Map<id, AnyObject__c>([SELECT Name, AnyField1__c, Any_Field2__c FROM AnyObject__c LIMIT 2000]);
    }

}