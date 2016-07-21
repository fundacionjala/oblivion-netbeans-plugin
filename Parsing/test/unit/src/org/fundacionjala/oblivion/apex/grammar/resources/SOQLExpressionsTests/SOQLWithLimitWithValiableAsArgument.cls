public class SOQLExpression {
    Integer num;
    {
        num = 20;
        List<Object> list1 = [SELECT field1, field2 FROM sdfgdf LIMIT :num];
        
    }
}