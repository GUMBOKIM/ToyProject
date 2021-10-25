import {
    Column,
    CreateDateColumn, DeleteDateColumn,
    Entity,
    ManyToOne,
    OneToMany,
    PrimaryGeneratedColumn,
    UpdateDateColumn
} from "typeorm";
import {Bom} from "./bom.entity";
import {Company} from "./company.entity";
import {Stock} from "./stock.entity";

@Entity()
export class Part {
    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    partCode: string;

    @Column()
    partName: string;

    @Column()
    poNumber: string;

    @Column()
    loadingAmount: number;

    @ManyToOne(() => Company, (company) => company.parts)
    company: Company;

    @ManyToOne(() => Bom, (bom) => bom.parts)
    bom: Bom;

    @OneToMany(() => Stock, (stock) => stock.part)
    stocks: Stock[];


    @CreateDateColumn()
    created!: Date;

    @UpdateDateColumn()
    updated!: Date;

    @DeleteDateColumn()
    deletedAt?: Date;
}