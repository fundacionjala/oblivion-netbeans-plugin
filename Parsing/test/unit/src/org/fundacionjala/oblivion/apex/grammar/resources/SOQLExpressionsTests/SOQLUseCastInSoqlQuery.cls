public with sharing class SOQLUseCastInSoqlQuery { 

    SOQLUseCastInSoqlQuery() {
        objs = [SELECT Name FROM Account WHERE Id =:(id)obj.get('Field__c')];
    }

}