public class SOQLExpression {
     List<List<SObject>> searchList = [FIND 'MyCompany' RETURNING Contact, Lead, Account];
}