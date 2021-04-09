import os

DATA_DIRECTOR=os.path.dirname(os.path.abspath("__file__"))+'/src/data'
print("Path of the file is "+DATA_DIRECTOR)
path0 = DATA_DIRECTOR+"/import2_mctotal.csv" # 1:0
path1 = DATA_DIRECTOR+"/import2_mc15.csv" # 3:1
path2 = DATA_DIRECTOR+"/import2_mc7.csv" # 2:1
path3 = DATA_DIRECTOR+"/import_mc3.csv" # 1:1
path4 = DATA_DIRECTOR+"/import_mc7.csv" #1:2
path5 = DATA_DIRECTOR+"/import_mc15.csv" #1:3
path6 = DATA_DIRECTOR+"/import_mctotal.csv" #0:1

code_file_path = DATA_DIRECTOR+"/import.csv"
RATIO_PATH_MAP={
    "1:0":path0,
    "3:1":path1,
    "2:1":path2,
    "1:1":path3,
    "1:2":path4,
    "1:3":path5,
    "0:1":path6,
    "code_file":code_file_path
}

