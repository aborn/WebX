import { timeStamp } from "node:console";
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
        let events: vscode.Disposable[] = [];
        vscode.window.onDidChangeWindowState(this.onFocus, this, events);
        vscode.workspace.onDidChangeTextDocument(this.onEdit, this, events);
    }

    private onEdit(e: vscode.TextDocumentChangeEvent) {
        let eventName = events.FILE_EDITED;
        if (e.contentChanges.length > 0) {
            this.record();
        }
    }

    private onFocus(e: vscode.WindowState) {
        this.timetrace.setVSCodeWindowState(e.focused);
    }

    private record() {
        this.timetrace.record();
    }

    public dispose() {
        this.timetrace.dispose();
        console.log('webx disposed.');
    }
}