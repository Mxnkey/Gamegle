package com.siw.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class VideoGame{

    private String name;
    private String kind;
    private ArrayList<String> platforms = new ArrayList<String>();
    private String editor;

    public VideoGame() {
    }

    public VideoGame(String name, String kind, ArrayList<String> platforms, String editor) {
        this.name = name;
        this.kind = kind;
        this.platforms = platforms;
        this.editor = editor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public ArrayList<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(ArrayList<String> platforms) {
        this.platforms = platforms;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}
