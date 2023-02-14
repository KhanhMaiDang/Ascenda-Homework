package com.example.ascendahomework.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Offer {
    private Long id;
    private String title;
    private String description;
    private Integer category;
    private LocalDate validTo;
    private ArrayList<Merchant> merchants;

}
