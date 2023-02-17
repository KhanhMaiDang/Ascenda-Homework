package com.example.ascendahomework.service.implement;

import com.example.ascendahomework.model.Offer;
import com.example.ascendahomework.service.OfferService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
@Slf4j
public class OfferServiceImp implements OfferService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public ArrayList<Offer> getNearByOffer(LocalDate checkinDate) throws JsonProcessingException, ParseException {
        String url = "https://61c3deadf1af4a0017d990e7.mockapi.io/offers/near_by?lat=1.313492&lon=103.860359&rad=20";
        ArrayList<Offer> offers = fetchExternalAPI(url);
        offers = filterOffer(checkinDate,offers);

        return offers;

    }

    public ArrayList<Offer> fetchExternalAPI(String url) throws JsonProcessingException {

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        JSONObject jsonObject = new JSONObject(response.getBody());
        JSONArray offersArray = jsonObject.getJSONArray("offers");


        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Offer> offerArrayList = new ArrayList<>();

        for (int i=0; i<offersArray.length(); ++i){
            JSONObject object = offersArray.getJSONObject(i);
            Offer offer = objectMapper.readValue(object.toString(), Offer.class);
            offerArrayList.add(offer);
        }

        return offerArrayList;
    }

    public ArrayList<Offer> filterOffer(LocalDate checkinDate, ArrayList<Offer> offers){
        ArrayList<Offer> result = new ArrayList<>();

        for (Offer offer: offers){
            if (offer.isValidUntil(checkinDate.plusDays(5))){
                System.out.println(offer.getId());
                System.out.println(offer.getValidTo());
                result.add(offer);
            }
        }

        return result;
    }

}
