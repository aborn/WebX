import * as dateutils from "./dateutils";
import { DayBitSet } from "./daybitset";

console.log('start runing.');
let daybitset = new DayBitSet();
daybitset.record();
console.log('cardinality:' + daybitset.cardinality());
daybitset.print();

console.log('end in main.');