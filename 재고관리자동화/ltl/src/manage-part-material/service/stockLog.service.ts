import {Injectable} from "@nestjs/common";
import {InjectRepository} from "@nestjs/typeorm";
import {Repository} from "typeorm";
import {StockLog} from "../../entity/stockLog.entity";
import {CreateStockLogDto} from "../dto/stock-log/createStockLog.dto";

@Injectable()
export class StockLogService {
    constructor(
        @InjectRepository(StockLog) private stockRepository: Repository<StockLog>,
    ) {
    }

    getAll(): Promise<StockLog[]> {
        return this.stockRepository.find();
    }

    getOneById(id: number): Promise<StockLog> {
        return this.stockRepository.findOne(id);
    }

    createStockLog(createStockDto: CreateStockLogDto): Promise<StockLog> {
        const newStockLog = this.stockRepository.create({
            amount: createStockDto.amount,
            kind: createStockDto.kind,
            part: createStockDto.part
        });
        return this.stockRepository.save(newStockLog);
    }


}