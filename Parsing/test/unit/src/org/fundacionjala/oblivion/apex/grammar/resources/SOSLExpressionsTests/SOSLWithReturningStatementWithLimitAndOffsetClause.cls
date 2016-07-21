public class SOQLExpression {
     List<List<SObject>> searchList = [FIND 'test' RETURNING Account(Name, Id ORDER BY Name LIMIT 100 OFFSET 100)];
}