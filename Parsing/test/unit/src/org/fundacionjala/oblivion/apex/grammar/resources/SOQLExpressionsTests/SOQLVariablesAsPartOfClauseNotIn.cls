public with sharing class SOQLVariablesAsPartOfClauseNotIn{
    SOQLVariablesAsPartOfClauseNotIn() {
        List<String> names = new List<String>();
        for(SObject name : [SELECT fooName__c FROM Foo__c WHERE fooName__c IN :names]) { 
        
        }
    }
}