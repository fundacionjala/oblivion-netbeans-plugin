public with sharing class SOQLAliasInSoqlFunctionsForSelectNested {
    {
         List<Object> list1 = [SELECT count(a) total, (SELECT count(b) total FROM system) FROM document];
    }
}