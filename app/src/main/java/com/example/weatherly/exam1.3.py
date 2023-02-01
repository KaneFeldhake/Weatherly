def main():
    numbers_fileOut = open('numtest.txt', 'r')
    number = numbers_fileOut.readline()

    sum = 0

    while number != '':
        sum = sum + float(number)

        number = numbers_fileOut.readline()

    numbers_fileIn = open('numtest.txt', 'a')
    numbers_fileIn.write(str(sum))

    numbers_fileOut.close()


main()

