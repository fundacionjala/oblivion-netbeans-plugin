public class SOQLExpression {
     List<List<SObject>> searchList = [FIND 'myCompany'  RETURNING Contact(Salutation, FirstName, LastName , AccountId WHERE Name = 'test' LIMIT 10),
                                          User(FirstName, LastName LIMIT 10),
                                          Lead(company, toLabel(Recordtype.Name), Id ORDER BY Name LIMIT 100 OFFSET 100 ),
                                          Account(id WHERE BillingState IN ('California', 'New York'))];
}