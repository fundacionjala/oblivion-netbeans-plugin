public with sharing class SOQLMemberAccessInWhere {
    SOQLMemberAccessInWhere(){
        Account myAcct = [SELECT Id, Name, BillingCity FROM Account WHERE Id = :obj.Id];
    } 
}