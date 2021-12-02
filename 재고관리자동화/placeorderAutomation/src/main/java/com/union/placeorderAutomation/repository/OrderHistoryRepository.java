package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.Company;
import com.union.placeorderAutomation.entity.OrderHistory;
import com.union.placeorderAutomation.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderHistoryRepository  extends JpaRepository<OrderHistory, Long> {

    @Query(value ="select o.orderSeq from OrderHistory o where o.company = :company and o.plant = :plant and o.date = :date")
    List<Integer> findOrderHistory(@Param("company") Company company, @Param("plant") Plant plant, @Param("date") String date);

    @Query(value ="select o from OrderHistory o where o.company = :company and o.date = :date")
    List<OrderHistory> findOrderHistoryList(@Param("company") Company company, @Param("date") String date);

    @Query(value ="select o from OrderHistory o where o.company = :company and o.plant = :plant and o.date = :date and o.orderSeq = :orderSeq")
    OrderHistory findOrderHistoryForDelete(@Param("company") Company company, @Param("plant") Plant plant, @Param("date") String date, @Param("orderSeq") int orderSeq);



}
