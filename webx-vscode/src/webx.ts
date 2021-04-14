import * as vscode from "vscode";
import * as events from "./events";
import { DayBitSet } from "./daybitset";

export class WebX {
    private daybitset: DayBitSet;
    constructor(state: vscode.Memento) {
        this.initEventListeners();
        this.daybitset = new DayBitSet();
    }

    private initEventListeners(): void {
        let events: vscode.Disposable[] = [];
        vscode.workspace.onDidChangeTextDocument(this.onEdit, this, events);
    }

    private onEdit(e: vscode.TextDocumentChangeEvent) {
        let eventName = events.FILE_EDITED;
        if (e.contentChanges.length > 0) {
            this.record();
        }
    }

    private record() {
        this.daybitset.record();
        this.daybitset.print();
    }

    public dispose() {
        // clear all temp state and post current data
    }
}