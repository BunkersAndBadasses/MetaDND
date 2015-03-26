#Use Python 3.X, tested with 3.4
import re, os

def textFunc():
	global description
	
	for line in inputFile:
		parse = re.search('\w*\[[\w\s]+\]', line)
		if parse:
			outputFile.write("\t\t<NAME>\n")
			outputFile.write("\t\t\t" + line)
			outputFile.write("\t\t</NAME>\n")
			continue
		
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

			if broke:
				continue

			outputFile.write("\t\t<" + tag + ">\n")
			line = re.sub('[\w\s]+[:]\s','',line)
			outputFile.write("\t\t\t" + line)
			outputFile.write("\t\t</" + tag + ">\n")
			
		else:
			parse = re.search('A fighter may[\w\s]+', line)
			if parse:
				outputFile.write("\t\t<FIGHTERBONUS>\n")
				outputFile.write("\t\t\t" + line)
				outputFile.write("\t\t</FIGHTERBONUS>\n")
			else:
				description += "\n\t\t\t" + line
#				outputFile.write("\t\t<DESCRIPTION>\n")
#				outputFile.write("\t\t\t" + line)
#				outputFile.write("\t\t</DESCRIPTION>\n")

#Main begins here
inputFile = open("featsIn.txt")
outputFile = open("tempOut.txt",'w+')
outputFile.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
outputFile.write("<FEATS>\n")
validTags = ["NAME", "DESCRIPTION", "PREREQUISITE", "PREREQUISITES", "SPECIAL", "NORMAL", "FIGHTERBONUS", "BENEFIT"]
while True:
	description = ""
	outputFile.write("\t<FEAT>\n")
	done = textFunc()
	if description:
		outputFile.write("\t\t<DESCRIPTION>\n")
		description = description[:-1]
		outputFile.write("\t\t\t" + description)
		outputFile.write("\n\t\t</DESCRIPTION>\n")
	outputFile.write("\t</FEAT>\n")
	if not done:
		break
outputFile.write("</FEATS>\n")
outputFile.close()

#Pass number 2: Electric Bugaloo
f = open("tempOut.txt",'r')
filedata = f.read()
f.close()
os.remove("tempOut.txt")

newdata = filedata.replace("</DESCRIPTION>\n\t\t<DESCRIPTION>",'')
newdata = filedata.replace("<PREREQUISITES>","<PREREQUISITE>")
newdata = filedata.replace("</PREREQUISITES>","</PREREQUISITE>")
newdata = filedata.replace("\n\n", '\n')
newdata = filedata.replace("\t\t\t\t\t\t", '\t\t\t')
newdata = filedata.replace("\t\t\t\n","")

f = open("..\XML\Feats.xml",'w')
f.write(newdata)
f.close()