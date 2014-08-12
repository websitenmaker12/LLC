package llc.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class EventBus {

	public static EventBus bus = new EventBus();
	
	private Map<Class, List<Method>> eventHandlers;
	
	public EventBus() {
		this.eventHandlers = new HashMap<Class, List<Method>>();
	}
	
	/**
	 * Registers a class which can handle event calls
	 */
	public void register(Object obj) {
		for(Method method : obj.getClass().getMethods()) {
			if(method.isAnnotationPresent(OnEvent.class) && method.getParameterCount() == 1) {
				Class eventClass = method.getParameterTypes()[0];
				
				if(!this.eventHandlers.containsKey(eventClass)) {
					this.eventHandlers.put(eventClass, new ArrayList<Method>());
				}
				
				this.eventHandlers.get(eventClass).add(method);
			}
		}
	}
	
	/**
	 * Posts the event
	 */
	public void post(Object event) {
		if(this.eventHandlers.containsKey(event.getClass())) {
			for(Method method : this.eventHandlers.get(event.getClass())) {
				try {
					method.invoke(event);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
