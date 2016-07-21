public with sharing class IfStatementWithSoqlQueriesWithDifferentOperators {
    IfStatementWithSoqlQueriesWithDifferentOperators() {
        if(contacts <= [select count() from Contact where Id = :instance.id]) { }

        if(contacts >= [select count() from Contact where Id = :instance.id]) { }

        if(contacts > [select count() from Contact where Id = :instance.id]) { }

        if(contacts < [select count() from Contact where Id = :instance.id]) { }

        if(contacts != [select count() from Contact where Id = :instance.id]) { }
    }
}