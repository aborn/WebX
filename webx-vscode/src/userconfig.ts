import * as fs from "fs";

export class UserConfig {
    private token: string | null;
    private id: string | null;

    constructor() {
        this.token = null;
        this.id = null;

        this.init((id, token) => {
            this.id = id;
            this.token = token;
        });
    }

    public getToken(): string | null {
        return this.token;
    }

    private init(callback: (id: string, token: string) => void): void {
        // TODO file root 
        fs.readFile('/Users/aborn/.webx.cfg', function (err, data) {
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
                        console.log('token=' + value);
                        token = value;
                    }
                    if (key === 'id') {
                        console.log('id=' + value);
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
}


export function setToken(): boolean {
    return true;
}