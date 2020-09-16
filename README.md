# Tetris
A game of Tetris in JavaFX.

Controls:
<ul>
<li> left arrow: move shape to the left </li>
<li> right arrow: move shape to the right </li>
<li> up arrow: rotate the shape </li>
<li> down arrow: make shape drop faster </li>
<li> spacebar: drop the shape all the way (not yet implemented - 18 June 2018) </li>
</ul>
<br>

<b>How it works:</b>
<p>
Each shape is represented with a 2D schema (2D array of ints). There is only one shape that is currently moving
and I keep track of its position by keeping track of indeces of row and column of the top left corner of the
schema. <br>
I use two threads to allow the game to pause and resume.<br>
The size of the grid can be any number of rows and columns, determined by constants in the MyGrid class.
</p>

Read [a blog post](https://lukakralj.com/tetris/) about this project.

<h2>TODO:</h2>
<p>
<ul>
<li>fix: removing rows and incrementing points is throwing thread-related exceptions</li>
<li>add game ending conditions (reaching top of the grid,...)</li>
<li>add preview of the next shape</li>
<li>add spacebar action</li>
</ul>
</p>
