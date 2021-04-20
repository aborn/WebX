import { DayBitSet } from "./daybitset";
import { DataSender } from "./datasender";

export class TimeTrace {
    private daybitset: DayBitSet;
    private datasender: DataSender;
    // vs-code is active or inactive
    private isActive: boolean;

    constructor() {
        this.daybitset = new DayBitSet();
        this.datasender = new DataSender();
        this.isActive = true;
    }

    public record(): void {
        var slot = this.daybitset.record();
        var log = this.datasender.postData(this.daybitset);

        // TODO: 在vscode是active的情况下，往前追踪5分钟，间隔10个slot
        console.log(log);
    }

    public setVSCodeWindowState(state: boolean): void {
        console.log('------ window' + (state ? 'actived' : 'deactived') + ' ------', new Date());
        this.isActive = state;
    }
}