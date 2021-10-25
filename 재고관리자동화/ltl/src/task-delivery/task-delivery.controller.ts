import {Controller, Get} from '@nestjs/common';
import {TaskDeliveryService} from "./task-delivery.service";

@Controller('task-delivery')
export class TaskDeliveryController {
    constructor(private deliveryService: TaskDeliveryService) {
    }

    @Get('product-plan')
    async findProductPlan(): Promise<Object> {
        const regExp = new RegExp('\{value:"[a-zA-Z0-9,]+"\}','g');
        const code = await this.deliveryService.getProductPlan();
        let arr = Array.from(code.matchAll(regExp), m => m[0].substring(8, m[0].length - 2).replace(/,/g, ''));
        return arr;
    }

}
