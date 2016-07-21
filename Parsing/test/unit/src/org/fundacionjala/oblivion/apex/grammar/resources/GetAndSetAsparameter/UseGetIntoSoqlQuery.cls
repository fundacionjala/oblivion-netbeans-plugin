public with sharing class UseGetIntoSoqlQuery {
        
    private final Account account;

    UseGetIntoSoqlQuery() {
       account = [select id, name, site, NumberOfEmployees, Industry 
                    from Account
                where id = :ApexPages.currentPage().getParameters().get('id')];
    }

}