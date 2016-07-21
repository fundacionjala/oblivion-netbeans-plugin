global class UsingIterator implements Database.Batchable<Account, User> {
    Integer i;
    global Iterator<Account> Iterator() {
        return new Six_p72_CustomIterable();
    }
    public static void executeBatch(Database.Batchable batch, Integer limit) {
        Database.BatchableContext BC = new Database.BatchableContext();
        Database.Iterator iterator = batch.start(BC);
        List<Object> items = new List<Object>();
        while(iterator.hasNext()) {
            items.add(iterator.next());
            if(items.size()==limit) {
                batch.execute(BC,items);
                items.clear();

            }
        }
        batch.finish(BC);
    }
    {
        Database.QueryLocator q = Database.getQueryLocator(
        [SELECT Name FROM Account LIMIT 5]);
        Database.QueryLocatorIterator it = q.iterator();
        while (it.hasNext()) {
            Account a = (Account)it.next();
            System.debug(a);

        }
    }
}
