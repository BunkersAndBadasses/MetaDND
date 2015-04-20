import re


def writeline(file, string):
    file.write(string)
    file.write('\n')

if __name__ == '__main__':
    i = open('weapontable.txt', 'r')
    m = open('weapondescriptions.txt', 'r')
    o = open('Weapons.xml', 'w')

    writeline(o,'<?xml version="1.0" encoding="UTF-8"?>')
    writeline(o, '<WEAPONS>')

    weaponDict = {}
    name = ''
    for line in m.readlines():
        if line[0] == '^':
            name = line[1:line.index(':')].lower()
            weaponDict[name] = []
        else:
            weaponDict[name].append(line)


    p = re.compile('[A-Za-z,]+')

    category = ''
    name = ''
    for line in i.readlines():

        j = 0;
        s = line.split(' ')
        if len(s) == 1:
            category = line[:-1]
            continue
        writeline(o, '\t<WEAPON>')
        writeline(o, '\t\t<NAME>')
        o.write('\t\t\t' + s[j])
        name = s[j]
        j += 1
        while p.match(s[j]) is not None:
            o.write(' ' + s[j])
            name += ' ' + s[j]
            j += 1
            if s[j] == 'special':
                break
        o.write('\n')
        writeline(o, '\t\t</NAME>')
        writeline(o, '\t\t<TYPE>')
        writeline(o, '\t\t\t' + category)
        writeline(o, '\t\t</TYPE>')
        writeline(o, '\t\t<COST>')
        writeline(o, '\t\t\t' + s[j])
        j += 1
        writeline(o, '\t\t</COST>')
        writeline(o, '\t\t<DAMAGESMALL>')
        writeline(o, '\t\t\t' + s[j])
        j += 1
        writeline(o, '\t\t</DAMAGESMALL>')
        writeline(o, '\t\t<DAMAGEMEDIUM>')
        writeline(o, '\t\t\t' + s[j])
        j += 1
        writeline(o, '\t\t</DAMAGEMEDIUM>')
        writeline(o, '\t\t<CRITICAL>')
        writeline(o, '\t\t\t' + s[j])
        j += 1
        writeline(o, '\t\t</CRITICAL>')
        writeline(o, '\t\t<RANGEINCREMENT>')
        writeline(o, '\t\t\t' + s[j])
        j += 1
        writeline(o, '\t\t</RANGEINCREMENT>')
        writeline(o, '\t\t<WEIGHT>')
        writeline(o, '\t\t\t' + s[j])
        j += 1
        writeline(o, '\t\t</WEIGHT>')
        writeline(o, '\t\t<DAMAGETYPE>')
        writeline(o, '\t\t\t' + s[j][:-1])
        j += 1
        writeline(o, '\t\t</DAMAGETYPE>')
        writeline(o, '\t\t<ISMAGIC>')
        writeline(o, '\t\t\tFalse')
        writeline(o, '\t\t</ISMAGIC>')
        writeline(o, '\t\t<MAGICPROPERTIES>')
        writeline(o, '\t\t\tNone')
        writeline(o, '\t\t</MAGICPROPERTIES>')
        writeline(o, '\t\t<DESCRIPTION>')
        description = ''
        try:
            value = weaponDict[name.lower()]
            for item in value:
                description += item
        except KeyError:
            if 'dagger' in name.lower():
                value = weaponDict['dagger']
                for item in value:
                    description += item
            else:
                pass

        writeline(o, '\t\t\t' + description )
        writeline(o, '\t\t</DESCRIPTION>')

        writeline(o, '\t</WEAPON>')

    writeline(o, '</WEAPONS>')
#    writeline(o, '</xml>')
    i.close()
    o.close()


