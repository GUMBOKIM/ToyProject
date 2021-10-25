import { Module } from '@nestjs/common';
import { TaskDeliveryController } from './task-delivery.controller';
import { TaskDeliveryService } from './task-delivery.service';
import {HttpModule} from "@nestjs/axios";

@Module({
  imports: [HttpModule],
  controllers: [TaskDeliveryController],
  providers: [TaskDeliveryService]
})
export class TaskDeliveryModule {}
