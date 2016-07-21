public with sharing class SOQLWithGroupByStatementWithMethodInvocation {
    
    List<Contact> contacts1 = [
            SELECT LeadSource, COUNT(Name) cnt
            FROM Lead
            GROUP BY ROLLUP(LeadSource)
        ];
        
        List<Contact> contacts2 = [
            SELECT Status, LeadSource, COUNT(Name) cnt
            FROM Lead
            GROUP BY ROLLUP(Status, LeadSource)
        ];
    
    SOQLWithGroupByStatementWithMethodInvocation() {
        List<Contact> contacts1 = [
            SELECT LeadSource, COUNT(Name) cnt
            FROM Lead
            GROUP BY ROLLUP(LeadSource)
        ];
        
        List<Contact> contacts2 = [
            SELECT Status, LeadSource, COUNT(Name) cnt
            FROM Lead
            GROUP BY ROLLUP(Status, LeadSource)
        ];
        
    }

    public void mehtod() {
        List<Contact> contacts1 = [
            SELECT LeadSource, COUNT(Name) cnt
            FROM Lead
            GROUP BY ROLLUP(LeadSource)
        ];
        
        List<Contact> contacts2 = [
            SELECT Status, LeadSource, COUNT(Name) cnt
            FROM Lead
            GROUP BY ROLLUP(Status, LeadSource), CUBE(Type, BillingCountry)

        ];
    }   
    
}