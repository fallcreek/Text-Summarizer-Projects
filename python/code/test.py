import numpy
import pickle

import mysql.connector
import numpy as np

import random

import theano
import theano.tensor as T

# def updateData():
conn = mysql.connector.connect(user='root', password='sa', database='datasets')
cursor = conn.cursor()

    # cursor.execute('select length,hasname,hasothername,titlewithkeyword,oriknumber,orianumber,orinnumber,oriaspectnumber,result from trainset where id <= 1322')
    # cursor.execute('select length,hasname,hasothername,titlewithkeyword,knumber,anumber,nnumber,aspectnumber,result from trainset')

list=[[1, 2, 3, 4, 5], [1, 2, 3, 5, 4], [1, 2, 4, 3, 5], [1, 2, 4, 5, 3], [1, 2, 5, 3, 4], [1, 2, 5, 4, 3],
    [1, 3, 4, 2, 5], [1, 3, 4, 5, 2], [1, 3, 5, 2, 4], [1, 3, 5, 4, 2], [1, 4, 5, 2, 3], [1, 4, 5, 3, 2],
    [2, 3, 4, 1, 5], [2, 3, 4, 5, 1], [2, 3, 5, 1, 4], [2, 3, 5, 4, 1], [2, 4, 5, 1, 3], [2, 4, 5, 3, 1],
    [3, 4, 5, 1, 2], [3, 4, 5, 2, 1]]

index = 0
for l in list:
    index += 1

    # print(l)
    cursor.execute(
        'select length,hasname,hasothername,titlewithkeyword,oriknumber,orianumber,orinnumber,oriaspectnumber,result from trainset where type = %s or type = %s or type = %s', [l[0], l[1], l[2]])

    train_sets = np.asarray(cursor.fetchall())

    cursor.execute(
        'select length,hasname,hasothername,titlewithkeyword,oriknumber,orianumber,orinnumber,oriaspectnumber,result from trainset where type = %s', [l[3]])

    validate_sets = np.asarray(cursor.fetchall())

    cursor.execute(
        'select length,hasname,hasothername,titlewithkeyword,oriknumber,orianumber,orinnumber,oriaspectnumber,result from trainset where type =%s', [l[4]])

    test_sets = np.asarray(cursor.fetchall())

    train_sets_x = ([[row[i] for i in range(0, 8)] for row in train_sets])
    train_sets_y = ([row[8] for row in train_sets])

    validate_sets_x = ([[row[i] for i in range(0, 8)] for row in validate_sets])
    validate_sets_y = ([row[8] for row in validate_sets])

    test_sets_x = ([[row[i] for i in range(0, 8)] for row in test_sets])
    test_sets_y = ([row[8] for row in test_sets])

    train = (train_sets_x, train_sets_y)
    validate = (validate_sets_x, validate_sets_y)
    test = (test_sets_x, test_sets_y)

    data = (train, validate, test)

    # print(data)

    # print(train_sets)
    # print(train_sets_x)
    # print(train_sets_y)

    filename = '../data/datasets' + str(index) + '.pkl'
    f = open(filename, 'wb')
    pickle.dump(data, f)
    f.close()


    # cursor.execute(
    #     'select length,hasname,hasothername,titlewithkeyword,oriknumber,orianumber,orinnumber,oriaspectnumber,result from trainset where type between 1 and 3')
    #
    # train_sets = np.asarray(cursor.fetchall())
    #
    # cursor.execute(
    #     'select length,hasname,hasothername,titlewithkeyword,oriknumber,orianumber,orinnumber,oriaspectnumber,result from trainset where type =4')
    #
    # validate_sets = np.asarray(cursor.fetchall())
    #
    # cursor.execute(
    #     'select length,hasname,hasothername,titlewithkeyword,oriknumber,orianumber,orinnumber,oriaspectnumber,result from trainset where type =5')
    #
    # test_sets = np.asarray(cursor.fetchall())
    #
    #
    # # random.shuffle(data_sets)
    #
    # # train_sets = data_sets[0:700]
    # # validate_sets = data_sets[700:1000]
    # # test_sets = data_sets[1000:1322]
    #
    # train_sets_x = ([[row[i] for i in range(0, 8)] for row in train_sets])
    # train_sets_y = ([row[8] for row in train_sets])
    #
    # validate_sets_x = ([[row[i] for i in range(0, 8)] for row in validate_sets])
    # validate_sets_y = ([row[8] for row in validate_sets])
    #
    # test_sets_x = ([[row[i] for i in range(0, 8)] for row in test_sets])
    # test_sets_y = ([row[8] for row in test_sets])
    #
    # train = (train_sets_x, train_sets_y)
    # validate = (validate_sets_x, validate_sets_y)
    # test = (test_sets_x, test_sets_y)
    #
    # data = (train, validate, test)
    #
    # # print(data)
    #
    # # print(train_sets)
    # # print(train_sets_x)
    # # print(train_sets_y)
    #
    #
    # f = open('../data/datasets.pkl', 'wb')
    # pickle.dump(data, f)
    # f.close()


#
# rng = numpy.random.RandomState(1234)
#
# train_set_x = numpy.asarray(
#     rng.uniform(
#         low=0,
#         high=2,
#         size=(1000, 10)
#     ),
#     dtype=int
# )
#
# train_set_y = numpy.asarray([x[9] for x in train_set_x])
#
# train_set = (train_set_x, train_set_y)
#
# validate_set_x = numpy.asarray(
#     rng.uniform(
#         low=0,
#         high=2,
#         size=(200, 10)
#     ),
#     dtype=int
# )
#
# validate_set_y = numpy.asarray([x[9] for x in validate_set_x])
#
# validate_set = (validate_set_x, validate_set_y)
#
#
# test_set_x = numpy.asarray(
#     rng.uniform(
#         low=0,
#         high=2,
#         size=(500, 10)
#     ),
#     dtype=int
# )
#
# test_set_y = numpy.asarray([x[9] for x in test_set_x])
#
# test_set = (test_set_x, test_set_y)
#
# data_sets = (train_set, validate_set, test_set)
# #
# # print(train_set_x)
# # print(train_set_y)
#
# f = open('../data/1.pkl', 'wb')
# pickle.dump(data_sets, f)
# f.close()
#

