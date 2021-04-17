
export function getSlotIndex(date: Date = new Date()): number {
    const hours = date.getHours();
    const minutes = date.getMinutes();
    const seconds = date.getSeconds();
    return hours * 60 * 2 + minutes * 2 + Math.floor(seconds / 30);
}

export function getDayInfo(date: Date = new Date()): string {
    var month = date.getMonth() + 1;
    var arr = [date.getFullYear(), month < 10 ? '0' + month : month, date.getDate()];
    return arr.join("-");
}

export function isToday(dayInfo: string): boolean {
    var todayInfo = getDayInfo();
    return todayInfo === dayInfo;
}