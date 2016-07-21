public with sharing class SOQLDateLiteralExpression {
    {
        Result result =  [SELECT Id FROM Account WHERE CreatedDate > 1999-12-31];
        Result result =  [SELECT Id FROM Account WHERE CreatedDate > 1999-08-01T23:01:01Z];
        Result result =  [SELECT Id FROM Account WHERE CreatedDate > 1999-01-01T23:01:01+01:00];
        Result result =  [SELECT Id FROM Account WHERE CreatedDate > 1999-01-01T23:01:01-08:00];
    }
}