package org.quantum.rms.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;

public class AdminSettingsView extends Composite<Component>{

    private static final long serialVersionUID = 1L;

    @Override
    protected Component initContent() {
	return new Div(new Text("admin settings"));
    }
    
    

}