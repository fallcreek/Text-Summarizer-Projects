import mysql.connector
import numpy as np

conn = mysql.connector.connect(user='root', password='sa', database='datasets')
cursor = conn.cursor()
import random

cursor.execute('select id from trainset')

data = cursor.fetchall()
# print(data)

random.shuffle(data)
# print(data)

data_sets = np.asarray(data)
# print(data_sets)
#
set1 = data_sets[0:480]
set2 = data_sets[480:960]
set3 = data_sets[960:1440]
set4 = data_sets[1440:1920]
set5 = data_sets[1920:2394]

for r in set1:
    cursor.execute('update trainset set type = %s where id = %s', [1, int(r[0])])

for r in set2:
    cursor.execute('update trainset set type = %s where id = %s', [2, int(r[0])])

for r in set3:
    cursor.execute('update trainset set type = %s where id = %s', [3, int(r[0])])

for r in set4:
    cursor.execute('update trainset set type = %s where id = %s', [4, int(r[0])])

for r in set5:
    cursor.execute('update trainset set type = %s where id = %s', [5, int(r[0])])


conn.commit()
