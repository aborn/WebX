import * as vscode from "vscode";
import * as events from "./events";
import { DayBitSet } from "./daybitset";
import { DataSender } from "./datasender";

export class WebX {
    private daybitset: DayBitSet;
    private datasender: DataSender;

    constructor(state: vscode.Memento) {
        this.initEventListeners();
        this.daybitset = new DayBitSet();
        this.datasender = new DataSender();
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
        var log = this.datasender.postData(this.daybitset);
        console.log(log);
        // this.daybitset.print();
    }

    public dispose() {
        // clear all temp state and post current data
    }
}