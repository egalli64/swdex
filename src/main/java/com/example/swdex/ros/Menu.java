package com.example.swdex.ros;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Menu implements Serializable {
    private static final long serialVersionUID = -7076053921726503431L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ID")
    private Integer id;

    private String name;
    private String description;
    private Double price;
    @Column(name = "CATEGORY_ID")
    private Integer category;
    @Transient
    private int quantity;

    public Menu() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void changeQuantity(int delta) {
        quantity += delta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, description, id, name, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Menu other = (Menu) obj;
        return Objects.equals(category, other.category) && Objects.equals(description, other.description)
                && Objects.equals(id, other.id) && Objects.equals(name, other.name)
                && Objects.equals(price, other.price);
    }

    @Override
    public String toString() {
        return "Menu [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", category="
                + category + ", quantity=" + quantity + "]";
    }
}