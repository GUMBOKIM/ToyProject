package com.union.placeorderAutomation.dto.task.part.status;

import lombok.Data;

@Data
public class PartLogDetail {

     private int orderSeq;
     private int quantity;

     public PartLogDetail(int orderSeq, Long quantity) {
          this.orderSeq = orderSeq;
          this.quantity = quantity.intValue();
     }
}
