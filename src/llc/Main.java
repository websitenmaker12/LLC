package llc;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import llc.util.SystemUtil;

import org.lwjgl.LWJGLException;

public class Main {

	public static void main(String[] args) {
		/**
		 * Sets the global swing design (for dialogs) to the system GUI library.
		 */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		String versionJRE = System.getProperty("java.version");
		
		if (SystemUtil.compareVersions(versionJRE, "1.7") == -1) {
			JOptionPane.showMessageDialog(null, "Your java runtime environment is not up to date ): (You use " + versionJRE + ")", "Higher JRE version expected", JOptionPane.ERROR_MESSAGE);
			return;
		}

		LLC game = new LLC();
		
		try {
			game.startGame();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
}

