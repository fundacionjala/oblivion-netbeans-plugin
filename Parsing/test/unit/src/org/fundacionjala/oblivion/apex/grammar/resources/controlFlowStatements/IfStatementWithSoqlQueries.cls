public with sharing class IfStatementWithSoqlQueries {
    IfStatementWithSoqlQueries() {
        if(contacts == [select count() from Contact where Id = :instance.id]) {

        }
    }
}