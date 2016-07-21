public with sharing class SOQLUseVariablesAfterLikeKeyword {

    SOQLUseVariablesAfterLikeKeyword(){
        [SELECT Id FROM Account WHERE Name like :abc];
    }

}