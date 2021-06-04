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

interface EdgeListProps {
    onChange(edges: any): void;  // called when a new edge list is ready
                                 // once you decide how you want to communicate the edges to the App, you should
                                 // change the type of edges so it isn't `any`
}


interface EdgeListState {
    content: string,
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {
    constructor(props: EdgeListProps) {
        super(props);
        this.state = {
            content: ""

        };
    }
    onInputChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        const content: string = event.target.value;
        this.setState({content: content});
    };

    onClear = () => {
        this.setState({content: ""});
        this.props.onChange([]);
    }

    onDraw = () => {
        const content: string = this.state.content;
        const list: string[] = content.split("\n");

        let edge_list:[number, number, number, number, string][] = [];
        list.forEach((row:string) => {
            console.log(row);
            // parse rows
            let items: string[] = row.split(" ");
            if(items.length < 3) {
                //alert("There was an error with some of your line input.\nFor reference, the correct form for each line is: x1,y1 x2,y2 color");
                return;
            }

            let start_pt: string[] = items[0].split(",");
            let end_pt: string[] = items[1].split(",");
            if(start_pt.length < 2 || end_pt.length < 2) {
                alert("There was an error with some of your line input.\nFor reference, the correct form for each line is: x1,y1 x2,y2 color");
                return;
            }

            let x1: number = parseInt(start_pt[0]);
            let y1: number = parseInt(start_pt[1]);
            let x2: number = parseInt(end_pt[0]);
            let y2: number = parseInt(end_pt[1]);
            let color: string = items[2];

            edge_list.push([x1, y1, x2, y2, color]);
        });

        this.props.onChange(edge_list);
    }

    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={this.onInputChange}
                    value={this.state.content}
                /> <br/>
                <button onClick={this.onDraw}>Draw</button>
                <button onClick={this.onClear}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
