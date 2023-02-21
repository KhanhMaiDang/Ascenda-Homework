package com.example.ascendahomework.service.implement;

import com.example.ascendahomework.model.Merchant;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class OfferServiceImp implements OfferService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public ArrayList<Offer> getNearByOffer(LocalDate checkinDate) throws JsonProcessingException{
        String url = "https://61c3deadf1af4a0017d990e7.mockapi.io/offers/near_by?lat=1.313492&lon=103.860359&rad=20";
        ArrayList<Offer> offers = fetchExternalAPI(url);
        offers = filterOffer(checkinDate,offers,2);

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

    public ArrayList<Offer> filterOffer(LocalDate checkinDate, ArrayList<Offer> offers, int numberOfOffer){
        ArrayList<Offer> offerArrayList = new ArrayList<>();

        for (Offer offer: offers){
            if (offer.isValidUntil(checkinDate.plusDays(5)) && offer.getCategory() != 3){


                Offer newOffer = offer.shallowCopy();
                Merchant closestMerchant = offer.findClosestMerchant();
                ArrayList<Merchant> tmp = new ArrayList<>();
                tmp.add(closestMerchant);
                newOffer.setMerchants(tmp);

                offerArrayList.add(newOffer);
            }
        }

        offerArrayList.sort((o1, o2) -> {
            if (o1.getCategory() == o2.getCategory()){
                Float d1 = o1.findClosestMerchant().getDistance();
                Float d2 = o2.findClosestMerchant().getDistance();
                return d1.compareTo(d2);
            }
            else{
                return o1.getCategory().compareTo(o2.getCategory());
            }
        });

        Set<Integer> selectedCategory = new HashSet<>();
        ArrayList<Offer> result = new ArrayList<>();
        int index = 0;

        while(numberOfOffer > 0 && index < offerArrayList.size()){ // number of offer may useful for the case of selecting any number of offers
            if (!selectedCategory.contains(offerArrayList.get(index).getCategory())){
                result.add(offerArrayList.get(index));
                selectedCategory.add(offerArrayList.get(index).getCategory());
                --numberOfOffer;
            }
            ++index;
        }
        
        return result;
    }

}
