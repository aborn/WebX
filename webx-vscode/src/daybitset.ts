import BitSet from "bitset";
const SLOT_SIZE = 24 * 60 * 2;

export class DayBitSet {
    private day: string;
    private bitset: BitSet;
    

    constructor() {
        this.day = "2021-04-07";
        this.bitset = new BitSet()
        this.bitset.setRange(0, SLOT_SIZE)
    }

    public record(): void {

    }

}