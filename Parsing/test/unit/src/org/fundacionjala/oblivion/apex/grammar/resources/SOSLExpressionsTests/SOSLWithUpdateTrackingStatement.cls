public class SOQLExpression {
     List<List<SObject>> searchList = [FIND 'ArticleSample' 
                                       RETURNING KnowledgeArticleVersion (Title WHERE PublishStatus='Online' and language='en_US') 
                                       UPDATE TRACKING];
}