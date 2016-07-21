public class SOQLExpression {
     List<List<SObject>> searchList = [FIND 'test' RETURNING User (id),FeedItem (id, ParentId WHERE CreatedDate = THIS_YEAR Order by CreatedDate DESC)
                                                   WITH NETWORK = 'NetworkId'];
}