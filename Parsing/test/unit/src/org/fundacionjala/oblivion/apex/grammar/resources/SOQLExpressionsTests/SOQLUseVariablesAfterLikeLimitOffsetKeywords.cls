public with sharing class SOQLUseVariablesAfterLikeLimitOffsetKeywords {

    SOQLUseVariablesAfterLikeLimitOffsetKeywords() {
        [SELECT Id FROM Account WHERE Name like :abc LIMIT :m1() OFFSET :m1()];
    }

}