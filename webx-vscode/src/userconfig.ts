import * as fs from "fs";
import * as os from 'os';
import * as path from 'path';

export class UserConfig {
    private configFile: string;
    // private logFile: string;
    private token: string | null;
    private id: string | null;

    constructor() {
        this.token = null;
        this.id = null;
        let homePath = this.getUserHomeDir();
        this.configFile = path.join(homePath, '.webx.cfg');
        // this.logFile = path.join(homePath, '.webx.log');

        this.init((id, token) => {
            this.id = id;
            this.token = token;
        });
    }

    // TODO: token need init when token is null.
    public getToken(): string | null {
        return this.token;
    }

    public print(): void {
        console.log("[id = " + this.id + ", token = " + this.token);
    }

    // TODO: User command setting for token config.
    public config(id: string, token: string): void {
        let contents: string[] = [];
        if (id && id.trim.length > 0 && token && token.trim.length > 0) {
            contents.push("id = " + id);
            contents.push("token = " + token);

            fs.writeFile(this.getConfigFile(), contents.join('\n'), err => {
                if (err) {
                    throw err;
                };
            });
        }
    }

    private init(callback: (id: string, token: string) => void): void {
        fs.readFile(this.getConfigFile(), function (err, data) {
            if (err) {
                return console.error(err);
            }
            //console.log("Asynchronous read: " + data.toString());

            var id = null;
            var token = null;

            let lines = data.toString().split('\n');
            for (var i = 0; i < lines.length; i++) {
                let line = lines[i];

                if (line.indexOf('=') >= 0) {
                    let parts = line.split('=');
                    let key = parts[0].trim();
                    let value = parts[1].trim();
                    if (key === 'token') {
                        token = value;
                    }
                    if (key === 'id') {
                        id = value;
                    }
                }
            }

            if (id !== null && token !== null) {
                callback(id, token);
                console.log('init id and token success. [id:' + id + ', token:' + token + ']');
            } else {
                console.log('init id and token failed.');
            }
        });
    }

    private isPortable(): boolean {
        return !!process.env['VSCODE_PORTABLE'];
    }

    public getUserHomeDir(): string {
        if (this.isPortable()) {
            return process.env['VSCODE_PORTABLE'] as string;
        }

        return process.env[UserConfig.isWindows() ? 'USERPROFILE' : 'HOME'] || '';
    }

    public static isWindows(): boolean {
        return os.platform() === 'win32';
    }

    public getConfigFile(): string {
        return this.configFile;
    }
}