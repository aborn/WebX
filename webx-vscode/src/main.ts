import { DayBitSet } from "./daybitset";
import axios from 'axios';

console.log('start runing.');
let daybitset = new DayBitSet();
daybitset.record();

daybitset.set(1);
//console.log('cardinality:' + daybitset.cardinality());
daybitset.print();

const data = {
    token: '8ba394513f8420e',
    day: daybitset.getDay(),
    dayBitSetArray: daybitset.getBitSet().toIntArray()
};

console.log(data);

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