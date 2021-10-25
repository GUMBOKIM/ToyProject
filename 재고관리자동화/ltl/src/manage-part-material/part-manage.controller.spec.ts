import { Test, TestingModule } from '@nestjs/testing';
import { PartManageController } from './part-manage.controller';

describe('PartManageController', () => {
  let controller: PartManageController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [PartManageController],
    }).compile();

    controller = module.get<PartManageController>(PartManageController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
