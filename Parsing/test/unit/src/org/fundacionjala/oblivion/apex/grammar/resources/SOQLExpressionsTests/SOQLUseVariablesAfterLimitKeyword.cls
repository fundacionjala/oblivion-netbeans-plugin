public with sharing class SOQLUseVariablesAfterLimitKeyword {

    SOQLUseVariablesAfterLimitKeyword(){
        [SELECT Id FROM Account LIMIT :m1()];
    }

}