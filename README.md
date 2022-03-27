# Route finder

## Backgound

The program tries to solve a problem of finding the "best route" between two geographical locations
via sea freight.

The initial approach (used in the demonstrated solution) was to use Dijkstra's algorithm on a graph built from the filtered/cleaned sample data set.
Graph vertices represent different geospatial locations.
Edge weights in the graph are computed using reciprocal of harmonic mean of time intervals (duration in milliseconds),
representing time amount needed by a vessel to change it's position between two distinct geographical locations (represented by graph vertrices).

## Key takeaways

The algorithm created to solve the problem seems to be sensitive to the input data quality.
The approach taken while cleaning the data set was to remove "noise" from the geospatial coordinates
in the data set by value rounding and merging multiple data points into sets represented by graph vertices.

## Ideas

The solution could be improved by using a different edge weight cost function (for ex. including containers count carried by a vessel)
or/and using a more accurate input data preparation algorithm (2D point clustering algorithm). 

##  Program output

#### Statistics
Some insight can be gained by reviewing route statistics found in the program output
produced using the data set provided

+ Fastest voyage from Hamburg to Bremerhaven (hours): min=6.526478, average=19.923394, max=396.693988
+ Fastest voyage from Bremerhaven to Hamburg (hours): min=6.601266, average=11.909399, max=175.561214

#### Here are example routes found by the program:

+ Hamburg to Bremerhaven in ~ 8.18h
![Hamburg to Bremerhaven](https://raw.githubusercontent.com/dart1277/r-finder/main/program_output/Hamburg_to_Bremerhaven_8.23h.png "Hamburg to Bremerhaven")

+ Bremerhaven to Hamburg int ~ 8.08h
![Bremerhaven to Hamburg](https://raw.githubusercontent.com/dart1277/r-finder/main/program_output/Bremerhaven_to_Hamburg_11.48h.png "Bremerhaven to Hamburg")
