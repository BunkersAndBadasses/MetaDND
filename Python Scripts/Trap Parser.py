#Use Python 3.X, tested with 3.4
import re, os

def textFunc():
	
	for line in inputFile:
		if line is '\n':
			return True
		if  not line:
			return False
		parse = re.search('[\w\s]+[:]', line)
		if parse:
			text = parse.group(0)[:-1]
			tag = "NAME"
			outputFile.write("\t\t<" + tag + ">\n\t\t\t" + text + "\n\t\t</" + tag + ">\n")
			remain = re.sub('[\w\s]+[:]',"", line)
			fields = remain.split('; ')
			for value in fields:
				cr = re.search("CR\s[0-9]+", value)
				trapType = re.compile("mechanical|magic device")
				trigger = re.search("trigger", value)
				reset = re.search("reset", value)
				damage = re.search("[0-9]+d[0-9]+", value)
				searchDC = re.search("Search DC", value)
				bypass = re.search("bypass", value)
				disable = re.search("Disable", value)
				price = re.search("[0-9]+[,]*[0-9]* gp", value)
				target = re.search("target", value)
				save = re.search("save", value)
				spellEffect = re.search("spell effect", value)
				neverMiss = re.search("never miss", value)
				delay = re.search("delay", value)
				attack = re.search("Atk", value)

				if cr:
					tag = "CHALLENGERATING"
					rating = re.search("[0-9]+", value)
					outputFile.write("\t\t<" + tag + ">\n\t\t\t" + rating.group(0) + "\n\t\t</" + tag + ">\n")
					continue
				if trapType.match(value):
					tag = "TYPE"
					outputFile.write("\t\t<" + tag + ">\n\t\t\t" + value.title() + "\n\t\t</" + tag + ">\n")
					continue
				if trigger:
					tag = "TRIGGER"
					outputFile.write("\t\t<" + tag + ">\n\t\t\t" + value.title() + "\n\t\t</" + tag + ">\n")
					continue
				if reset:
					tag = "RESET"
					outputFile.write("\t\t<" + tag + ">\n\t\t\t" + value.title() + "\n\t\t</" + tag + ">\n")
					continue
				if bypass:
					tag = "BYPASS"
					value = value[0].upper() + value[1:]
					outputFile.write("\t\t<" + tag + ">\n\t\t\t" + value + "\n\t\t</" + tag + ">\n")
					continue
				if damage:
					tag = "DAMAGE"
					value = value[0].upper() + value[1:]
					outputFile.write("\t\t<" + tag + ">\n\t\t\t" + value + "\n\t\t</" + tag + ">\n")
					continue
				if searchDC:
					tag = "SEARCHDC"
					rating = re.search("[0-9]+", value)
					outputFile.write("\t\t<" + tag + ">\n\t\t\t" + rating.group(0) + "\n\t\t</" + tag + ">\n")
					continue
				if disable:
					tag = "DISABLEDC"
					rating = re.search("[0-9]+", value)
					outputFile.write("\t\t<" + tag + ">\n\t\t\t" + rating.group(0) + "\n\t\t</" + tag + ">\n")
					continue
				if price:
					tag = "VALUE"
					outputFile.write("\t\t<" + tag + ">\n\t\t\t" + value.strip() + "\n\t\t</" + tag + ">\n")
					continue
				if target:
					tag = "TARGET"
					value = value[0].upper() + value[1:]
					outputFile.write("\t\t<" + tag + ">\n\t\t\t" + value + "\n\t\t</" + tag + ">\n")
					continue
				if save:
					tag = "SAVE"
					outputFile.write("\t\t<" + tag + ">\n\t\t\t" + value + "\n\t\t</" + tag + ">\n")
					continue
				if spellEffect:
					tag = "SPELLEFFECT"
					value = value[0].upper() + value[1:]
					outputFile.write("\t\t<" + tag + ">\n\t\t\t" + value + "\n\t\t</" + tag + ">\n")
					continue
				if neverMiss:
					tag = "NEVERMISS"
					outputFile.write("\t\t<" + tag + ">\n\t\t\t" + "TRUE" + "\n\t\t</" + tag + ">\n")
					continue
				if delay:
					tag = "DELAY"
					value = value[0].upper() + value[1:]
					outputFile.write("\t\t<" + tag + ">\n\t\t\t" + value + "\n\t\t</" + tag + ">\n")
					continue
				if attack:
					tag = "ATTACK"
					value = value[0].upper() + value[1:]
					outputFile.write("\t\t<" + tag + ">\n\t\t\t" + value + "\n\t\t</" + tag + ">\n")
					continue

#Main begins here
inputFile = open("trapsIn.txt")
outputFile = open("tempOut.txt",'w+')
outputFile.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
outputFile.write("<TRAPS>\n")
while True:
	outputFile.write("\t<TRAP>\n")
	done = textFunc()
	outputFile.write("\t</TRAP>\n")
	if not done:
		break
outputFile.write("</TRAPS>\n")
outputFile.close()

#Pass number 2: Electric Bugaloo
f = open("tempOut.txt",'r')
filedata = f.read()
f.close()
os.remove("tempOut.txt")

newdata = filedata.replace("</DESCRIPTION>\n\t\t<DESCRIPTION>",'')

f = open("Traps.xml",'w')
f.write(newdata)
f.close()