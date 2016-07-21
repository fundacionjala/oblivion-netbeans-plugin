public with sharing class DMLDelete {
    public DMLDelete(){
        Account[] doomedAccts = [SELECT Id, Name FROM Account 
        WHERE Name = 'DotCom']; 
        try {
            delete doomedAccts;
        } catch (DmlException e) {
            // Process exception here
        }
    }
}