public with sharing class soql_where {
    List<Object> list1 = [SELECT name, address, age, administrator
              FROM datas, table as t 
             WHERE x or (a and b or h ) 
    ];
}