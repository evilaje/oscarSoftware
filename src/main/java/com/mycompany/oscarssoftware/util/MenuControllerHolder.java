package com.mycompany.oscarssoftware.util;

import com.mycompany.oscarssoftware.MenuController;

public class MenuControllerHolder {
    private static MenuController menuController;

    public static MenuController getMenuController() {
        return menuController;
    }

    public static void setMenuController(MenuController controller) {
        menuController = controller;
    }
}

