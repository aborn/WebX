
const WORD_LENGTH = 8;
const WORD_LOG = 3;

export class BitSet {
    // 总共有多少个bitslot
    private slot: number;
    private data: number[];

    constructor(slot: number) {
        if (slot <= 0) {
            throw SyntaxError('Invalid slot!');
        }

        this.slot = slot;
        this.data = [];
        var l = slot >>> WORD_LOG;

        for (var i = 0; l >= i; l--) {
            this.data.push(0);
        }
    }

    public set(ndx: number): void {
        this.data[ndx >>> WORD_LOG] |= (1 << ndx);
    }

    public get(ndx: number): number {
        var n = ndx >>> WORD_LOG;

        if (n >= this.data.length) {
            throw Error('Index out of box.');
        }
        return (this.data[n] >>> ndx) & 1;
    }

    public cardinality(): number {
        var s = 0;
        var d = this.data;
        for (var i = 0; i < d.length; i++) {
            var n = d[i];
            if (n !== 0) {
                s += this.popCount(n);
            }
        }
        return s;
    }

    public toByteBuffer(): ArrayBuffer {
        const buffer = new ArrayBuffer(this.data.length * WORD_LENGTH);
        var unit32Array = new Uint32Array(buffer);

        for (var i = 0; i < unit32Array.length; i++) {
            unit32Array[i] = this.data[i];
        }
        return buffer;
    }

    public toIntArray() : number[] {        
        return this.data;
    }

    public toBuffer() : Int8Array {
        const buffer = new ArrayBuffer(this.data.length * WORD_LENGTH);
        var int8Array = new Int8Array(buffer);

        for (var i = 0; i < int8Array.length; i++) {
            int8Array[i] = this.data[i];
        }
        return int8Array;
    }

    private popCount(v: number): number {
        v -= ((v >>> 1) & 0x55555555);
        v = (v & 0x33333333) + ((v >>> 2) & 0x33333333);
        return (((v + (v >>> 4) & 0xF0F0F0F) * 0x1010101) >>> 24);
    }

    public wordLength(): number {
        return this.data.length;
    }

}