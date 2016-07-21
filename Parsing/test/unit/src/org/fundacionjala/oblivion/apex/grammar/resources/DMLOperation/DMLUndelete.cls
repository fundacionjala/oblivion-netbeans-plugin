public with sharing class DMLUndelete {
    public DMLUndelete(){
        Account[] savedAccts = [SELECT Id, Name FROM Account WHERE Name = 'Trump' ]; 
        try {
            undelete savedAccts;
        } catch (DmlException e) {
            // Process exception here
        }
    }
}