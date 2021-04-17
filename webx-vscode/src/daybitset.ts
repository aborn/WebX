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

    public record(): number {
        this.clearIfNotToday();
        let slot = dateutils.getSlotIndex();
        this.bitset.set(slot);

        console.log("slot=" + slot + ":" + this.bitset.get(slot) + ", cardinality=" + this.bitset.cardinality() + ", wordlength=" + this.bitset.wordLength());
        return slot;
    }

    private clearIfNotToday(): void {
        if (dateutils.isToday(this.day)) {
            return;
        }

        this.day = dateutils.getDayInfo();
        this.bitset.clear();
    }

    public getBitSet(): BitSet {
        return this.bitset;
    }

    public getDayBitSetByteArray(): number[] {
        return this.bitset.toIntArray();
    }

    public print(): void {
        console.log("day:" + this.day + ", cardinality:" + this.bitset.cardinality());
    }

    public getDay(): string {
        return this.day;
    }
}