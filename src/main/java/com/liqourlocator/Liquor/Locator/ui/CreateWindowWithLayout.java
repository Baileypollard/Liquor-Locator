package com.liqourlocator.Liquor.Locator.ui;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class CreateWindowWithLayout extends Window
{
    private HorizontalLayout layout;

    public CreateWindowWithLayout(HorizontalLayout layout)
    {
        super("Establishment Information");
        this.layout = layout;
        this.layout.setSizeFull();
        setContent(layout);
        getContent().setSizeUndefined();
        center();
    }
}