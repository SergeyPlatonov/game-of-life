# 🧬 Conway's Game of Life

A simple implementation of **Conway's Game of Life**, a cellular automaton devised by mathematician **John Conway** in 1970.  
This simulation demonstrates how simple local rules on an infinite 2D grid can generate complex, emergent behaviors over discrete time steps called generations.

---

## 📖 Definition

The universe of the Game of Life is an infinite two-dimensional orthogonal grid of square cells, each in one of two possible states: **alive** or **dead**. Every cell interacts with its eight neighbors, which are the cells horizontally, vertically, or diagonally adjacent.

---

## 📋 Rules

At each discrete time step ("tick"), the state of every cell updates simultaneously according to these rules:

1. Any **live** cell with fewer than two live neighbors **dies** (underpopulation).
2. Any **live** cell with two or three live neighbors **lives on** to the next generation.
3. Any **live** cell with more than three live neighbors **dies** (overcrowding).
4. Any **dead** cell with exactly three live neighbors **becomes alive** (reproduction).

---

## 🎯 Objective

- Implement the Game of Life data structures and algorithm.
- Demonstrate the algorithm works correctly.
- Use a **25x25** grid universe.
- Place a **Glider** pattern at the center as the initial seed.
- Ensure the program runs properly.

---

## 🔁 Behavior

The game evolves from the initial seed through repeated generations, where births and deaths occur simultaneously. Patterns like the **Glider** move diagonally across the grid, showing emergent, dynamic behavior.

---

## 🚀 Features

- Configurable **25x25** grid size.
- Initial seed with the **Glider** pattern placed in the center.
- Stepwise simulation applying the Game of Life rules.
- Console/terminal output displaying each generation visually.

---

## ⚙️ Configurable Constants

These values can be changed directly in the source code to customize the behavior of the simulation.

| Constant Name   | Description                             | Default Value |
|------------------|-----------------------------------------|------|
| `width`          | Width of the universe (grid)            | `25` |
| `height`         | Height of the universe (grid)           | `25` |
| `generations`    | Number of generations to simulate       | `10` |
| `delayMs`        | Delay between generations (in ms)       | `100` |
| `alive`          | Character for live cells                | `"■ "` |
| `dead`           | Character for dead cells                | `". "` |
| `boundaryType`   | Edge behavior: `Toroidal` or `Finite`   | `Toroidal` |

You can modify these values at the top of the code to fit your simulation needs.

---

## 💻 Technologies

- **Java**: Standard JDK, console output.
- **Go**: Standard Go modules, console output.

---

## 📦 Installation and Usage

Clone the repository:

```bash
git clone https://github.com/SergeyPlatonov/game-of-life.git
cd game-of-life
```

### Java

Build using your preferred method.

Run the main class to see Game of Life in action.

### Go

```bash
go run main.go
```
