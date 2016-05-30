package ch4.calculator.example2;

import java.util.Arrays;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import ch4.calculator.Calculator;
import ch4.calculator.CalculatorInt;

public class CalculatorActorSytem {

	public static void main(String[] args) throws Exception {
		ActorSystem _system = ActorSystem.create("TypedActorsExample");

		CalculatorInt calculator1 = TypedActor.get(_system).typedActorOf(
				new TypedProps<Calculator>(CalculatorInt.class,Calculator.class));

		CalculatorInt calculator2 = TypedActor.get(_system).typedActorOf(
				new TypedProps<Calculator>(CalculatorInt.class,Calculator.class));

		// Create a router with Typed Actors
		ActorRef actor1 = TypedActor.get(_system).getActorRefFor(calculator1);
		ActorRef actor2 = TypedActor.get(_system).getActorRefFor(calculator2);

		Iterable<ActorRef> routees = Arrays.asList(new ActorRef[] { actor1,	actor2 });
		ActorRef router = _system.actorOf(Props.create(routees)
				.withRouter(BroadcastRouter.create(routees)));

		router.tell("Hello there",ActorRef.noSender());

		_system.shutdown();

	}

}
