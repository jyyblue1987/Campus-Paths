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
import "./Control.css";

interface ControlState {
    start: string | "";
    end: string | "";
}

interface ControlProps {
    buildings: string[] | [];
    onSelectStart: (building: string) => void
    onSelectEnd: (building: string) => void
    onReset: () => void
}


class Control extends Component<ControlProps, ControlState> {
    start: string = "";
    end: string = "";

    constructor(props: ControlProps) {
        super(props);
        this.state = {
            start: "",
            end: "",
        };
    }

    componentDidMount() {

    }

    onSelectStart(event: React.FormEvent) {
        // Use cast to any works but is not type safe
        let unsafeSearchTypeValue = ((event.target) as any).value;

        console.log("onSelectStart", unsafeSearchTypeValue);
        this.setState({start: unsafeSearchTypeValue});
        this.props.onSelectStart(unsafeSearchTypeValue);
    }

    onSelectEnd(event: React.FormEvent) {
        let unsafeSearchTypeValue = ((event.target) as any).value;

        console.log("onSelectEnd", unsafeSearchTypeValue);
        this.setState({end: unsafeSearchTypeValue});
        this.props.onSelectEnd(unsafeSearchTypeValue);
    }

    onReset(event: React.FormEvent ) {
        this.setState({start: this.props.buildings[0] + ""});
        this.setState({end: this.props.buildings[0] + ""});
        this.props.onReset();
    }

    render() {
        return (
            <div className="container">
                <table>
                    <tbody>
                        <tr>
                            <td><label>Start</label></td>
                            <td>
                                <select  value={this.state.start} onChange={(e) => this.onSelectStart(e)}>
                                    {this.props.buildings?.map((item: string) => {
                                        return (<option key={item} value={item}>{item}</option>);
                                    })}
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><label>End</label></td>
                            <td>
                                <select value={this.state.end} onChange={(e) => this.onSelectEnd(e)}>
                                    {this.props.buildings?.map((item: string) => {
                                        return (<option key={item} value={item}>{item}</option>);
                                    })}
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <button onClick={(e) => this.onReset(e)}>Reset</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        );
    }

}

export default Control;
