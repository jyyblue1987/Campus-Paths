/*
 * Copyright (C) 2021 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';
import "./App.css";
import MapView from './MapView';

interface AppState {
    buildings: string[] | [];
    start: string | "";
    end: string | "";
    path: any | [],
}


class App extends Component<{}, AppState> {
    start: string = "";
    end: string = "";

    constructor(props: {}) {
        super(props);
        this.state = {
            buildings: [],
            start: "",
            end: "",
            path: [],
        };
    }

    async componentDidMount() {
        // Might want to do something here?
        console.log("App componentDidMount");

        await this.getBuildNames();
    }

    async getBuildNames() {
        try {
            let res = await fetch('http://localhost:4567/getBuildingNames');
            let result = await res.json();
            this.setState({
                buildings: result,
                start: result[0],
                end: result[0],
            });
        } catch(err) {
            console.error(err);
        }
    }

    async onChangeStart(event: React.FormEvent) {
        // Use cast to any works but is not type safe
        let unsafeSearchTypeValue = ((event.target) as any).value;

        console.log("onChangeStart", unsafeSearchTypeValue);
        await this.setState({start: unsafeSearchTypeValue});

        await this.findPath();
    }

    async onChangeEnd(event: React.FormEvent) {
        let unsafeSearchTypeValue = ((event.target) as any).value;

        console.log("onChangeEnd", unsafeSearchTypeValue);
        await this.setState({end: unsafeSearchTypeValue});

        await this.findPath();
    }

    async findPath() {
        try {
            let res = await fetch(`http://localhost:4567/findPath?start=${this.state.start}&end=${this.state.end}`);
            let result = await res.json();
            this.setState({path: result.path})
        } catch(err) {
            console.error(err);
        }
    }

    async onReset(event: React.FormEvent ) {
        await this.setState({start: this.state.buildings[0] + ""});
        await this.setState({end: this.state.buildings[0] + ""});
        await this.findPath();
    }

    render() {
        return (
            <div>
                <div className="control">
                    <label>Start</label>
                    <select value={this.state.start} onChange={(e) => this.onChangeStart(e)}>
                        {this.state.buildings?.map((item: string) => {
                            return (<option key={item} value={item}>{item}</option>);
                        })}
                    </select>

                    <label>End</label>
                    <select value={this.state.end} onChange={(e) => this.onChangeEnd(e)}>
                        {this.state.buildings?.map((item: string) => {
                            return (<option key={item} value={item}>{item}</option>);
                        })}
                    </select>

                    <button onClick={(e) => this.onReset(e)}>Reset</button>
                </div>
                <MapView path={this.state.path} />
            </div>
        );
    }

}

export default App;
