public class SOQLExpression {
    List<Object> list1 = [SELECT documents.field1, field2, (SELECT elements, selectors, (SELECT elements, selectors FROM system) FROM system) FROM documents];
}