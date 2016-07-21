public class SOQLExpression {
     List<List<SObject>> searchList = [	FIND 'MyContactName' RETURNING Contact(Name, Id ORDER BY Name), 
                                                                Account(Description, Id ORDER BY Description)];
}