import { DayBitSet } from "./daybitset";
import { DataSender } from "./datasender";
import * as DateUtils from "./dateutils";

export class TimeTrace {
    private daybitset: DayBitSet;
    private datasender: DataSender;
    // vs-code is active or inactive
    private isActive: boolean;
    private openTime: Date | null;
    private closeTime: Date | null;
    private timer: NodeJS.Timeout;

    constructor() {
        this.daybitset = new DayBitSet();
        
        // initial record when open.
        this.record();
        this.datasender = new DataSender();
        this.isActive = true;
        this.openTime = new Date();
        this.closeTime = null;
        
        // post data for each every 30s 
        this.timer = setInterval((that) => {
            that.timerAction();
        }, 30 * 1000, this);
    }

    public record(): void {
        var currentSlot = this.daybitset.record();
        if (this.isActive) {
            let openTimeSlot = this.getOpenedSlot();

            if (openTimeSlot >= 0) {
                console.log('current slot:' + currentSlot + ", openedTimeSlot:" + openTimeSlot);
                var findVerIndex = -1;
                for (var i = currentSlot - 1; i >= openTimeSlot; i--) {
                    if (this.daybitset.getBitSet().get(i)) {
                        findVerIndex = i;
                        break;
                    }
                }

                // only trace back 5 minutes, interval 10 slot.
                if (findVerIndex >= 0 && findVerIndex < currentSlot
                    && (currentSlot - findVerIndex) < 10) {
                    for (var j = findVerIndex + 1; j < currentSlot; j++) {
                        this.daybitset.getBitSet().set(i);
                    }
                }
            }
        }
    }

    private timerAction(): void {
        console.log('start to post:');
        let log = this.datasender.postData(this.daybitset);
        console.log('post success!', new Date(), ' : ', log);
    }

    public setVSCodeWindowState(state: boolean): void {
        console.log('------ window ' + (state ? 'actived' : 'deactived') + ' ------', new Date());
        state ? this.active() : this.deactive();
    }

    private getOpenedSlot(): number {
        if (this.openTime === null) { return -1; }

        // ignore if not today
        if (!DateUtils.isToday(DateUtils.getDayInfo(this.openTime))) {
            return -1;
        }

        return DateUtils.getSlotIndex(this.openTime);
    }

    private active() {
        if (this.isActive) {
            if (this.openTime === null) {
                this.openTime = new Date();
            }
        } else {
            this.isActive = true;
            this.openTime = new Date();
        }
    }

    private deactive() {
        if (this.isActive) {
            this.isActive = false;
            this.closeTime = new Date();
        } else {
            if (this.closeTime === null) {
                this.closeTime = new Date();
            }
        }
    }

    public dispose() {
        clearInterval(this.timer);
    }
}