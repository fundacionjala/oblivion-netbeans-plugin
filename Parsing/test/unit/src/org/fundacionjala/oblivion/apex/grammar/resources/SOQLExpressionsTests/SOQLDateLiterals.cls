public with sharing class DateLiterals {
    {
        Result result;
        result =  [SELECT Id FROM Account WHERE CreatedDate = YESTERDAY];
        result =  [SELECT Id FROM Account WHERE CreatedDate > TODAY];
        result =  [SELECT Id FROM Opportunity WHERE CloseDate = TOMORROW];
        result =  [SELECT Id FROM Account WHERE CreatedDate > LAST_WEEK];
        result =  [SELECT Id FROM Account WHERE CreatedDate < THIS_WEEK];
        result =  [SELECT Id FROM Opportunity WHERE CloseDate = NEXT_WEEK];
        result =  [SELECT Id FROM Opportunity WHERE CloseDate > LAST_MONTH];
        result =  [SELECT Id FROM Account WHERE CreatedDate < THIS_MONTH];
        result =  [SELECT Id FROM Opportunity WHERE CloseDate = NEXT_MONTH];
        result =  [SELECT Id FROM Account WHERE CreatedDate = LAST_90_DAYS];
        result =  [SELECT Id FROM Opportunity WHERE CloseDate > NEXT_90_DAYS];
        result =  [SELECT Id FROM Account WHERE CreatedDate = LAST_N_DAYS:365];
        result =  [SELECT Id FROM Opportunity WHERE CloseDate > NEXT_N_DAYS:15];
        result =  [SELECT Id FROM Opportunity WHERE CloseDate > NEXT_N_WEEKS:4];
        result =  [SELECT Id FROM Account WHERE CreatedDate = LAST_N_WEEKS:52];
        result =  [SELECT Id FROM Opportunity WHERE CloseDate > NEXT_N_MONTHS:2];
        result =  [SELECT Id FROM Account WHERE CreatedDate = LAST_N_MONTHS:12];
        result =  [SELECT Id FROM Account WHERE CreatedDate = THIS_QUARTER];
        result =  [SELECT Id FROM Account WHERE CreatedDate > LAST_QUARTER];
        result =  [SELECT Id FROM Account WHERE CreatedDate < NEXT_QUARTER];
        result =  [SELECT Id FROM Account WHERE CreatedDate < NEXT_N_QUARTERS:2];
        result =  [SELECT Id FROM Account WHERE CreatedDate > LAST_N_QUARTERS:2];
        result =  [SELECT Id FROM Opportunity WHERE CloseDate = THIS_YEAR];
        result =  [SELECT Id FROM Opportunity WHERE CloseDate < NEXT_YEAR];
        result =  [SELECT Id FROM Opportunity WHERE CloseDate < NEXT_N_YEARS:5];
        result =  [SELECT Id FROM Account WHERE CreatedDate = THIS_FISCAL_QUARTER];
        result =  [SELECT Id FROM Account WHERE CreatedDate > LAST_FISCAL_QUARTER];
        result =  [SELECT Id FROM Account WHERE CreatedDate < NEXT_FISCAL_QUARTER];
        result =  [SELECT Id FROM Account WHERE CreatedDate < NEXT_N_FISCAL_QUARTERS:6];
        result =  [SELECT Id FROM Opportunity WHERE CloseDate = THIS_FISCAL_YEAR];
        result =  [SELECT Id FROM Opportunity WHERE CloseDate > LAST_FISCAL_YEAR];
        result =  [SELECT Id FROM Opportunity WHERE CloseDate < NEXT_FISCAL_YEAR];
        result =  [SELECT Id FROM Opportunity WHERE CloseDate < NEXT_N_FISCAL_YEARS:3];
        result =  [SELECT Id FROM Opportunity WHERE CloseDate > LAST_N_FISCAL_YEARS:3];

    }

}