import mysql.connector
import numpy as np

conn = mysql.connector.connect(user='root', password='sa', database='datasets')
cursor = conn.cursor()

cursor.execute('select oriaspectnumber from rawset')
values = cursor.fetchall()
print(values)
x = np.asarray(values)
x_mean = x.mean()
z_scores_np = (x - x.mean()) / x.std()

index = 1
for score in z_scores_np:
    print(float(score[0]))
    cursor.execute('update rawset set aspectnumber = %s where id = %s', [float(score[0]), index])
    index += 1


print(z_scores_np)
conn.commit()
cursor.close()
conn.close()
