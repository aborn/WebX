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
        vscode.window.onDidChangeTextEditorSelection(this.onTextEditorSelect, this, events);
        vscode.window.onDidChangeTextEditorViewColumn(this.onTextEditorViewChange, this, events);
        vscode.window.onDidChangeActiveTextEditor(this.onTextEditorActive, this, events);

        vscode.workspace.onDidChangeTextDocument(this.onEdit, this, events);
        vscode.workspace.onDidSaveTextDocument(this.onSave, this, events);
        vscode.workspace.onDidCreateFiles(this.onCreate, this, events);
    }

    private onTextEditorActive() {
        this.onChange(events.TEXT_EDITOR_ACTIVE);
    }

    private onTextEditorViewChange() {
        this.onChange(events.TEXT_EDITOR_VIEW_CHANGE);    
    }

    private onTextEditorSelect() {
        this.onChange(events.TEXT_EDITOR_SELECT);
    }

    private onCreate() {
        this.onChange(events.FILE_CREATED);
    }

    private onSave(e: vscode.TextDocument) {
        this.onChange(events.FILE_SAVED);
    }

    private onChange(eventName = "unknown") {
        console.log(eventName);
        this.record();
    }

    private onEdit(e: vscode.TextDocumentChangeEvent) {
        let eventName = events.FILE_EDITED;
        console.log(eventName);
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