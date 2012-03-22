package org.vaadin.csstools;

import java.util.HashMap;

import org.vaadin.csstools.client.VRenderInfoFetcher.CssProperty;

import com.vaadin.ui.Component;

/**
 * Class for fetching render information from the client side widgets.
 * 
 * @author jouni@vaadin.com
 * 
 */
public class RenderInfo {

	HashMap<String, String> props;

	/**
	 * Initiate a request to get the render information for a given component.
	 * <p>
	 * The information can be for example the exact pixel size and position, the
	 * font size, visibility, margin, padding or border of the component in the
	 * browser. See possible values from the {@link CssProperty} enumerable.
	 * <p>
	 * The information will be delivered asynchronously from the client, and
	 * passed as a parameter to the callback method.
	 * <p>
	 * You can limit the amount of properties transmitted over the connection by
	 * passing the desired property names as the last parameter for the method.
	 * 
	 * @param c
	 *            The component whose render information you wish to get.
	 * @param cb
	 *            The callback object which will receive the information when it
	 *            is available.
	 * @param props
	 *            Optional. The list of CSS properties you wish to get from the
	 *            client (limit the amount of transferred data). You can pass
	 *            any type of objects here, the <code>toString()</code> method
	 *            will be used to convert them to actual property names.
	 *            Preferably use the {@link CssProperty} enumerable for feasible values.
	 */
	public static void get(Component c, Callback cb, Object... props) {
		get(c.gtApplication().getMainWindow(), c, cb, props);
	}

	public static void get(Window w, Component c, Callback cb, Object... props) {
		if (c.getApplication() == null) {
			throw new IllegalStateException(
					"The component must be attached to the application before you try to get it's RenderInfo");
		}
		RenderInfoFetcher fetcher = new RenderInfoFetcher(c, cb, props);
		w.addWindow(fetcher);
	}

	/**
	 * The callback interface for RenderInformation requests.
	 * 
	 * @author jouni@vaadin.com
	 * 
	 */
	public interface Callback {
		/**
		 * This method is called when a RenderInfo request is returned from the
		 * client and the RenderInfo for that request is available.
		 * 
		 * @param info
		 *            The RenderInfo object for the request, containing the
		 *            requested properties (or all possible properties if no
		 *            specific properties were requested).
		 */
		public void infoReceived(RenderInfo info);
	}

	@SuppressWarnings("unchecked")
	protected RenderInfo(Object object) {
		props = (HashMap<String, String>) object;
	}

	public String getProperty(CssProperty prop) {
		return props.get(prop.toString());
	}

}
