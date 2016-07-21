public with sharing class SOQLMisuseCastInSoqlQuery { 
    
    SOQLMisuseCastInSoqlQuery() {
        objs = [SELECT Name FROM Account WHERE Id =:obj.(id)method('Field__c')];
    }

}