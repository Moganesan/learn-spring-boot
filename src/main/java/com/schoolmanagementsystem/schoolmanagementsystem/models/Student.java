package com.schoolmanagementsystem.schoolmanagementsystem.models;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    private int id;

    private String name;
    private Integer age;
    private Integer rollNo;

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getRollNo() {
        return rollNo;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }
}
