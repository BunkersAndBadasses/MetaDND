__author__ = 'Josh'

import re, copy

a = open('alldragons.txt', 'r')
d = open('dragon.txt', 'r')
o = open('dragons.xml', 'w')

description = ""
allCombat = ""
dragonBreathDict = {}
abilities = []
agesDict = {}
attacksDict = {}
movement = ""
toWriteDict = {}
fullInfoDict = {}
fullAbilitiesDict = {}
infoDict = {}
abilitiesDict = {}
dragonInfoDict = {}
challengeRatingDict  = {}
organizationDict = {}
levelAdjustmentDict = {}
combat = ""



def writeline(file, string):
    file.write(string)
    file.write('\n')

def fillXMLInfo(name, toWrite, infoDict, abilitiesDict):
    for k in toWrite.keys():
        toWrite[k] += "\t\t<HITDICE>"
        toWrite[k] += "\t\t\t" + infoDict[k][1] + " " + infoDict[k][2]
        toWrite[k] += "\t\t</HITDICE>"
        toWrite[k] += "\t\t<INITIATIVE>"
        if "Red" in name or "Copper" in name:
            toWrite[k] += "\t\t\t" + abilitiesDict[k][4]
        elif "White" in name:
            toWrite[k] += "\t\t\t" + abilitiesDict[k][8]
        else:
            toWrite[k] += "\t\t\t" + abilitiesDict[k][6]
        toWrite[k] += "\t\t</INITIATIVE>"
        toWrite[k] += "\t\t<SPEED>"
        if "Red" in name or "Copper" in name:
            toWrite[k] += "\t\t\t" + abilitiesDict[k][0] + ' ' + abilitiesDict[k][1] + ' '
            toWrite[k] += abilitiesDict[k][2]  + ' ' + abilitiesDict[k][3]
        elif "White" in name:
            toWrite[k] += "\t\t\t" + abilitiesDict[k][0]  + ' ' + abilitiesDict[k][1] + ' '
            toWrite[k] += abilitiesDict[k][2] + ' ' + abilitiesDict[k][3] + ' '
            toWrite[k] += abilitiesDict[k][4] + ' ' + abilitiesDict[k][5] + ' '
        else:
            toWrite[k] += "\t\t\t" + abilitiesDict[k][0] + ' ' + abilitiesDict[k][1] + ' '
            toWrite[k] += abilitiesDict[k][2] + ' ' + abilitiesDict[k][3] + ' '
            toWrite[k] += abilitiesDict[k][4] + ' ' + abilitiesDict[k][5]
        toWrite[k] += "\t\t</SPEED>"
        toWrite[k] += "\t\t<ARMORCLASS>"
        if "Red" in name or "Copper" in name:
            toWrite[k] += "\t\t\t" + abilitiesDict[k][5] + ' ' + abilitiesDict[k][6] + ' '
            toWrite[k] += abilitiesDict[k][7]  + ' ' + abilitiesDict[k][8]
            toWrite[k] += abilitiesDict[k][9] + ' ' + abilitiesDict[k][10] + ' '
            toWrite[k] += abilitiesDict[k][11] + ' ' + abilitiesDict[k][12] + ' '
            toWrite[k] += abilitiesDict[k][13]
        elif "White" in name:
            toWrite[k] += "\t\t\t" + abilitiesDict[k][9] + ' ' + abilitiesDict[k][10] + ' '
            toWrite[k] += abilitiesDict[k][11] + ' ' + abilitiesDict[k][12] + ' '
            toWrite[k] += abilitiesDict[k][13] + ' ' + abilitiesDict[k][14] + ' '
            toWrite[k] += abilitiesDict[k][15] + ' ' + abilitiesDict[k][16] + ' '
            toWrite[k] += abilitiesDict[k][17]
        elif "Black" in name:
            toWrite[k] += "\t\t\t" + abilitiesDict[k][7] + ' ' + abilitiesDict[k][8] + ' '
            toWrite[k] += abilitiesDict[k][9] + ' ' + abilitiesDict[k][10] + ' '
            toWrite[k] += abilitiesDict[k][11] + ' ' + abilitiesDict[k][12] + ' '
            toWrite[k] += abilitiesDict[k][13] + ' ' + abilitiesDict[k][14]
        else:
            toWrite[k] += "\t\t\t" + abilitiesDict[k][7] + ' ' + abilitiesDict[k][8] + ' '
            toWrite[k] += abilitiesDict[k][9] + ' ' + abilitiesDict[k][10] + ' '
            toWrite[k] += abilitiesDict[k][11] + ' ' + abilitiesDict[k][12] + ' '
            toWrite[k] += abilitiesDict[k][13] + ' ' + abilitiesDict[k][14] + ' '
            toWrite[k] += abilitiesDict[k][15]
        toWrite[k] += "\t\t</ARMORCLASS>"
        toWrite[k] += "\t\t<GRAPPLE>"
        toWrite[k] += "\t\t\t" + infoDict[k][9]
        toWrite[k] += "\t\t</GRAPPLE>"
        toWrite[k] += "\t\t<ATTACK>"
        s = attacksDict[infoDict[k][0]].split(')')

        t = s[1].split(' ')
        toWrite[k] += "\t\t\t" + "1 Bite: " + t[0]
        toWrite[k] += "\t\t\t" + "2 Claws: " + t[1]
        toWrite[k] += "\t\t\t" + "2 Wings: " + t[2]
        toWrite[k] += "\t\t\t" + "1 Tail Slap: " + t[3]
        toWrite[k] += "\t\t\t" + "1 Crush: " + t[4]
        toWrite[k] += "\t\t\t" + "1 Tail Sweep: " + t[5]
        toWrite[k] += "\t\t</ATTACK>"
        toWrite[k] += "\t\t<FULLATTACK>"
        toWrite[k] += "\t\t</FULLATTACK>"
        toWrite[k] += "\t\t<REACH>"
        toWrite[k] += "\t\t\t" + s[0] + ')'
        toWrite[k] += "\t\t</REACH>"
        toWrite[k] += "\t\t<SPECIALATTACKS>"
        toWrite[k] += "\t\t\t" + combat + '\n'
        toWrite[k] += "\t\t</SPECIALATTACKS>\n"

        toWrite[k] += "\t\t<SAVES>"
        toWrite[k] += '\t\t\t' + infoDict[k][11] + ' ' + infoDict[k][12] + ' '
        toWrite[k] += infoDict[k][13]
        toWrite[k] += "\t\t</SAVES>\n"
        toWrite[k] += "\t\t<ABILITIES>"
        toWrite[k] += '\t\t\tStr ' + infoDict[k][3] + ' Dex ' + infoDict[k][4] + ' '
        toWrite[k] += 'Con ' + infoDict[k][5] + ' Int ' + infoDict[k][6] + ' '
        toWrite[k] += 'Wis ' + infoDict[k][7] + ' Cha ' + infoDict[k][8]
        toWrite[k] += "\t\t</ABILITIES>\n"
        toWrite[k] += "\t\t<SKILLS>"
        toWrite[k] += "\t\t\t" + abilities[8]
        toWrite[k] += "\t\t</SKILLS>\n"
        toWrite[k] += "\t\t<FEATS>"
        toWrite[k] += "\t\t\t" + abilities[9]
        toWrite[k] += "\t\t</FEATS>"
        toWrite[k] += "\t\t<ENVIRONMENT>"
        toWrite[k] += "\t\t\t" + dragonInfoDict["Environment"]
        toWrite[k] += "\t\t</ENVIRONMENT>"
        tmp = dragonInfoDict["Organization"].split(';')
        first = tmp[0].split(':')
        second = tmp[1].split(':')
        firstTypes = first[0].split(',')
        secondTypes = second[0].split(',')
        for item in firstTypes:
            organizationDict[item[1:]] = first[1]
        for item in secondTypes:
            organizationDict[item[1:]] = second[1]
        toWrite[k] += "\t\t<ORGANIZATION>"
        toWrite[k] += "\t\t\t" + organizationDict[k]
        toWrite[k] += "\t\t</ORGANIZATION>"
        tmp = dragonInfoDict["Challenge Rating"].split(';')
        for item in tmp:
            s = item.split(' ')
            i = 1
            key = ''
            while alphanumeric.match(s[i]):
                key += s[i] + ' '
                i += 1
            key = key[:-1]
            challengeRatingDict[key] = s[i]
        toWrite[k] += "\t\t<CHALLENGERATING>"
        toWrite[k] += "\t\t\t" + challengeRatingDict[k]
        toWrite[k] += "\t\t</CHALLENGERATING>"
        toWrite[k] += "\t\t<TREASURE>"
        toWrite[k] += "\t\t\t" + dragonInfoDict["Treasure"]
        toWrite[k] += "\t\t</TREASURE>"
        toWrite[k] += "\t\t<ALIGNMENT>"
        toWrite[k] += "\t\t\t" + dragonInfoDict["Alignment"]
        toWrite[k] += "\t\t</ALIGNMENT>"
        tmp = dragonInfoDict["Advancement"].split(';')
        for item in tmp:
            s = item.split(' ')
            i = 1
            key = ''
            while alphanumeric.match(s[i]):
                key += s[i] + ' '
                i += 1
            key = key[:-1]
            challengeRatingDict[key] = s[i]
        toWrite[k] += "\t\t<ADVANCEMENT>"
        toWrite[k] += "\t\t\t" + challengeRatingDict[k]
        toWrite[k] += "\t\t</ADVANCEMENT>"
        tmp = dragonInfoDict["Level Adjustment"].split(';')
        for item in tmp:
            s = item.split(' ')
            i = 1
            key = ''
            while alphanumeric.match(s[i]):
                key += s[i] + ' '
                i += 1
            key = key[:-1]
            levelAdjustmentDict[key] = s[i]
        toWrite[k] += "\t\t<LEVELADJUSTMENT>"
        try:
            toWrite[k] += "\t\t\t" + levelAdjustmentDict[k]
        except KeyError:
            toWrite[k] += "\t\t\t-"
        toWrite[k] += "\t\t</LEVELADJUSTMENT>"
        toWrite[k] += "\t\t<DESCRIPTION>"
        toWrite[k] += description
        toWrite[k] += allCombat
        toWrite[k] += movement
        toWrite[k] += "\t\t</DESCRIPTION>"
    for key, value in toWrite.items():
        writeline(o, '<MONSTER>')
        writeline(o, toWrite[key])
        writeline(o, '</MONSTER>')






