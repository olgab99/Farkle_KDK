package com.olmatech.kindle.farkle;

import com.amazon.kindle.kindlet.AbstractKindlet;
import com.amazon.kindle.kindlet.KindletContext;


public class Farkle extends AbstractKindlet {
	
	private Controller controller;

    public void create(final KindletContext context) {
    	controller = Controller.getController();
    	controller.initUI(context);
    }

    public void start() {
    	controller.onStart();
    }    

    public void stop() {
    	controller.onStop();
    }    

    public void destroy() {
    	controller.onDestroy();
    }
}
