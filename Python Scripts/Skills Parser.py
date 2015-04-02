#Use Python 3.X, tested with 3.4
import re, os

def textFunc():
	global description

	#All feats have their type in square brackets
	line = inputFile.readline()
	outputFile.write("\t\t<NAME>\n")
	outputFile.write("\t\t\t" + line)
	outputFile.write("\t\t</NAME>\n")

	for line in inputFile:
		if line is '\n':
			return True
		if  not line:
			return False

		parse = re.search('[\w\s]+[:]', line)
		if parse:
			broke = False
			tag = parse.group(0).upper()[:-1]
			tag = re.sub(' +','', tag)
			for i in range(0, len(validTags)):
				temp = tag
				if tag not in validTags:
					description += "\n\t\t\t" + line
					broke = True
					break
			#Do this because there isn't a break and continue option for multiple loops
			if broke:
				continue

			outputFile.write("\t\t<" + tag + ">\n")
			line = re.sub('[\w\s]+[:]\s','',line)
			outputFile.write("\t\t\t" + line)
			outputFile.write("\t\t</" + tag + ">\n")

#Main begins here
inputFile = open("skillsIn.txt")
outputFile = open("tempOut.txt",'w+')
outputFile.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
outputFile.write("<SKILLS>\n")
validTags = ["NAME", "SPECIAL", "CHECK", "TRYAGAIN", "DESCRIPTION", "ACTION", "SYNERGY", "RESTRICTION", "UNTRAINED"]
while True:
	description = ""
	outputFile.write("\t<SKILL>\n")
	done = textFunc()
	if description:
		outputFile.write("\t\t<DESCRIPTION>\n")
		description = description[:-1]
		outputFile.write("\t\t\t" + description)
		outputFile.write("\n\t\t</DESCRIPTION>\n")
	outputFile.write("\t</SKILL>\n")
	if not done:
		break
outputFile.write("</SKILLS>\n")
outputFile.close()

#Pass number 2: Electric Bugaloo
f = open("tempOut.txt",'r')
filedata = f.read()
f.close()
os.remove("tempOut.txt")

#All this stuff makes sure it looks nice to us, yes it's all necessary in this ordering
newdata = filedata.replace("</DESCRIPTION>\n\t\t<DESCRIPTION>",'')
# newdata = filedata.replace("<PREREQUISITES>","<PREREQUISITE>")
# newdata = filedata.replace("</PREREQUISITES>","</PREREQUISITE>")
newdata = filedata.replace("\n\n", '\n')
# newdata = filedata.replace("\t\t\t\t\t\t", '\t\t\t')
newdata = filedata.replace("\t\t\t\n","")

f = open("..\XML\Skills.xml",'w')
f.write(newdata)
f.close()