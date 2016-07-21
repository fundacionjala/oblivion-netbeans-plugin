public class SOQLExpression {
     List<List<SObject>> searchList = [FIND 'San Francisco' IN ALL FIELDS RETURNING KnowledgeArticleVersion(id, title WHERE PublishStatus = 'Online' AND Language = 'en_US') 
                                                            WITH SNIPPET (length=120)];
}