public class SOQLExpression {
    List<Object> list1 = [SELECT Title FROM Question WHERE Numbers > 55 WITH DATA CATEGORY Geography__c AT (usa__c, uk__c)];
    List<Object> list2 = [SELECT UrlName FROM KnowledgeArticleVersion WHERE PublishStatus='draft' WITH DATA CATEGORY Geography__c AT usa__c AND Product__c ABOVE_OR_BELOW mobile_phones__c];
}