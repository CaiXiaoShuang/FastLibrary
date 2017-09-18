package com.intelligence.androidlibrary.bottomNavigationViews;

public class BottomNavigationItem {

    private String title;
    private int color;
    private int activeImageResource;
    private int inactiveImageResource;

    public BottomNavigationItem(String title, int color, int activeImageResource,int inactiveImageResource) {
        this.title = title;
        this.color = color;
        this.activeImageResource = activeImageResource;
        this.inactiveImageResource=inactiveImageResource;
    }

    public int getActiveImageResource() {
        return activeImageResource;
    }

    public void setActiveImageResource(int activeImageResource) {
        this.activeImageResource = activeImageResource;
    }

    public int getInactiveImageResource() {
        return inactiveImageResource;
    }

    public void setInactiveImageResource(int inactiveImageResource) {
        this.inactiveImageResource = inactiveImageResource;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
