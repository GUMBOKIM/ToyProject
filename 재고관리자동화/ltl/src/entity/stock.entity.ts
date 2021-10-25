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

@Entity()
export class Stock {
    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    lot: string;

    @Column()
    stock: number;

    @OneToMany(() => Part, (part) => part.stocks)
    part: Part;

    @CreateDateColumn()
    created!: Date;

    @UpdateDateColumn()
    updated!: Date;

    @DeleteDateColumn()
    deletedAt?: Date;
}