import { DayBitSet } from "./daybitset";
import axios from 'axios';

console.log('start runing.');
let daybitset = new DayBitSet();
daybitset.record();
console.log('cardinality:' + daybitset.cardinality());
daybitset.print();

axios({
    url: 'http://localhost:8080/webx/postUserAction',
    method: 'POST',
    headers: {
        // eslint-disable-next-line @typescript-eslint/naming-convention
        'Content-type': 'application/json; charset=utf-8',
        // eslint-disable-next-line @typescript-eslint/naming-convention
        'Accept': 'application/json'
    },
    data: {
        token: '8ba394513f8420e',
        day: daybitset.getDay(),
        dayBitSetArray: daybitset.getDay
    }
}).then((response: any) => {
    // handle success
    console.log(response.data);
}).catch((error: any) => {
    // handle error
    console.log(error);
}).then(() => {
    // always executed
});

console.log('end in main.');