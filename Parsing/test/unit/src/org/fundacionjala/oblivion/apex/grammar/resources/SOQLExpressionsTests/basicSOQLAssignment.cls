public class SOQLExpression {
    List<Object> list1 = [SELECT field1, field2 FROM documents];
    List<Object> list2;
    {
        list2 =  [SELECT field1, field2 FROM documents];
    }
}