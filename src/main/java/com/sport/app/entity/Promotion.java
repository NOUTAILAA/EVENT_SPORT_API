package com.sport.app.entity;

<<<<<<< HEAD
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
=======
import jakarta.persistence.*;
import java.util.Date;
>>>>>>> c8da34cd386035dbbdeb50b338b01bc9e988acec

@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private Double discountPercentage;
    private Date validUntil;
<<<<<<< HEAD
    @ManyToMany(mappedBy = "promotions")
    private List<Evenement> evenements;
public Promotion (){}
=======

    // Getters and Setters
>>>>>>> c8da34cd386035dbbdeb50b338b01bc9e988acec
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }
<<<<<<< HEAD
    public List<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }
    
    
    
    


}
=======
}
>>>>>>> c8da34cd386035dbbdeb50b338b01bc9e988acec