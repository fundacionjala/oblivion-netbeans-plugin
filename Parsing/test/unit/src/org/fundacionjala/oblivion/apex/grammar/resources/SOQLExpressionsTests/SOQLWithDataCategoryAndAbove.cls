public class SOQLExpression {
    List<Object> list1 = [SELECT Title FROM KnowledgeArticleVersion WHERE PublishStatus='online' WITH DATA CATEGORY Geography__c ABOVE usa__c];
        
}