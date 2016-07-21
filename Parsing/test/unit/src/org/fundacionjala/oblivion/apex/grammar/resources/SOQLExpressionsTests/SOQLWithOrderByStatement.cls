public with sharing class soql_oreder_by {
    {
        fx([
                SELECT name
                  FROM datas
              order by name asc,
                       name desc,
                       name asc nulls first,
                       name desc nulls last
          ]);
    }
}