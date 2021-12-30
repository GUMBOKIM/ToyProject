package union.seosan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import union.seosan.dto.inventory.InventoryResult;
import union.seosan.entity.Inventory;
import union.seosan.entity.Part;
import union.seosan.repository.InventoryRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepo;

    private InventoryResult increaseStock(Part part, String lot, int amount) {
        InventoryResult result = new InventoryResult(part.getPartCode(), lot, amount);
        Optional<Inventory> inventoryOpt = inventoryRepo.findInventoryByPartAndLot(part, lot);
        if (inventoryOpt.isEmpty()) {
            result.setBeforeStock(0);

            Inventory newInventory = Inventory.builder()
                    .part(part)
                    .lot(lot)
                    .stock(amount)
                    .build();
            inventoryRepo.save(newInventory);

            result.setBeforeStock(newInventory.getStock());
        } else {
            Inventory originInventory = inventoryOpt.get();
            result.setBeforeStock(originInventory.getStock());

            originInventory.increaseStock(amount);
            inventoryRepo.save(originInventory);

            result.setBeforeStock(originInventory.getStock());
        }
        return result;
    }

    private InventoryResult decreaseStock(Part part, String lot, int amount) {
        InventoryResult result = new InventoryResult(part.getPartCode(), lot, amount);

        Optional<Inventory> inventoryOpt = inventoryRepo.findInventoryByPartAndLot(part, lot);
        if (inventoryOpt.isEmpty()) {
            result.setBeforeStock(0);
            result.setAfterStock(0);
            result.setResult("N");
        } else {
            Inventory originInventory = inventoryOpt.get();
            result.setBeforeStock(originInventory.getStock());

            if(originInventory.getStock() >= amount){
                originInventory.decreaseStock(amount);
                inventoryRepo.save(originInventory);
            } else {
                result.setResult("N");
            }

            result.setAfterStock(originInventory.getStock());
        }

        return result;
    }
}
