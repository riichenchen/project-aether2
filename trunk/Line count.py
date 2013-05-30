import os

extension = "java"
#extension = "py"

totalLines = 0
longestLine = 0
counts = {}

currentDirectory = os.getcwd()
for root, dirs, files in os.walk(currentDirectory):
    for fileName in files:
        if fileName.endswith("." + extension):
            lineCount = open(root + os.sep + fileName).read().count("\n")
            longestLine = max(longestLine, len(fileName))
            totalLines += lineCount
            counts[fileName] = lineCount
    
print("Total linecount: " + str(totalLines))

for filePath in sorted(counts.keys(), key=lambda k:counts[k], reverse=True):
    print(("%-" + str(longestLine + 2) + "s%i")%(filePath,counts[filePath]))
