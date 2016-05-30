package ch2.first.akka.app.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import ch2.first.akka.app.messages.Result;

public class MasterActor extends UntypedActor {

    private ActorRef aggregateActor = getContext().actorOf(
            Props.create(AggregateActor.class), "aggregate");

    private ActorRef reduceActor = getContext().actorOf(Props.create(ReduceActor.class, aggregateActor), "reduce");
    private ActorRef mapActor = getContext().actorOf(Props.create(MapActor.class, reduceActor), "map");
//	private ActorRef reduceActor = getContext().actorOf(
//			Props(new UntypedActorContext() {
//				public UntypedActor create() {
//					return new ReduceActor(aggregateActor);
//				}
//			}), "reduce");

//	private ActorRef mapActor = getContext().actorOf(
//			new Props(new UntypedActorContext() {
//				public UntypedActor create() {
//					return new MapActor(reduceActor);
//				}
//			}), "map");

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            mapActor.tell(message,self());
        } else if (message instanceof Result) {
            aggregateActor.tell(message,self());
        } else
            unhandled(message);
    }
}
