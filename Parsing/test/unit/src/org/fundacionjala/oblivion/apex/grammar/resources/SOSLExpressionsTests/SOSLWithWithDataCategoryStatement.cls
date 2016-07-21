public class SOQLExpression {
     List<List<SObject>> searchList = [FIND 'tourism' RETURNING FAQ(Id, Title WHERE PublishStatus='online')
                                                      WITH DATA CATEGORY Geography ABOVE France
                                                           AND Product AT mobile];
}