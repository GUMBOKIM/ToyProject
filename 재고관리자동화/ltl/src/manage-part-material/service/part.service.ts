import {Injectable} from "@nestjs/common";
import {InjectRepository} from "@nestjs/typeorm";
import {Repository} from "typeorm";
import {Part} from "../../entity/part.entity";
import {CreatePartDto} from "../dto/part/createPart.dto";

@Injectable()
export class PartService {
    constructor(
        @InjectRepository(Part) private partRepository: Repository<Part>,
    ) {
    }

    getAll(): Promise<Part[]> {
        return this.partRepository.find();
    }

    getOneById(id: number): Promise<Part> {
        return this.partRepository.findOne(id);
    }

    createPart(createPartDto: CreatePartDto): Promise<Part> {
        const newPart = this.partRepository.create({
            partCode: createPartDto.partCode,
            partName: createPartDto.partName,
            poNumber: createPartDto.poNumber,
            loadingAmount: createPartDto.loadingAmount
        });
        return this.partRepository.save(newPart);
    }


}