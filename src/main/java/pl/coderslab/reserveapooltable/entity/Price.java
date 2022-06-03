package pl.coderslab.reserveapooltable.entity;

import pl.coderslab.reserveapooltable.enums.PriceGroup;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private PriceGroup priceGroup;
    private boolean isNightTime;
    private double pricePerHour;

    public Price() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PriceGroup getPriceGroup() {
        return priceGroup;
    }

    public void setPriceGroup(PriceGroup priceGroup) {
        this.priceGroup = priceGroup;
    }

    public boolean getIsNightTime() {
        return isNightTime;
    }

    public void setNightTime(boolean nightTime) {
        this.isNightTime = nightTime;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }


}
