import {Module} from '@nestjs/common';
import {AppController} from './app.controller';
import {AppService} from './app.service';
import {TypeOrmModule} from "@nestjs/typeorm";
import { PartManageModule } from './manage-part-material/part-manage.module';
import { TaskDeliveryModule } from './task-delivery/task-delivery.module';
import ormConfig from "../ormconfig";

@Module({
    imports: [
        TypeOrmModule.forRoot(ormConfig),
        // PartManageModule,
        TaskDeliveryModule,
    ],
    controllers: [AppController],
    providers: [AppService],
})
export class AppModule {
}
