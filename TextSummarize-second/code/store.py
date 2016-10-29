import mysql.connector

conn = mysql.connector.connect(user='root', password='sa', database='score')
cursor = conn.cursor()

id = 0
for i in (1, 6, 11, 12, 16):
    productname = ''
    if i == 1:
        productname = 'iphone 6'
    elif i == 6:
        productname = 'GalaxyNote5'
    elif i == 11:
        productname = 'GALAXY S5'
    elif i == 12:
        productname = 'Galaxy A8'
    elif i == 16:
        productname = '荣耀7'

    # productname = i
    # if i == 2:
    #     continue

    filename = '../data/final_MMR/' + str(i) + '.txt'
    # filename = '../data/MMR_ALL/' + str(i) + '.txt'
    f = open(filename, 'r')
    aspect = 0

    for line in f.readlines():
        string = line.strip()
        if string[0] == '@':
            aspect = int(string[1:-1])
        else:
            id += 1
            cursor.execute(
                "insert into MMR values(%s,%s,%s,%s)",
                [id, productname, aspect, string])

    conn.commit()

cursor.close()
conn.close()

