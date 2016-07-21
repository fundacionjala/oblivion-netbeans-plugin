public class SOQLExpression {
     List<List<SObject>> searchList = [FIND 'MyProspect' RETURNING Account(LIMIT 10)];
}