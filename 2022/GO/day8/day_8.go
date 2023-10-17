package day8

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

type Tree struct {
	height  int
	visible bool
}

var size = 99
var field = make([][]*Tree, size)

func main() {

	part, _ := strconv.Atoi(os.Args[1])

	createField()
	if part == 1 {
		Part1()
	} else if part == 2 {
		Part2()
	} else {
		fmt.Println("Wrong or missing input")
	}
}

func RunBoth() {
	createField()
	Part1()
	Part2()
}

func Part1() {
	//printField(field)
	visibleTrees := calcVisibleTrees(field)
	fmt.Printf("D8P1: There are %d Trees visible\n", visibleTrees)
}

func Part2() {

	visibleTrees := calcBestView()
	fmt.Printf("D8P2: The best view contains %d Trees in sight\n", visibleTrees)
}

func createField() {

	readFile, err := os.Open("day8/input.txt")
	if err != nil {
		fmt.Println(err)
	}

	for i := range field {
		field[i] = make([]*Tree, size)
	}

	fileScanner := bufio.NewScanner(readFile)
	fileScanner.Split(bufio.ScanRunes)

	indexX := 0
	indexY := 0

	for fileScanner.Scan() {
		digit := fileScanner.Text()

		if digit == "\n" {
			indexX = 0
			indexY++
		} else {
			height, _ := strconv.Atoi(digit)
			field[indexY][indexX] = &Tree{height: height, visible: false}
			indexX++
		}

	}
}

func printField(field [][]*Tree) {
	for i := 0; i < len(field); i++ {
		for j := 0; j < len(field[0]); j++ {
			fmt.Print(field[i][j].height)
		}
		fmt.Print("\n")
	}
}
func calcVisibleTrees(field [][]*Tree) int {
	visibleTrees := 0

	// from left
	for i := 0; i < len(field); i++ {
		highestVisibleTree := -1
		for j := 0; j < len(field[i]); j++ {

			if field[i][j].visible == true {
				if field[i][j].height > highestVisibleTree {
					highestVisibleTree = field[i][j].height
				}
				continue
			}
			if field[i][j].height > highestVisibleTree {
				highestVisibleTree = field[i][j].height
				field[i][j].visible = true
				visibleTrees++
			}
			if highestVisibleTree == 9 {
				break
			}
		}
	}

	// from right
	for i := 0; i < len(field); i++ {
		highestVisibleTree := -1
		for j := len(field[i]) - 1; j >= 0; j-- {

			if field[i][j].visible == true {
				if field[i][j].height > highestVisibleTree {
					highestVisibleTree = field[i][j].height
				}
				continue
			}
			if field[i][j].height > highestVisibleTree {
				highestVisibleTree = field[i][j].height
				field[i][j].visible = true
				visibleTrees++
			}
			if highestVisibleTree == 9 {
				break
			}
		}
	}

	// from bottom
	for col := 0; col < len(field[0]); col++ {
		highestVisibleTree := -1
		for row := len(field) - 1; row >= 0; row-- {

			if field[row][col].visible == true {
				if field[row][col].height > highestVisibleTree {
					highestVisibleTree = field[row][col].height
				}
				continue
			}
			if field[row][col].height > highestVisibleTree {
				highestVisibleTree = field[row][col].height
				field[row][col].visible = true
				visibleTrees++
			}
			if highestVisibleTree == 9 {
				break
			}
		}
	}

	// from top
	for col := 0; col < len(field[0]); col++ {
		highestVisibleTree := -1
		for row := 0; row < len(field); row++ {

			if field[row][col].visible == true {
				if field[row][col].height > highestVisibleTree {
					highestVisibleTree = field[row][col].height
				}
				continue
			}
			if field[row][col].height > highestVisibleTree {
				highestVisibleTree = field[row][col].height
				field[row][col].visible = true
				visibleTrees++
			}
			if highestVisibleTree == 9 {
				break
			}
		}
	}

	return visibleTrees

}

func calcBestView() int {
	bestViewVal := 0
	for i := 0; i < len(field); i++ {
		for j := 0; j < len(field[0]); j++ {

			val := calcViewValue(i, j)
			if val > bestViewVal {
				bestViewVal = val
			}
		}
	}

	return bestViewVal
}

func calcViewValue(ypos, xpos int) int {
	var u, d, l, r int = 1, 1, 1, 1
	var currentHeight int = field[ypos][xpos].height
	// check up
	if ypos-1 < 0 {
		u = 0
		return 0
	}
	for {
		if ypos-u < 0 {
			u--
			break
		}

		if field[ypos-u][xpos].height >= currentHeight {
			break
		} else {
			u++
		}
	}

	// check down
	if ypos+1 > len(field)-1 {
		d = 0
		return 0
	}
	for {
		if ypos+d > len(field)-1 {
			d--
			break
		}

		if field[ypos+d][xpos].height >= currentHeight {
			break
		} else {
			d++
		}
	}

	// check left
	if xpos-1 < 0 {
		l = 0
		return 0
	}
	for {
		if xpos-l < 0 {
			l--
			break
		}

		if field[ypos][xpos-l].height >= currentHeight {
			break
		} else {
			l++
		}
	}

	// check right
	if xpos+1 > len(field[0])-1 {
		r = 0
		return 0
	}
	for {
		if xpos+r > len(field[0])-1 {
			r--
			break
		}
		if field[ypos][xpos+r].height >= currentHeight {
			break
		} else {
			r++
		}
	}
	return u * d * r * l
}
