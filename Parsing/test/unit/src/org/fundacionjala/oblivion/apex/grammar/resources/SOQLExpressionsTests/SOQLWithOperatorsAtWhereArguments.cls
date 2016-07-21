public with sharing class soql_where {
    List<Object> list1 = [SELECT name, address, age, administrator
              FROM datas, table as t 
             WHERE age != 54 
                    or age <> 54
                    or age > 10
                    or age <  100
                    or age <= 100
                    or age >= 10
    ];
}