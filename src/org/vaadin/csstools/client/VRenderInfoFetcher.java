package org.vaadin.csstools.client;

import java.util.HashMap;

import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.VWindow;

public class VRenderInfoFetcher extends VWindow {

	public static final String ATTR_TARGET_COMPONENT = "c";
	public static final String ATTR_PROPERTIES = "props";
	public static final String ATTR_RENDER_INFO = "ri";

	public static enum CssProperty {
		width, height,

		marginTop, marginRight, marginBottom, marginLeft,

		paddingTop, paddingRight, paddingBottom, paddingLeft,

		borderTopWidth, borderRightWidth, borderBottomWidth, borderLeftWidth,

		fontSize, color,

		display, visibility, overflow, overflowX, overflowY,

		position, top, right, bottom, left, zIndex,

		absoluteTop, absoluteLeft
	}

	public VRenderInfoFetcher() {
		super();
		setShadowEnabled(false);
		getElement().getStyle().setVisibility(Visibility.HIDDEN);
		setWidth("0px");
		setHeight("0px");
		setPopupPosition(-999, -999);
	}

	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
		super.updateFromUIDL(uidl, client);
		getElement().getStyle().setVisibility(Visibility.HIDDEN);

		Widget w = (Widget) uidl.getPaintableAttribute(ATTR_TARGET_COMPONENT,
				client);
		ComputedStyle cs = new ComputedStyle(w.getElement());

		Object[] props = CssProperty.values();
		if (uidl.hasAttribute(ATTR_PROPERTIES)) {
			props = uidl.getStringArrayAttribute(ATTR_PROPERTIES);
		}

		HashMap<String, Object> values = new HashMap<String, Object>();
		for (Object prop : props) {
			if (prop.toString().equals(CssProperty.absoluteLeft.toString())) {
				values.put(CssProperty.absoluteLeft.toString(),
						w.getAbsoluteLeft() + "px");
			} else if (prop.toString().equals(
					CssProperty.absoluteTop.toString())) {
				values.put(CssProperty.absoluteTop.toString(),
						w.getAbsoluteTop() + "px");
			} else {
				values.put(prop.toString(), cs.getProperty(prop.toString()));
			}
		}
		client.updateVariable(uidl.getId(), ATTR_RENDER_INFO, values, true);
	}
}
