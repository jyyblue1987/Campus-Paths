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
import Control from "./Control";

interface AppState {
    buildings: string[] | [];
    path: any | [],
}


class App extends Component<{}, AppState> {
    start: string = "";
    end: string = "";
    BASE_URL: string = "http://localhost:4567";

    constructor(props: {}) {
        super(props);
        this.state = {
            buildings: [],
            path: [],
        };
    }

    componentDidMount() {
        // Might want to do something here?
        console.log("App componentDidMount");

        this.getBuildNames();
    }

    getBuildNames() {
        fetch(`${this.BASE_URL}/getBuildingNames`)
            .then(response => response.json())
            .then(response => {
                this.setState({
                    buildings: response,
                })
                this.start = response[0];
                this.end = response[0];

                console.log("Buildings", response);
            })
            .catch(error => {

            });
    }

    onSelectStart(building: string) {
        // Use cast to any works but is not type safe
        this.start = building;

        this.findPath();
    }

    onSelectEnd(building: string) {
        this.end = building;

        this.findPath();
    }

    findPath() {
        fetch(`${this.BASE_URL}/findPath?start=${this.start}&end=${this.end}`)
            .then(response => response.json())
            .then(response => {
                this.setState({path: response.path})
            })
            .catch(error => {

            });
    }

    onReset() {
        this.start = this.state.buildings[0];
        this.end = this.state.buildings[0];
        this.findPath();
    }

    render() {
        return (
            <div>
                <Control buildings={this.state.buildings}
                         onSelectStart={(building) => this.onSelectStart(building)}
                         onSelectEnd={(building) => this.onSelectEnd(building)}
                         onReset={() => this.onReset()}/>
                <MapView path={this.state.path} />
            </div>
        );
    }

}

export default App;
