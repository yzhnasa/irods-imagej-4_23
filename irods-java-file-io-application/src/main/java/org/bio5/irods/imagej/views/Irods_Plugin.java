package org.bio5.irods.imagej.views;

import ij.IJ;
import ij.ImageJ;
import ij.plugin.frame.PlugInFrame;

import java.awt.Frame;
import java.awt.event.WindowEvent;

import org.apache.log4j.Logger;
import org.bio5.irods.imagej.bean.IrodsImageJBean;

public class Irods_Plugin extends PlugInFrame {

	private static final long serialVersionUID = 3225639715931294038L;

	/* Declare static variables */
	private static Frame instance;
	private IrodsImageJBean irodsImagej;

	/* Logger instantiation */
	static Logger log = Logger.getLogger(Irods_Plugin.class.getName());

	public Irods_Plugin() {
		super("iRODS");
		log = Logger.getLogger(Irods_Plugin.class);
	}

	public void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			instance = null;
		}
	}

	public void run(String arg) {

		MainWindow mainWindow = new MainWindow();
		irodsImagej = new IrodsImageJBean();
		irodsImagej.setMainWindow(mainWindow);
		mainWindow.setVisible(true);

	}

	/* Main method to Debug the code - Remove it once app is done! */
	public static void main(String[] args) {
		// set the plugins.dir property to make the plugin appear in the Plugins
		// menu
		Class<?> clazz = Irods_Plugin.class;
		String url = clazz.getResource(
				"/" + clazz.getName().replace('.', '/') + ".class").toString();
		String pluginsDir = url.substring(5, url.length()
				- clazz.getName().length() - 6);
		System.setProperty("plugins.dir", pluginsDir);

		// start ImageJ
		new ImageJ();

		// open a sample bio5 image
		/*
		 * ImagePlus image = IJ.openImage(
		 * "http://www.bio5.org/sites/default/files/homepage/slides/5_areas_circle_300pxWidth.png"
		 * ); image.show();
		 */

		// run the plugin
		IJ.runPlugIn(clazz.getName(), "");
	}
}
