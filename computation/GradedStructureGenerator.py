


"""
Generates a csv file for a solar cell composed of 3 layers of  P3HT, 1 layer of 2:1 P3HT:PCBM, 4 layers of 1:1
P3HT:PCBM (2 with import and 2 with import2), 1 layer of 1:2 P3HT:PCBM,
and 3 layers of PCBM, for a coarsely graded "sandwich" structure
"""
import os
from sys import argv


import pandas as pd
from multiprocessing import Pool, Process,Manager,Array
import matplotlib.pyplot as plt
#plt.use("TkAgg")

import matplotlib.patches as mpatches

from mpl_toolkits import mplot3d
from mpl_toolkits.mplot3d import Axes3D
import numpy as np
import matplotlib.colors as mcolors
import time

import Constants

"""
import2_mctotal.csv = 100% acceptor (represented by all red)
import2_mc15.csv = 3:1 (acceptor : donor)
import2_mc7.csv = 2:1
import2_mc3.csv = 1:1
import_mc3.csv = 1:1
import_mc7.csv = 1:2
import_mc15.csv = 1:3
import_mctotal.csv = 100% donor (represented by all blue)

c stands for the charge and z stands for the z axis in the following code
"""


class GradedStructureGenerator:
    # paths for donor/acceptor ratio

    def __init__(self, process_id, ratio_and_layer, working_directory):
        self.working_directory=working_directory
        self.process_id = process_id # process name
        self.imported_csv_files={}
        self.ratio_and_layer=ratio_and_layer
        self.ratio_and_layer.append("code_file") # to import the file used by the codebase
        self.read_csv_files() # imports csv files that defines properties for the ratio

    def getChargeForLayeredFile(self,layer_num):
        return self.imported_csv_files[self.ratio_and_layer[layer_num]][3]
    def getZValues(self):
        return self.imported_csv_files[self.ratio_and_layer[12]][2]
    def getCodeFile(self):
        return self.imported_csv_files[self.ratio_and_layer[12]]

    def read_csv_files(self):
        start_time = time.time()
        for ratio_value, file_path in Constants.RATIO_PATH_MAP.items():
            if ratio_value not in self.imported_csv_files:
                self.imported_csv_files[ratio_value]=pd.read_csv(file_path, header=None)
        print("pd.read_csv took %s seconds to load csv files" % (time.time() - start_time))

def generate_structure(working_directory):

    processes=[]
    #os_count=os.cpu_count()*2 # it's taking longer than single process
    os_count=1
    chunks_size=600000//os_count # floor division
    idx={}
    for i in range(os_count):
        start_row=i*chunks_size
        end_row=chunks_size+i*chunks_size
        idx['start']=start_row
        if(i==os_count-1):
            end_row=600000
        idx['end']=end_row
        process=Process(target=add_c_to_structure,args=(idx,c,z,[c0,c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11]))
        processes.append(process)
        process.start()

    for process in processes:
        process.join()
    code_file[3] = c

    # checking csv files
    file_id=-1
    for file in os.listdir(working_directory):
        if file.endswith(".csv"):
            arr=file.split("_")
            if(len(arr)==3):
                temp_id=int(arr[1])
                if(temp_id>file_id):
                    file_id=temp_id
    if(file_id==-1):
        file_id=0
    else:
        file_id=1

    file_name = "structure_"+str(file_id)+"_.csv"
    code_file.to_csv(os.path.join(working_directory, file_name), header=False, index=False)

    fig = plt.figure(figsize=(5,4))
    ax = fig.add_subplot(111, projection='3d')
    ax.scatter(code_file[0], code_file[1], code_file[2],s=10,c=code_file[3],
               cmap=mcolors.ListedColormap(["blue","red"]))
    classes = ['Acceptor','Donor']
    class_colours = ['r','b']
    recs = []
    for i in range(0,len(class_colours)):
        recs.append(mpatches.Rectangle((0,0),1,1,fc=class_colours[i]))
    plt.legend(recs,classes,loc=1)
    plt.title("structure_"+str(file_id))
    plt.savefig(os.path.join(working_directory, "structure_"+str(file_id)+".png"))
    plt.show()

def add_c_to_structure(idx,c,z,charges):
    #
    row_start=idx['start']
    row_end=idx['end']
    print("start "+str(row_start))
    print("end "+str(row_end))
    start_time = time.time()
    for m in range(row_start, row_end):
        if(z[m] >= 0 and z[m] < 5):
            c[m] = charges[11][m]
        elif (z[m] >= 5 and z[m] < 10):
            c[m] = charges[10][m]
        elif (z[m] >= 10 and z[m] < 15):
            c[m] = charges[9][m]
        elif (z[m] >= 15 and z[m] < 20):
            c[m] = charges[8][m]
        elif (z[m] >= 20 and z[m] < 25):
            c[m] = charges[7][m]
        elif (z[m] >= 25 and z[m] < 30):
            c[m] = charges[6][m]
        elif (z[m] >= 30 and z[m] < 35):
            c[m] = charges[5][m]
        elif (z[m] >= 35 and z[m] < 40):
            c[m] = charges[4][m]
        elif (z[m] >= 40 and z[m] < 45):
            c[m] = charges[3][m]
        elif (z[m] >= 45 and z[m] < 50):
            c[m] = charges[2][m]
        elif (z[m] >= 50 and z[m] < 55):
            c[m] = charges[1][m]
        elif (z[m] >= 55 and z[m] <= 60):
            c[m] = charges[0][m]

    print("add charge to the str took %s seconds" % (time.time() - start_time))

if __name__ == '__main__':
    print(argv[0])
    print(argv[1])

    print("Started generation str")
    global c0
    global c1
    global c2
    global c3
    global c4
    global c5
    global c6
    global c7
    global c8
    global c9
    global c10
    global c11
    global c
    global z
    global code_file
    path="/Users/abebeamare/Desktop/Desktop/spring2021/ECE493/FinalProject/src/data"
    #ratio_path=["0:1","0:1","0:1","1:2","1:1_","1:1_","1:1","1:1","2:1","1:0","1:0","1:0"]
    ratio_path=[argv[1],argv[2],argv[3],argv[4],argv[5],argv[6],argv[7],argv[8],argv[9],argv[10],argv[11],argv[12]];
    working_path=argv[13]

    process1 = GradedStructureGenerator(25, ratio_path, working_path)
    c = Array('i',np.zeros(600000,'i'))
    c0 =process1.getChargeForLayeredFile(0)
    c1 =process1.getChargeForLayeredFile(1)
    c2 =process1.getChargeForLayeredFile(2)
    c3 =process1.getChargeForLayeredFile(3)
    c4 =process1.getChargeForLayeredFile(4)
    c5= process1.getChargeForLayeredFile(5)
    c6 = process1.getChargeForLayeredFile(6)
    c7 =process1.getChargeForLayeredFile(7)
    c8 =process1.getChargeForLayeredFile(8)
    c9 =process1.getChargeForLayeredFile(9)
    c10 =process1.getChargeForLayeredFile(10)
    c11 = process1.getChargeForLayeredFile(11)
    z = Array('i',process1.getZValues()) # z value from code file
    code_file=process1.getCodeFile()
    start_time = time.time()

    generate_structure(working_path)

    print("add c to str took in total %s seconds" % (time.time() - start_time))
