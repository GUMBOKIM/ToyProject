package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.Company;
import com.union.placeorderAutomation.entity.OrderHistory;
import com.union.placeorderAutomation.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepository  extends JpaRepository<OrderHistory, Long> {

    List<OrderHistory> findOrderHistoriesByCompanyAndPlantAndDate(Company company, Plant plant, String date);
}
