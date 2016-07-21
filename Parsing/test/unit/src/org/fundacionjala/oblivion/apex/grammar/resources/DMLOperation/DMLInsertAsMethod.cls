public with sharing class DMLInsert {
    public DMLInsert(){
        Account newAcct = new Account(name = 'Acme');
        try {
            Database.insert(newAcct);
        } catch (DmlException e) {
            // Process exception here
        }
    }
}