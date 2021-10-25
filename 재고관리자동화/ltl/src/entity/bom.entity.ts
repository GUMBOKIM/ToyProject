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
export class Bom {
    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    name: string;

    @Column()
    uph: number;

    @OneToMany(() => Part, (part) => part.bom)
    parts: Part[];

    @CreateDateColumn()
    created!: Date;

    @UpdateDateColumn()
    updated!: Date;

    @DeleteDateColumn()
    deletedAt?: Date;
}