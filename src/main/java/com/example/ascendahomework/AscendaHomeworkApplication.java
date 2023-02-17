package com.example.ascendahomework;

import com.example.ascendahomework.service.OfferService;
import com.example.ascendahomework.service.implement.OfferServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;

@SpringBootApplication
public class AscendaHomeworkApplication {

    public static void main(String[] args){
        SpringApplication.run(AscendaHomeworkApplication.class, args);
    }

}
