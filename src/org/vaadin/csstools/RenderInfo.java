package org.vaadin.csstools;

import java.util.HashMap;

import org.vaadin.csstools.client.VRenderInfoFetcher.CssProperty;

import com.vaadin.ui.Component;

public class RenderInfo {

	HashMap<String, String> props;

	public static void get(Component c, Callback cb, Object... props) {
		if (c.getApplication() == null) {
			throw new IllegalStateException(
					"The component must be attached to the application before you try to get it's RenderInfo");
		}
		RenderInfoFetcher fetcher = new RenderInfoFetcher(c, cb, props);
		c.getApplication().getMainWindow().addWindow(fetcher);
	}

	public interface Callback {
		public void infoReceived(RenderInfo info);
	}

	public RenderInfo(Object object) {
		props = (HashMap<String, String>) object;
	}

	public String getProperty(CssProperty prop) {
		return props.get(prop.toString());
	}

}
