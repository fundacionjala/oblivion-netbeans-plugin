public with sharing class SOQLAliasInSoqlFunctions {
    {
        AggregateResult[] contactsCount = [SELECT count(a) total FROM Contact__c];
    }
}