#Use Python 3.X, tested with 3.4
import re, os

def textFunc():
	spellName = inputFile.readline()	
	outputFile.write("\t\t<NAME>\n")
	outputFile.write("\t\t\t" + spellName.title())
	outputFile.write("\t\t</NAME>\n")
	spellSchool = inputFile.readline()
	outputFile.write("\t\t<TYPE>\n")
	outputFile.write("\t\t\t" + spellSchool)
	outputFile.write("\t\t</TYPE>\n")
	
	tagsDone = False
	for line in inputFile:
		if line is '\n':
			return True
		if  not line:
			return False
		parse = re.search('[\w\s]+[:]', line)
		if parse and tagsDone == False:
			tag = parse.group(0).upper()[:-1]
			tag = re.sub(' +','', tag)

			outputFile.write("\t\t<" + tag + ">\n")
			line = re.sub('[\w\s]+[:]\s','',line)
			outputFile.write("\t\t\t" + line)
			outputFile.write("\t\t</" + tag + ">\n")
			for i in range(0, len(validTags)):
				temp = tag
				if tag not in validTags:
					tag = "DESCRIPTION"
			if tag == "LEVELADJUSTMENT":
				tagsDone = True		
		else:
			outputFile.write("\t\t<DESCRIPTION>\n")
			outputFile.write("\t\t\t" + line)
			outputFile.write("\t\t</DESCRIPTION>\n")

#Main begins here
inputFile = open("monstersIn.txt")
outputFile = open("tempOut.txt",'w+')
outputFile.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
outputFile.write("<MONSTERS>\n")
validTags = ["NAME", "TYPE", "LEVEL", "HITDICE", "SKILLS", "DAMAGE", "INITIATIVE", "SPEED", "ARMORCLASS", "GRAPPLE", "DESCRIPTION", "ATTACK", "FULLATTACK", "REACH", "SPECIALATTACKS", "SPECIALQUALITIES", "SAVES", "ABILITIES", "SKILLS", "FEATS", "ORGANIZATION", "CHALLEGENGERATING", "TREASURE", "ALIGNMENT", "ADVANCEMENT", "LEVELADJUSTMENT", "ENVIRONMENT"]
while True:
	outputFile.write("\t<MONSTER>\n")
	done = textFunc()
	outputFile.write("\t</MONSTER>\n")
	if not done:
		break
outputFile.write("</MONSTERS>\n")
outputFile.close()

#Pass number 2: Electric Bugaloo
f = open("tempOut.txt",'r')
filedata = f.read()
f.close()
os.remove("tempOut.txt")

newdata = filedata.replace("</DESCRIPTION>\n\t\t<DESCRIPTION>",'')

f = open("Monsters.xml",'w')
f.write(newdata)
f.close()