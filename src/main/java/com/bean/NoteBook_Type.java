package com.bean;

public class NoteBook_Type {
    private String id;
    private String name;
    private String type_desc;//类型说明

    @Override
    public String toString() {
        return "NoteBook_Type{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type_desc='" + type_desc + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType_desc() {
        return type_desc;
    }

    public void setType_desc(String type_desc) {
        this.type_desc = type_desc;
    }
}
