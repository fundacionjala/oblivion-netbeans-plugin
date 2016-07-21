public with sharing class SOQLNestedQueries {
    {
        List<String> names = [SELECT id, name FROM Contact__c WHERE id in (SELECT Id FROM Group__c)];
    }
}