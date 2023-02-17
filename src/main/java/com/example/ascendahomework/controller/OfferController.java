package com.example.ascendahomework.controller;

import com.example.ascendahomework.model.Offer;
import com.example.ascendahomework.service.OfferService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @GetMapping("/near-by-offers")
    public ArrayList<Offer> getNearByOffers(@RequestParam(value="checkinDate", required = true)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                        LocalDate checkinDate) throws ParseException, JsonProcessingException {

        ArrayList<Offer> nearByOffer =  offerService.getNearByOffer(checkinDate);
        return nearByOffer;
    }


}
