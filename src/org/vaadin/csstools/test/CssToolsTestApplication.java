package org.vaadin.csstools.test;

import java.util.HashMap;

import org.vaadin.csstools.RenderInfo;
import org.vaadin.csstools.RenderInfo.Callback;
import org.vaadin.csstools.client.VRenderInfoFetcher.CssProperty;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public class CssToolsTestApplication extends Application {

	@Override
	public void init() {
		final Window main = new Window("CSS Tools Add-on Test");
		setMainWindow(main);

		final Window testWindow = new Window("Normal Window");
		testWindow.addComponent(new Label(
				"<p>This window is used as the component to measure.</p>",
				Label.CONTENT_XHTML));
		main.addWindow(testWindow);
		testWindow.center();

		Label title = new Label("CSS Properties to Retrieve");
		title.addStyleName(Reindeer.LABEL_H2);
		main.addComponent(title);

		final NativeSelect target = new NativeSelect("Target Component");
		main.addComponent(target);

		Button get = new Button("Refresh Properties",
				new Button.ClickListener() {
					public void buttonClick(ClickEvent event) {
						RenderInfo.get((Component) target.getValue(),
								new Callback() {
									public void infoReceived(RenderInfo info) {
										for (CssProperty prop : CssProperty
												.values()) {
											props.get(prop)
													.setValue(
															info.getProperty(CssProperty
																	.valueOf(prop
																			.toString())));
										}
									}
								});
					}
				});
		main.addComponent(get);

		main.addComponent(buildLabels());

		target.addItem(main.getContent());
		target.setItemCaption(main.getContent(), "Root layout");
		target.addItem(testWindow);
		target.setItemCaption(testWindow, "Sub window");
		target.addItem(get);
		target.setItemCaption(get, "The '" + get.getCaption() + "' Button");
		target.setNullSelectionAllowed(false);
		target.select(testWindow);

	}

	HashMap<CssProperty, Label> props = new HashMap<CssProperty, Label>();

	private GridLayout buildLabels() {
		GridLayout grid = new GridLayout();
		grid.setSpacing(true);
		grid.setWidth("100%");
		grid.setColumns(6);
		for (CssProperty prop : CssProperty.values()) {
			Label l = new Label("-");
			l.setSizeUndefined();
			l.setCaption(prop.toString());
			props.put(prop, l);
			grid.addComponent(l);
		}
		return grid;
	}

}
