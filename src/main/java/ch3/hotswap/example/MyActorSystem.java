package ch3.hotswap.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class MyActorSystem {

	public static void main(String[] args) throws Exception {

		ActorSystem _system = ActorSystem.create("BecomeUnbecome");
		ActorRef pingPongActor = _system
				.actorOf(Props.create(PingPongActor.class),"PingPong");

		pingPongActor.tell(PingPongActor.PING,ActorRef.noSender());

		Thread.sleep(2000);

		_system.shutdown();

	}

}
