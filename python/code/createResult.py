import mysql.connector

conn = mysql.connector.connect(user='root', password='sa', database='datasets')
cursor = conn.cursor()



for name in range(1, 22):

    outputfilename = '../data/output/' + str(name) + '.txt'
    f = open(outputfilename, 'w')

    if name < 10:
        phonename = '0000100' + str(name)
    else:
        phonename = '000010' + str(name)

    for asp in range(1, 15):
        cursor.execute(
            "select text from rawset where phonename = %s and aspectname = %s and predict = 1",
            [phonename, asp])

        f.write("aspect:%d------------" % asp)
        f.write('\n')
        for s in cursor.fetchall():
            # print(s[0])
            f.write(s[0] + '\n')

cursor.close()
conn.close()

