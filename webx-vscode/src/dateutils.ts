
export function getSlotIndex(date: Date = new Date()): number {
    const hours = date.getHours();
    const minutes = date.getMinutes();
    const seconds = date.getSeconds();
    return hours * 60 * 2 + minutes * 2 + Math.floor(seconds / 30);
}