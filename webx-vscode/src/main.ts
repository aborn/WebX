import * as dateutils from "./dateutils";
import { DayBitSet } from "./daybitset";

console.log('start runing');
console.log('slotIndex=' + dateutils.getSlotIndex());

let daybitset = new DayBitSet();
daybitset.record();
console.log('cardinality = ' + daybitset.cardinality());
daybitset.print();