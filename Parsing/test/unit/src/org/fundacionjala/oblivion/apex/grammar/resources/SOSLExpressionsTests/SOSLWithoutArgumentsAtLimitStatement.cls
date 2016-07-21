public class SOQLExpression {
     List<List<SObject>> searchList = [FIND 'test' RETURNING Account(id LIMIT 20), Contact, Opportunity(id LIMIT 75) 
                                                   LIMIT];
}