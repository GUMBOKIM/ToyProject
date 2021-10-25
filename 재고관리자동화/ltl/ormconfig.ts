import {MysqlConnectionOptions} from "typeorm/driver/mysql/MysqlConnectionOptions";

const ormConfig: MysqlConnectionOptions = {
    type: 'mysql',
    host: 'localhost',
    port: 3306,
    username: 'ltl',
    password: 'ltl1234!',
    database: 'ltl',
    entities: ['dist/src/**/*.entity.js'],
    synchronize: true, // 나중에 바꿔야함
    logging: true,

}
export default ormConfig;