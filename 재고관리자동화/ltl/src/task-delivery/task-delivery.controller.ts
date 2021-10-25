import {Controller, Get} from '@nestjs/common';
import {TaskDeliveryService} from "./task-delivery.service";

@Controller('task-delivery')
export class TaskDeliveryController {
    constructor(private deliveryService: TaskDeliveryService) {
    }

    @Get('product-plan')
     findProductPlan(){
        const regExp = new RegExp('\{value:"[a-zA-Z0-9,]+"\}','g');
        const code = this.deliveryService.getProductPlan();
        console.log(code);
        console.log(typeof code);
        // let arr = Array.from(code.matchAll(regExp), m => m[m0].substring(8,m[0].length-2).replace(/,/g,''));
        return code;
    }

}
