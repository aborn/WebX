import axios from 'axios';
import { DayBitSet } from "./daybitset";
import { BitSet } from "./bitset";

const TOKEN_WEBX = "0x4af97337";
const URL_REMOTE = "https://aborn.me/webx/postUserAction";
const URL_LOCAL = "http://127.0.0.1:8080/webx/postUserAction";

export class DataSender {
    private lastPostDate: Date | null;
    private lastPostData: BitSet;

    constructor() {
        this.lastPostDate = null;
        this.lastPostData = new DayBitSet().getBitSet();
    }

    public postData(daybitset: DayBitSet): string {
        if (this.isNeedPost(daybitset)) {
            console.log('Start Post!');
            var result = this.doPostData(daybitset);
            this.lastPostDate = new Date();
            this.lastPostData.or(daybitset.getBitSet());
            return result;
        } else {
            return "No need to post!";
        }
    }

    private isNeedPost(daybitset: DayBitSet): boolean {
        var now = new Date();        
        var timeLasped = 0;
        if (this.lastPostDate !== null) {
            timeLasped = (now.getTime() - this.lastPostDate.getTime()) / 1000;            
        }

        if (this.lastPostDate === null
            || this.lastPostData === null
            || daybitset.countOfCodingSlot() !== this.lastPostData.cardinality()
            || (timeLasped) > 5 * 60  // 5分钟以上
        ) {
            return true;
        }

        return false;
    }

    private doPostData(daybitset: DayBitSet): string {
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

        return "post finished.";
    }

    private getServerInfo(): any {
        return {
            token: TOKEN_WEBX,
            url: URL_LOCAL
        };
    }

}