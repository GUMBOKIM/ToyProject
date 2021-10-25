import {
    Column,
    CreateDateColumn,
    DeleteDateColumn,
    Entity,
    OneToMany,
    PrimaryGeneratedColumn,
    UpdateDateColumn
} from "typeorm";
import {Part} from "./part.entity";


export enum LogKind {
    IN = "IN",
    EX = "EX",
    MO = "MO"
}

@Entity()
export class StockLog {
    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    amount: number;

    @Column({ type: 'enum', enum: LogKind })
    kind: LogKind;

    @OneToMany(() => Part, (part) => part.stocks)
    part: Part;

    @CreateDateColumn()
    created!: Date;

    @UpdateDateColumn()
    updated!: Date;

    @DeleteDateColumn()
    deletedAt?: Date;
}
