import {Injectable} from "@nestjs/common";
import {InjectRepository} from "@nestjs/typeorm";
import {Repository} from "typeorm";
import {Company} from "../../entity/company.entity";
import {CreateCompanyDto} from "../dto/company/createCompany.dto";

@Injectable()
export class CompanyService {
    constructor(
        @InjectRepository(Company) private companyRepository: Repository<Company>,
    ) {
    }

    getAll(): Promise<Company[]> {
        return this.companyRepository.find();
    }

    getOneById(id: number): Promise<Company> {
        return this.companyRepository.findOne(id);
    }

    createCompany(createCompanyDto: CreateCompanyDto): Promise<Company> {
        const newCompany = this.companyRepository.create({
            name: createCompanyDto.name,
            account: createCompanyDto.account,
            password: createCompanyDto.password
        });
        return this.companyRepository.save(newCompany);
    }


}