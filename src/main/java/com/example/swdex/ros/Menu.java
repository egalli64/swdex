package com.example.swdex.ros;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ID")
    private Integer id;

    private String name;
    private String description;
    private Double price;
    @Column(name = "CATEGORY_ID")
    private Integer category;

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
                + category + "]";
    }
}