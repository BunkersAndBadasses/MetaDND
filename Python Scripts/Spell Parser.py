#Use Python 3.X, tested with 3.4
import re, os

def textFunc():
	spellName = inputFile.readline()	
	outputFile.write("\t\t<NAME>\n")
	outputFile.write("\t\t\t" + spellName)
	outputFile.write("\t\t</NAME>\n")
	spellSchool = inputFile.readline()
	outputFile.write("\t\t<SCHOOL>\n")
	outputFile.write("\t\t\t" + spellSchool)
	outputFile.write("\t\t</SCHOOL>\n")
	
	for line in inputFile:
		if line is '\n':
			return True
		if  not line:
			return False
		parse = re.search('[\w\s]+[:]', line)
		if parse:
			tag = parse.group(0).upper()[:-1]
			tag = re.sub(' +','', tag)
			for i in range(0, len(validTags)):
				temp = tag
				if tag not in validTags:
					tag = "DESCRIPTION"

			outputFile.write("\t\t<" + tag + ">\n")
			line = re.sub('[\w\s]+[:]\s','',line)
			outputFile.write("\t\t\t" + line)
			outputFile.write("\t\t</" + tag + ">\n")
			
		else:
			outputFile.write("\t\t<DESCRIPTION>\n")
			outputFile.write("\t\t\t" + line)
			outputFile.write("\t\t</DESCRIPTION>\n")

#Main begins here
inputFile = open("spellsIn.txt")
outputFile = open("tempOut.txt",'w+')
outputFile.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
outputFile.write("<SPELLS>\n")
validTags = ["NAME", "SCHOOL", "LEVEL", "COMPONENTS", "CASTINGTIME", "RANGE", "EFFECT", "DURATION", "SAVINGTHROW", "SPELLRESISTANCE", "DESCRIPTION", "MATERIALCOMPONENT", "FOCUS"]
while True:
	outputFile.write("\t<SPELL>\n")
	done = textFunc()
	outputFile.write("\t</SPELL>\n")
	if not done:
		break
outputFile.write("</SPELLS>\n")
outputFile.close()

#Pass number 2: Electric Bugaloo
f = open("tempOut.txt",'r')
filedata = f.read()
f.close()
os.remove("tempOut.txt")

newdata = filedata.replace("</DESCRIPTION>\n\t\t<DESCRIPTION>",'')

f = open("..\XML\Spells.xml",'w')
f.write(newdata)
f.close()