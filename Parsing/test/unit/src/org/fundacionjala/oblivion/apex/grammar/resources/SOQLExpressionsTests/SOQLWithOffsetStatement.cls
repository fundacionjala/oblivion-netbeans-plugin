public class SOQLExpression {
        List<Object> list1 = [SELECT Name FROM Account WHERE NumberOfEmployees < 10 OFFSET 10];
}