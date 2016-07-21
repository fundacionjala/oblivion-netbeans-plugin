public class SOQLExpression {
     List<List<SObject>> searchList = [FIND 'MyProspect' RETURNING Contact(FirstName, LastName), Account(Name, Industry), Opportunity];
}