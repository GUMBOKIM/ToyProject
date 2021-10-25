import {Injectable} from "@nestjs/common";
import {InjectRepository} from "@nestjs/typeorm";
import {Bom} from "../../entity/bom.entity";
import {Repository} from "typeorm";
import {CreateBomDto} from "../dto/bom/createBom.dto";

@Injectable()
export class BomService {
    constructor(
        @InjectRepository(Bom) private bomRepository: Repository<Bom>,
    ) {
    }

    getAll(): Promise<Bom[]> {
        return this.bomRepository.find();
    }

    getOneById(id: number): Promise<Bom>{
        return this.bomRepository.findOne(id);
    }

    createBom(createBomDto: CreateBomDto): Promise<Bom> {
        const newBom = this.bomRepository.create({name: createBomDto.name, uph: createBomDto.uph});
        return this.bomRepository.save(newBom);
    }


    getOnePartListById(id: number): Promise<Bom> {
        return this.bomRepository.findOne(id);
    }
}