package day7

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func Start() {

	//part, _ := strconv.Atoi(os.Args[1])
	readFileToTable()
	Part1()
	Part2()
	/*if part == 1 {
		Part1()
	} else if part == 2 {
		Part2()
	} else {
		fmt.Println("Wrong or missing input")
	}

	*/
}

var totalSum int = 0
var id int = 0
var directorySizes []int
var totalUsedSpace int

type Node struct {
	name     string
	children []*Node
	value    int
	isFile   bool
	parent   *Node
	id       int
}

var (
	nodeTable   = map[int]*Node{}
	nameIdTable = map[string]int{} // map name of node to id for quick switch between nodes
	root        *Node
)

func Part1() {
	//printTree()
	calcUnder100kDirectories(nodeTable[0])
	fmt.Printf("D7P1: sum of all directory file sizes: %d\n", totalSum)
}

func Part2() {
	//printTree()

	size := findDirectorySizeToDelete()
	fmt.Printf("D7P2: directory size to delete: %d\n", size)
}

func add(name string, currentDirId int, isFile bool, val int) {
	id++
	node := &Node{name: name, children: []*Node{}, isFile: isFile, id: id}
	if isFile {
		node.value = val
	}

	parent, ok := nodeTable[currentDirId]
	if ok {
		node.parent = parent
		parent.children = append(parent.children, node)
		nodeTable[id] = node
		nameIdTable[node.name] = id
	} else {
		fmt.Println("ALAAAARM!")
	}

}

func calcUnder100kDirectories(directoryNode *Node) int {
	directorySum := 0
	for _, child := range directoryNode.children {
		if child.isFile {
			directorySum += child.value
		} else {
			val := calcUnder100kDirectories(child)
			directorySum += val
		}
	}

	if directorySum <= 100000 {
		totalSum += directorySum
	}

	return directorySum
}

func calcDirectories(directoryNode *Node) int {
	directorySum := 0
	for _, child := range directoryNode.children {
		if child.isFile {
			directorySum += child.value
			totalUsedSpace += child.value
		} else {
			val := calcDirectories(child)
			directorySum += val
		}
	}

	directorySizes = append(directorySizes, directorySum)
	return directorySum
}

func findDirectorySizeToDelete() int {
	calcDirectories(nodeTable[0])
	var spaceToFree = 30000000 - (70000000 - totalUsedSpace)
	var smallestDirectory int = 70000000

	for _, val := range directorySizes {
		if val > spaceToFree && val < smallestDirectory {
			smallestDirectory = val
		}
	}
	return smallestDirectory
}

func printTree() {
	level := 0
	root := nodeTable[0]
	printNode(root, level)
}

func printNode(node *Node, level int) {
	if node.isFile {
		for i := 0; i < level; i++ {
			fmt.Print("| ")
		}
		fmt.Printf("%s %d\n", node.name, node.value)
	} else {
		for i := 0; i < level; i++ {
			fmt.Print("| ")
		}
		fmt.Printf("(dir)%s\n", node.name)
		for _, child := range node.children {
			printNode(child, level+1)
		}
	}
}

func findChildNode(children []*Node, childName string) *Node {
	for _, child := range children {
		if child.name == childName {
			return child
		}
	}
	return nil
}

func readFileToTable() {
	readFile, err := os.Open("day7/input.txt")
	if err != nil {
		fmt.Println(err)
	}

	fileScanner := bufio.NewScanner(readFile)
	fileScanner.Split(bufio.ScanLines)

	nodeTable[id] = &Node{name: "/", children: []*Node{}, isFile: false}
	nameIdTable["/"] = id

	var currentDir *Node = nodeTable[0]
	for fileScanner.Scan() {
		line := fileScanner.Text()

		if line == "$ cd /" {
			continue
		}

		if line == "$ cd .." {
			if currentDir.parent != nil {
				currentDir = currentDir.parent
			} else {
				fmt.Println("ALARM")
			}
		} else if strings.HasPrefix(line, "$ cd") { // move to directory
			name := strings.TrimPrefix(line, "$ cd ")
			currentDir = findChildNode(currentDir.children, name)
		} else if strings.HasPrefix(line, "$ ls") { // list pwd

		} else if strings.HasPrefix(line, "dir ") { // directory
			name := strings.TrimPrefix(line, "dir ")
			add(name, currentDir.id, false, 0)
		} else { // file
			name := strings.Split(line, " ")[1]
			value, _ := strconv.Atoi(strings.Split(line, " ")[0])
			add(name, currentDir.id, true, value)
		}

	}
}
