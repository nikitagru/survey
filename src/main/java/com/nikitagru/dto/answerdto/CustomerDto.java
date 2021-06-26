package com.nikitagru.dto.answerdto;

import com.nikitagru.entities.Customer;
import lombok.Data;

import javax.persistence.ManyToMany;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class CustomerDto {
    private String uniqueUserData;

    private boolean isCorrectData(String uniqueUserData) {
        Pattern email = Pattern.compile(".+@.+\\..+");
        Pattern tel = Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");

        Matcher emailMatcher = email.matcher(uniqueUserData);
        Matcher telMatcher = tel.matcher(uniqueUserData);

        return emailMatcher.find() || telMatcher.find();
    }

    public Customer customerDtoToCustomer() {
        Customer customer = new Customer();
        if (isCorrectData(uniqueUserData)) {
            customer.setUniqueId(uniqueUserData);
            return customer;
        }
        return null;
    }
}
