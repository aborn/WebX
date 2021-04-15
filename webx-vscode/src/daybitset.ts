import BitSet from "bitset";
import * as dateutils from "./dateutils";
const SLOT_SIZE = 24 * 60 * 2;

export class DayBitSet {
    private day: string;
    private bitset: BitSet;

    constructor() {
        this.day = "2021-04-07";
        this.bitset = new BitSet;
        this.bitset.setRange(0, SLOT_SIZE, 0);
        this.bitset.set(SLOT_SIZE - 1, 0);
    }

    public record(): void {
        let slot = dateutils.getSlotIndex();
        this.bitset.set(slot, 1);
    }

    public cardinality(): number {
        return this.bitset.cardinality();
    }

    public toByteBuffer(): ArrayBuffer {
        const buffer = new ArrayBuffer(SLOT_SIZE);
        return buffer;
    }

    public print(): void {
        let arr = this.bitset.toArray();
        console.log(arr);
    }

    public getDay(): string {
        return this.day;
    }
}