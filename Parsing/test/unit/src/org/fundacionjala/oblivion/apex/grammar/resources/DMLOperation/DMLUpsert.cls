public with sharing class DMLUpsert {
    public DMLUpsert(){
        List<Account> acctList = new List<Account>();
        // Fill the accounts list with some accounts
        try {
            upsert acctList;
        } catch (DmlException e) {

        }
    }
}