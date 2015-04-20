import re


def writeline(file, string):
    file.write(string)
    file.write('\n')

if __name__ == '__main__':
    i = open('armorTable.txt', 'r')
    m = open('armorDescription.txt', 'r')
    o = open('Armor.xml', 'w')

    writeline(o,'<?xml version="1.0" encoding="UTF-8"?>')
    writeline(o, '<ARMORS>')

    armorDict = {}
    name = ''
    for line in m.readlines():
        if line[0] == '^':
            name = line[1:line.index(':')].lower()
            armorDict[name] = []
        else:
            armorDict[name].append(line)

    p = re.compile('[A-Za-z]+')


    name = ''
    for line in i.readlines():
        j = 0;
        s = line.split(' ')
        writeline(o, '\t<ARMOR>')
        writeline(o, '\t\t<NAME>')
        o.write('\t\t\t' + s[j])
        name = s[j]
        j += 1
        while p.match(s[j]) is not None:
            o.write(' ' + s[j])
            name += ' ' + s[j]
            j += 1
        o.write('\n')
        writeline(o, '\t\t</NAME>')
        writeline(o, '\t\t<COST>')
        writeline(o, '\t\t\t' + s[j])
        j += 1
        writeline(o, '\t\t</COST>')
        writeline(o, '\t\t<ARMORBONUS>')
        writeline(o, '\t\t\t' + s[j])
        j += 1
        writeline(o, '\t\t</ARMORBONUS>')
        writeline(o, '\t\t<MAXDEXBONUS>')
        writeline(o, '\t\t\t' + s[j])
        j += 1
        writeline(o, '\t\t</MAXDEXBONUS>')
        writeline(o, '\t\t<ARMORCHECKPENALTY>')
        writeline(o, '\t\t\t' + s[j])
        j += 1
        writeline(o, '\t\t</ARMORCHECKPENALTY>')
        writeline(o, '\t\t<ARCANESPELLFAILURECHANCE>')
        writeline(o, '\t\t\t' + s[j])
        j += 1
        writeline(o, '\t\t</ARCANESPELLFAILURECHANCE>')
        writeline(o, '\t\t<ISMAGIC>')
        writeline(o, '\t\t\tFalse')
        writeline(o, '\t\t</ISMAGIC>')
        writeline(o, '\t\t<MAGICPROPERTIES>')
        writeline(o, '\t\t\tNone')
        writeline(o, '\t\t</MAGICPROPERTIES>')
        writeline(o, '\t\t<DESCRIPTION>')
        description = ''
        try:
            value = armorDict[name.lower()]
            for item in value:
                description += item
        except KeyError:
            print(name.lower())
        writeline(o, '\t\t\t' + description )
        writeline(o, '\t\t</DESCRIPTION>')
        writeline(o, '\t</ARMOR>')

    writeline(o, '</ARMORS>')
#    writeline(o, '</xml>')
    i.close()
    o.close()


