package com.example.ascendahomework.model;

import com.example.ascendahomework.utils.CustomDateDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
public class Offer implements Serializable {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("category")
    private Integer category;
    @JsonProperty("merchants")
    @Setter
    private ArrayList<Merchant> merchants;

    @JsonProperty("valid_to")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate validTo;

    public Offer shallowCopy(){
        Offer newOffer = new Offer();

        newOffer.id = this.id;
        newOffer.title = this.title;
        newOffer.description = this.description;
        newOffer.category = this.category;
        newOffer.validTo = this.validTo;
        newOffer.merchants = new ArrayList<>();
        for (Merchant m : this.merchants){
            newOffer.merchants.add(m);
        }
        return newOffer;
    }

    public boolean isValidUntil(LocalDate date){
        if (validTo.compareTo(date) >= 0)
            return true;
        return false;
    }

    public Merchant findClosestMerchant(){
        if (merchants == null || merchants.size() == 0)
            return null;
        Float minDistance = merchants.get(0).getDistance();
        int index = 0;
        for (int i = 1; i< merchants.size(); ++i){
            if (minDistance.compareTo(merchants.get(i).getDistance()) > 0) { // minDistance > merchants[i].distance
                minDistance = merchants.get(i).getDistance();
                index = i;
            }
        }

        return merchants.get(index);

    }

}
