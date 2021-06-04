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
import "./MapView.css";

interface MapViewState {
    backgroundImage: HTMLImageElement | null;
}

interface MapViewProps {
    path: any[]| [];
}

class MapView extends Component<MapViewProps, MapViewState> {

    // NOTE:
    // This component is a suggestion for you to use, if you would like to.
    // It has some skeleton code that helps set up some of the more difficult parts
    // of getting <canvas> elements to display nicely with large images.
    //
    // If you don't want to use this component, you're free to delete it.

    canvas: React.RefObject<HTMLCanvasElement>;

    constructor(props: MapViewProps) {
        super(props);
        this.state = {
            backgroundImage: null
        };
        this.canvas = React.createRef();
    }

    componentDidMount() {
        // Might want to do something here?
        this.fetchAndSaveImage();
    }

    componentDidUpdate() {
        // Might want something here too...
        this.drawBackgroundImage();
        this.drawFindPath();
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        let background: HTMLImageElement = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./campus_map.jpg";
    }

    drawBackgroundImage() {
        let canvas = this.canvas.current;
        if (canvas === null) throw Error("Unable to draw, no canvas ref.");
        let context = canvas.getContext("2d");
        if (context === null) throw Error("Unable to draw, no valid graphics context.");
        //
        if (this.state.backgroundImage !== null) { // This means the image has been loaded.
            // Sets the internal "drawing space" of the canvas to have the correct size.
            // This helps the canvas not be blurry.
            canvas.width = this.state.backgroundImage.width;
            canvas.height = this.state.backgroundImage.height;
            context.drawImage(this.state.backgroundImage, 0, 0);
        }
    }

    drawFindPath() {
        let canvas = this.canvas.current;
        if (canvas === null) throw Error("Unable to draw, no canvas ref.");
        let context = canvas.getContext("2d");
        if (context === null) throw Error("Unable to draw, no valid graphics context.");

        context.beginPath();     // Start a new path.
        context.lineWidth = 10;
        context.strokeStyle = "blue";  // This path is red.

        if( this.props.path.length > 0 ) {
            context.moveTo(this.props.path[0].start.x, this.props.path[0].start.y);
            for(let i = 0; i < this.props.path.length; i++) {
                let pos = this.props.path[i];
                context?.lineTo(pos.start.x, pos.start.y);
            };

            context.stroke();
        }
        context.closePath(); // Close the current path.
    }

    render() {
        return (
            <div>
                <canvas ref={this.canvas}/>
            </div>
        )
    }
}

export default MapView;
