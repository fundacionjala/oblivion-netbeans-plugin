public class SOQLExpression {
     List<List<SObject>> searchList = [FIND 'MyProspect' 
                                            RETURNING Contact(Salutation, FirstName, LastName, AccountId WHERE Name = 'test'),
                                                User(FirstName, LastName), 
                                                Account(id WHERE BillingState IN ('California', 'New York'))];
}