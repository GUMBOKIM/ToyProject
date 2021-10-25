import { Test, TestingModule } from '@nestjs/testing';
import { TaskDeliveryController } from './task-delivery.controller';

describe('TaskDeliveryController', () => {
  let controller: TaskDeliveryController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [TaskDeliveryController],
    }).compile();

    controller = module.get<TaskDeliveryController>(TaskDeliveryController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
