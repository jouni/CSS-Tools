package org.vaadin.csstools;

import java.util.Map;

import org.vaadin.csstools.RenderInfo.Callback;
import org.vaadin.csstools.client.VRenderInfoFetcher;
import org.vaadin.csstools.client.VRenderInfoFetcher.CssProperty;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

@ClientWidget(org.vaadin.csstools.client.VRenderInfoFetcher.class)
class RenderInfoFetcher extends Window {

	private Component c;
	private Callback cb;
	private Object[] props;

	public RenderInfoFetcher(Component c, Callback cb, Object... props) {
		this.c = c;
		this.cb = cb;
		this.props = props;
	}

	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);
		target.addAttribute(VRenderInfoFetcher.ATTR_TARGET_COMPONENT, c);
		if (props != null && props.length > 0) {
			target.addAttribute(VRenderInfoFetcher.ATTR_PROPERTIES, props);
		}
	}

	public void changeVariables(Object source, Map<String, Object> variables) {
		super.changeVariables(source, variables);
		if (variables.containsKey(VRenderInfoFetcher.ATTR_RENDER_INFO)) {
			cb.infoReceived(new RenderInfo(variables
					.get(VRenderInfoFetcher.ATTR_RENDER_INFO)));
			getParent().removeWindow(this);
		}
	}

}
