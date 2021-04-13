import * as vscode from "vscode";
import * as events from "./events";

export class WebX {
    constructor(state: vscode.Memento) {
        this.initEventListeners();
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
        console.log('record.');
    }

    public dispose() {
        // clear all temp state and post current data
    }
}