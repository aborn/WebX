import { BitSet } from "./bitset";
import * as dateutils from "./dateutils";
const SLOT_SIZE = 24 * 60 * 2;

export class DayBitSet {
    private day: string;
    private bitset: BitSet;

    constructor() {
        this.day = "2021-04-07";
        this.bitset = new BitSet(SLOT_SIZE);
    }

    public record(): void {
        let slot = dateutils.getSlotIndex();
        this.bitset.set(slot);

        console.log("slot=" + slot + ":" + this.bitset.get(slot) + ", cardinality=" + this.cardinality() + "wordlength=" + this.bitset.wordLength());
    }

    public getBitSet(): BitSet {
        return this.bitset;
    }

    public get(ndx: number): number {
        return this.bitset.get(ndx);
    }

    public set(ndx: number): void {
        this.bitset.set(ndx);
    }

    public cardinality(): number {
        return this.bitset.cardinality();
    }

    public toByteBuffer(): ArrayBuffer {
        return this.bitset.toByteBuffer();
    }

    public print(): void {
        console.log("cardinality=" + this.cardinality());
    }

    public getDay(): string {
        return this.day;
    }
}