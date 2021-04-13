"use strict";
exports.__esModule = true;
exports.DayBitSet = void 0;
var bitset_1 = require("bitset");
var SLOT_SIZE = 24 * 60 * 2;
var DayBitSet = /** @class */ (function () {
    function DayBitSet() {
        this.day = "2021-04-07";
        this.bitset = new bitset_1["default"]();
        this.bitset.setRange(0, SLOT_SIZE);
    }
    DayBitSet.prototype.record = function () {
    };
    return DayBitSet;
}());
exports.DayBitSet = DayBitSet;
