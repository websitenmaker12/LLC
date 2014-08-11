package llc.engine;

import java.util.ArrayList;
import java.util.List;

public class Profiler {

	private String currentOperation = "";
	private List<String> history = new ArrayList<String>();
	private List<Long> stamps = new ArrayList<Long>();
	
	public void start(String name) {
		this.currentOperation = name;
		this.history.add(name);
		this.stamps.add(System.currentTimeMillis());
	}
	
	public void end() {
		long time = System.currentTimeMillis() - this.stamps.get(this.stamps.size() - 1);
		if(time > 4000) System.out.println("Operation '" + this.currentOperation + "' took longer than excepted (" + time + " millis)");
		
		this.history.remove(this.history.size() - 1);
		this.stamps.remove(this.stamps.size() - 1);
		
		if(this.history.size() > 0) this.currentOperation = this.history.get(this.history.size() - 1);
		else this.currentOperation = "";
	}
	
	public void endStart(String name) {
		this.end();
		this.start(name);
	}
	
}
