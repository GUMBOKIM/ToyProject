import { Test, TestingModule } from '@nestjs/testing';
import { TaskDeliveryService } from './task-delivery.service';

describe('TaskDeliveryService', () => {
  let service: TaskDeliveryService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [TaskDeliveryService],
    }).compile();

    service = module.get<TaskDeliveryService>(TaskDeliveryService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
