import { DayBitSet } from "./daybitset";
import axios from 'axios';

console.log('start runing.');
let daybitset = new DayBitSet();
daybitset.record();

daybitset.set(1);
daybitset.set(8);
daybitset.set(24 * 60 * 2 - 1);
console.log('100===' +  daybitset.get(100));
//console.log('cardinality:' + daybitset.cardinality());
daybitset.print();

var intArr = daybitset.getBitSet().toIntArray();
const data = {
    token: '8ba394513f8420e',
    day: daybitset.getDay(),
    dayBitSetArray: intArr
};

console.log(data);
console.log('length=' + intArr.length);

var a = true;
if (a) {
    axios({
        url: 'http://localhost:8080/webx/postUserAction',
        method: 'post',
        headers: {
            // eslint-disable-next-line @typescript-eslint/naming-convention
            'Content-type': 'application/json; charset=utf-8',
            // eslint-disable-next-line @typescript-eslint/naming-convention
            'Accept': 'application/json'
        },
        data: data
    }).then((response: any) => {
        // handle success
        console.log(response.data);
    }).catch((error: any) => {
        // handle error
        console.log(error);
    }).then(() => {
        // always executed
    });
}

console.log('end in main.');