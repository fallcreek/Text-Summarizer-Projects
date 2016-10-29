import mysql.connector

conn = mysql.connector.connect(user='root', password='sa', database='score')
cursor = conn.cursor()

for i in range(1, 22):
    filename = '../data/MMR_14/' + str(i) + '.txt'
    f = open(filename, 'w')

    for asp in range(1, 15):
        cursor.execute(
            "select text from MMR_ALL where productname = %s and aspect = %s",
            [i, asp])
        result = cursor.fetchall()

        aspline = "@" + str(asp) + ":"
        f.write(aspline)
        f.write('\n')

        for sentence in result:
            # print(sentence[0])
            f.write(sentence[0])
            f.write('\n')

    f.close()