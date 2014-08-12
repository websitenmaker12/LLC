package llc.loading;

import java.util.ArrayList;
import java.util.List;

public class IOThread extends Thread{

	private List<Runnable> tasks = new ArrayList<Runnable>();
	
	@Override
	public void run() {
		if (tasks.size() > 0) {
			Runnable r = tasks.get(0);
			try {
				r.run();
			}
			catch (Exception e) {
				System.out.println("Exception occured while trying to perfom task in IO Thread. Stacktrace:");
				e.printStackTrace();
			}
			tasks.remove(0);
		}
	}
	
	public void add(Runnable r) {
		tasks.add(r);
	}
}
