package ch3.future.example;


import akka.actor.UntypedActor;
import ch3.future.example.messages.Order;

public class OrderActor extends UntypedActor {

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Integer) {
			Integer userId = (Integer) message;
			// ideally we will get list of orders for given user id
			Order order = new Order(Integer.valueOf(123), Float.valueOf(345),
					Integer.valueOf(5));
			getSender().tell(order,self());
		}
	}
}
