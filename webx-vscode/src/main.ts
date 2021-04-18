import { DayBitSet } from "./daybitset";
import axios from 'axios';
import * as dateutils from "./dateutils";
import { DataSender } from "./datasender";

console.log('start runing.');
console.log('start time==>' + new Date().getTime());

console.log(dateutils.getDayInfo());
console.log("isToday:" + dateutils.isToday("2021-04-17"));
console.log("isToday:" + dateutils.isToday("2021-04-16"));
var daybitset = new DayBitSet();

daybitset.record();
var bitset = daybitset.getBitSet();
bitset.set(1);
bitset.set(8);
bitset.set(24 * 60 * 2 - 1, 1);
daybitset.print();

console.log('cardinality:' + bitset.cardinality());
bitset.set(8, 0);
console.log('cardinality:' + bitset.cardinality());
bitset.clear();
console.log('cardinality:' + bitset.cardinality());

var byteArray = daybitset.getBitSet().toIntArray();
console.log('byte length=' + byteArray.length);
const data = {
    token: '8ba394513f8420e',
    day: daybitset.getDay(),
    dayBitSetArray: byteArray
};

var a = false;
if (a) {
    var datasender = new DataSender();
    var postResult = datasender.postData(daybitset);
    console.log(postResult);
}

console.log('end time==>' + new Date().getTime());
console.log('end in main.');