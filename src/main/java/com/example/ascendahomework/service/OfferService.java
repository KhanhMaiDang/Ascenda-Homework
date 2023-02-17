package com.example.ascendahomework.service;

import com.example.ascendahomework.model.Offer;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface OfferService {
    ArrayList<Offer> getNearByOffer(LocalDate checkinDate) throws JsonProcessingException, ParseException;
}
