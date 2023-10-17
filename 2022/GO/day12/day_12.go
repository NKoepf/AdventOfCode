package day12

import (
	"bufio"
	"fmt"
	"os"
)

func RunBoth() {
	Part1()
	Part2()
}

type Void struct{}

type Position struct {
	xPos      int
	yPos      int
	elevation int

	uNei *Position
	dNei *Position
	lNei *Position
	rNei *Position

	visited    bool
	travelCost int
}

const height = 41
const width = 171

var field [height][width]Position
var startPos Position
var targetPos Position
var shortestPath = 999999999999

func Part1() {
	createField()
	for i := 0; i < height; i++ {
		for j := 0; j < width; j++ {
			//field[i][j].travelCost = 999999999
			if i-1 >= 0 {
				field[i][j].uNei = &field[i-1][j]
			}
			if i+1 < height {
				field[i][j].dNei = &field[i+1][j]
			}
			if j-1 >= 0 {
				field[i][j].lNei = &field[i][j-1]
			}
			if j+1 < width {
				field[i][j].rNei = &field[i][j+1]
			}
		}
	}

	field[startPos.yPos][startPos.xPos].travelCost = 0

	//printField()
	BFSearchClassic()
	//printField()
	fmt.Printf("D12P1: shortest path has %d steps\n", shortestPath)
}

func BFSearchClassic() {

	posToVisit := make([]*Position, 0)
	posToVisit = append(posToVisit, &field[startPos.yPos][startPos.xPos])

	field[startPos.yPos][startPos.xPos].visited = true

	for {
		pos := posToVisit[0]
		posToVisit = posToVisit[1:]

		if pos.xPos == targetPos.xPos && pos.yPos == targetPos.yPos && shortestPath > pos.travelCost {
			shortestPath = pos.travelCost
		}

		if pos.uNei != nil {
			if !field[pos.uNei.yPos][pos.uNei.xPos].visited &&
				field[pos.uNei.yPos][pos.uNei.xPos].elevation <= pos.elevation+1 {

				field[pos.uNei.yPos][pos.uNei.xPos].visited = true
				field[pos.uNei.yPos][pos.uNei.xPos].travelCost = pos.travelCost + 1
				posToVisit = append(posToVisit, &field[pos.uNei.yPos][pos.uNei.xPos])
			}
		}

		if pos.rNei != nil {
			if !field[pos.rNei.yPos][pos.rNei.xPos].visited && field[pos.rNei.yPos][pos.rNei.xPos].elevation <= pos.elevation+1 {
				field[pos.rNei.yPos][pos.rNei.xPos].visited = true
				field[pos.rNei.yPos][pos.rNei.xPos].travelCost = pos.travelCost + 1
				posToVisit = append(posToVisit, &field[pos.rNei.yPos][pos.rNei.xPos])
			}
		}

		if pos.dNei != nil {
			if !field[pos.dNei.yPos][pos.dNei.xPos].visited && field[pos.dNei.yPos][pos.dNei.xPos].elevation <= pos.elevation+1 {
				field[pos.dNei.yPos][pos.dNei.xPos].visited = true
				field[pos.dNei.yPos][pos.dNei.xPos].travelCost = pos.travelCost + 1
				posToVisit = append(posToVisit, &field[pos.dNei.yPos][pos.dNei.xPos])
			}
		}

		if pos.lNei != nil {
			if !field[pos.lNei.yPos][pos.lNei.xPos].visited && field[pos.lNei.yPos][pos.lNei.xPos].elevation <= pos.elevation+1 {
				field[pos.lNei.yPos][pos.lNei.xPos].visited = true
				field[pos.lNei.yPos][pos.lNei.xPos].travelCost = pos.travelCost + 1
				posToVisit = append(posToVisit, &field[pos.lNei.yPos][pos.lNei.xPos])
			}
		}

		if len(posToVisit) == 0 {
			break
		}
	}

}

