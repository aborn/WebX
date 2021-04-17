import axios from 'axios';
import { DayBitSet } from "./daybitset";

const TOKEN_WEBX = "0x4af97337";
const URL_REMOTE = "https://aborn.me/webx/postUserAction";
const URL_LOCAL = "http://127.0.0.1:8080/webx/postUserAction";

export class DataSender {

    constructor() {

    }
    
    public postData(daybitset: DayBitSet): string {
        var serverInfo = this.getServerInfo();
        axios({
            url: serverInfo.url,
            method: 'post',
            headers: {
                // eslint-disable-next-line @typescript-eslint/naming-convention
                'Content-type': 'application/json; charset=utf-8',
                // eslint-disable-next-line @typescript-eslint/naming-convention
                'Accept': 'application/json'
            },
            data: {
                token: serverInfo.token,
                day: daybitset.getDay(),
                dayBitSetArray: daybitset.getDayBitSetByteArray()
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

        return "";
    }

    private getServerInfo(): any {
        return {
            token: TOKEN_WEBX,
            url: URL_LOCAL
        };
    }

}