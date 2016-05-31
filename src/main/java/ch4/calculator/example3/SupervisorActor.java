package ch4.calculator.example3;

import akka.actor.*;
import akka.actor.SupervisorStrategy.*;
import akka.actor.TypedActor.PostStop;
import akka.actor.TypedActor.PreStart;
import akka.actor.TypedActor.Receiver;
import akka.actor.TypedActor.Supervisor;
import akka.dispatch.Futures;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.japi.Option;
import ch4.calculator.CalculatorInt;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.*;

public class SupervisorActor implements Receiver, CalculatorInt, PreStart,
        PostStop, Supervisor {

    LoggingAdapter log = Logging.getLogger(TypedActor.context().system(), this);
    Integer counter = 0;

    // create a child actor under the Typed Actor context
    ActorRef childActor = TypedActor.context().actorOf(
            Props.create(ChildActor.class), "childActor");

    // Non blocking request response
    public Future<Integer> add(Integer first, Integer second) {
        return Futures.successful(first + second);
    }

    // Non blocking request response
    public Future<Integer> subtract(Integer first, Integer second) {
        return Futures.successful(first - second);

    }

    // fire and forget
    public void incrementCount() {
        counter++;
    }

    // Blocking request response
    public Option<Integer> incrementAndReturn() {
        return Option.some(++counter);
    }

    // Allows to tap into the Actor PreStart hook
    public void preStart() {
        log.info("Actor Started !");
    }

    public void onReceive(Object msg, ActorRef actor) {
        log.info("Received Message -> {}", msg);
        childActor.tell(msg, actor);
    }

    // Allows to tap into the Actor PostStop hook
    public void postStop() {
        log.info("Actor Stopped ! ");
    }

    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    private static SupervisorStrategy strategy = new OneForOneStrategy(10,
            Duration.create(10, "second"), new Function<Throwable, Directive>() {
        public Directive apply(Throwable t) {
            if (t instanceof ArithmeticException) {
                return resume();
            } else if (t instanceof IllegalArgumentException) {
                return restart();
            } else if (t instanceof NullPointerException) {
                return stop();
            } else {
                return escalate();
            }
        }
    });
}
