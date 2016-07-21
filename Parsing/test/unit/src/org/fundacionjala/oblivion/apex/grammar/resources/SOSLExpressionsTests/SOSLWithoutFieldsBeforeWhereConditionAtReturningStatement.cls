public class SOQLExpression {
     List<List<SObject>> searchList = [FIND 'MyProspect' RETURNING Contact(WHERE Name = 'test')];
}