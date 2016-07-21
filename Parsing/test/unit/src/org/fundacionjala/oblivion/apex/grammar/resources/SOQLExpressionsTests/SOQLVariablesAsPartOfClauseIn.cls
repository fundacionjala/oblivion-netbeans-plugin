public with sharing class SOQLVariablesAsPartOfClauseIn{
    SOQLVariablesAsPartOfClauseIn() {
        List<String> names = new List<String>();
        for(SObject name : [SELECT fooName__c FROM Foo__c WHERE fooName__c NOT IN :names]) { 

        }
    }
}