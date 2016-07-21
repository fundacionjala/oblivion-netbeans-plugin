public class SOQLExpression {
    List<Object> list1 = [SELECT Name FROM Account WHERE NumberOfEmployees > 10];
    List<Object> list2 = [SELECT Name FROM Account WHERE NumberOfEmployees < 10];
    List<Object> list3 = [SELECT Name FROM Account WHERE NumberOfEmployees = 10];
    List<Object> list4 = [SELECT Name FROM Account WHERE NumberOfEmployees >= 10];
    List<Object> list5 = [SELECT Name FROM Account WHERE NumberOfEmployees <= 10];
    List<Object> list6 = [SELECT Name FROM Account WHERE NumberOfEmployees != 10];
}