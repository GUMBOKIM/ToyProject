import {LogKind} from "../../../entity/stockLog.entity";
import {Part} from "../../../entity/part.entity";

export interface CreateStockLogDto {
    amount: number;
    kind: LogKind;
    part: Part;
}
