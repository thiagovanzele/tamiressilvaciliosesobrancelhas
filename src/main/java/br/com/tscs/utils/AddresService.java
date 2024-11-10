package br.com.tscs.utils;

import br.com.tscs.dtos.response.AddressDTO;
import br.com.tscs.exceptions.ValidationException;
import br.com.tscs.model.Address;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AddresService {

    public Address findByPostalCode(String postalCode, String number) {

        if (postalCode.length() != 8) {
            throw new ValidationException("Invalid Postal Code");
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://viacep.com.br/ws/" + postalCode + "/json/";

        ResponseEntity<AddressDTO> addresResponse = restTemplate.getForEntity(url, AddressDTO.class);

        if (addresResponse.getStatusCode().equals(HttpStatus.OK) && addresResponse.hasBody()) {
            AddressDTO response = addresResponse.getBody();

            Address address = new Address();
            address.setStreet(response.street());
            address.setPostalCode(postalCode);
            address.setCity(response.city());
            address.setNumber(number);
            address.setState(response.state());

            return address;
        } else {
            throw new ValidationException("Address not found");
        }
    }
}
