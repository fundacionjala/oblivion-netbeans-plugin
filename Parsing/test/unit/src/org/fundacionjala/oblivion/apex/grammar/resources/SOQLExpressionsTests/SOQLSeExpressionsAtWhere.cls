public with sharing class soql_where {
    {
        fx([ 
                SELECT name, address, age, administrator
                  FROM datas, table as t 
                 WHERE name in ('text1','text2','text3')
                    or name not in ('text1','text2','text3')
                    or name includes ('text1','text2','text3')
                    or name excludes ('text1','text2','text3')
        ]);
    }
}