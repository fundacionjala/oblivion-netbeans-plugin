public with sharing class IfStatementWithSoqlQueriesAndOperatorOr {
    IfStatementWithSoqlQueriesAndOperatorOr() {
        if(true || contacts == [select count() from Contact where Id = :instance.id] ) {

        }
    }
}