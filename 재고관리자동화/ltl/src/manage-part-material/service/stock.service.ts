import {Injectable} from "@nestjs/common";
import {InjectRepository} from "@nestjs/typeorm";
import {Repository} from "typeorm";
import {Stock} from "../../entity/stock.entity";
import {CreateStockDto} from "../dto/stock/createStock.dto";

@Injectable()
export class StockService {
    constructor(
        @InjectRepository(Stock) private stockRepository: Repository<Stock>,
    ) {
    }

    getAll(): Promise<Stock[]> {
        return this.stockRepository.find();
    }

    getOneById(id: number): Promise<Stock> {
        return this.stockRepository.findOne(id);
    }

    createStock(createStockDto: CreateStockDto): Promise<Stock> {
        const newPart = this.stockRepository.create({
            lot: createStockDto.lot,
            stock: createStockDto.stock
        });
        return this.stockRepository.save(newPart);
    }


}