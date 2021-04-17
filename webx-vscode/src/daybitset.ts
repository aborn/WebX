import { BitSet } from "./bitset";
import * as dateutils from "./dateutils";
const SLOT_SIZE = 24 * 60 * 2;

export class DayBitSet {
    private day: string;
    private bitset: BitSet;

    constructor() {
        this.day = dateutils.getDayInfo();
        this.bitset = new BitSet(SLOT_SIZE);
    }

    public record(): void {
        let slot = dateutils.getSlotIndex();
        this.bitset.set(slot);

        console.log("slot=" + slot + ":" + this.bitset.get(slot) + ", cardinality=" + this.bitset.cardinality() + ", wordlength=" + this.bitset.wordLength());
    }

    public getBitSet(): BitSet {
        return this.bitset;
    }

    public print(): void {
        console.log("cardinality=" + this.getBitSet().cardinality());
    }

    public getDay(): string {
        return this.day;
    }
}