/*
 * generated by Xtext 2.30.0
 */
package com.intuit.dsl.flow.web;

import java.net.InetSocketAddress;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.log.Slf4jLog;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

/**
 * This program starts an HTTP server for testing the web integration of your DSL.
 * Just execute it and point a web browser to http://localhost:8080/
 */
public class ServerLauncher {
	public static void main(String[] args) {
		Server server = new Server(new InetSocketAddress("localhost", 8080));
		WebAppContext ctx = new WebAppContext();
		ctx.setResourceBase("WebRoot");
		ctx.setWelcomeFiles(new String[] {"index.html"});
		ctx.setContextPath("/");
		ctx.setConfigurations(new Configuration[] {
			new AnnotationConfiguration(),
			new WebXmlConfiguration(),
			new WebInfConfiguration(),
			new MetaInfConfiguration()
		});
		ctx.setAttribute(WebInfConfiguration.CONTAINER_JAR_PATTERN,
			".*/com\\.intuit\\.dsl\\.flow\\.web/.*,.*\\.jar");
		ctx.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
		server.setHandler(ctx);
		Slf4jLog log = new Slf4jLog(ServerLauncher.class.getName());
		try {
			server.start();
			log.info("Server started " + server.getURI() + "...");
			new Thread() {

				public void run() {
					try {
						log.info("Press enter to stop the server...");
						int key = System.in.read();
						if (key != -1) {
							server.stop();
						} else {
							log.warn(
									"Console input is not available. In order to stop the server, you need to cancel process manually.");
						}
					} catch (Exception e) {
						log.warn(e);
					}
				}

			}.start();
			server.join();
		} catch (Exception exception) {
			log.warn(exception.getMessage());
			System.exit(1);
		}
	}
}
