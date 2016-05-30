package ch2.first.akka.app;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import ch2.first.akka.app.actors.MasterActor;
import ch2.first.akka.app.messages.Result;

public class MapReduceApplication {

	public static void main(String[] args) throws Exception {

		ActorSystem _system = ActorSystem.create("MapReduceApp");
		
		ActorRef master = _system.actorOf(Props.create(MasterActor.class),"master");
		
		master.tell("The quick brown fox tried to jump over the lazy dog and fell on the dog",ActorRef.noSender());
		master.tell("Dog is man's best friend",ActorRef.noSender());
		master.tell("Dog and Fox belong to the same family",ActorRef.noSender());
		
		Thread.sleep(500);
		
		master.tell(new Result(),ActorRef.noSender());
		
		Thread.sleep(500);
		
		_system.shutdown();
	}
}
