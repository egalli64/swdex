package com.example.swdex.ros;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Ordering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDERING_ID")
    private Integer id;

    private String name;

    @Transient
    private int counter;
    @Transient
    private double total;

    public Ordering() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void changeCounter(int delta) {
        counter += delta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ordering other = (Ordering) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "Ordering [id=" + id + ", name=" + name + ", counter=" + counter + ", total=" + total + "]";
    }
}