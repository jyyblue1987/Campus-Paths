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
        };
    }

    async componentDidMount() {
        // Might want to do something here?
        console.log("App componentDidMount");

        this.getBuildNames();
    }

    async getBuildNames() {
        fetch('http://localhost:4567/getBuildingNames')
            .then(response => response.json())
            .then(response => {
                this.setState({
                    buildings: response
                })

                console.log("Buildings", response);
            })
            .catch(error => {

            });
    }

    async onChangeStart(event: React.FormEvent) {
        // Use cast to any works but is not type safe
        let unsafeSearchTypeValue = ((event.target) as any).value;

        console.log("onChangeStart", unsafeSearchTypeValue);
        this.setState({start: unsafeSearchTypeValue});
    }

    async onChangeEnd(event: React.FormEvent) {
        let unsafeSearchTypeValue = ((event.target) as any).value;

        console.log("onChangeEnd", unsafeSearchTypeValue);
        this.setState({end: unsafeSearchTypeValue});
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
                </div>
                <MapView />
            </div>
        );
    }

}

export default App;
