import mysql.connector


conn = mysql.connector.connect(user='root', password='sa', database='datasets')
cursor = conn.cursor()

cursor.execute('select text,oriaspectnumber from trainset where id between 2074 and 2394')
values = cursor.fetchall()

index = 2074
for score, number in values:
    print(score)
    print(number)
    tag = input()
    cursor.execute('update trainset set result = %s where id = %s', [tag, index])
    index += 1
    conn.commit()



cursor.close()
conn.close()