func Part2() {
	createField()
	for i := 0; i < height; i++ {
		for j := 0; j < width; j++ {
			//field[i][j].travelCost = 999999999
			if i-1 >= 0 {
				field[i][j].uNei = &field[i-1][j]
			}
			if i+1 < height {
				field[i][j].dNei = &field[i+1][j]
			}
			if j-1 >= 0 {
				field[i][j].lNei = &field[i][j-1]
			}
			if j+1 < width {
				field[i][j].rNei = &field[i][j+1]
			}
		}
	}
	BFSearchReversed()
	fmt.Printf("D12P2: shortest path has %d steps\n", shortestPath)
}

func BFSearchReversed() {
	posToVisit := make([]*Position, 0)
	posToVisit = append(posToVisit, &field[targetPos.yPos][targetPos.xPos])

	field[startPos.yPos][startPos.xPos].visited = true

	for {
		pos := posToVisit[0]
		posToVisit = posToVisit[1:]

		if pos.elevation == 0 && shortestPath > pos.travelCost {
			shortestPath = pos.travelCost
		}

		if pos.uNei != nil {
			if !field[pos.uNei.yPos][pos.uNei.xPos].visited &&
				field[pos.uNei.yPos][pos.uNei.xPos].elevation >= pos.elevation-1 {

				field[pos.uNei.yPos][pos.uNei.xPos].visited = true
				field[pos.uNei.yPos][pos.uNei.xPos].travelCost = pos.travelCost + 1
				posToVisit = append(posToVisit, &field[pos.uNei.yPos][pos.uNei.xPos])
			}
		}

		if pos.rNei != nil {
			if !field[pos.rNei.yPos][pos.rNei.xPos].visited &&
				field[pos.rNei.yPos][pos.rNei.xPos].elevation >= pos.elevation-1 {

				field[pos.rNei.yPos][pos.rNei.xPos].visited = true
				field[pos.rNei.yPos][pos.rNei.xPos].travelCost = pos.travelCost + 1
				posToVisit = append(posToVisit, &field[pos.rNei.yPos][pos.rNei.xPos])
			}
		}

		if pos.dNei != nil {
			if !field[pos.dNei.yPos][pos.dNei.xPos].visited &&
				field[pos.dNei.yPos][pos.dNei.xPos].elevation >= pos.elevation-1 {

				field[pos.dNei.yPos][pos.dNei.xPos].visited = true
				field[pos.dNei.yPos][pos.dNei.xPos].travelCost = pos.travelCost + 1
				posToVisit = append(posToVisit, &field[pos.dNei.yPos][pos.dNei.xPos])
			}
		}

		if pos.lNei != nil {
			if !field[pos.lNei.yPos][pos.lNei.xPos].visited &&
				field[pos.lNei.yPos][pos.lNei.xPos].elevation >= pos.elevation-1 {

				field[pos.lNei.yPos][pos.lNei.xPos].visited = true
				field[pos.lNei.yPos][pos.lNei.xPos].travelCost = pos.travelCost + 1
				posToVisit = append(posToVisit, &field[pos.lNei.yPos][pos.lNei.xPos])
			}
		}

		if len(posToVisit) == 0 {
			break
		}
	}
}

func createField() {
	file, _ := os.Open("day12/input.txt")
	fileReader := bufio.NewScanner(file)
	fileReader.Split(bufio.ScanLines)

	yIndex := 0
	for fileReader.Scan() {
		line := fileReader.Text()
		char := []rune(line)

		// S = 83
		// a = 97
		// E = 69
		for i := 0; i < len(char); i++ {
			field[yIndex][i].xPos = i
			field[yIndex][i].yPos = yIndex
			if char[i] == 83 {
				// start found
				field[yIndex][i].elevation = 0
				startPos = field[yIndex][i]
			} else if char[i] == 69 {
				// end found
				field[yIndex][i].elevation = 25
				targetPos = field[yIndex][i]
			} else {
				field[yIndex][i].elevation = int(char[i] - 97)
			}
		}
		yIndex++

		//field[yIndex][xIndex].elevation

	}

}

func printField() {
	for i := 0; i < height; i++ {
		for j := 0; j < width; j++ {
			if !field[i][j].visited {
				fmt.Printf("|%02d", field[i][j].elevation)
			} else {
				fmt.Print("|__")
			}
		}
		fmt.Print("\n")
	}
	fmt.Print("\n")
}
