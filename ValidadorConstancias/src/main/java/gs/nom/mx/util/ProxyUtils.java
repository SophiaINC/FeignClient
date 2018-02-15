/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gs.nom.mx.util;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

/**
 *
 * @author acruzb
 */
public class ProxyUtils {

	private static String proxyAddress;
	private static int proxyPort;

	static {
		proxyAddress = "10.50.8.20";
		proxyPort = 8080;
	}

	private ProxyUtils() {
	}

	public static boolean isDevelopmentEnvironment() {
		String environment = System.getProperty("system.environment");
		if ("produccion".equalsIgnoreCase(environment)) {
			return false;
		}
		return true;
	}

	/**
	 * Crea un objecto Proxy
	 * 
	 * @return
	 */
	public static Proxy getProxyDevelopment() {
		return new Proxy(Type.HTTP, new InetSocketAddress(proxyAddress, proxyPort));
	}

}