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
import MapView from './MapView';

class App extends Component<{}, {}> {

    render() {
        return (
            <div>
                <p>Here's the beginning of your AMAZING CampusPaths GUI!</p>
                <MapView />
            </div>
        );
    }

}

export default App;
