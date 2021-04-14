import BitSet from "bitset";
import * as dateutils from "./dateutils";
const SLOT_SIZE = 24 * 60 * 2;

export class DayBitSet {
    private day: string;
    private bitset: BitSet;

    constructor() {
        this.day = "2021-04-07";
        this.bitset = new BitSet(SLOT_SIZE);
        this.bitset.clear();
        // this.bitset.setRange(0, SLOT_SIZE);
    }

    public record(): void {
        let slot = dateutils.getSlotIndex();
        this.bitset.set(slot, 1);
    }

    public cardinality(): number {
        return this.bitset.cardinality();
    }

    public print(): void {
        let index = 0;
        for (let b of this.bitset) {
            if (b === 1) {
                console.log(index + ':' + b + '\n');
            }
            index = index + 1;
        }
        console.log('last index=' + index);
        let arr = this.bitset.toArray();
        console.log("array length = " + arr.length);
        console.log(arr);
    }
}