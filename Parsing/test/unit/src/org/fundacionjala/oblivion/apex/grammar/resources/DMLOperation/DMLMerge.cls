public with sharing class DMLMerge {
    public DMLMerge(){
        List<Account> ls = new List<Account>{new Account(name='Acme Inc.'),new Account(name='Acme')};
        insert ls;
        Account masterAcct = [SELECT Id, Name FROM Account WHERE Name = 'Acme Inc.' LIMIT 1];
        Account mergeAcct = [SELECT Id, Name FROM Account WHERE Name = 'Acme' LIMIT 1];
        try {
            merge masterAcct mergeAcct;
        } catch (DmlException e) {
            // Process exception here
        }
    }
}