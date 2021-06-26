package com.nikitagru.services;

import com.nikitagru.entities.Customer;
import com.nikitagru.repositories.CustomerRepository;
import com.nikitagru.repositories.CustomerSurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * Сервис клиентов
 */
@Service
public class CustomerService {
    private CustomerRepository customerRepository;
    private CustomerSurveyRepository customerSurveyRepository;

    @Autowired
    public void setCustomerSurveyRepository(CustomerSurveyRepository customerSurveyRepository) {
        this.customerSurveyRepository = customerSurveyRepository;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void addCustomerToSurvey(Long surveyId, Long customerId) {
        customerSurveyRepository.saveCustomerWithSurvey(customerId, surveyId);
    }

    public void saveCustomerAndAddToSurvey(Long surveyId, Customer customer) {
        Customer savedCustomer = saveCustomer(customer);
        addCustomerToSurvey(surveyId, savedCustomer.getId());
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
