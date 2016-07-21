public class SOQLExpression {
     List<List<SObject>> searchList = [FIND 'test' RETURNING Account (id where name like '%test%'),Contact (id where name like '%test%')
                                                   WITH DIVISION = ];
}