if __name__ == '__main__':


    writeline(o,'<?xml version="1.0" encoding="UTF-8"?>')





    alphanumeric = re.compile("[A-Za-z]+")
    lowercase = re.compile("[a-z]+")

    descriptionBool = False
    combatBool = False
    dragonBreathDictBool = False
    abilitiesBool = False
    agesBool = False
    attacksBool = False
    movementBool = False
    for line in a.readlines():
        if "DESCRIPTION" in line:
            descriptionBool = True
            continue
        if "COMBAT" in line:
            combatBool = True
            descriptionBool = False
            continue
        if "DRAGONBREATH" in line:
            dragonBreathDictBool = True
            combatBool = False
            continue
        if "ABILITIES" in line:
            abilitiesBool = True
            dragonBreathDictBool = False
            continue
        if "AGES" in line:
            agesBool = True
            abilitiesBool = False
            continue
        if "ATTACKS" in line:
            attacksBool = True
            agesBool = False
            continue
        if "MOVEMENT" in line:
            movementBool = True
            attacksBool = False
            continue
        if descriptionBool:
            description += line
        elif combatBool:
            allCombat += line
        elif dragonBreathDictBool:
            s = line.split(" ")
            dragonBreathDict[s[0]] = s[1] + " " + s[2]
        elif abilitiesBool:
            abilities.append(line)
        elif agesBool:
            s = line.split(" ")
            key = ""
            i = 0
            while alphanumeric.match(s[i]) != None:
                key += s[i]
                i += 1
                key += " "
            key = key[:-1]
            agesDict[key] = s[i]
        elif attacksBool:
            s = line.split(" ")
            value = ""
            for element in s[1:]:
                value += element + " "
            value = value[:-1]
            attacksDict[s[0]] = value
        elif movementBool:
            movement += line

    combatBool = False
    dragonBool = False
    tableBool = False
    abilitiesBool = False
    name = ""
    color = ""
    counter =  0
    dragonCount = 0
    for line in d.readlines():
        if "DRAGON" in line:
            fullAbilitiesDict = copy.deepcopy(abilitiesDict)
            fullInfoDict = copy.deepcopy(infoDict)
            if dragonCount > 0:
                fillXMLInfo(name, toWriteDict, fullInfoDict, fullAbilitiesDict)

            s = line.split(" ")
            color = s[0].lower()
            color = color[0].upper() + color[1:]
            name = color + " Dragon, "


            for k,v in agesDict.items():
                toWriteDict[k] = ""

            infoDict = {}
            abilitiesDict = {}
            dragonBool = True
            dragonCount += 1
            counter = 0
            continue
        if "TABLES" in line:
            tableBool = True
            dragonBool = False
            counter = 0
            continue
        if "ABILITIES" in line:
            abilitiesBool = True
            tableBool = False
            counter = 0
            continue
        if 'COMBAT' in line:
            abilitiesBool = False
            combatBool = True
            counter = 0
            continue
        if dragonBool:
            trimmedLine = ""
            if len(line) > 1 and counter > 0:
                trimmedLine = line[line.index(': ')+1:]
            if counter == 0:
                for k in agesDict.keys():
                    toWriteDict[k] += "\t\t<NAME>"
                    toWriteDict[k] += "\t\t\t" + name + k
                    toWriteDict[k] += "\t\t</NAME>"
                for k in agesDict.keys():
                    toWriteDict[k] += "\t\t<TYPE>"
                    toWriteDict[k] += "\t\t\t" + line
                    toWriteDict[k] += "\t\t</TYPE>"
            elif counter == 1:
                dragonInfoDict["Environment"] = trimmedLine
            elif counter == 2:
                dragonInfoDict["Organization"] = trimmedLine
            elif counter == 3:
                dragonInfoDict["Challenge Rating"] = trimmedLine
            elif counter == 4:
                dragonInfoDict["Treasure"] = trimmedLine
            elif counter == 5:
                dragonInfoDict["Alignment"] = trimmedLine
            elif counter == 6:
                dragonInfoDict["Advancement"] = trimmedLine
            elif counter == 7:
                dragonInfoDict["Level Adjustment"] = trimmedLine

            counter += 1
        elif tableBool:
            s = line.split(' ')
            i = 0
            key = ""
            s[0] = s[0].lower()
            while lowercase.match(s[i]) is not None:
                key += s[i]
                i += 1
                key += " "
            if key is "":
                continue
            key = key[:-1]
            key = key[0].upper() + key[1:]
            infoDict[key] = s[i:]
        elif abilitiesBool:
            s = line.split(' ')
            i = 0
            key = ""
            while alphanumeric.match(s[i]) != None:
                key += s[i]
                i += 1
                key += " "
            key = key[:-1]
            abilitiesDict[key] = s[i:]
        elif combatBool:
            combat += line
    writeline(o, '</MONSTERS>')


