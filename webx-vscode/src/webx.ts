import * as vscode from "vscode";
import * as events from "./events";
import { TimeTrace } from "./timetrace";

export class WebX {
    private timetrace: TimeTrace;

    constructor(state: vscode.Memento) {
        this.initEventListeners();
        this.timetrace = new TimeTrace();
    }

    private initEventListeners(): void {
        // TODO: vs code active / inactive event.
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
        this.timetrace.record();
    }

    public dispose() {
        console.log('webx closed.');
        // clear all temp state and post current data
    }
}