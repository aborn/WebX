import axios from 'axios';
import { DayBitSet } from "./daybitset";
import { BitSet } from "./bitset";
import * as servers from "./serverinfo";
import { UserConfig } from "./userconfig";

export class DataSender {
    private lastPostDate: Date | null;
    private lastPostData: BitSet;
    private userConfig: UserConfig;

    constructor() {
        this.lastPostDate = null;
        this.lastPostData = new DayBitSet().getBitSet();
        this.userConfig = new UserConfig();
    }

    public postData(daybitset: DayBitSet): string {
        if (this.isNeedPost(daybitset)) {
            var result = this.doPostData(daybitset);
            this.lastPostDate = new Date();
            this.lastPostData.or(daybitset.getBitSet());
            return result.msg;
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

    private doPostData(daybitset: DayBitSet): { status: boolean, msg: string } {
        var serverInfo = this.getServerInfo();
        var token = this.userConfig.getToken();
        if (token === null) {
            return {
                status: false,
                msg: "token is null, doPostData failed."
            };
        }

        console.log(daybitset.getDay() + ":" + daybitset.countOfCodingSlot());
        
        axios({
            baseURL: serverInfo.baseURL,
            url: 'postUserAction',
            method: 'post',
            headers: {
                // eslint-disable-next-line @typescript-eslint/naming-convention
                'Content-type': 'application/json; charset=utf-8',
                // eslint-disable-next-line @typescript-eslint/naming-convention
                'Accept': 'application/json'
            },
            data: {
                token: token,
                day: daybitset.getDay(),
                dayBitSetArray: daybitset.getDayBitSetByteArray()
            },
            timeout: serverInfo.timeout
        }).then((response: any) => {
            // handle success
            console.log(response.data);
            return {
                status: true, 
                msg: 'post data success.'
            };
        }).catch((error: any) => {
            console.log(error);
            if (error.response) {
                return {
                    status: false,
                    msg: 'post data failed: server error'
                };
            } else {
                return {
                    status: false,
                    msg: error.message
                };
            }
        }).then(() => {
            // always executed
        });

        return {
            status: true,
            msg: 'finished post, status: unknown.'
        };
    }

    private getServerInfo(): any {
        return servers.REMOTE_SERVER;
        // return servers.LOCAL_SERVER;
    }

}