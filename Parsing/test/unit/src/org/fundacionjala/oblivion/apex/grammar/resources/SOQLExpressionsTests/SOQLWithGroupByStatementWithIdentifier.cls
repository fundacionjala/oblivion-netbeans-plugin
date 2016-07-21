public with sharing class SOQLWithGroupByStatementWithIdentifier {
    
    List<Contact> contacts = [
        SELECT Name, MAX(Amount), MIN(Amount)
        FROM Opportunity
        GROUP BY Name
    ];
    
    SOQLWithGroupByStatementWithIdentifier() {
        List<Contact> contacts = [
            SELECT Name, MAX(Amount), MIN(Amount)
            FROM Opportunity
            GROUP BY Name
        ];
        
    }

    public void mehtod() {
        List<Contact> contacts = [
            SELECT Name, MAX(Amount), MIN(Amount)
            FROM Opportunity
            GROUP BY Name, Address
        ];
    }   
    
}
