
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

    public set(ndx: number): void {
        var n = ndx >>> WORD_LOG;
        var b = ndx & 0x7;
        this.data[n] |= (1 << b);
        // console.log('ndx=' + ndx + ', n=' + n + ', value=' + this.data[n] + ", get=" + this.get(ndx));
    }

    public get(ndx: number): number {
        var n = ndx >>> WORD_LOG;
        var b = ndx & 0x7;

        if (n >= this.data.length) {
            throw Error('Index out of box.');
        }
        return (this.data[n] >> b) & 1;
    }

    // TODO 这里有问题
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

    public toIntArray(): Int8Array {
        return this.data;
    }

    public toBuffer(): Int8Array {
        var int8Array = new Int8Array(this.data.length - 1);

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