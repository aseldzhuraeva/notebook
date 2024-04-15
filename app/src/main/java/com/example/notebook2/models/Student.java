package com.example.notebook2.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Student {
    @PrimaryKey(autoGenerate = true)
    Long id;

    @ColumnInfo(name = "name_sur")
    String name_sur;

    @ColumnInfo(name = "num")
    String num;

    @ColumnInfo(name = "image", typeAffinity =  ColumnInfo.BLOB)
    byte[] image;

    public Student(String name_sur, String num, byte[] image) {
        this.name_sur = name_sur;
        this.num = num;
        this.image = image;
    }

    public Student() {
    }

    public Student(Long id, String name_sur, String num, byte[] image) {
        this.id = id;
        this.name_sur = name_sur;
        this.num = num;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName_sur() {
        return name_sur;
    }

    public void setName_sur(String name_sur) {
        this.name_sur = name_sur;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
