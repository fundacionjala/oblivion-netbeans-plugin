trigger AccountTrigger on Account (after insert, after update){
	if(!Trigger.isInsert){
        }
        for (Account a : Trigger.old) {
        }
}
