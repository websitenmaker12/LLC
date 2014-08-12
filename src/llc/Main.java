package llc;

import llc.event.EventBus;
import llc.event.OnEvent;

import org.lwjgl.LWJGLException;

public class Main {

	public static void main(String[] args) {
//		Main main = new Main();
//		EventBus.global.register(main);
//		EventBus.global.post(new Event("hallo"));
		
		LLC game = new LLC();
		try {
			game.startGame();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	@OnEvent
	public void test(Event event) {
		System.out.println(event.data);
	}
	
	public static class Event {
		
		public String data;
		
		public Event(String data) {
			this.data = data;
		}
		
	}
	
}

