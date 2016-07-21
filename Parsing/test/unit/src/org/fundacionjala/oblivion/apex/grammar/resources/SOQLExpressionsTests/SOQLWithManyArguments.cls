public class SOQLExpression {
    List<Object> list3 = [SELECT Id, Name FROM Contact WHERE BirthDate = TODAY AND (( Phone != null AND DoNotCall = false AND Phone LIKE '%(650)%' ) OR Email != null) ORDER BY Name ASC LIMIT 5];
}