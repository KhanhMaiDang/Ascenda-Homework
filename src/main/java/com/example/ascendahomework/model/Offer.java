package com.example.ascendahomework.model;

import com.example.ascendahomework.utils.CustomDateDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

//@JsonIgnoreProperties(ignoreUnknown = true)
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
    private ArrayList<Merchant> merchants;

    @JsonProperty("valid_to")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate validTo;

    public boolean isValidUntil(LocalDate date){
        if (validTo.compareTo(date) >= 0)
            return true;
        return false;
    }


}
