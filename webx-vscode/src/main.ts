import { DayBitSet } from "./daybitset";
import { DataSender } from "./datasender";
import { BitSet } from "./bitset";
import * as config from "./config";

config.getToken();

console.log('start runing.');
console.log('start time==>' + new Date().getTime());
var daybitset = new DayBitSet();

daybitset.record();
var bitset = daybitset.getBitSet();
bitset.set(1);
bitset.set(8);
bitset.set(24 * 60 * 2 - 1, 1);
console.log(bitset.toBitSetSlotArray());
daybitset.print();

var bitset2 = new BitSet(24 * 60 * 2);
bitset2.set(1);
bitset2.set(100);
bitset2.and(bitset);
console.log(bitset2.toBitSetSlotArray());

console.log('cardinality:' + bitset.cardinality());
bitset.set(8, 0);
console.log('cardinality:' + bitset.cardinality());
bitset.clear();
console.log('cardinality:' + bitset.cardinality());

var byteArray = daybitset.getBitSet().toIntArray();
console.log('byte length=' + byteArray.length);

var a = false;
if (a) {
    var datasender = new DataSender();
    var postResult = datasender.postData(daybitset);
    console.log(postResult);
}

console.log('end time==>' + new Date().getTime());
console.log('end in main.');