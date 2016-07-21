public with sharing class SOQLMethodAccessInWhere {
    SOQLMethodAccessInWhere(){
        Account myAcct = [SELECT Id, Name, BillingCity FROM Account WHERE Name = :obj.getName()];
    } 
}