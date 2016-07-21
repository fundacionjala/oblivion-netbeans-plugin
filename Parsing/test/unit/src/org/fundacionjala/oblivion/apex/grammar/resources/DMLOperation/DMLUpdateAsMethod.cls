public with sharing class DMLUpdate {
    public DMLUpdate(){
        Account a = new Account(Name='Acme2');
        insert(a);
        Account myAcct = [SELECT Id, Name, BillingCity FROM Account WHERE Id = :a];
        myAcct.BillingCity = 'San Francisco'; 
        try {
            Database.update(myAcct);
        } catch (DmlException e) {
            // Process exception here
        }
    }
}