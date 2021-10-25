import {Body, Controller, Get, Param, Post} from "@nestjs/common";
import {BomService} from "../service/bom.service";
import {Bom} from "../../entity/bom.entity";
import {CreateBomDto} from "../dto/bom/createBom.dto";
import {Part} from "../../entity/part.entity";

@Controller('bom')
export class BomRestController {
    constructor(private bomService: BomService) {
    }

    @Get()
    async findBomList(): Promise<Bom[]>{
        return this.bomService.getAll();
    }

    @Get(':id')
    async findBom(@Param('id') id: number): Promise<Bom>{
        return this.bomService.getOneById(id);
    }

    @Get(':id/part')
    async findBomPartList(@Param('id') id: number): Promise<Bom>{
        return this.bomService.getOnePartListById(id);
    }

    @Post()
    async createBom(@Body() createBomDto: CreateBomDto): Promise<Bom> {
        return this.bomService.createBom(createBomDto);
    }
}