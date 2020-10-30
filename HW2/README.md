# JavaScript_D3_Application

`Important: remember to setup a local HTTP server to run D3 visualizations. The easiest way is to use http.server for Python 3.x, or SimpleHTTPServer for Python 2.x.`


This repository contains several D3 implementation examples for dava visualization and user interaction;
Support Chrome and firefox;

## Q2: Force-directed graph layout (graph.html)

  Provides code to show the data relationship with force-directed graph based on Javascript and D3;

  The code includes the following functions:

    -- Create force-directed graph;

    -- Add node labels and styling edges based on the values of edges;

    -- Scale the radius of each node and change edge color based on its degree;

    -- Pin nodes so that the node will be marked and pinned at first double click, and unfreezed at second double click.  

![image](https://github.com/JolinQChen/JavaScriptD3Application/blob/master/Q2/Screen%20Recording%202020-04-04%20at%2019.06.22.gif)
## Q3: Line Charts
Use the dataset provided in the file earthquakes.csv to create line charts.  
Must run on local host.  

![image](https://github.com/JolinQChen/JavaScriptD3Application/blob/master/Q3/Screen%20Recording%202020-04-04%20at%2019.32.45.gif)

## Q4: Heatmap and Select Box (heatmap.html, earthquakes.csv)

Visualize the earthquakes in Americans states in the form of heatmap.  
![image](https://github.com/JolinQChen/JavaScriptD3Application/blob/master/Q4/Screen%20Recording%202020-04-04%20at%2019.48.53.gif)

## Q5: Interactive Visualization of State Earthquakes (state-year-earthquakes.csv, interactive.html)

  Provides code to interactively visualize the earthquake information in American states.  
<img width="300" height="300" src="https://github.com/JolinQChen/JavaScriptD3Application/blob/master/Q5/Screen%20Recording%202020-04-04%20at%2020.03.14.gif"/>

## Q6: Choropleth Map of State Data (state-earthquakes.csv, states-10m.json, choropleth.html)

  Provides code to process data provided in state-earthquakes.csv and states-10m.json and visualize through an interactive map    in browser.  
![image](https://github.com/JolinQChen/JavaScriptD3Application/blob/master/Q6/Screen%20Recording%202020-04-04%20at%2020.05.59.gif)
