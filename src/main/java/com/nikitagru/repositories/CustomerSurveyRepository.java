package com.nikitagru.repositories;

import com.nikitagru.entities.CustomerSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/***
 * Репозиторий отношений клиентов к опросу
 */
@Repository
public interface CustomerSurveyRepository extends JpaRepository<CustomerSurvey, Long> {
    @Query(value = "INSERT INTO customers_surveys (survey_id, customer_id) VALUES (:#{#surveyid}, :#{#customerid})", nativeQuery = true)
    @Modifying
    @Transactional
    void saveCustomerWithSurvey(@Param("customerid") Long customerId, @Param("surveyid") Long surveyId);
}
