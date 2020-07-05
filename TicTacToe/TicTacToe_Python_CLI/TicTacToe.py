import math

OKBLUE = '\033[94m'
OKGREEN = '\033[92m'
ENDC = '\033[0m'
COMPUTER = ''
HUMAN = ''

board = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
]


def printWinner(decision):
    if decision == 'tie':
        print("Nobody won!")
    else:
        print(decision, 'won!')


def printTable(board):
    count = 0
    for row in board:
        print(2 * '   ', '|', 2 * '   ', '|', 2 * '   ')
        temp = 0
        for el in row:
            print('  ', end='')
            if el == 'X':
                print((OKBLUE + str(el) + ENDC), end=' ')
            elif el == 'O':
                print((OKGREEN + str(el) + ENDC), end=' ')
            else:
                print(el, end=' ')
            print('  ', end=' ')
            if temp < 2:
                print('|', end=' ')
                temp += 1
        print()
        # print(' ', row[0], '  ', '|', ' ', row[1], '  ', '|', ' ', row[2], '   ')
        print(2 * '   ', '|', 2 * '   ', '|', 2 * '   ')
        if count < 2:
            print('--------------------------')
            count += 1


def gameOver(board):
    totalEmptyCells = emptyCells(board)
    if totalEmptyCells < 8:
        for i in range(3):  # Checks if one of the rows or columns somebody won
            if board[i][0] == board[i][1] == board[i][2]:
                return board[i][0]
            elif board[0][i] == board[1][i] == board[2][i]:
                return board[0][i]
        if board[0][0] == board[1][1] == board[2][2]:  # Checks main diagonal
            return board[0][0]
        if board[2][0] == board[1][1] == board[0][2]:  # Checks other diagonal
            return board[2][0]
    if totalEmptyCells == 0:
        return 'tie'
    return None


def emptyCells(board):
    count = 0
    for row in board:
        for el in row:
            if el != 'X' and el != 'O':
                count += 1
    return count


def move(board, playerMark, moveChoice):
    if 0 < moveChoice < 4:
        board[0][moveChoice - 1] = playerMark
    elif 3 < moveChoice < 7:
        board[1][moveChoice - 4] = playerMark
    elif 6 < moveChoice < 10:
        board[2][moveChoice - 7] = playerMark
    else:
        raise ValueError("Out of bound value")


def checkIfPopulated(board, moveChoice):
    if 0 < moveChoice < 4:
        return True if board[0][moveChoice - 1] == 'X' or board[0][moveChoice - 1] == 'O' else False
    elif 3 < moveChoice < 7:
        return True if board[1][moveChoice - 4] == 'X' or board[1][moveChoice - 4] == 'O' else False
    elif 6 < moveChoice < 10:
        return True if board[2][moveChoice - 7] == 'X' or board[2][moveChoice - 7] == 'O' else False
    else:
        return False


def bestMove(board):
    bestScore = -math.inf
    bestMove = 0
    for i in range(3):
        for j in range(3):
            if board[i][j] != 'X' and board[i][j] != 'O':
                current = board[i][j]
                board[i][j] = COMPUTER
                score = minimax(board, 0, -1, -math.inf, math.inf)
                board[i][j] = current
                if score > bestScore:
                    bestScore = score
                    bestMove = tuple((i, j))
    board[bestMove[0]][bestMove[1]] = COMPUTER
    print("Computer's choice: x =", bestMove[0] + 1, "y =", bestMove[1] + 1)


def minimax(board, depth, isMaximizer, alpha, beta):  # https://www.youtube.com/watch?v=trKjYdBASyQ
    result = gameOver(board)
    if result is not None:
        return {'tie': 0, COMPUTER: 10 - depth}.get(result, -10 + depth)
    bestScore = -math.inf if isMaximizer == 1 else math.inf
    for i in range(3):
        for j in range(3):
            if board[i][j] != 'X' and board[i][j] != 'O':
                current = board[i][j]
                board[i][j] = COMPUTER if isMaximizer == 1 else HUMAN
                score = minimax(board, depth + 1, -isMaximizer, alpha, beta)
                board[i][j] = current
                bestScore = max(score, bestScore) if isMaximizer == 1 else min(score, bestScore)
                if isMaximizer == 1:
                    alpha = max(alpha, bestScore)
                else:
                    beta = min(beta, bestScore)
                if alpha >= beta:
                    return bestScore
    return bestScore


# Main
choice = input("2 Players? (Y/N): ")
if choice.lower() == "y":
    printTable(board)
    firstPlayerMove = True
    decision = gameOver(board)
    while decision is None:
        moveChoice = int(input("Please make a move: "))
        if firstPlayerMove:
            move(board, 'X', moveChoice)
            firstPlayerMove = False
        else:
            move(board, 'O', moveChoice)
            firstPlayerMove = True
        decision = gameOver(board)
        printTable(board)
    printWinner(decision)
elif choice.lower() == 'n':
    firstPlayerMove = True if input("Go first? (Y/N): ").lower() == 'y' else False
    if firstPlayerMove:
        HUMAN = 'X'
        COMPUTER = 'O'
    else:
        HUMAN = 'O'
        COMPUTER = 'X'
    printTable(board)
    decision = gameOver(board)
    while decision is None:
        if firstPlayerMove:
            moveChoice = int(input("Please make a move: "))
            move(board, HUMAN, moveChoice)
            firstPlayerMove = False
        else:
            bestMove(board)
            firstPlayerMove = True
            printTable(board)
        decision = gameOver(board)
    printWinner(decision)
else:
    print("Bye!")
