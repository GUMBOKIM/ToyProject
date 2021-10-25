import {Module} from '@nestjs/common';
import {BomRestController} from "./rest-controller/bom.rest-controller";
import {BomService} from "./service/bom.service";
import {TypeOrmModule} from "@nestjs/typeorm";
import {PartService} from "./service/part.service";
import {CompanyService} from "./service/company.service";
import {StockService} from "./service/stock.service";
import {StockLogService} from "./service/stockLog.service";

@Module({
    imports: [TypeOrmModule.forFeature([PartService, CompanyService, StockService, StockLogService])],
    controllers: [BomRestController],
    providers: [BomService],
})
export class PartManageModule {
}
