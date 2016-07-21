public class SOQLExpression {
     List<List<SObject>> searchList = [FIND 'Joe' RETURNING Lead(company, toLabel(Recordtype.Name))];
}