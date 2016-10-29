import mysql.connector

conn = mysql.connector.connect(user='root', password='sa', database='score_web')
cursor = conn.cursor()

# mmr mlp
# cursor.execute('select result from mmr_mlp where aspect = 14')
# values = cursor.fetchall()
#
# relative_mmr = 0
# relative_mlp = 0
# relative_equal = 0
#
# plentiful_mmr = 0
# plentiful_mlp = 0
# plentiful_equal = 0
#
# redundant_mmr = 0
# redundant_mlp = 0
# redundant_equal = 0
#
# for item in values:
#     if item[0][0] == '0':
#         relative_equal += 1
#     elif item[0][0] == '1':
#         relative_mmr += 1
#     elif item[0][0] == '2':
#         relative_mlp += 1
#
#     if item[0][2] == '0':
#         plentiful_equal += 1
#     elif item[0][2] == '1':
#         plentiful_mmr += 1
#     elif item[0][2] == '2':
#         plentiful_mlp += 1
#
#     if item[0][4] == '0':
#         redundant_equal += 1
#     elif item[0][4] == '1':
#         redundant_mmr += 1
#     elif item[0][4] == '2':
#         redundant_mlp += 1
#
# print(relative_equal)
# print(relative_mmr)
# print(relative_mlp)
#
# print(plentiful_equal)
# print(plentiful_mmr)
# print(plentiful_mlp)
#
# print(redundant_equal)
# print(redundant_mmr)
# print(redundant_mlp)

# mlp ilp
# cursor.execute('select result from mlp_ilp where aspect = 14')
# values = cursor.fetchall()
#
# relative_ilp = 0
# relative_mlp = 0
# relative_equal = 0
#
# plentiful_ilp = 0
# plentiful_mlp = 0
# plentiful_equal = 0
#
# redundant_ilp = 0
# redundant_mlp = 0
# redundant_equal = 0
#
# for item in values:
#     if item[0][0] == '0':
#         relative_equal += 1
#     elif item[0][0] == '3':
#         relative_ilp += 1
#     elif item[0][0] == '2':
#         relative_mlp += 1
#
#     if item[0][2] == '0':
#         plentiful_equal += 1
#     elif item[0][2] == '3':
#         plentiful_ilp += 1
#     elif item[0][2] == '2':
#         plentiful_mlp += 1
#
#     if item[0][4] == '0':
#         redundant_equal += 1
#     elif item[0][4] == '3':
#         redundant_ilp += 1
#     elif item[0][4] == '2':
#         redundant_mlp += 1
#
# print(relative_equal)
# print(relative_ilp)
# print(relative_mlp)
#
# print(plentiful_equal)
# print(plentiful_ilp)
# print(plentiful_mlp)
#
# print(redundant_equal)
# print(redundant_ilp)
# print(redundant_mlp)


cursor.execute('select result from mmr_ilp where aspect = 14')
values = cursor.fetchall()

relative_ilp = 0
relative_mmr = 0
relative_equal = 0

plentiful_ilp = 0
plentiful_mmr = 0
plentiful_equal = 0

redundant_ilp = 0
redundant_mmr = 0
redundant_equal = 0

for item in values:
    if item[0][0] == '0':
        relative_equal += 1
    elif item[0][0] == '3':
        relative_ilp += 1
    elif item[0][0] == '1':
        relative_mmr += 1

    if item[0][2] == '0':
        plentiful_equal += 1
    elif item[0][2] == '3':
        plentiful_ilp += 1
    elif item[0][2] == '1':
        plentiful_mmr += 1

    if item[0][4] == '0':
        redundant_equal += 1
    elif item[0][4] == '3':
        redundant_ilp += 1
    elif item[0][4] == '1':
        redundant_mmr += 1

print(relative_equal)
print(relative_mmr)
print(relative_ilp)


print(plentiful_equal)
print(plentiful_mmr)
print(plentiful_ilp)


print(redundant_equal)
print(redundant_mmr)
print(redundant_ilp)

