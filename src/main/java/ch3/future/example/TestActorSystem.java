package ch3.future.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class TestActorSystem {

	public static void main(String[] args) throws Exception {
		ActorSystem _system = ActorSystem.create("FutureUsageExample");
		ActorRef processOrder = _system.actorOf(Props.create(
				ProcessOrderActor.class),"ProcessOrder");
		processOrder.tell(Integer.valueOf(456),ActorRef.noSender());

		Thread.sleep(5000);

		_system.shutdown();
	}

}
