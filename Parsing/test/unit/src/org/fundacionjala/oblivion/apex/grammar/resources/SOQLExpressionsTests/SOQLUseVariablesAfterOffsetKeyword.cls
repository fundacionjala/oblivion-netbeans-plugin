public with sharing class SOQLUseVariablesAfterOffsetKeyword {

    SOQLUseVariablesAfterOffsetKeyword(){
        [SELECT Id FROM Account OFFSET :mi()];
    }

}