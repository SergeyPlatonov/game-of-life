package main

import (
	"fmt"
	"time"
)

const (
	width       = 25
	height      = 25
	generations = 10
	delay       = time.Millisecond * 100
	alive       = "■ "
	dead        = ". "
)

type BoundaryType int

const (
	Toroidal BoundaryType = iota
	Finite
)

type GameOfLife struct {
	grid         [height][width]bool
	nextGrid     [height][width]bool
	boundaryType BoundaryType
}

func NewGameOfLife(boundaryType BoundaryType) *GameOfLife {
	return &GameOfLife{boundaryType: boundaryType}
}

func (g *GameOfLife) initializeGrid() {
	midX := width / 2
	midY := height / 2

	// Place a "Glider" pattern in the middle of the grid
	g.grid[midY-1][midX] = true
	g.grid[midY][midX+1] = true
	g.grid[midY+1][midX-1] = true
	g.grid[midY+1][midX] = true
	g.grid[midY+1][midX+1] = true
}

func (g *GameOfLife) printGrid() {
	for _, row := range g.grid {
		for _, cell := range row {
			if cell {
				fmt.Print(alive)
			} else {
				fmt.Print(dead)
			}
		}
		fmt.Println()
	}
	fmt.Println()
}

func (g *GameOfLife) computeNextGeneration() {
	for y := 0; y < height; y++ {
		for x := 0; x < width; x++ {
			var liveNeighbors int
			if g.boundaryType == Toroidal {
				liveNeighbors = g.countLiveNeighborsToroidal(x, y)
			} else {
				liveNeighbors = g.countLiveNeighborsFinite(x, y)
			}

			// Apply rules
			if g.grid[y][x] {
				g.nextGrid[y][x] = liveNeighbors == 2 || liveNeighbors == 3
			} else {
				g.nextGrid[y][x] = liveNeighbors == 3
			}
		}
	}

	// Swap grids
	g.grid, g.nextGrid = g.nextGrid, g.grid
}

func (g *GameOfLife) countLiveNeighborsToroidal(x, y int) int {
	count := 0
	for i := -1; i <= 1; i++ {
		for j := -1; j <= 1; j++ {
			if i == 0 && j == 0 {
				continue
			}
			neighborX := (x + j + width) % width
			neighborY := (y + i + height) % height
			if g.grid[neighborY][neighborX] {
				count++
			}
		}
	}
	return count
}

func (g *GameOfLife) countLiveNeighborsFinite(x, y int) int {
	count := 0
	for i := -1; i <= 1; i++ {
		for j := -1; j <= 1; j++ {
			if i == 0 && j == 0 {
				continue
			}
			neighborX := x + j
			neighborY := y + i
			if neighborY >= 0 && neighborY < height &&
				neighborX >= 0 && neighborX < width &&
				g.grid[neighborY][neighborX] {
				count++
			}
		}
	}
	return count
}

func (g *GameOfLife) run() {
	g.initializeGrid()

	// Print initial state
	fmt.Println("Generation: 0 (Initial State)")
	g.printGrid()

	// Run generations
	for i := 1; i <= generations; i++ {
		fmt.Printf("Generation: %d\n", i)
		g.computeNextGeneration()
		g.printGrid()
		time.Sleep(delay)
	}
}

func main() {
	game := NewGameOfLife(Toroidal)
	game.run()
}
