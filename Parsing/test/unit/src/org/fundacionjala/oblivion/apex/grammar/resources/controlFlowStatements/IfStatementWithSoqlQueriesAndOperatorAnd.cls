public with sharing class IfStatementWithSoqlQueriesAndOperatorAnd {
    IfStatementWithSoqlQueriesAndOperatorAnd() {
        if(true && contacts == [select count() from Contact where Id = :instance.id] ) {

        }
    }
}