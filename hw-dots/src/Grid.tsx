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

interface GridProps {
    size: number;    // size of the grid to display
    width: number;   // width of the canvas on which to draw
    height: number;  // height of the canvas on which to draw
    edges: []       // edge list
}

interface GridState {
    backgroundImage: any
}

/**
 *  A simple grid with a variable size
 *
 *  Most of the assignment involves changes to this class
 */
class Grid extends Component<GridProps, GridState> {

    canvasReference: React.RefObject<HTMLCanvasElement>

    constructor(props: GridProps) {
        super(props);
        this.state = {
            backgroundImage: null  // An image object to render into the canvas.

        };
        this.canvasReference = React.createRef();
        console.log(this.state)
    }

    componentDidMount() {
        // Since we're saving the image in the state and re-using it any time we
        // redraw the canvas, we only need to load it once, when our component first mounts.
        this.fetchAndSaveImage();
        this.redraw();
    }

    componentDidUpdate() {
        this.redraw()
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        const background = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./image.jpg";
    }

    redraw = () => {
        if(this.canvasReference.current === null) {
            throw new Error("Unable to access canvas.");
        }
        const ctx = this.canvasReference.current.getContext('2d');
        if(ctx === null) {
            throw new Error("Unable to create canvas drawing context.");
        }

        // First, let's clear the existing drawing so we can start fresh:
        ctx.clearRect(0, 0, this.props.width, this.props.height);

        // Once the image is done loading, it'll be saved inside our state, and we can draw it.
        // Otherwise, we can't draw the image, so skip it.
        if(this.state.backgroundImage !== null) {
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }

        // Draw all the dots.
        const coordinates = this.getCoordinates();
        for(let coordinate of coordinates) {
            this.drawCircle(ctx, coordinate);
        }

        // draw edges
        this.drawEdges(ctx, this.props.edges);
    };

    /**
     * Returns an array of coordinate pairs that represent all the points where grid dots should
     * be drawn.
     */
    getCoordinates = (): [number, number][] => {
        console.log("Number", this.props.size, this.props.width, this.props.height);
        var pts: [number, number][] = [];
        const space_x = this.props.width / (this.props.size + 1);
        const space_y = this.props.height /(this.props.size + 1);
        for(var i = 0; i < this.props.size; i++)
        {
            let y = (i + 1) * space_y;
            for(var j = 0; j < this.props.size; j++)
            {
                let x = (j + 1) * space_x;
                pts.push([x, y]);
            }
        }

        return pts;
    };

    drawCircle = (ctx: CanvasRenderingContext2D, coordinate: [number, number]) => {
        ctx.fillStyle = "white";
        // Generally use a radius of 4, but when there are lots of dots on the grid (> 50)
        // we slowly scale the radius down so they'll all fit next to each other.
        const radius = Math.min(4, 100 / this.props.size);
        ctx.beginPath();
        ctx.arc(coordinate[0], coordinate[1], radius, 0, 2 * Math.PI);
        ctx.fill();
    };

    drawEdges = (ctx: CanvasRenderingContext2D, edges: []) => {
        let coordinates = this.getCoordinates();

        for(var i = 0; i < edges.length; i++)
        {
            let edge = edges[i];

            if(edge[0] >= this.props.size || edge[1] >= this.props.size ||
                edge[2] >= this.props.size || edge[3] >= this.props.size)
            {
                alert("Cannot draw edges, grid must be at least size " + (this.props.size + 1));
            }

            if(edge[1] * this.props.size + edge[0] < coordinates.length &&
                edge[3] * this.props.size + edge[2] < coordinates.length)
            {
                let x1 = coordinates[edge[1] * this.props.size + edge[0]][0];
                let y1 = coordinates[edge[1] * this.props.size + edge[0]][1];

                let x2 = coordinates[edge[3] * this.props.size + edge[2]][0];
                let y2 = coordinates[edge[3] * this.props.size + edge[2]][1];

                let color = edge[4];

                this.drawLine(ctx, x1, y1, x2, y2, color);
            }
        }
    }

    drawLine = (ctx: CanvasRenderingContext2D, x1: number, y1: number, x2: number, y2: number, color: string) => {
        ctx.strokeStyle = color;

        const radius = Math.min(4, 100 / this.props.size);
        ctx.lineWidth = radius;
        ctx.beginPath();
        ctx.moveTo(x1, y1);
        ctx.lineTo(x2, y2);

        ctx.stroke();

    }

    render() {
        return (
            <div id="grid">
                <canvas ref={this.canvasReference} width={this.props.width} height={this.props.height}/>
                <p>Current Grid Size: {this.props.size}</p>
            </div>
        );
    }
}

export default Grid;
