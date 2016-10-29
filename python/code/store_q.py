import mysql.connector

conn = mysql.connector.connect(user='root', password='sa', database='score')
cursor = conn.cursor()

# for (productname, aspect) in ('iphone 6', 'GalaxyNote5', 'GALAXY S5', 'Galaxy A8', '荣耀7'), range(1, 15):
#     print(productname)
#     print(aspect)

id = 0
for productname in ('iphone 6', 'GalaxyNote5', 'GALAXY S5', 'Galaxy A8', '荣耀7'):
    for aspect in range(1, 15):
        cursor.execute(
            "select text from MMR where productname = %s and aspect = %s",
            [productname, aspect])
        MMR = cursor.fetchall()

        cursor.execute(
            "select text from MLP where productname = %s and aspect = %s",
            [productname, aspect])
        MLP = cursor.fetchall()

        cursor.execute(
            "select text from ILP where productname = %s and aspect = %s",
            [productname, aspect])
        ILP = cursor.fetchall()

        s_mmr = 'a1:'
        for s in MMR:
            s_mmr += s[0]

        s_mlp = 'a2:'
        for s in MLP:
            s_mlp += s[0]

        s_ilp = 'a3:'
        for s in ILP:
            s_ilp += s[0]

        id += 1
        cursor.execute(
            "insert into question values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)",
            [id, productname, aspect, s_mmr, s_mlp, '', -1, -1, -1, -1, -1, -1])
        id += 1
        cursor.execute(
            "insert into question values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)",
            [id, productname, aspect, s_mmr, '', s_ilp, -1, -1, -1, -1, -1, -1])
        id += 1
        cursor.execute(
            "insert into question values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)",
            [id, productname, aspect, '', s_mlp, s_ilp, -1, -1, -1, -1, -1, -1])

conn.commit()
cursor.close()
conn.close()
