
const WORD_LENGTH = 8;
const WORD_LOG = 3;

export class BitSet {
    // 总共有多少个bitslot
    private slot: number;
    private data: Int8Array;

    constructor(slot: number) {
        if (slot <= 0) {
            throw SyntaxError('Invalid slot!');
        }

        this.slot = slot;
        this.data = new Int8Array(slot >>> WORD_LOG);
    }

    /**
     * set the value of ndx
     * @param ndx the index of bit slot to be set
     * @param value Optional value that should be set 0/1.
     */
    public set(ndx: number, value?: number): void {
        var n = ndx >>> WORD_LOG;
        var b = ndx & 0x7;

        if (value === 0) {
            this.data[n] &= ~(1 << b);
        } else {
            this.data[n] |= (1 << b);
        }
    }

    public get(ndx: number): number {
        var n = ndx >>> WORD_LOG;
        var b = ndx & 0x7;

        if (n >= this.data.length) {
            throw Error('Index out of bitset.');
        }
        return (this.data[n] >> b) & 1;
    }

    public or(bitset: BitSet): void {
        var int8Array = bitset.toInt8Array();
        for (var i = 0; i < Math.min(bitset.wordLength(), this.wordLength()); i++) {
            this.data[i] |= int8Array[i];
        }
    }

    public and(bitset: BitSet): void {
        var int8Array = bitset.toInt8Array();
        for (var i = 0; i < Math.min(bitset.wordLength(), this.wordLength()); i++) {
            this.data[i] &= int8Array[i];
        }
    }

    public clear(): void {
        for (var i = 0; i < this.data.length; i++) {
            this.data[i] = 0;
        }
    }

    public cardinality(): number {
        var s = 0;
        var d = this.data;
        for (var i = 0; i < d.length; i++) {
            var n = d[i];
            if (n !== 0) {
                s += this.popCount(this.toUnit8Int(n));
            }
        }
        return s;
    }

    // as Java action
    public toIntArray(): number[] {
        var intArray = [];
        for (var i = 0; i < this.data.length; i++) {
            intArray[i] = this.data[i];
        }
        return intArray;
    }

    // 与 toIntArray() 不一样的地方是，这里把的Int8Array是object，而toIntArray返回的是array
    public toInt8Array(): Int8Array {
        return this.data;
    }

    private toUnit8Int(v: number): number {
        var u8a = new Uint8Array(1);
        u8a[0] = v;
        return u8a[0];
    }

    private popCount(v: number): number {
        var count = 0;
        while (v !== 0) {
            v = (v - 1) & v;
            count++;
        }
        return count;
    }

    // https://blog.csdn.net/haiyoushui123456/article/details/83997517
    private popCountHammingWeightAlgorithm(v: number): number {
        v -= ((v >>> 1) & 0x55555555);
        v = (v & 0x33333333) + ((v >>> 2) & 0x33333333);
        return (((v + (v >>> 4) & 0xF0F0F0F) * 0x1010101) >>> 24);
    }

    // the world array length
    public wordLength(): number {
        return this.data.length;
    }

}