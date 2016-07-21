public with sharing class DMLUpsertWithOptField {
    public DMLUpsertWithOptField(){
        List<Account> acctList = new List<Account>();
        // Fill the accounts list with some accounts
        try {
            // Upsert using an external ID field
            upsert acctList myExtIDField__c;
        } catch (DmlException e) {

        }
    }
}