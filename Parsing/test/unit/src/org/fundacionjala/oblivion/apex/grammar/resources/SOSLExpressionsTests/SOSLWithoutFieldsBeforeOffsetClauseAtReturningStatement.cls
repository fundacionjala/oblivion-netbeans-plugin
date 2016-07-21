public class SOQLExpression {
     List<List<SObject>> searchList = [FIND 'John Smith' RETURNING Account (OFFSET 25)];
}